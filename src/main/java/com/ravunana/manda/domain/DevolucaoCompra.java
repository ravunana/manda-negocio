package com.ravunana.manda.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A DevolucaoCompra.
 */
@Entity
@Table(name = "cmp_devolucao_compra")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DevolucaoCompra implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @DecimalMin(value = "1")
    @Column(name = "quantidade")
    private Double quantidade;

    @DecimalMin(value = "0")
    @Column(name = "valor")
    private Double valor;

    @DecimalMin(value = "0")
    @Column(name = "desconto")
    private Double desconto;

    @Column(name = "data")
    private ZonedDateTime data;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "descricao")
    private String descricao;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private ItemCompra item;

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

    public DevolucaoCompra quantidade(Double quantidade) {
        this.quantidade = quantidade;
        return this;
    }

    public void setQuantidade(Double quantidade) {
        this.quantidade = quantidade;
    }

    public Double getValor() {
        return valor;
    }

    public DevolucaoCompra valor(Double valor) {
        this.valor = valor;
        return this;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Double getDesconto() {
        return desconto;
    }

    public DevolucaoCompra desconto(Double desconto) {
        this.desconto = desconto;
        return this;
    }

    public void setDesconto(Double desconto) {
        this.desconto = desconto;
    }

    public ZonedDateTime getData() {
        return data;
    }

    public DevolucaoCompra data(ZonedDateTime data) {
        this.data = data;
        return this;
    }

    public void setData(ZonedDateTime data) {
        this.data = data;
    }

    public String getDescricao() {
        return descricao;
    }

    public DevolucaoCompra descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public ItemCompra getItem() {
        return item;
    }

    public DevolucaoCompra item(ItemCompra itemCompra) {
        this.item = itemCompra;
        return this;
    }

    public void setItem(ItemCompra itemCompra) {
        this.item = itemCompra;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DevolucaoCompra)) {
            return false;
        }
        return id != null && id.equals(((DevolucaoCompra) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "DevolucaoCompra{" +
            "id=" + getId() +
            ", quantidade=" + getQuantidade() +
            ", valor=" + getValor() +
            ", desconto=" + getDesconto() +
            ", data='" + getData() + "'" +
            ", descricao='" + getDescricao() + "'" +
            "}";
    }
}
