package com.ravunana.manda.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A GrupoTributacaoImposto.
 */
@Entity
@Table(name = "tbr_grupo_tributacao")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class GrupoTributacaoImposto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "valor", nullable = false)
    private Double valor;

    @Column(name = "de")
    private Double de;

    @Column(name = "ate")
    private Double ate;

    @Min(value = 1)
    @Max(value = 31)
    @Column(name = "limite_liquidacao")
    private Integer limiteLiquidacao;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("grupoTributacaoImpostos")
    private Imposto imposto;

    @ManyToOne
    @JsonIgnoreProperties("grupoTributacaoImpostos")
    private UnidadeMedida unidadeLimite;

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

    public GrupoTributacaoImposto nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getValor() {
        return valor;
    }

    public GrupoTributacaoImposto valor(Double valor) {
        this.valor = valor;
        return this;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Double getDe() {
        return de;
    }

    public GrupoTributacaoImposto de(Double de) {
        this.de = de;
        return this;
    }

    public void setDe(Double de) {
        this.de = de;
    }

    public Double getAte() {
        return ate;
    }

    public GrupoTributacaoImposto ate(Double ate) {
        this.ate = ate;
        return this;
    }

    public void setAte(Double ate) {
        this.ate = ate;
    }

    public Integer getLimiteLiquidacao() {
        return limiteLiquidacao;
    }

    public GrupoTributacaoImposto limiteLiquidacao(Integer limiteLiquidacao) {
        this.limiteLiquidacao = limiteLiquidacao;
        return this;
    }

    public void setLimiteLiquidacao(Integer limiteLiquidacao) {
        this.limiteLiquidacao = limiteLiquidacao;
    }

    public Imposto getImposto() {
        return imposto;
    }

    public GrupoTributacaoImposto imposto(Imposto imposto) {
        this.imposto = imposto;
        return this;
    }

    public void setImposto(Imposto imposto) {
        this.imposto = imposto;
    }

    public UnidadeMedida getUnidadeLimite() {
        return unidadeLimite;
    }

    public GrupoTributacaoImposto unidadeLimite(UnidadeMedida unidadeMedida) {
        this.unidadeLimite = unidadeMedida;
        return this;
    }

    public void setUnidadeLimite(UnidadeMedida unidadeMedida) {
        this.unidadeLimite = unidadeMedida;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GrupoTributacaoImposto)) {
            return false;
        }
        return id != null && id.equals(((GrupoTributacaoImposto) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "GrupoTributacaoImposto{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", valor=" + getValor() +
            ", de=" + getDe() +
            ", ate=" + getAte() +
            ", limiteLiquidacao=" + getLimiteLiquidacao() +
            "}";
    }
}
