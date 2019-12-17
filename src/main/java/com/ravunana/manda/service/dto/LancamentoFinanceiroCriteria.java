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

/**
 * Criteria class for the {@link com.ravunana.manda.domain.LancamentoFinanceiro} entity. This class is used
 * in {@link com.ravunana.manda.web.rest.LancamentoFinanceiroResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /lancamento-financeiros?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class LancamentoFinanceiroCriteria implements Serializable, Criteria {
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

    private StringFilter tipoLancamento;

    private BigDecimalFilter valor;

    private BooleanFilter externo;

    private StringFilter numero;

    private EntidadeSistemaFilter entidadeDocumento;

    private StringFilter numeroDocumento;

    private LongFilter detalheLancamentoId;

    private LongFilter utilizadorId;

    private LongFilter impostoId;

    private LongFilter formaLiquidacaoId;

    private LongFilter empresaId;

    private LongFilter tipoReciboId;

    private LongFilter contaId;

    public LancamentoFinanceiroCriteria(){
    }

    public LancamentoFinanceiroCriteria(LancamentoFinanceiroCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.tipoLancamento = other.tipoLancamento == null ? null : other.tipoLancamento.copy();
        this.valor = other.valor == null ? null : other.valor.copy();
        this.externo = other.externo == null ? null : other.externo.copy();
        this.numero = other.numero == null ? null : other.numero.copy();
        this.entidadeDocumento = other.entidadeDocumento == null ? null : other.entidadeDocumento.copy();
        this.numeroDocumento = other.numeroDocumento == null ? null : other.numeroDocumento.copy();
        this.detalheLancamentoId = other.detalheLancamentoId == null ? null : other.detalheLancamentoId.copy();
        this.utilizadorId = other.utilizadorId == null ? null : other.utilizadorId.copy();
        this.impostoId = other.impostoId == null ? null : other.impostoId.copy();
        this.formaLiquidacaoId = other.formaLiquidacaoId == null ? null : other.formaLiquidacaoId.copy();
        this.empresaId = other.empresaId == null ? null : other.empresaId.copy();
        this.tipoReciboId = other.tipoReciboId == null ? null : other.tipoReciboId.copy();
        this.contaId = other.contaId == null ? null : other.contaId.copy();
    }

    @Override
    public LancamentoFinanceiroCriteria copy() {
        return new LancamentoFinanceiroCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getTipoLancamento() {
        return tipoLancamento;
    }

    public void setTipoLancamento(StringFilter tipoLancamento) {
        this.tipoLancamento = tipoLancamento;
    }

    public BigDecimalFilter getValor() {
        return valor;
    }

    public void setValor(BigDecimalFilter valor) {
        this.valor = valor;
    }

    public BooleanFilter getExterno() {
        return externo;
    }

    public void setExterno(BooleanFilter externo) {
        this.externo = externo;
    }

    public StringFilter getNumero() {
        return numero;
    }

    public void setNumero(StringFilter numero) {
        this.numero = numero;
    }

    public EntidadeSistemaFilter getEntidadeDocumento() {
        return entidadeDocumento;
    }

    public void setEntidadeDocumento(EntidadeSistemaFilter entidadeDocumento) {
        this.entidadeDocumento = entidadeDocumento;
    }

    public StringFilter getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(StringFilter numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public LongFilter getDetalheLancamentoId() {
        return detalheLancamentoId;
    }

    public void setDetalheLancamentoId(LongFilter detalheLancamentoId) {
        this.detalheLancamentoId = detalheLancamentoId;
    }

    public LongFilter getUtilizadorId() {
        return utilizadorId;
    }

    public void setUtilizadorId(LongFilter utilizadorId) {
        this.utilizadorId = utilizadorId;
    }

    public LongFilter getImpostoId() {
        return impostoId;
    }

    public void setImpostoId(LongFilter impostoId) {
        this.impostoId = impostoId;
    }

    public LongFilter getFormaLiquidacaoId() {
        return formaLiquidacaoId;
    }

    public void setFormaLiquidacaoId(LongFilter formaLiquidacaoId) {
        this.formaLiquidacaoId = formaLiquidacaoId;
    }

    public LongFilter getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(LongFilter empresaId) {
        this.empresaId = empresaId;
    }

    public LongFilter getTipoReciboId() {
        return tipoReciboId;
    }

    public void setTipoReciboId(LongFilter tipoReciboId) {
        this.tipoReciboId = tipoReciboId;
    }

    public LongFilter getContaId() {
        return contaId;
    }

    public void setContaId(LongFilter contaId) {
        this.contaId = contaId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final LancamentoFinanceiroCriteria that = (LancamentoFinanceiroCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(tipoLancamento, that.tipoLancamento) &&
            Objects.equals(valor, that.valor) &&
            Objects.equals(externo, that.externo) &&
            Objects.equals(numero, that.numero) &&
            Objects.equals(entidadeDocumento, that.entidadeDocumento) &&
            Objects.equals(numeroDocumento, that.numeroDocumento) &&
            Objects.equals(detalheLancamentoId, that.detalheLancamentoId) &&
            Objects.equals(utilizadorId, that.utilizadorId) &&
            Objects.equals(impostoId, that.impostoId) &&
            Objects.equals(formaLiquidacaoId, that.formaLiquidacaoId) &&
            Objects.equals(empresaId, that.empresaId) &&
            Objects.equals(tipoReciboId, that.tipoReciboId) &&
            Objects.equals(contaId, that.contaId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        tipoLancamento,
        valor,
        externo,
        numero,
        entidadeDocumento,
        numeroDocumento,
        detalheLancamentoId,
        utilizadorId,
        impostoId,
        formaLiquidacaoId,
        empresaId,
        tipoReciboId,
        contaId
        );
    }

    @Override
    public String toString() {
        return "LancamentoFinanceiroCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (tipoLancamento != null ? "tipoLancamento=" + tipoLancamento + ", " : "") +
                (valor != null ? "valor=" + valor + ", " : "") +
                (externo != null ? "externo=" + externo + ", " : "") +
                (numero != null ? "numero=" + numero + ", " : "") +
                (entidadeDocumento != null ? "entidadeDocumento=" + entidadeDocumento + ", " : "") +
                (numeroDocumento != null ? "numeroDocumento=" + numeroDocumento + ", " : "") +
                (detalheLancamentoId != null ? "detalheLancamentoId=" + detalheLancamentoId + ", " : "") +
                (utilizadorId != null ? "utilizadorId=" + utilizadorId + ", " : "") +
                (impostoId != null ? "impostoId=" + impostoId + ", " : "") +
                (formaLiquidacaoId != null ? "formaLiquidacaoId=" + formaLiquidacaoId + ", " : "") +
                (empresaId != null ? "empresaId=" + empresaId + ", " : "") +
                (tipoReciboId != null ? "tipoReciboId=" + tipoReciboId + ", " : "") +
                (contaId != null ? "contaId=" + contaId + ", " : "") +
            "}";
    }

}
