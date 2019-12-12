package com.ravunana.manda.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.ravunana.manda.domain.enumeration.LookupType;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.ravunana.manda.domain.LookupItem} entity. This class is used
 * in {@link com.ravunana.manda.web.rest.LookupItemResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /lookup-items?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class LookupItemCriteria implements Serializable, Criteria {
    /**
     * Class for filtering LookupType
     */
    public static class LookupTypeFilter extends Filter<LookupType> {

        public LookupTypeFilter() {
        }

        public LookupTypeFilter(LookupTypeFilter filter) {
            super(filter);
        }

        @Override
        public LookupTypeFilter copy() {
            return new LookupTypeFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter valor;

    private StringFilter nome;

    private LookupTypeFilter type;

    private IntegerFilter ordem;

    private LongFilter lookupId;

    public LookupItemCriteria(){
    }

    public LookupItemCriteria(LookupItemCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.valor = other.valor == null ? null : other.valor.copy();
        this.nome = other.nome == null ? null : other.nome.copy();
        this.type = other.type == null ? null : other.type.copy();
        this.ordem = other.ordem == null ? null : other.ordem.copy();
        this.lookupId = other.lookupId == null ? null : other.lookupId.copy();
    }

    @Override
    public LookupItemCriteria copy() {
        return new LookupItemCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getValor() {
        return valor;
    }

    public void setValor(StringFilter valor) {
        this.valor = valor;
    }

    public StringFilter getNome() {
        return nome;
    }

    public void setNome(StringFilter nome) {
        this.nome = nome;
    }

    public LookupTypeFilter getType() {
        return type;
    }

    public void setType(LookupTypeFilter type) {
        this.type = type;
    }

    public IntegerFilter getOrdem() {
        return ordem;
    }

    public void setOrdem(IntegerFilter ordem) {
        this.ordem = ordem;
    }

    public LongFilter getLookupId() {
        return lookupId;
    }

    public void setLookupId(LongFilter lookupId) {
        this.lookupId = lookupId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final LookupItemCriteria that = (LookupItemCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(valor, that.valor) &&
            Objects.equals(nome, that.nome) &&
            Objects.equals(type, that.type) &&
            Objects.equals(ordem, that.ordem) &&
            Objects.equals(lookupId, that.lookupId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        valor,
        nome,
        type,
        ordem,
        lookupId
        );
    }

    @Override
    public String toString() {
        return "LookupItemCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (valor != null ? "valor=" + valor + ", " : "") +
                (nome != null ? "nome=" + nome + ", " : "") +
                (type != null ? "type=" + type + ", " : "") +
                (ordem != null ? "ordem=" + ordem + ", " : "") +
                (lookupId != null ? "lookupId=" + lookupId + ", " : "") +
            "}";
    }

}
