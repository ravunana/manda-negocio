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
 * Criteria class for the {@link com.ravunana.manda.domain.CoordenadaBancaria} entity. This class is used
 * in {@link com.ravunana.manda.web.rest.CoordenadaBancariaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /coordenada-bancarias?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CoordenadaBancariaCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter descricao;

    private StringFilter proprietario;

    private StringFilter numeroConta;

    private StringFilter iban;

    private BooleanFilter ativo;

    private BooleanFilter mostrarDocumento;

    private BooleanFilter mostrarPontoVenda;

    private BooleanFilter padraoRecebimento;

    private BooleanFilter padraoPagamento;

    private LongFilter detalheLancamentoId;

    private LongFilter contaId;

    private LongFilter empresaId;

    public CoordenadaBancariaCriteria(){
    }

    public CoordenadaBancariaCriteria(CoordenadaBancariaCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.descricao = other.descricao == null ? null : other.descricao.copy();
        this.proprietario = other.proprietario == null ? null : other.proprietario.copy();
        this.numeroConta = other.numeroConta == null ? null : other.numeroConta.copy();
        this.iban = other.iban == null ? null : other.iban.copy();
        this.ativo = other.ativo == null ? null : other.ativo.copy();
        this.mostrarDocumento = other.mostrarDocumento == null ? null : other.mostrarDocumento.copy();
        this.mostrarPontoVenda = other.mostrarPontoVenda == null ? null : other.mostrarPontoVenda.copy();
        this.padraoRecebimento = other.padraoRecebimento == null ? null : other.padraoRecebimento.copy();
        this.padraoPagamento = other.padraoPagamento == null ? null : other.padraoPagamento.copy();
        this.detalheLancamentoId = other.detalheLancamentoId == null ? null : other.detalheLancamentoId.copy();
        this.contaId = other.contaId == null ? null : other.contaId.copy();
        this.empresaId = other.empresaId == null ? null : other.empresaId.copy();
    }

    @Override
    public CoordenadaBancariaCriteria copy() {
        return new CoordenadaBancariaCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getDescricao() {
        return descricao;
    }

    public void setDescricao(StringFilter descricao) {
        this.descricao = descricao;
    }

    public StringFilter getProprietario() {
        return proprietario;
    }

    public void setProprietario(StringFilter proprietario) {
        this.proprietario = proprietario;
    }

    public StringFilter getNumeroConta() {
        return numeroConta;
    }

    public void setNumeroConta(StringFilter numeroConta) {
        this.numeroConta = numeroConta;
    }

    public StringFilter getIban() {
        return iban;
    }

    public void setIban(StringFilter iban) {
        this.iban = iban;
    }

    public BooleanFilter getAtivo() {
        return ativo;
    }

    public void setAtivo(BooleanFilter ativo) {
        this.ativo = ativo;
    }

    public BooleanFilter getMostrarDocumento() {
        return mostrarDocumento;
    }

    public void setMostrarDocumento(BooleanFilter mostrarDocumento) {
        this.mostrarDocumento = mostrarDocumento;
    }

    public BooleanFilter getMostrarPontoVenda() {
        return mostrarPontoVenda;
    }

    public void setMostrarPontoVenda(BooleanFilter mostrarPontoVenda) {
        this.mostrarPontoVenda = mostrarPontoVenda;
    }

    public BooleanFilter getPadraoRecebimento() {
        return padraoRecebimento;
    }

    public void setPadraoRecebimento(BooleanFilter padraoRecebimento) {
        this.padraoRecebimento = padraoRecebimento;
    }

    public BooleanFilter getPadraoPagamento() {
        return padraoPagamento;
    }

    public void setPadraoPagamento(BooleanFilter padraoPagamento) {
        this.padraoPagamento = padraoPagamento;
    }

    public LongFilter getDetalheLancamentoId() {
        return detalheLancamentoId;
    }

    public void setDetalheLancamentoId(LongFilter detalheLancamentoId) {
        this.detalheLancamentoId = detalheLancamentoId;
    }

    public LongFilter getContaId() {
        return contaId;
    }

    public void setContaId(LongFilter contaId) {
        this.contaId = contaId;
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
        final CoordenadaBancariaCriteria that = (CoordenadaBancariaCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(descricao, that.descricao) &&
            Objects.equals(proprietario, that.proprietario) &&
            Objects.equals(numeroConta, that.numeroConta) &&
            Objects.equals(iban, that.iban) &&
            Objects.equals(ativo, that.ativo) &&
            Objects.equals(mostrarDocumento, that.mostrarDocumento) &&
            Objects.equals(mostrarPontoVenda, that.mostrarPontoVenda) &&
            Objects.equals(padraoRecebimento, that.padraoRecebimento) &&
            Objects.equals(padraoPagamento, that.padraoPagamento) &&
            Objects.equals(detalheLancamentoId, that.detalheLancamentoId) &&
            Objects.equals(contaId, that.contaId) &&
            Objects.equals(empresaId, that.empresaId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        descricao,
        proprietario,
        numeroConta,
        iban,
        ativo,
        mostrarDocumento,
        mostrarPontoVenda,
        padraoRecebimento,
        padraoPagamento,
        detalheLancamentoId,
        contaId,
        empresaId
        );
    }

    @Override
    public String toString() {
        return "CoordenadaBancariaCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (descricao != null ? "descricao=" + descricao + ", " : "") +
                (proprietario != null ? "proprietario=" + proprietario + ", " : "") +
                (numeroConta != null ? "numeroConta=" + numeroConta + ", " : "") +
                (iban != null ? "iban=" + iban + ", " : "") +
                (ativo != null ? "ativo=" + ativo + ", " : "") +
                (mostrarDocumento != null ? "mostrarDocumento=" + mostrarDocumento + ", " : "") +
                (mostrarPontoVenda != null ? "mostrarPontoVenda=" + mostrarPontoVenda + ", " : "") +
                (padraoRecebimento != null ? "padraoRecebimento=" + padraoRecebimento + ", " : "") +
                (padraoPagamento != null ? "padraoPagamento=" + padraoPagamento + ", " : "") +
                (detalheLancamentoId != null ? "detalheLancamentoId=" + detalheLancamentoId + ", " : "") +
                (contaId != null ? "contaId=" + contaId + ", " : "") +
                (empresaId != null ? "empresaId=" + empresaId + ", " : "") +
            "}";
    }

}
