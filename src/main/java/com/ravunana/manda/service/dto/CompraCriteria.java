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
import io.github.jhipster.service.filter.ZonedDateTimeFilter;

/**
 * Criteria class for the {@link com.ravunana.manda.domain.Compra} entity. This class is used
 * in {@link com.ravunana.manda.web.rest.CompraResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /compras?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CompraCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter numero;

    private ZonedDateTimeFilter data;

    private LongFilter itemCompraId;

    private LongFilter utilizadorId;

    private LongFilter tipoDocumentoId;

    private LongFilter empresaId;

    public CompraCriteria(){
    }

    public CompraCriteria(CompraCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.numero = other.numero == null ? null : other.numero.copy();
        this.data = other.data == null ? null : other.data.copy();
        this.itemCompraId = other.itemCompraId == null ? null : other.itemCompraId.copy();
        this.utilizadorId = other.utilizadorId == null ? null : other.utilizadorId.copy();
        this.tipoDocumentoId = other.tipoDocumentoId == null ? null : other.tipoDocumentoId.copy();
        this.empresaId = other.empresaId == null ? null : other.empresaId.copy();
    }

    @Override
    public CompraCriteria copy() {
        return new CompraCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getNumero() {
        return numero;
    }

    public void setNumero(StringFilter numero) {
        this.numero = numero;
    }

    public ZonedDateTimeFilter getData() {
        return data;
    }

    public void setData(ZonedDateTimeFilter data) {
        this.data = data;
    }

    public LongFilter getItemCompraId() {
        return itemCompraId;
    }

    public void setItemCompraId(LongFilter itemCompraId) {
        this.itemCompraId = itemCompraId;
    }

    public LongFilter getUtilizadorId() {
        return utilizadorId;
    }

    public void setUtilizadorId(LongFilter utilizadorId) {
        this.utilizadorId = utilizadorId;
    }

    public LongFilter getTipoDocumentoId() {
        return tipoDocumentoId;
    }

    public void setTipoDocumentoId(LongFilter tipoDocumentoId) {
        this.tipoDocumentoId = tipoDocumentoId;
    }

    public LongFilter getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(LongFilter empresaId) {
        this.empresaId = empresaId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CompraCriteria that = (CompraCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(numero, that.numero) &&
            Objects.equals(data, that.data) &&
            Objects.equals(itemCompraId, that.itemCompraId) &&
            Objects.equals(utilizadorId, that.utilizadorId) &&
            Objects.equals(tipoDocumentoId, that.tipoDocumentoId) &&
            Objects.equals(empresaId, that.empresaId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        numero,
        data,
        itemCompraId,
        utilizadorId,
        tipoDocumentoId,
        empresaId
        );
    }

    @Override
    public String toString() {
        return "CompraCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (numero != null ? "numero=" + numero + ", " : "") +
                (data != null ? "data=" + data + ", " : "") +
                (itemCompraId != null ? "itemCompraId=" + itemCompraId + ", " : "") +
                (utilizadorId != null ? "utilizadorId=" + utilizadorId + ", " : "") +
                (tipoDocumentoId != null ? "tipoDocumentoId=" + tipoDocumentoId + ", " : "") +
                (empresaId != null ? "empresaId=" + empresaId + ", " : "") +
            "}";
    }

}
