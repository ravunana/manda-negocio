package com.ravunana.manda.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A ConversaoUnidade.
 */
@Entity
@Table(name = "stk_conversao_unidade")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ConversaoUnidade implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @DecimalMin(value = "1")
    @Column(name = "valor_entrada", nullable = false)
    private Double valorEntrada;

    @NotNull
    @DecimalMin(value = "1")
    @Column(name = "valor_saida", nullable = false)
    private Double valorSaida;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("conversaoUnidades")
    private UnidadeMedida entrada;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("conversaoUnidades")
    private UnidadeMedida saida;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("conversaoUnidades")
    private Produto produto;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getValorEntrada() {
        return valorEntrada;
    }

    public ConversaoUnidade valorEntrada(Double valorEntrada) {
        this.valorEntrada = valorEntrada;
        return this;
    }

    public void setValorEntrada(Double valorEntrada) {
        this.valorEntrada = valorEntrada;
    }

    public Double getValorSaida() {
        return valorSaida;
    }

    public ConversaoUnidade valorSaida(Double valorSaida) {
        this.valorSaida = valorSaida;
        return this;
    }

    public void setValorSaida(Double valorSaida) {
        this.valorSaida = valorSaida;
    }

    public UnidadeMedida getEntrada() {
        return entrada;
    }

    public ConversaoUnidade entrada(UnidadeMedida unidadeMedida) {
        this.entrada = unidadeMedida;
        return this;
    }

    public void setEntrada(UnidadeMedida unidadeMedida) {
        this.entrada = unidadeMedida;
    }

    public UnidadeMedida getSaida() {
        return saida;
    }

    public ConversaoUnidade saida(UnidadeMedida unidadeMedida) {
        this.saida = unidadeMedida;
        return this;
    }

    public void setSaida(UnidadeMedida unidadeMedida) {
        this.saida = unidadeMedida;
    }

    public Produto getProduto() {
        return produto;
    }

    public ConversaoUnidade produto(Produto produto) {
        this.produto = produto;
        return this;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ConversaoUnidade)) {
            return false;
        }
        return id != null && id.equals(((ConversaoUnidade) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ConversaoUnidade{" +
            "id=" + getId() +
            ", valorEntrada=" + getValorEntrada() +
            ", valorSaida=" + getValorSaida() +
            "}";
    }
}
