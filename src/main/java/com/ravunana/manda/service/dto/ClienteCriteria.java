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
 * Criteria class for the {@link com.ravunana.manda.domain.Cliente} entity. This class is used
 * in {@link com.ravunana.manda.web.rest.ClienteResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /clientes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ClienteCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private BooleanFilter ativo;

    private DoubleFilter satisfacao;

    private DoubleFilter frequencia;

    private StringFilter canalUsado;

    private StringFilter numero;

    private BooleanFilter autofacturacao;

    private LongFilter pessoaId;

    private LongFilter vendaId;

    private LongFilter contaId;

    public ClienteCriteria(){
    }

    public ClienteCriteria(ClienteCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.ativo = other.ativo == null ? null : other.ativo.copy();
        this.satisfacao = other.satisfacao == null ? null : other.satisfacao.copy();
        this.frequencia = other.frequencia == null ? null : other.frequencia.copy();
        this.canalUsado = other.canalUsado == null ? null : other.canalUsado.copy();
        this.numero = other.numero == null ? null : other.numero.copy();
        this.autofacturacao = other.autofacturacao == null ? null : other.autofacturacao.copy();
        this.pessoaId = other.pessoaId == null ? null : other.pessoaId.copy();
        this.vendaId = other.vendaId == null ? null : other.vendaId.copy();
        this.contaId = other.contaId == null ? null : other.contaId.copy();
    }

    @Override
    public ClienteCriteria copy() {
        return new ClienteCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public BooleanFilter getAtivo() {
        return ativo;
    }

    public void setAtivo(BooleanFilter ativo) {
        this.ativo = ativo;
    }

    public DoubleFilter getSatisfacao() {
        return satisfacao;
    }

    public void setSatisfacao(DoubleFilter satisfacao) {
        this.satisfacao = satisfacao;
    }

    public DoubleFilter getFrequencia() {
        return frequencia;
    }

    public void setFrequencia(DoubleFilter frequencia) {
        this.frequencia = frequencia;
    }

    public StringFilter getCanalUsado() {
        return canalUsado;
    }

    public void setCanalUsado(StringFilter canalUsado) {
        this.canalUsado = canalUsado;
    }

    public StringFilter getNumero() {
        return numero;
    }

    public void setNumero(StringFilter numero) {
        this.numero = numero;
    }

    public BooleanFilter getAutofacturacao() {
        return autofacturacao;
    }

    public void setAutofacturacao(BooleanFilter autofacturacao) {
        this.autofacturacao = autofacturacao;
    }

    public LongFilter getPessoaId() {
        return pessoaId;
    }

    public void setPessoaId(LongFilter pessoaId) {
        this.pessoaId = pessoaId;
    }

    public LongFilter getVendaId() {
        return vendaId;
    }

    public void setVendaId(LongFilter vendaId) {
        this.vendaId = vendaId;
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
        final ClienteCriteria that = (ClienteCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(ativo, that.ativo) &&
            Objects.equals(satisfacao, that.satisfacao) &&
            Objects.equals(frequencia, that.frequencia) &&
            Objects.equals(canalUsado, that.canalUsado) &&
            Objects.equals(numero, that.numero) &&
            Objects.equals(autofacturacao, that.autofacturacao) &&
            Objects.equals(pessoaId, that.pessoaId) &&
            Objects.equals(vendaId, that.vendaId) &&
            Objects.equals(contaId, that.contaId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        ativo,
        satisfacao,
        frequencia,
        canalUsado,
        numero,
        autofacturacao,
        pessoaId,
        vendaId,
        contaId
        );
    }

    @Override
    public String toString() {
        return "ClienteCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (ativo != null ? "ativo=" + ativo + ", " : "") +
                (satisfacao != null ? "satisfacao=" + satisfacao + ", " : "") +
                (frequencia != null ? "frequencia=" + frequencia + ", " : "") +
                (canalUsado != null ? "canalUsado=" + canalUsado + ", " : "") +
                (numero != null ? "numero=" + numero + ", " : "") +
                (autofacturacao != null ? "autofacturacao=" + autofacturacao + ", " : "") +
                (pessoaId != null ? "pessoaId=" + pessoaId + ", " : "") +
                (vendaId != null ? "vendaId=" + vendaId + ", " : "") +
                (contaId != null ? "contaId=" + contaId + ", " : "") +
            "}";
    }

}
