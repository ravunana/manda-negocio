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
import io.github.jhipster.service.filter.ZonedDateTimeFilter;

/**
 * Criteria class for the {@link com.ravunana.manda.domain.Arquivo} entity. This class is used
 * in {@link com.ravunana.manda.web.rest.ArquivoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /arquivos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ArquivoCriteria implements Serializable, Criteria {
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

    private StringFilter descricao;

    private EntidadeSistemaFilter entidade;

    private StringFilter codigoEntidade;

    private ZonedDateTimeFilter data;

    private LongFilter utilizadorId;

    public ArquivoCriteria(){
    }

    public ArquivoCriteria(ArquivoCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.descricao = other.descricao == null ? null : other.descricao.copy();
        this.entidade = other.entidade == null ? null : other.entidade.copy();
        this.codigoEntidade = other.codigoEntidade == null ? null : other.codigoEntidade.copy();
        this.data = other.data == null ? null : other.data.copy();
        this.utilizadorId = other.utilizadorId == null ? null : other.utilizadorId.copy();
    }

    @Override
    public ArquivoCriteria copy() {
        return new ArquivoCriteria(this);
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

    public EntidadeSistemaFilter getEntidade() {
        return entidade;
    }

    public void setEntidade(EntidadeSistemaFilter entidade) {
        this.entidade = entidade;
    }

    public StringFilter getCodigoEntidade() {
        return codigoEntidade;
    }

    public void setCodigoEntidade(StringFilter codigoEntidade) {
        this.codigoEntidade = codigoEntidade;
    }

    public ZonedDateTimeFilter getData() {
        return data;
    }

    public void setData(ZonedDateTimeFilter data) {
        this.data = data;
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
        final ArquivoCriteria that = (ArquivoCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(descricao, that.descricao) &&
            Objects.equals(entidade, that.entidade) &&
            Objects.equals(codigoEntidade, that.codigoEntidade) &&
            Objects.equals(data, that.data) &&
            Objects.equals(utilizadorId, that.utilizadorId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        descricao,
        entidade,
        codigoEntidade,
        data,
        utilizadorId
        );
    }

    @Override
    public String toString() {
        return "ArquivoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (descricao != null ? "descricao=" + descricao + ", " : "") +
                (entidade != null ? "entidade=" + entidade + ", " : "") +
                (codigoEntidade != null ? "codigoEntidade=" + codigoEntidade + ", " : "") +
                (data != null ? "data=" + data + ", " : "") +
                (utilizadorId != null ? "utilizadorId=" + utilizadorId + ", " : "") +
            "}";
    }

}
