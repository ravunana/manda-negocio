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
 * Criteria class for the {@link com.ravunana.manda.domain.ContaCredito} entity. This class is used
 * in {@link com.ravunana.manda.web.rest.ContaCreditoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /conta-creditos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ContaCreditoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private BigDecimalFilter valor;

    private ZonedDateTimeFilter data;

    private LongFilter contaCreditarId;

    private LongFilter lancamentoCreditoId;

    public ContaCreditoCriteria(){
    }

    public ContaCreditoCriteria(ContaCreditoCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.valor = other.valor == null ? null : other.valor.copy();
        this.data = other.data == null ? null : other.data.copy();
        this.contaCreditarId = other.contaCreditarId == null ? null : other.contaCreditarId.copy();
        this.lancamentoCreditoId = other.lancamentoCreditoId == null ? null : other.lancamentoCreditoId.copy();
    }

    @Override
    public ContaCreditoCriteria copy() {
        return new ContaCreditoCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public BigDecimalFilter getValor() {
        return valor;
    }

    public void setValor(BigDecimalFilter valor) {
        this.valor = valor;
    }

    public ZonedDateTimeFilter getData() {
        return data;
    }

    public void setData(ZonedDateTimeFilter data) {
        this.data = data;
    }

    public LongFilter getContaCreditarId() {
        return contaCreditarId;
    }

    public void setContaCreditarId(LongFilter contaCreditarId) {
        this.contaCreditarId = contaCreditarId;
    }

    public LongFilter getLancamentoCreditoId() {
        return lancamentoCreditoId;
    }

    public void setLancamentoCreditoId(LongFilter lancamentoCreditoId) {
        this.lancamentoCreditoId = lancamentoCreditoId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ContaCreditoCriteria that = (ContaCreditoCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(valor, that.valor) &&
            Objects.equals(data, that.data) &&
            Objects.equals(contaCreditarId, that.contaCreditarId) &&
            Objects.equals(lancamentoCreditoId, that.lancamentoCreditoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        valor,
        data,
        contaCreditarId,
        lancamentoCreditoId
        );
    }

    @Override
    public String toString() {
        return "ContaCreditoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (valor != null ? "valor=" + valor + ", " : "") +
                (data != null ? "data=" + data + ", " : "") +
                (contaCreditarId != null ? "contaCreditarId=" + contaCreditarId + ", " : "") +
                (lancamentoCreditoId != null ? "lancamentoCreditoId=" + lancamentoCreditoId + ", " : "") +
            "}";
    }

}
