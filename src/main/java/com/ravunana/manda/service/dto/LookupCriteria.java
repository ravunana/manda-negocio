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

/**
 * Criteria class for the {@link com.ravunana.manda.domain.Lookup} entity. This class is used
 * in {@link com.ravunana.manda.web.rest.LookupResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /lookups?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class LookupCriteria implements Serializable, Criteria {
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

    private StringFilter nome;

    private EntidadeSistemaFilter entidade;

    private BooleanFilter modificavel;

    private LongFilter lookupItemId;

    public LookupCriteria(){
    }

    public LookupCriteria(LookupCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.nome = other.nome == null ? null : other.nome.copy();
        this.entidade = other.entidade == null ? null : other.entidade.copy();
        this.modificavel = other.modificavel == null ? null : other.modificavel.copy();
        this.lookupItemId = other.lookupItemId == null ? null : other.lookupItemId.copy();
    }

    @Override
    public LookupCriteria copy() {
        return new LookupCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getNome() {
        return nome;
    }

    public void setNome(StringFilter nome) {
        this.nome = nome;
    }

    public EntidadeSistemaFilter getEntidade() {
        return entidade;
    }

    public void setEntidade(EntidadeSistemaFilter entidade) {
        this.entidade = entidade;
    }

    public BooleanFilter getModificavel() {
        return modificavel;
    }

    public void setModificavel(BooleanFilter modificavel) {
        this.modificavel = modificavel;
    }

    public LongFilter getLookupItemId() {
        return lookupItemId;
    }

    public void setLookupItemId(LongFilter lookupItemId) {
        this.lookupItemId = lookupItemId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final LookupCriteria that = (LookupCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(nome, that.nome) &&
            Objects.equals(entidade, that.entidade) &&
            Objects.equals(modificavel, that.modificavel) &&
            Objects.equals(lookupItemId, that.lookupItemId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nome,
        entidade,
        modificavel,
        lookupItemId
        );
    }

    @Override
    public String toString() {
        return "LookupCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nome != null ? "nome=" + nome + ", " : "") +
                (entidade != null ? "entidade=" + entidade + ", " : "") +
                (modificavel != null ? "modificavel=" + modificavel + ", " : "") +
                (lookupItemId != null ? "lookupItemId=" + lookupItemId + ", " : "") +
            "}";
    }

}
