package com.ravunana.manda.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.BigDecimalFilter;
import io.github.jhipster.service.filter.ZonedDateTimeFilter;

/**
 * Criteria class for the {@link com.ravunana.manda.domain.ItemCompra} entity. This class is used
 * in {@link com.ravunana.manda.web.rest.ItemCompraResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /item-compras?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ItemCompraCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private DoubleFilter quantidade;

    private DoubleFilter desconto;

    private ZonedDateTimeFilter dataSolicitacao;

    private ZonedDateTimeFilter dataEntrega;

    private BigDecimalFilter valor;

    private LongFilter solicitanteId;

    private LongFilter compraId;

    private LongFilter produtoId;

    private LongFilter fornecedorId;

    private LongFilter statusId;

    public ItemCompraCriteria(){
    }

    public ItemCompraCriteria(ItemCompraCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.quantidade = other.quantidade == null ? null : other.quantidade.copy();
        this.desconto = other.desconto == null ? null : other.desconto.copy();
        this.dataSolicitacao = other.dataSolicitacao == null ? null : other.dataSolicitacao.copy();
        this.dataEntrega = other.dataEntrega == null ? null : other.dataEntrega.copy();
        this.valor = other.valor == null ? null : other.valor.copy();
        this.solicitanteId = other.solicitanteId == null ? null : other.solicitanteId.copy();
        this.compraId = other.compraId == null ? null : other.compraId.copy();
        this.produtoId = other.produtoId == null ? null : other.produtoId.copy();
        this.fornecedorId = other.fornecedorId == null ? null : other.fornecedorId.copy();
        this.statusId = other.statusId == null ? null : other.statusId.copy();
    }

    @Override
    public ItemCompraCriteria copy() {
        return new ItemCompraCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public DoubleFilter getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(DoubleFilter quantidade) {
        this.quantidade = quantidade;
    }

    public DoubleFilter getDesconto() {
        return desconto;
    }

    public void setDesconto(DoubleFilter desconto) {
        this.desconto = desconto;
    }

    public ZonedDateTimeFilter getDataSolicitacao() {
        return dataSolicitacao;
    }

    public void setDataSolicitacao(ZonedDateTimeFilter dataSolicitacao) {
        this.dataSolicitacao = dataSolicitacao;
    }

    public ZonedDateTimeFilter getDataEntrega() {
        return dataEntrega;
    }

    public void setDataEntrega(ZonedDateTimeFilter dataEntrega) {
        this.dataEntrega = dataEntrega;
    }

    public BigDecimalFilter getValor() {
        return valor;
    }

    public void setValor(BigDecimalFilter valor) {
        this.valor = valor;
    }

    public LongFilter getSolicitanteId() {
        return solicitanteId;
    }

    public void setSolicitanteId(LongFilter solicitanteId) {
        this.solicitanteId = solicitanteId;
    }

    public LongFilter getCompraId() {
        return compraId;
    }

    public void setCompraId(LongFilter compraId) {
        this.compraId = compraId;
    }

    public LongFilter getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(LongFilter produtoId) {
        this.produtoId = produtoId;
    }

    public LongFilter getFornecedorId() {
        return fornecedorId;
    }

    public void setFornecedorId(LongFilter fornecedorId) {
        this.fornecedorId = fornecedorId;
    }

    public LongFilter getStatusId() {
        return statusId;
    }

    public void setStatusId(LongFilter statusId) {
        this.statusId = statusId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ItemCompraCriteria that = (ItemCompraCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(quantidade, that.quantidade) &&
            Objects.equals(desconto, that.desconto) &&
            Objects.equals(dataSolicitacao, that.dataSolicitacao) &&
            Objects.equals(dataEntrega, that.dataEntrega) &&
            Objects.equals(valor, that.valor) &&
            Objects.equals(solicitanteId, that.solicitanteId) &&
            Objects.equals(compraId, that.compraId) &&
            Objects.equals(produtoId, that.produtoId) &&
            Objects.equals(fornecedorId, that.fornecedorId) &&
            Objects.equals(statusId, that.statusId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        quantidade,
        desconto,
        dataSolicitacao,
        dataEntrega,
        valor,
        solicitanteId,
        compraId,
        produtoId,
        fornecedorId,
        statusId
        );
    }

    @Override
    public String toString() {
        return "ItemCompraCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (quantidade != null ? "quantidade=" + quantidade + ", " : "") +
                (desconto != null ? "desconto=" + desconto + ", " : "") +
                (dataSolicitacao != null ? "dataSolicitacao=" + dataSolicitacao + ", " : "") +
                (dataEntrega != null ? "dataEntrega=" + dataEntrega + ", " : "") +
                (valor != null ? "valor=" + valor + ", " : "") +
                (solicitanteId != null ? "solicitanteId=" + solicitanteId + ", " : "") +
                (compraId != null ? "compraId=" + compraId + ", " : "") +
                (produtoId != null ? "produtoId=" + produtoId + ", " : "") +
                (fornecedorId != null ? "fornecedorId=" + fornecedorId + ", " : "") +
                (statusId != null ? "statusId=" + statusId + ", " : "") +
            "}";
    }

}
