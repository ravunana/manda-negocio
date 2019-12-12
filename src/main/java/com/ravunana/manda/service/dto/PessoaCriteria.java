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
 * Criteria class for the {@link com.ravunana.manda.domain.Pessoa} entity. This class is used
 * in {@link com.ravunana.manda.web.rest.PessoaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /pessoas?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PessoaCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter tipoPessoa;

    private StringFilter nome;

    private StringFilter nif;

    private LongFilter moradaPessoaId;

    private LongFilter contactoPessoaId;

    private LongFilter utilizadorId;

    public PessoaCriteria(){
    }

    public PessoaCriteria(PessoaCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.tipoPessoa = other.tipoPessoa == null ? null : other.tipoPessoa.copy();
        this.nome = other.nome == null ? null : other.nome.copy();
        this.nif = other.nif == null ? null : other.nif.copy();
        this.moradaPessoaId = other.moradaPessoaId == null ? null : other.moradaPessoaId.copy();
        this.contactoPessoaId = other.contactoPessoaId == null ? null : other.contactoPessoaId.copy();
        this.utilizadorId = other.utilizadorId == null ? null : other.utilizadorId.copy();
    }

    @Override
    public PessoaCriteria copy() {
        return new PessoaCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getTipoPessoa() {
        return tipoPessoa;
    }

    public void setTipoPessoa(StringFilter tipoPessoa) {
        this.tipoPessoa = tipoPessoa;
    }

    public StringFilter getNome() {
        return nome;
    }

    public void setNome(StringFilter nome) {
        this.nome = nome;
    }

    public StringFilter getNif() {
        return nif;
    }

    public void setNif(StringFilter nif) {
        this.nif = nif;
    }

    public LongFilter getMoradaPessoaId() {
        return moradaPessoaId;
    }

    public void setMoradaPessoaId(LongFilter moradaPessoaId) {
        this.moradaPessoaId = moradaPessoaId;
    }

    public LongFilter getContactoPessoaId() {
        return contactoPessoaId;
    }

    public void setContactoPessoaId(LongFilter contactoPessoaId) {
        this.contactoPessoaId = contactoPessoaId;
    }

    public LongFilter getUtilizadorId() {
        return utilizadorId;
    }

    public void setUtilizadorId(LongFilter utilizadorId) {
        this.utilizadorId = utilizadorId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PessoaCriteria that = (PessoaCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(tipoPessoa, that.tipoPessoa) &&
            Objects.equals(nome, that.nome) &&
            Objects.equals(nif, that.nif) &&
            Objects.equals(moradaPessoaId, that.moradaPessoaId) &&
            Objects.equals(contactoPessoaId, that.contactoPessoaId) &&
            Objects.equals(utilizadorId, that.utilizadorId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        tipoPessoa,
        nome,
        nif,
        moradaPessoaId,
        contactoPessoaId,
        utilizadorId
        );
    }

    @Override
    public String toString() {
        return "PessoaCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (tipoPessoa != null ? "tipoPessoa=" + tipoPessoa + ", " : "") +
                (nome != null ? "nome=" + nome + ", " : "") +
                (nif != null ? "nif=" + nif + ", " : "") +
                (moradaPessoaId != null ? "moradaPessoaId=" + moradaPessoaId + ", " : "") +
                (contactoPessoaId != null ? "contactoPessoaId=" + contactoPessoaId + ", " : "") +
                (utilizadorId != null ? "utilizadorId=" + utilizadorId + ", " : "") +
            "}";
    }

}
