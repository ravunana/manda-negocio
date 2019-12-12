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
 * Criteria class for the {@link com.ravunana.manda.domain.Produto} entity. This class is used
 * in {@link com.ravunana.manda.web.rest.ProdutoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /produtos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ProdutoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter tipo;

    private StringFilter ean;

    private StringFilter numero;

    private StringFilter nome;

    private BooleanFilter composto;

    private DoubleFilter estoqueMinimo;

    private DoubleFilter estoqueMaximo;

    private DoubleFilter estoqueAtual;

    private BooleanFilter mostrarPDV;

    private StringFilter prazoMedioEntrega;

    private LongFilter compostoProdutoId;

    private LongFilter conversaoUnidadeId;

    private LongFilter estruturaCalculoId;

    private LongFilter itemCompraId;

    private LongFilter itemVendaId;

    private LongFilter utilizadorId;

    private LongFilter impostoId;

    private LongFilter fornecedorId;

    private LongFilter localArmazenamentoId;

    private LongFilter familiaId;

    private LongFilter empresaId;

    private LongFilter estadoId;

    public ProdutoCriteria(){
    }

    public ProdutoCriteria(ProdutoCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.tipo = other.tipo == null ? null : other.tipo.copy();
        this.ean = other.ean == null ? null : other.ean.copy();
        this.numero = other.numero == null ? null : other.numero.copy();
        this.nome = other.nome == null ? null : other.nome.copy();
        this.composto = other.composto == null ? null : other.composto.copy();
        this.estoqueMinimo = other.estoqueMinimo == null ? null : other.estoqueMinimo.copy();
        this.estoqueMaximo = other.estoqueMaximo == null ? null : other.estoqueMaximo.copy();
        this.estoqueAtual = other.estoqueAtual == null ? null : other.estoqueAtual.copy();
        this.mostrarPDV = other.mostrarPDV == null ? null : other.mostrarPDV.copy();
        this.prazoMedioEntrega = other.prazoMedioEntrega == null ? null : other.prazoMedioEntrega.copy();
        this.compostoProdutoId = other.compostoProdutoId == null ? null : other.compostoProdutoId.copy();
        this.conversaoUnidadeId = other.conversaoUnidadeId == null ? null : other.conversaoUnidadeId.copy();
        this.estruturaCalculoId = other.estruturaCalculoId == null ? null : other.estruturaCalculoId.copy();
        this.itemCompraId = other.itemCompraId == null ? null : other.itemCompraId.copy();
        this.itemVendaId = other.itemVendaId == null ? null : other.itemVendaId.copy();
        this.utilizadorId = other.utilizadorId == null ? null : other.utilizadorId.copy();
        this.impostoId = other.impostoId == null ? null : other.impostoId.copy();
        this.fornecedorId = other.fornecedorId == null ? null : other.fornecedorId.copy();
        this.localArmazenamentoId = other.localArmazenamentoId == null ? null : other.localArmazenamentoId.copy();
        this.familiaId = other.familiaId == null ? null : other.familiaId.copy();
        this.empresaId = other.empresaId == null ? null : other.empresaId.copy();
        this.estadoId = other.estadoId == null ? null : other.estadoId.copy();
    }

    @Override
    public ProdutoCriteria copy() {
        return new ProdutoCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getTipo() {
        return tipo;
    }

    public void setTipo(StringFilter tipo) {
        this.tipo = tipo;
    }

    public StringFilter getEan() {
        return ean;
    }

    public void setEan(StringFilter ean) {
        this.ean = ean;
    }

    public StringFilter getNumero() {
        return numero;
    }

    public void setNumero(StringFilter numero) {
        this.numero = numero;
    }

    public StringFilter getNome() {
        return nome;
    }

    public void setNome(StringFilter nome) {
        this.nome = nome;
    }

    public BooleanFilter getComposto() {
        return composto;
    }

    public void setComposto(BooleanFilter composto) {
        this.composto = composto;
    }

    public DoubleFilter getEstoqueMinimo() {
        return estoqueMinimo;
    }

    public void setEstoqueMinimo(DoubleFilter estoqueMinimo) {
        this.estoqueMinimo = estoqueMinimo;
    }

    public DoubleFilter getEstoqueMaximo() {
        return estoqueMaximo;
    }

    public void setEstoqueMaximo(DoubleFilter estoqueMaximo) {
        this.estoqueMaximo = estoqueMaximo;
    }

    public DoubleFilter getEstoqueAtual() {
        return estoqueAtual;
    }

    public void setEstoqueAtual(DoubleFilter estoqueAtual) {
        this.estoqueAtual = estoqueAtual;
    }

    public BooleanFilter getMostrarPDV() {
        return mostrarPDV;
    }

    public void setMostrarPDV(BooleanFilter mostrarPDV) {
        this.mostrarPDV = mostrarPDV;
    }

    public StringFilter getPrazoMedioEntrega() {
        return prazoMedioEntrega;
    }

    public void setPrazoMedioEntrega(StringFilter prazoMedioEntrega) {
        this.prazoMedioEntrega = prazoMedioEntrega;
    }

    public LongFilter getCompostoProdutoId() {
        return compostoProdutoId;
    }

    public void setCompostoProdutoId(LongFilter compostoProdutoId) {
        this.compostoProdutoId = compostoProdutoId;
    }

    public LongFilter getConversaoUnidadeId() {
        return conversaoUnidadeId;
    }

    public void setConversaoUnidadeId(LongFilter conversaoUnidadeId) {
        this.conversaoUnidadeId = conversaoUnidadeId;
    }

    public LongFilter getEstruturaCalculoId() {
        return estruturaCalculoId;
    }

    public void setEstruturaCalculoId(LongFilter estruturaCalculoId) {
        this.estruturaCalculoId = estruturaCalculoId;
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

    public LongFilter getUtilizadorId() {
        return utilizadorId;
    }

    public void setUtilizadorId(LongFilter utilizadorId) {
        this.utilizadorId = utilizadorId;
    }

    public LongFilter getImpostoId() {
        return impostoId;
    }

    public void setImpostoId(LongFilter impostoId) {
        this.impostoId = impostoId;
    }

    public LongFilter getFornecedorId() {
        return fornecedorId;
    }

    public void setFornecedorId(LongFilter fornecedorId) {
        this.fornecedorId = fornecedorId;
    }

    public LongFilter getLocalArmazenamentoId() {
        return localArmazenamentoId;
    }

    public void setLocalArmazenamentoId(LongFilter localArmazenamentoId) {
        this.localArmazenamentoId = localArmazenamentoId;
    }

    public LongFilter getFamiliaId() {
        return familiaId;
    }

    public void setFamiliaId(LongFilter familiaId) {
        this.familiaId = familiaId;
    }

    public LongFilter getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(LongFilter empresaId) {
        this.empresaId = empresaId;
    }

    public LongFilter getEstadoId() {
        return estadoId;
    }

    public void setEstadoId(LongFilter estadoId) {
        this.estadoId = estadoId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProdutoCriteria that = (ProdutoCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(tipo, that.tipo) &&
            Objects.equals(ean, that.ean) &&
            Objects.equals(numero, that.numero) &&
            Objects.equals(nome, that.nome) &&
            Objects.equals(composto, that.composto) &&
            Objects.equals(estoqueMinimo, that.estoqueMinimo) &&
            Objects.equals(estoqueMaximo, that.estoqueMaximo) &&
            Objects.equals(estoqueAtual, that.estoqueAtual) &&
            Objects.equals(mostrarPDV, that.mostrarPDV) &&
            Objects.equals(prazoMedioEntrega, that.prazoMedioEntrega) &&
            Objects.equals(compostoProdutoId, that.compostoProdutoId) &&
            Objects.equals(conversaoUnidadeId, that.conversaoUnidadeId) &&
            Objects.equals(estruturaCalculoId, that.estruturaCalculoId) &&
            Objects.equals(itemCompraId, that.itemCompraId) &&
            Objects.equals(itemVendaId, that.itemVendaId) &&
            Objects.equals(utilizadorId, that.utilizadorId) &&
            Objects.equals(impostoId, that.impostoId) &&
            Objects.equals(fornecedorId, that.fornecedorId) &&
            Objects.equals(localArmazenamentoId, that.localArmazenamentoId) &&
            Objects.equals(familiaId, that.familiaId) &&
            Objects.equals(empresaId, that.empresaId) &&
            Objects.equals(estadoId, that.estadoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        tipo,
        ean,
        numero,
        nome,
        composto,
        estoqueMinimo,
        estoqueMaximo,
        estoqueAtual,
        mostrarPDV,
        prazoMedioEntrega,
        compostoProdutoId,
        conversaoUnidadeId,
        estruturaCalculoId,
        itemCompraId,
        itemVendaId,
        utilizadorId,
        impostoId,
        fornecedorId,
        localArmazenamentoId,
        familiaId,
        empresaId,
        estadoId
        );
    }

    @Override
    public String toString() {
        return "ProdutoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (tipo != null ? "tipo=" + tipo + ", " : "") +
                (ean != null ? "ean=" + ean + ", " : "") +
                (numero != null ? "numero=" + numero + ", " : "") +
                (nome != null ? "nome=" + nome + ", " : "") +
                (composto != null ? "composto=" + composto + ", " : "") +
                (estoqueMinimo != null ? "estoqueMinimo=" + estoqueMinimo + ", " : "") +
                (estoqueMaximo != null ? "estoqueMaximo=" + estoqueMaximo + ", " : "") +
                (estoqueAtual != null ? "estoqueAtual=" + estoqueAtual + ", " : "") +
                (mostrarPDV != null ? "mostrarPDV=" + mostrarPDV + ", " : "") +
                (prazoMedioEntrega != null ? "prazoMedioEntrega=" + prazoMedioEntrega + ", " : "") +
                (compostoProdutoId != null ? "compostoProdutoId=" + compostoProdutoId + ", " : "") +
                (conversaoUnidadeId != null ? "conversaoUnidadeId=" + conversaoUnidadeId + ", " : "") +
                (estruturaCalculoId != null ? "estruturaCalculoId=" + estruturaCalculoId + ", " : "") +
                (itemCompraId != null ? "itemCompraId=" + itemCompraId + ", " : "") +
                (itemVendaId != null ? "itemVendaId=" + itemVendaId + ", " : "") +
                (utilizadorId != null ? "utilizadorId=" + utilizadorId + ", " : "") +
                (impostoId != null ? "impostoId=" + impostoId + ", " : "") +
                (fornecedorId != null ? "fornecedorId=" + fornecedorId + ", " : "") +
                (localArmazenamentoId != null ? "localArmazenamentoId=" + localArmazenamentoId + ", " : "") +
                (familiaId != null ? "familiaId=" + familiaId + ", " : "") +
                (empresaId != null ? "empresaId=" + empresaId + ", " : "") +
                (estadoId != null ? "estadoId=" + estadoId + ", " : "") +
            "}";
    }

}
