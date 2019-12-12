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

/**
 * Criteria class for the {@link com.ravunana.manda.domain.CompostoProduto} entity. This class is used
 * in {@link com.ravunana.manda.web.rest.CompostoProdutoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /composto-produtos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CompostoProdutoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private DoubleFilter quantidade;

    private BigDecimalFilter valor;

    private BooleanFilter permanente;

    private LongFilter produtoId;

    private LongFilter unidadeId;

    private LongFilter compostoId;

    public CompostoProdutoCriteria(){
    }

    public CompostoProdutoCriteria(CompostoProdutoCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.quantidade = other.quantidade == null ? null : other.quantidade.copy();
        this.valor = other.valor == null ? null : other.valor.copy();
        this.permanente = other.permanente == null ? null : other.permanente.copy();
        this.produtoId = other.produtoId == null ? null : other.produtoId.copy();
        this.unidadeId = other.unidadeId == null ? null : other.unidadeId.copy();
        this.compostoId = other.compostoId == null ? null : other.compostoId.copy();
    }

    @Override
    public CompostoProdutoCriteria copy() {
        return new CompostoProdutoCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public DoubleFilter getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(DoubleFilter quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimalFilter getValor() {
        return valor;
    }

    public void setValor(BigDecimalFilter valor) {
        this.valor = valor;
    }

    public BooleanFilter getPermanente() {
        return permanente;
    }

    public void setPermanente(BooleanFilter permanente) {
        this.permanente = permanente;
    }

    public LongFilter getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(LongFilter produtoId) {
        this.produtoId = produtoId;
    }

    public LongFilter getUnidadeId() {
        return unidadeId;
    }

    public void setUnidadeId(LongFilter unidadeId) {
        this.unidadeId = unidadeId;
    }

    public LongFilter getCompostoId() {
        return compostoId;
    }

    public void setCompostoId(LongFilter compostoId) {
        this.compostoId = compostoId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CompostoProdutoCriteria that = (CompostoProdutoCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(quantidade, that.quantidade) &&
            Objects.equals(valor, that.valor) &&
            Objects.equals(permanente, that.permanente) &&
            Objects.equals(produtoId, that.produtoId) &&
            Objects.equals(unidadeId, that.unidadeId) &&
            Objects.equals(compostoId, that.compostoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        quantidade,
        valor,
        permanente,
        produtoId,
        unidadeId,
        compostoId
        );
    }

    @Override
    public String toString() {
        return "CompostoProdutoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (quantidade != null ? "quantidade=" + quantidade + ", " : "") +
                (valor != null ? "valor=" + valor + ", " : "") +
                (permanente != null ? "permanente=" + permanente + ", " : "") +
                (produtoId != null ? "produtoId=" + produtoId + ", " : "") +
                (unidadeId != null ? "unidadeId=" + unidadeId + ", " : "") +
                (compostoId != null ? "compostoId=" + compostoId + ", " : "") +
            "}";
    }

}
