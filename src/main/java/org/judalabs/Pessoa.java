package org.judalabs;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import io.quarkus.panache.common.Page;
import io.smallrye.mutiny.Uni;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "pessoa")
public class Pessoa extends PanacheEntityBase {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    public UUID id;

    @Column(name = "nome", length = 100, nullable = false)
    public String nome;

    @Column(name = "apelido", length = 32, nullable = false)
    public String apelido;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "nascimento", nullable = false)
    public LocalDate nascimento;

    @Column(name = "stack")
    public String stack;

    @Column(name = "busca_completa")
    public String buscaCompleta;

    public Pessoa() {
    }

    public Pessoa(String nome, String apelido, LocalDate nascimento, String stack, String buscaCompleta) {
        this.nome = nome;
        this.apelido = apelido;
        this.nascimento = nascimento;
        this.stack = stack;
        this.buscaCompleta = buscaCompleta;
    }

    public static Uni<Pessoa> findByUuid(UUID id) {
        return find("id", id).firstResult();
    }

    public static Uni<List<PessoaDTO>> listWhereLike(String like) {
        String searchInput = "%" + like + "%";

        return Pessoa.find("buscaCompleta like ?1", searchInput)
                .page(Page.ofSize(50))
                .project(PessoaDTO.class)
                .list();
    }
}
