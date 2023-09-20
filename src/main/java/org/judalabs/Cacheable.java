package org.judalabs;

import java.util.UUID;

import io.smallrye.mutiny.Uni;

public interface Cacheable {
    Uni<Boolean> existePorApelido(String apelido);

    Uni<PessoaDTO> existePorId(UUID id);

    void atualizacaoDeCacheListener(PessoaDTO dto);
}
