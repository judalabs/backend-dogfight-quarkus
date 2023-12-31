package org.judalabs;

import java.time.Duration;
import java.util.List;
import java.util.UUID;

import io.quarkus.hibernate.reactive.panache.common.WithSession;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class PessoaService {

    @Inject
    Cacheable cacheService;

    @Inject
    PessoaRepository pessoaRepository;

    @WithSession
    public Uni<PessoaDTO> criar(PessoaDTO pessoa) {
        return cacheService.existePorApelido(pessoa.getApelido())
            .onItem().ifNotNull().failWith(UnprocessableEntityException::new)
            .flatMap(e -> pessoaRepository.persistAndFlush(PessoaDTO.toEntity(pessoa)))
                .map( entity -> {
                    cacheService.atualizacaoDeCacheListener(PessoaDTO.toDto(entity));
                    return PessoaDTO.toDto(entity);
                });
    }

    @WithSession
    public Uni<PessoaDTO> buscarPorId(UUID id) {
        return cacheService.existePorId(id)
                .onItem().ifNull()
                .switchTo(Pessoa.findByUuid(id)
                        .onItem().ifNull()
                        .failWith(NotFoundException::new)
                        .map(PessoaDTO::toDto));
    }

    @WithSession
    public Uni<List<PessoaDTO>> buscarPorTermo(String termo) {
        return Pessoa.listWhereLike(termo);
    }

    @WithSession
    public Uni<Long> contar() {
        try {
            Thread.sleep(5000);
            return Pessoa.count();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
