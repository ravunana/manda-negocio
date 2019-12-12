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
 * Criteria class for the {@link com.ravunana.manda.domain.ContaDebito} entity. This class is used
 * in {@link com.ravunana.manda.web.rest.ContaDebitoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /conta-debitos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ContaDebitoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private BigDecimalFilter valor;

    private ZonedDateTimeFilter data;

    private LongFilter contaDebitarId;

    private LongFilter lancamentoDebitoId;

    public ContaDebitoCriteria(){
    }

    public ContaDebitoCriteria(ContaDebitoCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.valor = other.valor == null ? null : other.valor.copy();
        this.data = other.data == null ? null : other.data.copy();
        this.contaDebitarId = other.contaDebitarId == null ? null : other.contaDebitarId.copy();
        this.lancamentoDebitoId = other.lancamentoDebitoId == null ? null : other.lancamentoDebitoId.copy();
    }

    @Override
    public ContaDebitoCriteria copy() {
        return new ContaDebitoCriteria(this);
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

    public LongFilter getContaDebitarId() {
        return contaDebitarId;
    }

    public void setContaDebitarId(LongFilter contaDebitarId) {
        this.contaDebitarId = contaDebitarId;
    }

    public LongFilter getLancamentoDebitoId() {
        return lancamentoDebitoId;
    }

    public void setLancamentoDebitoId(LongFilter lancamentoDebitoId) {
        this.lancamentoDebitoId = lancamentoDebitoId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ContaDebitoCriteria that = (ContaDebitoCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(valor, that.valor) &&
            Objects.equals(data, that.data) &&
            Objects.equals(contaDebitarId, that.contaDebitarId) &&
            Objects.equals(lancamentoDebitoId, that.lancamentoDebitoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        valor,
        data,
        contaDebitarId,
        lancamentoDebitoId
        );
    }

    @Override
    public String toString() {
        return "ContaDebitoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (valor != null ? "valor=" + valor + ", " : "") +
                (data != null ? "data=" + data + ", " : "") +
                (contaDebitarId != null ? "contaDebitarId=" + contaDebitarId + ", " : "") +
                (lancamentoDebitoId != null ? "lancamentoDebitoId=" + lancamentoDebitoId + ", " : "") +
            "}";
    }

}
