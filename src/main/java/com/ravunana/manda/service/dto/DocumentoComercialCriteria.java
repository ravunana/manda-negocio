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
 * Criteria class for the {@link com.ravunana.manda.domain.DocumentoComercial} entity. This class is used
 * in {@link com.ravunana.manda.web.rest.DocumentoComercialResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /documento-comercials?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DocumentoComercialCriteria implements Serializable, Criteria {
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

    private StringFilter serie;

    private BooleanFilter padrao;

    private BooleanFilter movimentaEstoque;

    private BooleanFilter movimentaCaixa;

    private BooleanFilter movimentaContabilidade;

    private StringFilter cor;

    private BooleanFilter mostraPontoVenda;

    private EntidadeSistemaFilter entidade;

    private LongFilter lancamentoFinanceiroId;

    private LongFilter compraId;

    private LongFilter vendaId;

    public DocumentoComercialCriteria(){
    }

    public DocumentoComercialCriteria(DocumentoComercialCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.nome = other.nome == null ? null : other.nome.copy();
        this.serie = other.serie == null ? null : other.serie.copy();
        this.padrao = other.padrao == null ? null : other.padrao.copy();
        this.movimentaEstoque = other.movimentaEstoque == null ? null : other.movimentaEstoque.copy();
        this.movimentaCaixa = other.movimentaCaixa == null ? null : other.movimentaCaixa.copy();
        this.movimentaContabilidade = other.movimentaContabilidade == null ? null : other.movimentaContabilidade.copy();
        this.cor = other.cor == null ? null : other.cor.copy();
        this.mostraPontoVenda = other.mostraPontoVenda == null ? null : other.mostraPontoVenda.copy();
        this.entidade = other.entidade == null ? null : other.entidade.copy();
        this.lancamentoFinanceiroId = other.lancamentoFinanceiroId == null ? null : other.lancamentoFinanceiroId.copy();
        this.compraId = other.compraId == null ? null : other.compraId.copy();
        this.vendaId = other.vendaId == null ? null : other.vendaId.copy();
    }

    @Override
    public DocumentoComercialCriteria copy() {
        return new DocumentoComercialCriteria(this);
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

    public StringFilter getSerie() {
        return serie;
    }

    public void setSerie(StringFilter serie) {
        this.serie = serie;
    }

    public BooleanFilter getPadrao() {
        return padrao;
    }

    public void setPadrao(BooleanFilter padrao) {
        this.padrao = padrao;
    }

    public BooleanFilter getMovimentaEstoque() {
        return movimentaEstoque;
    }

    public void setMovimentaEstoque(BooleanFilter movimentaEstoque) {
        this.movimentaEstoque = movimentaEstoque;
    }

    public BooleanFilter getMovimentaCaixa() {
        return movimentaCaixa;
    }

    public void setMovimentaCaixa(BooleanFilter movimentaCaixa) {
        this.movimentaCaixa = movimentaCaixa;
    }

    public BooleanFilter getMovimentaContabilidade() {
        return movimentaContabilidade;
    }

    public void setMovimentaContabilidade(BooleanFilter movimentaContabilidade) {
        this.movimentaContabilidade = movimentaContabilidade;
    }

    public StringFilter getCor() {
        return cor;
    }

    public void setCor(StringFilter cor) {
        this.cor = cor;
    }

    public BooleanFilter getMostraPontoVenda() {
        return mostraPontoVenda;
    }

    public void setMostraPontoVenda(BooleanFilter mostraPontoVenda) {
        this.mostraPontoVenda = mostraPontoVenda;
    }

    public EntidadeSistemaFilter getEntidade() {
        return entidade;
    }

    public void setEntidade(EntidadeSistemaFilter entidade) {
        this.entidade = entidade;
    }

    public LongFilter getLancamentoFinanceiroId() {
        return lancamentoFinanceiroId;
    }

    public void setLancamentoFinanceiroId(LongFilter lancamentoFinanceiroId) {
        this.lancamentoFinanceiroId = lancamentoFinanceiroId;
    }

    public LongFilter getCompraId() {
        return compraId;
    }

    public void setCompraId(LongFilter compraId) {
        this.compraId = compraId;
    }

    public LongFilter getVendaId() {
        return vendaId;
    }

    public void setVendaId(LongFilter vendaId) {
        this.vendaId = vendaId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final DocumentoComercialCriteria that = (DocumentoComercialCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(nome, that.nome) &&
            Objects.equals(serie, that.serie) &&
            Objects.equals(padrao, that.padrao) &&
            Objects.equals(movimentaEstoque, that.movimentaEstoque) &&
            Objects.equals(movimentaCaixa, that.movimentaCaixa) &&
            Objects.equals(movimentaContabilidade, that.movimentaContabilidade) &&
            Objects.equals(cor, that.cor) &&
            Objects.equals(mostraPontoVenda, that.mostraPontoVenda) &&
            Objects.equals(entidade, that.entidade) &&
            Objects.equals(lancamentoFinanceiroId, that.lancamentoFinanceiroId) &&
            Objects.equals(compraId, that.compraId) &&
            Objects.equals(vendaId, that.vendaId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nome,
        serie,
        padrao,
        movimentaEstoque,
        movimentaCaixa,
        movimentaContabilidade,
        cor,
        mostraPontoVenda,
        entidade,
        lancamentoFinanceiroId,
        compraId,
        vendaId
        );
    }

    @Override
    public String toString() {
        return "DocumentoComercialCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nome != null ? "nome=" + nome + ", " : "") +
                (serie != null ? "serie=" + serie + ", " : "") +
                (padrao != null ? "padrao=" + padrao + ", " : "") +
                (movimentaEstoque != null ? "movimentaEstoque=" + movimentaEstoque + ", " : "") +
                (movimentaCaixa != null ? "movimentaCaixa=" + movimentaCaixa + ", " : "") +
                (movimentaContabilidade != null ? "movimentaContabilidade=" + movimentaContabilidade + ", " : "") +
                (cor != null ? "cor=" + cor + ", " : "") +
                (mostraPontoVenda != null ? "mostraPontoVenda=" + mostraPontoVenda + ", " : "") +
                (entidade != null ? "entidade=" + entidade + ", " : "") +
                (lancamentoFinanceiroId != null ? "lancamentoFinanceiroId=" + lancamentoFinanceiroId + ", " : "") +
                (compraId != null ? "compraId=" + compraId + ", " : "") +
                (vendaId != null ? "vendaId=" + vendaId + ", " : "") +
            "}";
    }

}
