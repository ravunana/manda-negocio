package com.ravunana.manda.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

/**
 * A ItemVenda.
 */
@Entity
@Table(name = "vnd_item_venda")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ItemVenda implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @DecimalMin(value = "1")
    @Column(name = "quantidade")
    private Double quantidade;

    @DecimalMin(value = "0")
    @Column(name = "valor", precision = 21, scale = 2)
    private BigDecimal valor;

    @DecimalMin(value = "0")
    @DecimalMax(value = "100")
    @Column(name = "desconto")
    private Double desconto;

    @Column(name = "data")
    private ZonedDateTime data;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("itemVendas")
    private Venda venda;

    @ManyToOne
    @JsonIgnoreProperties("itemVendas")
    private Produto produto;

    @ManyToOne
    @JsonIgnoreProperties("itemVendas")
    private FluxoDocumento status;

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

    public ItemVenda quantidade(Double quantidade) {
        this.quantidade = quantidade;
        return this;
    }

    public void setQuantidade(Double quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public ItemVenda valor(BigDecimal valor) {
        this.valor = valor;
        return this;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Double getDesconto() {
        return desconto;
    }

    public ItemVenda desconto(Double desconto) {
        this.desconto = desconto;
        return this;
    }

    public void setDesconto(Double desconto) {
        this.desconto = desconto;
    }

    public ZonedDateTime getData() {
        return data;
    }

    public ItemVenda data(ZonedDateTime data) {
        this.data = data;
        return this;
    }

    public void setData(ZonedDateTime data) {
        this.data = data;
    }

    public Venda getVenda() {
        return venda;
    }

    public ItemVenda venda(Venda venda) {
        this.venda = venda;
        return this;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }

    public Produto getProduto() {
        return produto;
    }

    public ItemVenda produto(Produto produto) {
        this.produto = produto;
        return this;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public FluxoDocumento getStatus() {
        return status;
    }

    public ItemVenda status(FluxoDocumento fluxoDocumento) {
        this.status = fluxoDocumento;
        return this;
    }

    public void setStatus(FluxoDocumento fluxoDocumento) {
        this.status = fluxoDocumento;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ItemVenda)) {
            return false;
        }
        return id != null && id.equals(((ItemVenda) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ItemVenda{" +
            "id=" + getId() +
            ", quantidade=" + getQuantidade() +
            ", valor=" + getValor() +
            ", desconto=" + getDesconto() +
            ", data='" + getData() + "'" +
            "}";
    }
}
