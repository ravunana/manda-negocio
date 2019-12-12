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
 * Criteria class for the {@link com.ravunana.manda.domain.Venda} entity. This class is used
 * in {@link com.ravunana.manda.web.rest.VendaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /vendas?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class VendaCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter numero;

    private ZonedDateTimeFilter data;

    private LongFilter itemVendaId;

    private LongFilter vendedorId;

    private LongFilter clienteId;

    private LongFilter tipoDocumentoId;

    private LongFilter empresaId;

    public VendaCriteria(){
    }

    public VendaCriteria(VendaCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.numero = other.numero == null ? null : other.numero.copy();
        this.data = other.data == null ? null : other.data.copy();
        this.itemVendaId = other.itemVendaId == null ? null : other.itemVendaId.copy();
        this.vendedorId = other.vendedorId == null ? null : other.vendedorId.copy();
        this.clienteId = other.clienteId == null ? null : other.clienteId.copy();
        this.tipoDocumentoId = other.tipoDocumentoId == null ? null : other.tipoDocumentoId.copy();
        this.empresaId = other.empresaId == null ? null : other.empresaId.copy();
    }

    @Override
    public VendaCriteria copy() {
        return new VendaCriteria(this);
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

    public LongFilter getItemVendaId() {
        return itemVendaId;
    }

    public void setItemVendaId(LongFilter itemVendaId) {
        this.itemVendaId = itemVendaId;
    }

    public LongFilter getVendedorId() {
        return vendedorId;
    }

    public void setVendedorId(LongFilter vendedorId) {
        this.vendedorId = vendedorId;
    }

    public LongFilter getClienteId() {
        return clienteId;
    }

    public void setClienteId(LongFilter clienteId) {
        this.clienteId = clienteId;
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
        final VendaCriteria that = (VendaCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(numero, that.numero) &&
            Objects.equals(data, that.data) &&
            Objects.equals(itemVendaId, that.itemVendaId) &&
            Objects.equals(vendedorId, that.vendedorId) &&
            Objects.equals(clienteId, that.clienteId) &&
            Objects.equals(tipoDocumentoId, that.tipoDocumentoId) &&
            Objects.equals(empresaId, that.empresaId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        numero,
        data,
        itemVendaId,
        vendedorId,
        clienteId,
        tipoDocumentoId,
        empresaId
        );
    }

    @Override
    public String toString() {
        return "VendaCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (numero != null ? "numero=" + numero + ", " : "") +
                (data != null ? "data=" + data + ", " : "") +
                (itemVendaId != null ? "itemVendaId=" + itemVendaId + ", " : "") +
                (vendedorId != null ? "vendedorId=" + vendedorId + ", " : "") +
                (clienteId != null ? "clienteId=" + clienteId + ", " : "") +
                (tipoDocumentoId != null ? "tipoDocumentoId=" + tipoDocumentoId + ", " : "") +
                (empresaId != null ? "empresaId=" + empresaId + ", " : "") +
            "}";
    }

}
