package com.ravunana.manda.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A FormaLiquidacao.
 */
@Entity
@Table(name = "fnc_forma_liquidacao")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class FormaLiquidacao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "nome", nullable = false, unique = true)
    private String nome;

    @NotNull
    @DecimalMin(value = "0")
    @DecimalMax(value = "100")
    @Column(name = "juro", nullable = false)
    private Double juro;

    @NotNull
    @Min(value = 0)
    @Column(name = "prazo_liquidacao", nullable = false)
    private Integer prazoLiquidacao;

    @NotNull
    @Min(value = 1)
    @Column(name = "quantidade", nullable = false)
    private Integer quantidade;

    @Column(name = "icon")
    private String icon;

    @OneToMany(mappedBy = "formaLiquidacao")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<LancamentoFinanceiro> lancamentoFinanceiros = new HashSet<>();

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

    public FormaLiquidacao nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getJuro() {
        return juro;
    }

    public FormaLiquidacao juro(Double juro) {
        this.juro = juro;
        return this;
    }

    public void setJuro(Double juro) {
        this.juro = juro;
    }

    public Integer getPrazoLiquidacao() {
        return prazoLiquidacao;
    }

    public FormaLiquidacao prazoLiquidacao(Integer prazoLiquidacao) {
        this.prazoLiquidacao = prazoLiquidacao;
        return this;
    }

    public void setPrazoLiquidacao(Integer prazoLiquidacao) {
        this.prazoLiquidacao = prazoLiquidacao;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public FormaLiquidacao quantidade(Integer quantidade) {
        this.quantidade = quantidade;
        return this;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public String getIcon() {
        return icon;
    }

    public FormaLiquidacao icon(String icon) {
        this.icon = icon;
        return this;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Set<LancamentoFinanceiro> getLancamentoFinanceiros() {
        return lancamentoFinanceiros;
    }

    public FormaLiquidacao lancamentoFinanceiros(Set<LancamentoFinanceiro> lancamentoFinanceiros) {
        this.lancamentoFinanceiros = lancamentoFinanceiros;
        return this;
    }

    public FormaLiquidacao addLancamentoFinanceiro(LancamentoFinanceiro lancamentoFinanceiro) {
        this.lancamentoFinanceiros.add(lancamentoFinanceiro);
        lancamentoFinanceiro.setFormaLiquidacao(this);
        return this;
    }

    public FormaLiquidacao removeLancamentoFinanceiro(LancamentoFinanceiro lancamentoFinanceiro) {
        this.lancamentoFinanceiros.remove(lancamentoFinanceiro);
        lancamentoFinanceiro.setFormaLiquidacao(null);
        return this;
    }

    public void setLancamentoFinanceiros(Set<LancamentoFinanceiro> lancamentoFinanceiros) {
        this.lancamentoFinanceiros = lancamentoFinanceiros;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FormaLiquidacao)) {
            return false;
        }
        return id != null && id.equals(((FormaLiquidacao) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "FormaLiquidacao{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", juro=" + getJuro() +
            ", prazoLiquidacao=" + getPrazoLiquidacao() +
            ", quantidade=" + getQuantidade() +
            ", icon='" + getIcon() + "'" +
            "}";
    }
}
