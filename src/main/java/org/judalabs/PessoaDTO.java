package org.judalabs;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.judalabs.validator.PessoaValida;

import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.validation.constraints.NotEmpty;

@PessoaValida
@RegisterForReflection
public class PessoaDTO implements Serializable {

    private UUID id;
    private String nome;
    private String apelido;
    private LocalDate nascimento;

    private List<String> stack;

    public PessoaDTO() {
    }

    public PessoaDTO(UUID id, String nome, String apelido, LocalDate nascimento, List<String> stack) {
        this.id = id;
        this.nome = nome;
        this.apelido = apelido;
        this.nascimento = nascimento;
        this.stack = stack;
    }

    public PessoaDTO(UUID id, String nome, String apelido, LocalDate nascimento, String stack) {
        this.id = id;
        this.nome = nome;
        this.apelido = apelido;
        this.nascimento = nascimento;
        this.stack = null;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public LocalDate getNascimento() {
        return nascimento;
    }

    public void setNascimento(LocalDate nascimento) {
        this.nascimento = nascimento;
    }

    public List<String> getStack() {
        return stack;
    }

    public void setStack(List<String> stack) {
        this.stack = stack;
    }

    public static Pessoa toEntity(PessoaDTO pessoa) {
        var stack = pessoa.stack != null ? String.join(",", pessoa.stack) : null;
        final String buscaCompleta = String.format("%s %s %s", pessoa.getNome(), pessoa.getApelido(), stack);
        return new Pessoa(pessoa.nome, pessoa.apelido, pessoa.nascimento, stack, buscaCompleta);
    }

    public static PessoaDTO toDto(Pessoa entity) {
        List<String> stacks = entity.stack != null ? Arrays.stream(entity.stack.split(",")).toList() : null;
        return new PessoaDTO(entity.id, entity.nome, entity.apelido, entity.nascimento, stacks);
    }
}
