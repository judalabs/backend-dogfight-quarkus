package org.judalabs;

import java.util.UUID;

import io.quarkus.redis.client.RedisClientName;
import io.quarkus.redis.datasource.ReactiveRedisDataSource;
import io.quarkus.redis.datasource.value.ReactiveValueCommands;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RedisService implements Cacheable {

    ReactiveValueCommands<String, Boolean> redisExistsApelido;
    ReactiveValueCommands<String, PessoaDTO> redisFindOne;

    public RedisService(@RedisClientName("redis-1") ReactiveRedisDataSource redisDataSource) {
        this.redisExistsApelido = redisDataSource.value(Boolean.class);
        this.redisFindOne = redisDataSource.value(PessoaDTO.class);
    }

    @Override
    public Uni<Boolean> existePorApelido(String apelido) {
        return redisExistsApelido.get(apelido);
    }

    @Override
    public Uni<PessoaDTO> existePorId(UUID id) {
        return redisFindOne.get(id.toString());
    }

    public void atualizacaoDeCacheListener(PessoaDTO pessoaDTO) {
        redisExistsApelido.set(pessoaDTO.getApelido(), true)
                .subscribe().with(c -> redisFindOne.set(pessoaDTO.getId().toString(), pessoaDTO));
    }
}
