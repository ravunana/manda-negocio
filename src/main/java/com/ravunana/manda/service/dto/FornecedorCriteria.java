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

/**
 * Criteria class for the {@link com.ravunana.manda.domain.Fornecedor} entity. This class is used
 * in {@link com.ravunana.manda.web.rest.FornecedorResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /fornecedors?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class FornecedorCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private BooleanFilter certificadoISO9001;

    private BooleanFilter garantiaDefeitoFabrica;

    private BooleanFilter transporte;

    private DoubleFilter qualidade;

    private BooleanFilter pagamentoPrazo;

    private DoubleFilter classificacao;

    private BooleanFilter ativo;

    private StringFilter numero;

    private LongFilter pessoaId;

    private LongFilter itemCompraId;

    private LongFilter contaId;

    private LongFilter produtoId;

    public FornecedorCriteria(){
    }

    public FornecedorCriteria(FornecedorCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.certificadoISO9001 = other.certificadoISO9001 == null ? null : other.certificadoISO9001.copy();
        this.garantiaDefeitoFabrica = other.garantiaDefeitoFabrica == null ? null : other.garantiaDefeitoFabrica.copy();
        this.transporte = other.transporte == null ? null : other.transporte.copy();
        this.qualidade = other.qualidade == null ? null : other.qualidade.copy();
        this.pagamentoPrazo = other.pagamentoPrazo == null ? null : other.pagamentoPrazo.copy();
        this.classificacao = other.classificacao == null ? null : other.classificacao.copy();
        this.ativo = other.ativo == null ? null : other.ativo.copy();
        this.numero = other.numero == null ? null : other.numero.copy();
        this.pessoaId = other.pessoaId == null ? null : other.pessoaId.copy();
        this.itemCompraId = other.itemCompraId == null ? null : other.itemCompraId.copy();
        this.contaId = other.contaId == null ? null : other.contaId.copy();
        this.produtoId = other.produtoId == null ? null : other.produtoId.copy();
    }

    @Override
    public FornecedorCriteria copy() {
        return new FornecedorCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public BooleanFilter getCertificadoISO9001() {
        return certificadoISO9001;
    }

    public void setCertificadoISO9001(BooleanFilter certificadoISO9001) {
        this.certificadoISO9001 = certificadoISO9001;
    }

    public BooleanFilter getGarantiaDefeitoFabrica() {
        return garantiaDefeitoFabrica;
    }

    public void setGarantiaDefeitoFabrica(BooleanFilter garantiaDefeitoFabrica) {
        this.garantiaDefeitoFabrica = garantiaDefeitoFabrica;
    }

    public BooleanFilter getTransporte() {
        return transporte;
    }

    public void setTransporte(BooleanFilter transporte) {
        this.transporte = transporte;
    }

    public DoubleFilter getQualidade() {
        return qualidade;
    }

    public void setQualidade(DoubleFilter qualidade) {
        this.qualidade = qualidade;
    }

    public BooleanFilter getPagamentoPrazo() {
        return pagamentoPrazo;
    }

    public void setPagamentoPrazo(BooleanFilter pagamentoPrazo) {
        this.pagamentoPrazo = pagamentoPrazo;
    }

    public DoubleFilter getClassificacao() {
        return classificacao;
    }

    public void setClassificacao(DoubleFilter classificacao) {
        this.classificacao = classificacao;
    }

    public BooleanFilter getAtivo() {
        return ativo;
    }

    public void setAtivo(BooleanFilter ativo) {
        this.ativo = ativo;
    }

    public StringFilter getNumero() {
        return numero;
    }

    public void setNumero(StringFilter numero) {
        this.numero = numero;
    }

    public LongFilter getPessoaId() {
        return pessoaId;
    }

    public void setPessoaId(LongFilter pessoaId) {
        this.pessoaId = pessoaId;
    }

    public LongFilter getItemCompraId() {
        return itemCompraId;
    }

    public void setItemCompraId(LongFilter itemCompraId) {
        this.itemCompraId = itemCompraId;
    }

    public LongFilter getContaId() {
        return contaId;
    }

    public void setContaId(LongFilter contaId) {
        this.contaId = contaId;
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
        final FornecedorCriteria that = (FornecedorCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(certificadoISO9001, that.certificadoISO9001) &&
            Objects.equals(garantiaDefeitoFabrica, that.garantiaDefeitoFabrica) &&
            Objects.equals(transporte, that.transporte) &&
            Objects.equals(qualidade, that.qualidade) &&
            Objects.equals(pagamentoPrazo, that.pagamentoPrazo) &&
            Objects.equals(classificacao, that.classificacao) &&
            Objects.equals(ativo, that.ativo) &&
            Objects.equals(numero, that.numero) &&
            Objects.equals(pessoaId, that.pessoaId) &&
            Objects.equals(itemCompraId, that.itemCompraId) &&
            Objects.equals(contaId, that.contaId) &&
            Objects.equals(produtoId, that.produtoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        certificadoISO9001,
        garantiaDefeitoFabrica,
        transporte,
        qualidade,
        pagamentoPrazo,
        classificacao,
        ativo,
        numero,
        pessoaId,
        itemCompraId,
        contaId,
        produtoId
        );
    }

    @Override
    public String toString() {
        return "FornecedorCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (certificadoISO9001 != null ? "certificadoISO9001=" + certificadoISO9001 + ", " : "") +
                (garantiaDefeitoFabrica != null ? "garantiaDefeitoFabrica=" + garantiaDefeitoFabrica + ", " : "") +
                (transporte != null ? "transporte=" + transporte + ", " : "") +
                (qualidade != null ? "qualidade=" + qualidade + ", " : "") +
                (pagamentoPrazo != null ? "pagamentoPrazo=" + pagamentoPrazo + ", " : "") +
                (classificacao != null ? "classificacao=" + classificacao + ", " : "") +
                (ativo != null ? "ativo=" + ativo + ", " : "") +
                (numero != null ? "numero=" + numero + ", " : "") +
                (pessoaId != null ? "pessoaId=" + pessoaId + ", " : "") +
                (itemCompraId != null ? "itemCompraId=" + itemCompraId + ", " : "") +
                (contaId != null ? "contaId=" + contaId + ", " : "") +
                (produtoId != null ? "produtoId=" + produtoId + ", " : "") +
            "}";
    }

}
