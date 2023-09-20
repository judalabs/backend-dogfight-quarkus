package org.judalabs;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import org.judalabs.validator.PessoaValida;

import io.quarkus.runtime.util.StringUtil;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

@Path("/")
public class PessoaController {

    @Inject
    PessoaService pessoaService;

    @Path("/pessoas")
    @POST
    public Uni<Response> criar(@PessoaValida PessoaDTO pessoaDTO) {
        validate422(pessoaDTO);

        return pessoaService.criar(pessoaDTO)
                .map(pessoa -> {
                    final URI uri = getUri(pessoa);
                    return Response.created(uri).entity(pessoa).build();
                });
    }

    @GET
    @Path("/pessoas/{id}")
    public Uni<PessoaDTO> buscarPorId(@PathParam("id") UUID id) {
        return pessoaService.buscarPorId(id);
    }

    @GET
    @Path("/pessoas")
    public Uni<List<PessoaDTO>> buscarPorTermo(@QueryParam(value = "t") String termo) {
        if(StringUtil.isNullOrEmpty(termo)) throw new BadRequestException();
        return pessoaService.buscarPorTermo(termo);
    }

    @GET
    @Path("/contagem-pessoas")
    public Uni<Long> countPeople() {
        return pessoaService.contar();
    }

    private URI getUri(PessoaDTO salvo) {
        return URI.create(String.format("/pessoas/%s", salvo.getId()));
    }

    public void validate422(PessoaDTO pessoaDTO) {
        if(StringUtil.isNullOrEmpty(pessoaDTO.getApelido()) || StringUtil.isNullOrEmpty(pessoaDTO.getNome()))
            throw new UnprocessableEntityException();
        if(pessoaDTO.getApelido().length() > 32 || pessoaDTO.getNome().length() > 100)
            throw new UnprocessableEntityException();
    }
}
