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

/**
 * Criteria class for the {@link com.ravunana.manda.domain.Imposto} entity. This class is used
 * in {@link com.ravunana.manda.web.rest.ImpostoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /impostos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ImpostoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter tipoImposto;

    private StringFilter codigoImposto;

    private BooleanFilter porcentagem;

    private BigDecimalFilter valor;

    private StringFilter pais;

    private LocalDateFilter vigencia;

    private LongFilter grupoTributacaoImpostoId;

    private LongFilter retencaoFonteId;

    private LongFilter contaId;

    private LongFilter lancamentoId;

    private LongFilter produtoId;

    public ImpostoCriteria(){
    }

    public ImpostoCriteria(ImpostoCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.tipoImposto = other.tipoImposto == null ? null : other.tipoImposto.copy();
        this.codigoImposto = other.codigoImposto == null ? null : other.codigoImposto.copy();
        this.porcentagem = other.porcentagem == null ? null : other.porcentagem.copy();
        this.valor = other.valor == null ? null : other.valor.copy();
        this.pais = other.pais == null ? null : other.pais.copy();
        this.vigencia = other.vigencia == null ? null : other.vigencia.copy();
        this.grupoTributacaoImpostoId = other.grupoTributacaoImpostoId == null ? null : other.grupoTributacaoImpostoId.copy();
        this.retencaoFonteId = other.retencaoFonteId == null ? null : other.retencaoFonteId.copy();
        this.contaId = other.contaId == null ? null : other.contaId.copy();
        this.lancamentoId = other.lancamentoId == null ? null : other.lancamentoId.copy();
        this.produtoId = other.produtoId == null ? null : other.produtoId.copy();
    }

    @Override
    public ImpostoCriteria copy() {
        return new ImpostoCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getTipoImposto() {
        return tipoImposto;
    }

    public void setTipoImposto(StringFilter tipoImposto) {
        this.tipoImposto = tipoImposto;
    }

    public StringFilter getCodigoImposto() {
        return codigoImposto;
    }

    public void setCodigoImposto(StringFilter codigoImposto) {
        this.codigoImposto = codigoImposto;
    }

    public BooleanFilter getPorcentagem() {
        return porcentagem;
    }

    public void setPorcentagem(BooleanFilter porcentagem) {
        this.porcentagem = porcentagem;
    }

    public BigDecimalFilter getValor() {
        return valor;
    }

    public void setValor(BigDecimalFilter valor) {
        this.valor = valor;
    }

    public StringFilter getPais() {
        return pais;
    }

    public void setPais(StringFilter pais) {
        this.pais = pais;
    }

    public LocalDateFilter getVigencia() {
        return vigencia;
    }

    public void setVigencia(LocalDateFilter vigencia) {
        this.vigencia = vigencia;
    }

    public LongFilter getGrupoTributacaoImpostoId() {
        return grupoTributacaoImpostoId;
    }

    public void setGrupoTributacaoImpostoId(LongFilter grupoTributacaoImpostoId) {
        this.grupoTributacaoImpostoId = grupoTributacaoImpostoId;
    }

    public LongFilter getRetencaoFonteId() {
        return retencaoFonteId;
    }

    public void setRetencaoFonteId(LongFilter retencaoFonteId) {
        this.retencaoFonteId = retencaoFonteId;
    }

    public LongFilter getContaId() {
        return contaId;
    }

    public void setContaId(LongFilter contaId) {
        this.contaId = contaId;
    }

    public LongFilter getLancamentoId() {
        return lancamentoId;
    }

    public void setLancamentoId(LongFilter lancamentoId) {
        this.lancamentoId = lancamentoId;
    }

    public LongFilter getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(LongFilter produtoId) {
        this.produtoId = produtoId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ImpostoCriteria that = (ImpostoCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(tipoImposto, that.tipoImposto) &&
            Objects.equals(codigoImposto, that.codigoImposto) &&
            Objects.equals(porcentagem, that.porcentagem) &&
            Objects.equals(valor, that.valor) &&
            Objects.equals(pais, that.pais) &&
            Objects.equals(vigencia, that.vigencia) &&
            Objects.equals(grupoTributacaoImpostoId, that.grupoTributacaoImpostoId) &&
            Objects.equals(retencaoFonteId, that.retencaoFonteId) &&
            Objects.equals(contaId, that.contaId) &&
            Objects.equals(lancamentoId, that.lancamentoId) &&
            Objects.equals(produtoId, that.produtoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        tipoImposto,
        codigoImposto,
        porcentagem,
        valor,
        pais,
        vigencia,
        grupoTributacaoImpostoId,
        retencaoFonteId,
        contaId,
        lancamentoId,
        produtoId
        );
    }

    @Override
    public String toString() {
        return "ImpostoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (tipoImposto != null ? "tipoImposto=" + tipoImposto + ", " : "") +
                (codigoImposto != null ? "codigoImposto=" + codigoImposto + ", " : "") +
                (porcentagem != null ? "porcentagem=" + porcentagem + ", " : "") +
                (valor != null ? "valor=" + valor + ", " : "") +
                (pais != null ? "pais=" + pais + ", " : "") +
                (vigencia != null ? "vigencia=" + vigencia + ", " : "") +
                (grupoTributacaoImpostoId != null ? "grupoTributacaoImpostoId=" + grupoTributacaoImpostoId + ", " : "") +
                (retencaoFonteId != null ? "retencaoFonteId=" + retencaoFonteId + ", " : "") +
                (contaId != null ? "contaId=" + contaId + ", " : "") +
                (lancamentoId != null ? "lancamentoId=" + lancamentoId + ", " : "") +
                (produtoId != null ? "produtoId=" + produtoId + ", " : "") +
            "}";
    }

}
