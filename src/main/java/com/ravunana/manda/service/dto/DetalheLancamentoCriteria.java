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
import io.github.jhipster.service.filter.LocalDateFilter;
import io.github.jhipster.service.filter.ZonedDateTimeFilter;

/**
 * Criteria class for the {@link com.ravunana.manda.domain.DetalheLancamento} entity. This class is used
 * in {@link com.ravunana.manda.web.rest.DetalheLancamentoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /detalhe-lancamentos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DetalheLancamentoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private BigDecimalFilter valor;

    private BigDecimalFilter desconto;

    private DoubleFilter juro;

    private ZonedDateTimeFilter data;

    private BooleanFilter retencaoFonte;

    private LocalDateFilter dataVencimento;

    private BooleanFilter liquidado;

    private LongFilter retencaoFonteId;

    private LongFilter utilizadorId;

    private LongFilter lancamentoFinanceiroId;

    private LongFilter metodoLiquidacaoId;

    private LongFilter moedaId;

    private LongFilter coordenadaId;

    public DetalheLancamentoCriteria(){
    }

    public DetalheLancamentoCriteria(DetalheLancamentoCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.valor = other.valor == null ? null : other.valor.copy();
        this.desconto = other.desconto == null ? null : other.desconto.copy();
        this.juro = other.juro == null ? null : other.juro.copy();
        this.data = other.data == null ? null : other.data.copy();
        this.retencaoFonte = other.retencaoFonte == null ? null : other.retencaoFonte.copy();
        this.dataVencimento = other.dataVencimento == null ? null : other.dataVencimento.copy();
        this.liquidado = other.liquidado == null ? null : other.liquidado.copy();
        this.retencaoFonteId = other.retencaoFonteId == null ? null : other.retencaoFonteId.copy();
        this.utilizadorId = other.utilizadorId == null ? null : other.utilizadorId.copy();
        this.lancamentoFinanceiroId = other.lancamentoFinanceiroId == null ? null : other.lancamentoFinanceiroId.copy();
        this.metodoLiquidacaoId = other.metodoLiquidacaoId == null ? null : other.metodoLiquidacaoId.copy();
        this.moedaId = other.moedaId == null ? null : other.moedaId.copy();
        this.coordenadaId = other.coordenadaId == null ? null : other.coordenadaId.copy();
    }

    @Override
    public DetalheLancamentoCriteria copy() {
        return new DetalheLancamentoCriteria(this);
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

    public BigDecimalFilter getDesconto() {
        return desconto;
    }

    public void setDesconto(BigDecimalFilter desconto) {
        this.desconto = desconto;
    }

    public DoubleFilter getJuro() {
        return juro;
    }

    public void setJuro(DoubleFilter juro) {
        this.juro = juro;
    }

    public ZonedDateTimeFilter getData() {
        return data;
    }

    public void setData(ZonedDateTimeFilter data) {
        this.data = data;
    }

    public BooleanFilter getRetencaoFonte() {
        return retencaoFonte;
    }

    public void setRetencaoFonte(BooleanFilter retencaoFonte) {
        this.retencaoFonte = retencaoFonte;
    }

    public LocalDateFilter getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(LocalDateFilter dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public BooleanFilter getLiquidado() {
        return liquidado;
    }

    public void setLiquidado(BooleanFilter liquidado) {
        this.liquidado = liquidado;
    }

    public LongFilter getRetencaoFonteId() {
        return retencaoFonteId;
    }

    public void setRetencaoFonteId(LongFilter retencaoFonteId) {
        this.retencaoFonteId = retencaoFonteId;
    }

    public LongFilter getUtilizadorId() {
        return utilizadorId;
    }

    public void setUtilizadorId(LongFilter utilizadorId) {
        this.utilizadorId = utilizadorId;
    }

    public LongFilter getLancamentoFinanceiroId() {
        return lancamentoFinanceiroId;
    }

    public void setLancamentoFinanceiroId(LongFilter lancamentoFinanceiroId) {
        this.lancamentoFinanceiroId = lancamentoFinanceiroId;
    }

    public LongFilter getMetodoLiquidacaoId() {
        return metodoLiquidacaoId;
    }

    public void setMetodoLiquidacaoId(LongFilter metodoLiquidacaoId) {
        this.metodoLiquidacaoId = metodoLiquidacaoId;
    }

    public LongFilter getMoedaId() {
        return moedaId;
    }

    public void setMoedaId(LongFilter moedaId) {
        this.moedaId = moedaId;
    }

    public LongFilter getCoordenadaId() {
        return coordenadaId;
    }

    public void setCoordenadaId(LongFilter coordenadaId) {
        this.coordenadaId = coordenadaId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final DetalheLancamentoCriteria that = (DetalheLancamentoCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(valor, that.valor) &&
            Objects.equals(desconto, that.desconto) &&
            Objects.equals(juro, that.juro) &&
            Objects.equals(data, that.data) &&
            Objects.equals(retencaoFonte, that.retencaoFonte) &&
            Objects.equals(dataVencimento, that.dataVencimento) &&
            Objects.equals(liquidado, that.liquidado) &&
            Objects.equals(retencaoFonteId, that.retencaoFonteId) &&
            Objects.equals(utilizadorId, that.utilizadorId) &&
            Objects.equals(lancamentoFinanceiroId, that.lancamentoFinanceiroId) &&
            Objects.equals(metodoLiquidacaoId, that.metodoLiquidacaoId) &&
            Objects.equals(moedaId, that.moedaId) &&
            Objects.equals(coordenadaId, that.coordenadaId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        valor,
        desconto,
        juro,
        data,
        retencaoFonte,
        dataVencimento,
        liquidado,
        retencaoFonteId,
        utilizadorId,
        lancamentoFinanceiroId,
        metodoLiquidacaoId,
        moedaId,
        coordenadaId
        );
    }

    @Override
    public String toString() {
        return "DetalheLancamentoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (valor != null ? "valor=" + valor + ", " : "") +
                (desconto != null ? "desconto=" + desconto + ", " : "") +
                (juro != null ? "juro=" + juro + ", " : "") +
                (data != null ? "data=" + data + ", " : "") +
                (retencaoFonte != null ? "retencaoFonte=" + retencaoFonte + ", " : "") +
                (dataVencimento != null ? "dataVencimento=" + dataVencimento + ", " : "") +
                (liquidado != null ? "liquidado=" + liquidado + ", " : "") +
                (retencaoFonteId != null ? "retencaoFonteId=" + retencaoFonteId + ", " : "") +
                (utilizadorId != null ? "utilizadorId=" + utilizadorId + ", " : "") +
                (lancamentoFinanceiroId != null ? "lancamentoFinanceiroId=" + lancamentoFinanceiroId + ", " : "") +
                (metodoLiquidacaoId != null ? "metodoLiquidacaoId=" + metodoLiquidacaoId + ", " : "") +
                (moedaId != null ? "moedaId=" + moedaId + ", " : "") +
                (coordenadaId != null ? "coordenadaId=" + coordenadaId + ", " : "") +
            "}";
    }

}
