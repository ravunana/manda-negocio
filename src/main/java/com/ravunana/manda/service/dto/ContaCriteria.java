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
 * Criteria class for the {@link com.ravunana.manda.domain.Conta} entity. This class is used
 * in {@link com.ravunana.manda.web.rest.ContaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /contas?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ContaCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter descricao;

    private StringFilter codigo;

    private StringFilter tipo;

    private IntegerFilter grau;

    private StringFilter natureza;

    private StringFilter grupo;

    private LongFilter contaId;

    private LongFilter contaDebitoId;

    private LongFilter contaCreditoId;

    private LongFilter empresaId;

    private LongFilter contaAgregadoraId;

    private LongFilter classeContaId;

    public ContaCriteria(){
    }

    public ContaCriteria(ContaCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.descricao = other.descricao == null ? null : other.descricao.copy();
        this.codigo = other.codigo == null ? null : other.codigo.copy();
        this.tipo = other.tipo == null ? null : other.tipo.copy();
        this.grau = other.grau == null ? null : other.grau.copy();
        this.natureza = other.natureza == null ? null : other.natureza.copy();
        this.grupo = other.grupo == null ? null : other.grupo.copy();
        this.contaId = other.contaId == null ? null : other.contaId.copy();
        this.contaDebitoId = other.contaDebitoId == null ? null : other.contaDebitoId.copy();
        this.contaCreditoId = other.contaCreditoId == null ? null : other.contaCreditoId.copy();
        this.empresaId = other.empresaId == null ? null : other.empresaId.copy();
        this.contaAgregadoraId = other.contaAgregadoraId == null ? null : other.contaAgregadoraId.copy();
        this.classeContaId = other.classeContaId == null ? null : other.classeContaId.copy();
    }

    @Override
    public ContaCriteria copy() {
        return new ContaCriteria(this);
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

    public StringFilter getCodigo() {
        return codigo;
    }

    public void setCodigo(StringFilter codigo) {
        this.codigo = codigo;
    }

    public StringFilter getTipo() {
        return tipo;
    }

    public void setTipo(StringFilter tipo) {
        this.tipo = tipo;
    }

    public IntegerFilter getGrau() {
        return grau;
    }

    public void setGrau(IntegerFilter grau) {
        this.grau = grau;
    }

    public StringFilter getNatureza() {
        return natureza;
    }

    public void setNatureza(StringFilter natureza) {
        this.natureza = natureza;
    }

    public StringFilter getGrupo() {
        return grupo;
    }

    public void setGrupo(StringFilter grupo) {
        this.grupo = grupo;
    }

    public LongFilter getContaId() {
        return contaId;
    }

    public void setContaId(LongFilter contaId) {
        this.contaId = contaId;
    }

    public LongFilter getContaDebitoId() {
        return contaDebitoId;
    }

    public void setContaDebitoId(LongFilter contaDebitoId) {
        this.contaDebitoId = contaDebitoId;
    }

    public LongFilter getContaCreditoId() {
        return contaCreditoId;
    }

    public void setContaCreditoId(LongFilter contaCreditoId) {
        this.contaCreditoId = contaCreditoId;
    }

    public LongFilter getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(LongFilter empresaId) {
        this.empresaId = empresaId;
    }

    public LongFilter getContaAgregadoraId() {
        return contaAgregadoraId;
    }

    public void setContaAgregadoraId(LongFilter contaAgregadoraId) {
        this.contaAgregadoraId = contaAgregadoraId;
    }

    public LongFilter getClasseContaId() {
        return classeContaId;
    }

    public void setClasseContaId(LongFilter classeContaId) {
        this.classeContaId = classeContaId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ContaCriteria that = (ContaCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(descricao, that.descricao) &&
            Objects.equals(codigo, that.codigo) &&
            Objects.equals(tipo, that.tipo) &&
            Objects.equals(grau, that.grau) &&
            Objects.equals(natureza, that.natureza) &&
            Objects.equals(grupo, that.grupo) &&
            Objects.equals(contaId, that.contaId) &&
            Objects.equals(contaDebitoId, that.contaDebitoId) &&
            Objects.equals(contaCreditoId, that.contaCreditoId) &&
            Objects.equals(empresaId, that.empresaId) &&
            Objects.equals(contaAgregadoraId, that.contaAgregadoraId) &&
            Objects.equals(classeContaId, that.classeContaId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        descricao,
        codigo,
        tipo,
        grau,
        natureza,
        grupo,
        contaId,
        contaDebitoId,
        contaCreditoId,
        empresaId,
        contaAgregadoraId,
        classeContaId
        );
    }

    @Override
    public String toString() {
        return "ContaCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (descricao != null ? "descricao=" + descricao + ", " : "") +
                (codigo != null ? "codigo=" + codigo + ", " : "") +
                (tipo != null ? "tipo=" + tipo + ", " : "") +
                (grau != null ? "grau=" + grau + ", " : "") +
                (natureza != null ? "natureza=" + natureza + ", " : "") +
                (grupo != null ? "grupo=" + grupo + ", " : "") +
                (contaId != null ? "contaId=" + contaId + ", " : "") +
                (contaDebitoId != null ? "contaDebitoId=" + contaDebitoId + ", " : "") +
                (contaCreditoId != null ? "contaCreditoId=" + contaCreditoId + ", " : "") +
                (empresaId != null ? "empresaId=" + empresaId + ", " : "") +
                (contaAgregadoraId != null ? "contaAgregadoraId=" + contaAgregadoraId + ", " : "") +
                (classeContaId != null ? "classeContaId=" + classeContaId + ", " : "") +
            "}";
    }

}
