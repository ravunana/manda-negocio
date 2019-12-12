package com.ravunana.manda.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

/**
 * A ItemCompra.
 */
@Entity
@Table(name = "cmp_item_compra")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ItemCompra implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @DecimalMin(value = "1")
    @Column(name = "quantidade")
    private Double quantidade;

    @DecimalMin(value = "0")
    @DecimalMax(value = "100")
    @Column(name = "desconto")
    private Double desconto;

    @Column(name = "data_solicitacao")
    private ZonedDateTime dataSolicitacao;

    @Column(name = "data_entrega")
    private ZonedDateTime dataEntrega;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "descricao")
    private String descricao;

    @Column(name = "valor", precision = 21, scale = 2)
    private BigDecimal valor;

    @ManyToOne
    @JsonIgnoreProperties("itemCompras")
    private User solicitante;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("itemCompras")
    private Compra compra;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("itemCompras")
    private Produto produto;

    @ManyToOne
    @JsonIgnoreProperties("itemCompras")
    private Fornecedor fornecedor;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("itemCompras")
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

    public ItemCompra quantidade(Double quantidade) {
        this.quantidade = quantidade;
        return this;
    }

    public void setQuantidade(Double quantidade) {
        this.quantidade = quantidade;
    }

    public Double getDesconto() {
        return desconto;
    }

    public ItemCompra desconto(Double desconto) {
        this.desconto = desconto;
        return this;
    }

    public void setDesconto(Double desconto) {
        this.desconto = desconto;
    }

    public ZonedDateTime getDataSolicitacao() {
        return dataSolicitacao;
    }

    public ItemCompra dataSolicitacao(ZonedDateTime dataSolicitacao) {
        this.dataSolicitacao = dataSolicitacao;
        return this;
    }

    public void setDataSolicitacao(ZonedDateTime dataSolicitacao) {
        this.dataSolicitacao = dataSolicitacao;
    }

    public ZonedDateTime getDataEntrega() {
        return dataEntrega;
    }

    public ItemCompra dataEntrega(ZonedDateTime dataEntrega) {
        this.dataEntrega = dataEntrega;
        return this;
    }

    public void setDataEntrega(ZonedDateTime dataEntrega) {
        this.dataEntrega = dataEntrega;
    }

    public String getDescricao() {
        return descricao;
    }

    public ItemCompra descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public ItemCompra valor(BigDecimal valor) {
        this.valor = valor;
        return this;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public User getSolicitante() {
        return solicitante;
    }

    public ItemCompra solicitante(User user) {
        this.solicitante = user;
        return this;
    }

    public void setSolicitante(User user) {
        this.solicitante = user;
    }

    public Compra getCompra() {
        return compra;
    }

    public ItemCompra compra(Compra compra) {
        this.compra = compra;
        return this;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
    }

    public Produto getProduto() {
        return produto;
    }

    public ItemCompra produto(Produto produto) {
        this.produto = produto;
        return this;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public ItemCompra fornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
        return this;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public FluxoDocumento getStatus() {
        return status;
    }

    public ItemCompra status(FluxoDocumento fluxoDocumento) {
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
        if (!(o instanceof ItemCompra)) {
            return false;
        }
        return id != null && id.equals(((ItemCompra) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ItemCompra{" +
            "id=" + getId() +
            ", quantidade=" + getQuantidade() +
            ", desconto=" + getDesconto() +
            ", dataSolicitacao='" + getDataSolicitacao() + "'" +
            ", dataEntrega='" + getDataEntrega() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", valor=" + getValor() +
            "}";
    }
}
