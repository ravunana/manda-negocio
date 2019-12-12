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
import io.github.jhipster.service.filter.ZonedDateTimeFilter;

/**
 * Criteria class for the {@link com.ravunana.manda.domain.DevolucaoVenda} entity. This class is used
 * in {@link com.ravunana.manda.web.rest.DevolucaoVendaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /devolucao-vendas?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DevolucaoVendaCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private DoubleFilter quantidade;

    private DoubleFilter valor;

    private DoubleFilter desconto;

    private ZonedDateTimeFilter data;

    private LongFilter itemId;

    public DevolucaoVendaCriteria(){
    }

    public DevolucaoVendaCriteria(DevolucaoVendaCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.quantidade = other.quantidade == null ? null : other.quantidade.copy();
        this.valor = other.valor == null ? null : other.valor.copy();
        this.desconto = other.desconto == null ? null : other.desconto.copy();
        this.data = other.data == null ? null : other.data.copy();
        this.itemId = other.itemId == null ? null : other.itemId.copy();
    }

    @Override
    public DevolucaoVendaCriteria copy() {
        return new DevolucaoVendaCriteria(this);
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

    public DoubleFilter getValor() {
        return valor;
    }

    public void setValor(DoubleFilter valor) {
        this.valor = valor;
    }

    public DoubleFilter getDesconto() {
        return desconto;
    }

    public void setDesconto(DoubleFilter desconto) {
        this.desconto = desconto;
    }

    public ZonedDateTimeFilter getData() {
        return data;
    }

    public void setData(ZonedDateTimeFilter data) {
        this.data = data;
    }

    public LongFilter getItemId() {
        return itemId;
    }

    public void setItemId(LongFilter itemId) {
        this.itemId = itemId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final DevolucaoVendaCriteria that = (DevolucaoVendaCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(quantidade, that.quantidade) &&
            Objects.equals(valor, that.valor) &&
            Objects.equals(desconto, that.desconto) &&
            Objects.equals(data, that.data) &&
            Objects.equals(itemId, that.itemId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        quantidade,
        valor,
        desconto,
        data,
        itemId
        );
    }

    @Override
    public String toString() {
        return "DevolucaoVendaCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (quantidade != null ? "quantidade=" + quantidade + ", " : "") +
                (valor != null ? "valor=" + valor + ", " : "") +
                (desconto != null ? "desconto=" + desconto + ", " : "") +
                (data != null ? "data=" + data + ", " : "") +
                (itemId != null ? "itemId=" + itemId + ", " : "") +
            "}";
    }

}
