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
 * Criteria class for the {@link com.ravunana.manda.domain.MeioLiquidacao} entity. This class is used
 * in {@link com.ravunana.manda.web.rest.MeioLiquidacaoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /meio-liquidacaos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MeioLiquidacaoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nome;

    private StringFilter sigla;

    private StringFilter icon;

    private BooleanFilter mostrarPontoVenda;

    private LongFilter detalheLancamentoId;

    public MeioLiquidacaoCriteria(){
    }

    public MeioLiquidacaoCriteria(MeioLiquidacaoCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.nome = other.nome == null ? null : other.nome.copy();
        this.sigla = other.sigla == null ? null : other.sigla.copy();
        this.icon = other.icon == null ? null : other.icon.copy();
        this.mostrarPontoVenda = other.mostrarPontoVenda == null ? null : other.mostrarPontoVenda.copy();
        this.detalheLancamentoId = other.detalheLancamentoId == null ? null : other.detalheLancamentoId.copy();
    }

    @Override
    public MeioLiquidacaoCriteria copy() {
        return new MeioLiquidacaoCriteria(this);
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

    public StringFilter getSigla() {
        return sigla;
    }

    public void setSigla(StringFilter sigla) {
        this.sigla = sigla;
    }

    public StringFilter getIcon() {
        return icon;
    }

    public void setIcon(StringFilter icon) {
        this.icon = icon;
    }

    public BooleanFilter getMostrarPontoVenda() {
        return mostrarPontoVenda;
    }

    public void setMostrarPontoVenda(BooleanFilter mostrarPontoVenda) {
        this.mostrarPontoVenda = mostrarPontoVenda;
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
        final MeioLiquidacaoCriteria that = (MeioLiquidacaoCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(nome, that.nome) &&
            Objects.equals(sigla, that.sigla) &&
            Objects.equals(icon, that.icon) &&
            Objects.equals(mostrarPontoVenda, that.mostrarPontoVenda) &&
            Objects.equals(detalheLancamentoId, that.detalheLancamentoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nome,
        sigla,
        icon,
        mostrarPontoVenda,
        detalheLancamentoId
        );
    }

    @Override
    public String toString() {
        return "MeioLiquidacaoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nome != null ? "nome=" + nome + ", " : "") +
                (sigla != null ? "sigla=" + sigla + ", " : "") +
                (icon != null ? "icon=" + icon + ", " : "") +
                (mostrarPontoVenda != null ? "mostrarPontoVenda=" + mostrarPontoVenda + ", " : "") +
                (detalheLancamentoId != null ? "detalheLancamentoId=" + detalheLancamentoId + ", " : "") +
            "}";
    }

}
