package com.ravunana.manda.service.dto;
import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Lob;

/**
 * A DTO for the {@link com.ravunana.manda.domain.Venda} entity.
 */
public class VendaDTO implements Serializable {

    private Long id;

    private String numero;

    private ZonedDateTime data;

    @Lob
    private String observacaoGeral;

    @Lob
    private String observacaoInterna;


    private Long vendedorId;

    private String vendedorLogin;

    private Long clienteId;

    private String clienteNumero;

    private Long tipoDocumentoId;

    private String tipoDocumentoNome;

    private Long empresaId;

    private String empresaNome;


    private Set<ImpostoDTO> impostos = new HashSet()<>();

    private Long formaLiquidacaoId;

    public Long getFormaLiquidacaoId() {
        return formaLiquidacaoId;
    }

    public void setFormaLiquidacaoId(Long formaLiquidacaoId) {
        this.formaLiquidacaoId = formaLiquidacaoId;
    }

    public Set<ImpostoDTO> getImpostos() {
        return impostos;
    }

    public void setImpostos(Set<ImpostoDTO> impostos) {
        this.impostos = impostos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public ZonedDateTime getData() {
        return data;
    }

    public void setData(ZonedDateTime data) {
        this.data = data;
    }

    public String getObservacaoGeral() {
        return observacaoGeral;
    }

    public void setObservacaoGeral(String observacaoGeral) {
        this.observacaoGeral = observacaoGeral;
    }

    public String getObservacaoInterna() {
        return observacaoInterna;
    }

    public void setObservacaoInterna(String observacaoInterna) {
        this.observacaoInterna = observacaoInterna;
    }

    public Long getVendedorId() {
        return vendedorId;
    }

    public void setVendedorId(Long userId) {
        this.vendedorId = userId;
    }

    public String getVendedorLogin() {
        return vendedorLogin;
    }

    public void setVendedorLogin(String userLogin) {
        this.vendedorLogin = userLogin;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public String getClienteNumero() {
        return clienteNumero;
    }

    public void setClienteNumero(String clienteNumero) {
        this.clienteNumero = clienteNumero;
    }

    public Long getTipoDocumentoId() {
        return tipoDocumentoId;
    }

    public void setTipoDocumentoId(Long documentoComercialId) {
        this.tipoDocumentoId = documentoComercialId;
    }

    public String getTipoDocumentoNome() {
        return tipoDocumentoNome;
    }

    public void setTipoDocumentoNome(String documentoComercialNome) {
        this.tipoDocumentoNome = documentoComercialNome;
    }

    public Long getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(Long empresaId) {
        this.empresaId = empresaId;
    }

    public String getEmpresaNome() {
        return empresaNome;
    }

    public void setEmpresaNome(String empresaNome) {
        this.empresaNome = empresaNome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        VendaDTO vendaDTO = (VendaDTO) o;
        if (vendaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), vendaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "VendaDTO{" +
            "id=" + getId() +
            ", numero='" + getNumero() + "'" +
            ", data='" + getData() + "'" +
            ", observacaoGeral='" + getObservacaoGeral() + "'" +
            ", observacaoInterna='" + getObservacaoInterna() + "'" +
            ", vendedor=" + getVendedorId() +
            ", vendedor='" + getVendedorLogin() + "'" +
            ", cliente=" + getClienteId() +
            ", cliente='" + getClienteNumero() + "'" +
            ", tipoDocumento=" + getTipoDocumentoId() +
            ", tipoDocumento='" + getTipoDocumentoNome() + "'" +
            ", empresa=" + getEmpresaId() +
            ", empresa='" + getEmpresaNome() + "'" +
            "}";
    }
}
