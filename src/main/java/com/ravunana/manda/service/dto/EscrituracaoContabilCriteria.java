package com.ravunana.manda.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.ravunana.manda.domain.enumeration.EntidadeSistema;
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
 * Criteria class for the {@link com.ravunana.manda.domain.EscrituracaoContabil} entity. This class is used
 * in {@link com.ravunana.manda.web.rest.EscrituracaoContabilResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /escrituracao-contabils?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class EscrituracaoContabilCriteria implements Serializable, Criteria {
    /**
     * Class for filtering EntidadeSistema
     */
    public static class EntidadeSistemaFilter extends Filter<EntidadeSistema> {

        public EntidadeSistemaFilter() {
        }

        public EntidadeSistemaFilter(EntidadeSistemaFilter filter) {
            super(filter);
        }

        @Override
        public EntidadeSistemaFilter copy() {
            return new EntidadeSistemaFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter numero;

    private BigDecimalFilter valor;

    private StringFilter referencia;

    private EntidadeSistemaFilter entidadeReferencia;

    private StringFilter tipo;

    private LocalDateFilter dataDocumento;

    private ZonedDateTimeFilter data;

    private LongFilter contaDebitoId;

    private LongFilter contaCreditoId;

    private LongFilter utilizadorId;

    private LongFilter empresaId;

    public EscrituracaoContabilCriteria(){
    }

    public EscrituracaoContabilCriteria(EscrituracaoContabilCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.numero = other.numero == null ? null : other.numero.copy();
        this.valor = other.valor == null ? null : other.valor.copy();
        this.referencia = other.referencia == null ? null : other.referencia.copy();
        this.entidadeReferencia = other.entidadeReferencia == null ? null : other.entidadeReferencia.copy();
        this.tipo = other.tipo == null ? null : other.tipo.copy();
        this.dataDocumento = other.dataDocumento == null ? null : other.dataDocumento.copy();
        this.data = other.data == null ? null : other.data.copy();
        this.contaDebitoId = other.contaDebitoId == null ? null : other.contaDebitoId.copy();
        this.contaCreditoId = other.contaCreditoId == null ? null : other.contaCreditoId.copy();
        this.utilizadorId = other.utilizadorId == null ? null : other.utilizadorId.copy();
        this.empresaId = other.empresaId == null ? null : other.empresaId.copy();
    }

    @Override
    public EscrituracaoContabilCriteria copy() {
        return new EscrituracaoContabilCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getNumero() {
        return numero;
    }

    public void setNumero(StringFilter numero) {
        this.numero = numero;
    }

    public BigDecimalFilter getValor() {
        return valor;
    }

    public void setValor(BigDecimalFilter valor) {
        this.valor = valor;
    }

    public StringFilter getReferencia() {
        return referencia;
    }

    public void setReferencia(StringFilter referencia) {
        this.referencia = referencia;
    }

    public EntidadeSistemaFilter getEntidadeReferencia() {
        return entidadeReferencia;
    }

    public void setEntidadeReferencia(EntidadeSistemaFilter entidadeReferencia) {
        this.entidadeReferencia = entidadeReferencia;
    }

    public StringFilter getTipo() {
        return tipo;
    }

    public void setTipo(StringFilter tipo) {
        this.tipo = tipo;
    }

    public LocalDateFilter getDataDocumento() {
        return dataDocumento;
    }

    public void setDataDocumento(LocalDateFilter dataDocumento) {
        this.dataDocumento = dataDocumento;
    }

    public ZonedDateTimeFilter getData() {
        return data;
    }

    public void setData(ZonedDateTimeFilter data) {
        this.data = data;
    }

    public LongFilter getContaDebitoId() {
        return contaDebitoId;
    }

    public void setContaDebitoId(LongFilter contaDebitoId) {
        this.contaDebitoId = contaDebitoId;
    }

    public LongFilter getContaCreditoId() {
        return contaCreditoId;
    }

    public void setContaCreditoId(LongFilter contaCreditoId) {
        this.contaCreditoId = contaCreditoId;
    }

    public LongFilter getUtilizadorId() {
        return utilizadorId;
    }

    public void setUtilizadorId(LongFilter utilizadorId) {
        this.utilizadorId = utilizadorId;
    }

    public LongFilter getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(LongFilter empresaId) {
        this.empresaId = empresaId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final EscrituracaoContabilCriteria that = (EscrituracaoContabilCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(numero, that.numero) &&
            Objects.equals(valor, that.valor) &&
            Objects.equals(referencia, that.referencia) &&
            Objects.equals(entidadeReferencia, that.entidadeReferencia) &&
            Objects.equals(tipo, that.tipo) &&
            Objects.equals(dataDocumento, that.dataDocumento) &&
            Objects.equals(data, that.data) &&
            Objects.equals(contaDebitoId, that.contaDebitoId) &&
            Objects.equals(contaCreditoId, that.contaCreditoId) &&
            Objects.equals(utilizadorId, that.utilizadorId) &&
            Objects.equals(empresaId, that.empresaId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        numero,
        valor,
        referencia,
        entidadeReferencia,
        tipo,
        dataDocumento,
        data,
        contaDebitoId,
        contaCreditoId,
        utilizadorId,
        empresaId
        );
    }

    @Override
    public String toString() {
        return "EscrituracaoContabilCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (numero != null ? "numero=" + numero + ", " : "") +
                (valor != null ? "valor=" + valor + ", " : "") +
                (referencia != null ? "referencia=" + referencia + ", " : "") +
                (entidadeReferencia != null ? "entidadeReferencia=" + entidadeReferencia + ", " : "") +
                (tipo != null ? "tipo=" + tipo + ", " : "") +
                (dataDocumento != null ? "dataDocumento=" + dataDocumento + ", " : "") +
                (data != null ? "data=" + data + ", " : "") +
                (contaDebitoId != null ? "contaDebitoId=" + contaDebitoId + ", " : "") +
                (contaCreditoId != null ? "contaCreditoId=" + contaCreditoId + ", " : "") +
                (utilizadorId != null ? "utilizadorId=" + utilizadorId + ", " : "") +
                (empresaId != null ? "empresaId=" + empresaId + ", " : "") +
            "}";
    }

}
