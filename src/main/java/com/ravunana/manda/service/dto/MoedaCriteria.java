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
 * Criteria class for the {@link com.ravunana.manda.domain.Moeda} entity. This class is used
 * in {@link com.ravunana.manda.web.rest.MoedaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /moedas?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MoedaCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nome;

    private StringFilter codigo;

    private StringFilter pais;

    private BooleanFilter nacional;

    private StringFilter icon;

    private LongFilter detalheLancamentoId;

    public MoedaCriteria(){
    }

    public MoedaCriteria(MoedaCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.nome = other.nome == null ? null : other.nome.copy();
        this.codigo = other.codigo == null ? null : other.codigo.copy();
        this.pais = other.pais == null ? null : other.pais.copy();
        this.nacional = other.nacional == null ? null : other.nacional.copy();
        this.icon = other.icon == null ? null : other.icon.copy();
        this.detalheLancamentoId = other.detalheLancamentoId == null ? null : other.detalheLancamentoId.copy();
    }

    @Override
    public MoedaCriteria copy() {
        return new MoedaCriteria(this);
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

    public StringFilter getCodigo() {
        return codigo;
    }

    public void setCodigo(StringFilter codigo) {
        this.codigo = codigo;
    }

    public StringFilter getPais() {
        return pais;
    }

    public void setPais(StringFilter pais) {
        this.pais = pais;
    }

    public BooleanFilter getNacional() {
        return nacional;
    }

    public void setNacional(BooleanFilter nacional) {
        this.nacional = nacional;
    }

    public StringFilter getIcon() {
        return icon;
    }

    public void setIcon(StringFilter icon) {
        this.icon = icon;
    }

    public LongFilter getDetalheLancamentoId() {
        return detalheLancamentoId;
    }

    public void setDetalheLancamentoId(LongFilter detalheLancamentoId) {
        this.detalheLancamentoId = detalheLancamentoId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MoedaCriteria that = (MoedaCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(nome, that.nome) &&
            Objects.equals(codigo, that.codigo) &&
            Objects.equals(pais, that.pais) &&
            Objects.equals(nacional, that.nacional) &&
            Objects.equals(icon, that.icon) &&
            Objects.equals(detalheLancamentoId, that.detalheLancamentoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nome,
        codigo,
        pais,
        nacional,
        icon,
        detalheLancamentoId
        );
    }

    @Override
    public String toString() {
        return "MoedaCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nome != null ? "nome=" + nome + ", " : "") +
                (codigo != null ? "codigo=" + codigo + ", " : "") +
                (pais != null ? "pais=" + pais + ", " : "") +
                (nacional != null ? "nacional=" + nacional + ", " : "") +
                (icon != null ? "icon=" + icon + ", " : "") +
                (detalheLancamentoId != null ? "detalheLancamentoId=" + detalheLancamentoId + ", " : "") +
            "}";
    }

}
