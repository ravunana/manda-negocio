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
 * Criteria class for the {@link com.ravunana.manda.domain.FluxoDocumento} entity. This class is used
 * in {@link com.ravunana.manda.web.rest.FluxoDocumentoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /fluxo-documentos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class FluxoDocumentoCriteria implements Serializable, Criteria {
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

    private BooleanFilter aumentaEstoque;

    private BooleanFilter aumentaValorCaixa;

    private StringFilter cor;

    private BooleanFilter padrao;

    private EntidadeSistemaFilter entidade;

    private LongFilter produtoId;

    private LongFilter itemCompraId;

    private LongFilter itemVendaId;

    public FluxoDocumentoCriteria(){
    }

    public FluxoDocumentoCriteria(FluxoDocumentoCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.nome = other.nome == null ? null : other.nome.copy();
        this.aumentaEstoque = other.aumentaEstoque == null ? null : other.aumentaEstoque.copy();
        this.aumentaValorCaixa = other.aumentaValorCaixa == null ? null : other.aumentaValorCaixa.copy();
        this.cor = other.cor == null ? null : other.cor.copy();
        this.padrao = other.padrao == null ? null : other.padrao.copy();
        this.entidade = other.entidade == null ? null : other.entidade.copy();
        this.produtoId = other.produtoId == null ? null : other.produtoId.copy();
        this.itemCompraId = other.itemCompraId == null ? null : other.itemCompraId.copy();
        this.itemVendaId = other.itemVendaId == null ? null : other.itemVendaId.copy();
    }

    @Override
    public FluxoDocumentoCriteria copy() {
        return new FluxoDocumentoCriteria(this);
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

    public BooleanFilter getAumentaEstoque() {
        return aumentaEstoque;
    }

    public void setAumentaEstoque(BooleanFilter aumentaEstoque) {
        this.aumentaEstoque = aumentaEstoque;
    }

    public BooleanFilter getAumentaValorCaixa() {
        return aumentaValorCaixa;
    }

    public void setAumentaValorCaixa(BooleanFilter aumentaValorCaixa) {
        this.aumentaValorCaixa = aumentaValorCaixa;
    }

    public StringFilter getCor() {
        return cor;
    }

    public void setCor(StringFilter cor) {
        this.cor = cor;
    }

    public BooleanFilter getPadrao() {
        return padrao;
    }

    public void setPadrao(BooleanFilter padrao) {
        this.padrao = padrao;
    }

    public EntidadeSistemaFilter getEntidade() {
        return entidade;
    }

    public void setEntidade(EntidadeSistemaFilter entidade) {
        this.entidade = entidade;
    }

    public LongFilter getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(LongFilter produtoId) {
        this.produtoId = produtoId;
    }

    public LongFilter getItemCompraId() {
        return itemCompraId;
    }

    public void setItemCompraId(LongFilter itemCompraId) {
        this.itemCompraId = itemCompraId;
    }

    public LongFilter getItemVendaId() {
        return itemVendaId;
    }

    public void setItemVendaId(LongFilter itemVendaId) {
        this.itemVendaId = itemVendaId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final FluxoDocumentoCriteria that = (FluxoDocumentoCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(nome, that.nome) &&
            Objects.equals(aumentaEstoque, that.aumentaEstoque) &&
            Objects.equals(aumentaValorCaixa, that.aumentaValorCaixa) &&
            Objects.equals(cor, that.cor) &&
            Objects.equals(padrao, that.padrao) &&
            Objects.equals(entidade, that.entidade) &&
            Objects.equals(produtoId, that.produtoId) &&
            Objects.equals(itemCompraId, that.itemCompraId) &&
            Objects.equals(itemVendaId, that.itemVendaId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nome,
        aumentaEstoque,
        aumentaValorCaixa,
        cor,
        padrao,
        entidade,
        produtoId,
        itemCompraId,
        itemVendaId
        );
    }

    @Override
    public String toString() {
        return "FluxoDocumentoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nome != null ? "nome=" + nome + ", " : "") +
                (aumentaEstoque != null ? "aumentaEstoque=" + aumentaEstoque + ", " : "") +
                (aumentaValorCaixa != null ? "aumentaValorCaixa=" + aumentaValorCaixa + ", " : "") +
                (cor != null ? "cor=" + cor + ", " : "") +
                (padrao != null ? "padrao=" + padrao + ", " : "") +
                (entidade != null ? "entidade=" + entidade + ", " : "") +
                (produtoId != null ? "produtoId=" + produtoId + ", " : "") +
                (itemCompraId != null ? "itemCompraId=" + itemCompraId + ", " : "") +
                (itemVendaId != null ? "itemVendaId=" + itemVendaId + ", " : "") +
            "}";
    }

}
