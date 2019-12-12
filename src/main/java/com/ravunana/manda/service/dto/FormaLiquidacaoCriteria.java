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
 * Criteria class for the {@link com.ravunana.manda.domain.FormaLiquidacao} entity. This class is used
 * in {@link com.ravunana.manda.web.rest.FormaLiquidacaoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /forma-liquidacaos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class FormaLiquidacaoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nome;

    private DoubleFilter juro;

    private IntegerFilter prazoLiquidacao;

    private IntegerFilter quantidade;

    private StringFilter icon;

    private LongFilter lancamentoFinanceiroId;

    public FormaLiquidacaoCriteria(){
    }

    public FormaLiquidacaoCriteria(FormaLiquidacaoCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.nome = other.nome == null ? null : other.nome.copy();
        this.juro = other.juro == null ? null : other.juro.copy();
        this.prazoLiquidacao = other.prazoLiquidacao == null ? null : other.prazoLiquidacao.copy();
        this.quantidade = other.quantidade == null ? null : other.quantidade.copy();
        this.icon = other.icon == null ? null : other.icon.copy();
        this.lancamentoFinanceiroId = other.lancamentoFinanceiroId == null ? null : other.lancamentoFinanceiroId.copy();
    }

    @Override
    public FormaLiquidacaoCriteria copy() {
        return new FormaLiquidacaoCriteria(this);
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

    public DoubleFilter getJuro() {
        return juro;
    }

    public void setJuro(DoubleFilter juro) {
        this.juro = juro;
    }

    public IntegerFilter getPrazoLiquidacao() {
        return prazoLiquidacao;
    }

    public void setPrazoLiquidacao(IntegerFilter prazoLiquidacao) {
        this.prazoLiquidacao = prazoLiquidacao;
    }

    public IntegerFilter getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(IntegerFilter quantidade) {
        this.quantidade = quantidade;
    }

    public StringFilter getIcon() {
        return icon;
    }

    public void setIcon(StringFilter icon) {
        this.icon = icon;
    }

    public LongFilter getLancamentoFinanceiroId() {
        return lancamentoFinanceiroId;
    }

    public void setLancamentoFinanceiroId(LongFilter lancamentoFinanceiroId) {
        this.lancamentoFinanceiroId = lancamentoFinanceiroId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final FormaLiquidacaoCriteria that = (FormaLiquidacaoCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(nome, that.nome) &&
            Objects.equals(juro, that.juro) &&
            Objects.equals(prazoLiquidacao, that.prazoLiquidacao) &&
            Objects.equals(quantidade, that.quantidade) &&
            Objects.equals(icon, that.icon) &&
            Objects.equals(lancamentoFinanceiroId, that.lancamentoFinanceiroId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nome,
        juro,
        prazoLiquidacao,
        quantidade,
        icon,
        lancamentoFinanceiroId
        );
    }

    @Override
    public String toString() {
        return "FormaLiquidacaoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nome != null ? "nome=" + nome + ", " : "") +
                (juro != null ? "juro=" + juro + ", " : "") +
                (prazoLiquidacao != null ? "prazoLiquidacao=" + prazoLiquidacao + ", " : "") +
                (quantidade != null ? "quantidade=" + quantidade + ", " : "") +
                (icon != null ? "icon=" + icon + ", " : "") +
                (lancamentoFinanceiroId != null ? "lancamentoFinanceiroId=" + lancamentoFinanceiroId + ", " : "") +
            "}";
    }

}
