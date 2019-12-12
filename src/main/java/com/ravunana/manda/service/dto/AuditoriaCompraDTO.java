package com.ravunana.manda.service.dto;
import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.ravunana.manda.domain.AuditoriaCompra} entity.
 */
public class AuditoriaCompraDTO implements Serializable {

    private Long id;

    private String estado;

    private ZonedDateTime data;

    private String motivoAlteracaoDocumento;

    private String origemDocumento;

    private String hash;

    private String hashControl;


    private Long compraId;

    private String compraNumero;

    private Long utilizadorId;

    private String utilizadorLogin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public ZonedDateTime getData() {
        return data;
    }

    public void setData(ZonedDateTime data) {
        this.data = data;
    }

    public String getMotivoAlteracaoDocumento() {
        return motivoAlteracaoDocumento;
    }

    public void setMotivoAlteracaoDocumento(String motivoAlteracaoDocumento) {
        this.motivoAlteracaoDocumento = motivoAlteracaoDocumento;
    }

    public String getOrigemDocumento() {
        return origemDocumento;
    }

    public void setOrigemDocumento(String origemDocumento) {
        this.origemDocumento = origemDocumento;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getHashControl() {
        return hashControl;
    }

    public void setHashControl(String hashControl) {
        this.hashControl = hashControl;
    }

    public Long getCompraId() {
        return compraId;
    }

    public void setCompraId(Long compraId) {
        this.compraId = compraId;
    }

    public String getCompraNumero() {
        return compraNumero;
    }

    public void setCompraNumero(String compraNumero) {
        this.compraNumero = compraNumero;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AuditoriaCompraDTO auditoriaCompraDTO = (AuditoriaCompraDTO) o;
        if (auditoriaCompraDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), auditoriaCompraDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AuditoriaCompraDTO{" +
            "id=" + getId() +
            ", estado='" + getEstado() + "'" +
            ", data='" + getData() + "'" +
            ", motivoAlteracaoDocumento='" + getMotivoAlteracaoDocumento() + "'" +
            ", origemDocumento='" + getOrigemDocumento() + "'" +
            ", hash='" + getHash() + "'" +
            ", hashControl='" + getHashControl() + "'" +
            ", compra=" + getCompraId() +
            ", compra='" + getCompraNumero() + "'" +
            ", utilizador=" + getUtilizadorId() +
            ", utilizador='" + getUtilizadorLogin() + "'" +
            "}";
    }
}
