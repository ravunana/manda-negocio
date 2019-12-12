package com.ravunana.manda.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A CompostoProduto.
 */
@Entity
@Table(name = "stk_composto_produto")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CompostoProduto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @DecimalMin(value = "1")
    @Column(name = "quantidade", nullable = false)
    private Double quantidade;

    @Column(name = "valor", precision = 21, scale = 2)
    private BigDecimal valor;

    @Column(name = "permanente")
    private Boolean permanente;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("compostoProdutos")
    private Produto produto;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("compostoProdutos")
    private UnidadeMedida unidade;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("compostoProdutos")
    private Produto composto;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getQuantidade() {
        return quantidade;
    }

    public CompostoProduto quantidade(Double quantidade) {
        this.quantidade = quantidade;
        return this;
    }

    public void setQuantidade(Double quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public CompostoProduto valor(BigDecimal valor) {
        this.valor = valor;
        return this;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Boolean isPermanente() {
        return permanente;
    }

    public CompostoProduto permanente(Boolean permanente) {
        this.permanente = permanente;
        return this;
    }

    public void setPermanente(Boolean permanente) {
        this.permanente = permanente;
    }

    public Produto getProduto() {
        return produto;
    }

    public CompostoProduto produto(Produto produto) {
        this.produto = produto;
        return this;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public UnidadeMedida getUnidade() {
        return unidade;
    }

    public CompostoProduto unidade(UnidadeMedida unidadeMedida) {
        this.unidade = unidadeMedida;
        return this;
    }

    public void setUnidade(UnidadeMedida unidadeMedida) {
        this.unidade = unidadeMedida;
    }

    public Produto getComposto() {
        return composto;
    }

    public CompostoProduto composto(Produto produto) {
        this.composto = produto;
        return this;
    }

    public void setComposto(Produto produto) {
        this.composto = produto;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CompostoProduto)) {
            return false;
        }
        return id != null && id.equals(((CompostoProduto) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CompostoProduto{" +
            "id=" + getId() +
            ", quantidade=" + getQuantidade() +
            ", valor=" + getValor() +
            ", permanente='" + isPermanente() + "'" +
            "}";
    }
}
