package com.ravunana.manda.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A UnidadeMedida.
 */
@Entity
@Table(name = "core_unidade_medida")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UnidadeMedida implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "nome", nullable = false, unique = true)
    private String nome;

    @NotNull
    @Column(name = "sigla", nullable = false, unique = true)
    private String sigla;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "valor", nullable = false)
    private Double valor;

    @OneToMany(mappedBy = "unidadeConversao")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<UnidadeMedida> unidadeMedidas = new HashSet<>();

    @OneToMany(mappedBy = "unidadeLimite")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<GrupoTributacaoImposto> grupoTributacaoImpostos = new HashSet<>();

    @OneToMany(mappedBy = "unidade")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CompostoProduto> compostoProdutos = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("unidadeMedidas")
    private UnidadeMedida unidadeConversao;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public UnidadeMedida nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSigla() {
        return sigla;
    }

    public UnidadeMedida sigla(String sigla) {
        this.sigla = sigla;
        return this;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public Double getValor() {
        return valor;
    }

    public UnidadeMedida valor(Double valor) {
        this.valor = valor;
        return this;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Set<UnidadeMedida> getUnidadeMedidas() {
        return unidadeMedidas;
    }

    public UnidadeMedida unidadeMedidas(Set<UnidadeMedida> unidadeMedidas) {
        this.unidadeMedidas = unidadeMedidas;
        return this;
    }

    public UnidadeMedida addUnidadeMedida(UnidadeMedida unidadeMedida) {
        this.unidadeMedidas.add(unidadeMedida);
        unidadeMedida.setUnidadeConversao(this);
        return this;
    }

    public UnidadeMedida removeUnidadeMedida(UnidadeMedida unidadeMedida) {
        this.unidadeMedidas.remove(unidadeMedida);
        unidadeMedida.setUnidadeConversao(null);
        return this;
    }

    public void setUnidadeMedidas(Set<UnidadeMedida> unidadeMedidas) {
        this.unidadeMedidas = unidadeMedidas;
    }

    public Set<GrupoTributacaoImposto> getGrupoTributacaoImpostos() {
        return grupoTributacaoImpostos;
    }

    public UnidadeMedida grupoTributacaoImpostos(Set<GrupoTributacaoImposto> grupoTributacaoImpostos) {
        this.grupoTributacaoImpostos = grupoTributacaoImpostos;
        return this;
    }

    public UnidadeMedida addGrupoTributacaoImposto(GrupoTributacaoImposto grupoTributacaoImposto) {
        this.grupoTributacaoImpostos.add(grupoTributacaoImposto);
        grupoTributacaoImposto.setUnidadeLimite(this);
        return this;
    }

    public UnidadeMedida removeGrupoTributacaoImposto(GrupoTributacaoImposto grupoTributacaoImposto) {
        this.grupoTributacaoImpostos.remove(grupoTributacaoImposto);
        grupoTributacaoImposto.setUnidadeLimite(null);
        return this;
    }

    public void setGrupoTributacaoImpostos(Set<GrupoTributacaoImposto> grupoTributacaoImpostos) {
        this.grupoTributacaoImpostos = grupoTributacaoImpostos;
    }

    public Set<CompostoProduto> getCompostoProdutos() {
        return compostoProdutos;
    }

    public UnidadeMedida compostoProdutos(Set<CompostoProduto> compostoProdutos) {
        this.compostoProdutos = compostoProdutos;
        return this;
    }

    public UnidadeMedida addCompostoProduto(CompostoProduto compostoProduto) {
        this.compostoProdutos.add(compostoProduto);
        compostoProduto.setUnidade(this);
        return this;
    }

    public UnidadeMedida removeCompostoProduto(CompostoProduto compostoProduto) {
        this.compostoProdutos.remove(compostoProduto);
        compostoProduto.setUnidade(null);
        return this;
    }

    public void setCompostoProdutos(Set<CompostoProduto> compostoProdutos) {
        this.compostoProdutos = compostoProdutos;
    }

    public UnidadeMedida getUnidadeConversao() {
        return unidadeConversao;
    }

    public UnidadeMedida unidadeConversao(UnidadeMedida unidadeMedida) {
        this.unidadeConversao = unidadeMedida;
        return this;
    }

    public void setUnidadeConversao(UnidadeMedida unidadeMedida) {
        this.unidadeConversao = unidadeMedida;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UnidadeMedida)) {
            return false;
        }
        return id != null && id.equals(((UnidadeMedida) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "UnidadeMedida{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", sigla='" + getSigla() + "'" +
            ", valor=" + getValor() +
            "}";
    }
}
