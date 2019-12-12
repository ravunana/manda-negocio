package com.ravunana.manda.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.ravunana.manda.domain.Imposto} entity.
 */
public class ImpostoDTO implements Serializable {

    private Long id;

    @NotNull
    private String tipoImposto;

    @NotNull
    private String codigoImposto;

    @NotNull
    private Boolean porcentagem;

    @NotNull
    @DecimalMin(value = "0")
    private BigDecimal valor;

    @Lob
    private String descricao;

    private String pais;

    private LocalDate vigencia;


    private Long contaId;

    private String contaDescricao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipoImposto() {
        return tipoImposto;
    }

    public void setTipoImposto(String tipoImposto) {
        this.tipoImposto = tipoImposto;
    }

    public String getCodigoImposto() {
        return codigoImposto;
    }

    public void setCodigoImposto(String codigoImposto) {
        this.codigoImposto = codigoImposto;
    }

    public Boolean isPorcentagem() {
        return porcentagem;
    }

    public void setPorcentagem(Boolean porcentagem) {
        this.porcentagem = porcentagem;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public LocalDate getVigencia() {
        return vigencia;
    }

    public void setVigencia(LocalDate vigencia) {
        this.vigencia = vigencia;
    }

    public Long getContaId() {
        return contaId;
    }

    public void setContaId(Long contaId) {
        this.contaId = contaId;
    }

    public String getContaDescricao() {
        return contaDescricao;
    }

    public void setContaDescricao(String contaDescricao) {
        this.contaDescricao = contaDescricao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ImpostoDTO impostoDTO = (ImpostoDTO) o;
        if (impostoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), impostoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ImpostoDTO{" +
            "id=" + getId() +
            ", tipoImposto='" + getTipoImposto() + "'" +
            ", codigoImposto='" + getCodigoImposto() + "'" +
            ", porcentagem='" + isPorcentagem() + "'" +
            ", valor=" + getValor() +
            ", descricao='" + getDescricao() + "'" +
            ", pais='" + getPais() + "'" +
            ", vigencia='" + getVigencia() + "'" +
            ", conta=" + getContaId() +
            ", conta='" + getContaDescricao() + "'" +
            "}";
    }
}
