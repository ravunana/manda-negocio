package com.ravunana.manda.service.dto;
import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.ravunana.manda.domain.Compra} entity.
 */
public class CompraDTO implements Serializable {

    private Long id;

    @NotNull
    private String numero;

    private ZonedDateTime data;

    @Lob
    private String observacaoGeral;

    @Lob
    private String observacaoInterna;


    private Long utilizadorId;

    private String utilizadorLogin;

    private Long tipoDocumentoId;

    private String tipoDocumentoNome;

    private Long empresaId;

    private String empresaNome;

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

    public Long getUtilizadorId() {
        return utilizadorId;
    }

    public void setUtilizadorId(Long userId) {
        this.utilizadorId = userId;
    }

    public String getUtilizadorLogin() {
        return utilizadorLogin;
    }

    public void setUtilizadorLogin(String userLogin) {
        this.utilizadorLogin = userLogin;
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

        CompraDTO compraDTO = (CompraDTO) o;
        if (compraDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), compraDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CompraDTO{" +
            "id=" + getId() +
            ", numero='" + getNumero() + "'" +
            ", data='" + getData() + "'" +
            ", observacaoGeral='" + getObservacaoGeral() + "'" +
            ", observacaoInterna='" + getObservacaoInterna() + "'" +
            ", utilizador=" + getUtilizadorId() +
            ", utilizador='" + getUtilizadorLogin() + "'" +
            ", tipoDocumento=" + getTipoDocumentoId() +
            ", tipoDocumento='" + getTipoDocumentoNome() + "'" +
            ", empresa=" + getEmpresaId() +
            ", empresa='" + getEmpresaNome() + "'" +
            "}";
    }
}
