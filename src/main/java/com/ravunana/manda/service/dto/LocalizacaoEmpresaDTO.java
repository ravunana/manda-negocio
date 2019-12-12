package com.ravunana.manda.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.ravunana.manda.domain.LocalizacaoEmpresa} entity.
 */
public class LocalizacaoEmpresaDTO implements Serializable {

    private Long id;

    private String pais;

    private String provincia;

    private String municipio;

    @NotNull
    private String bairro;

    @NotNull
    @Size(max = 200)
    private String rua;

    @Size(max = 10)
    private String quarteirao;

    @Size(max = 10)
    private String numeroPorta;

    private String caixaPostal;


    private Long empresaId;

    private String empresaNome;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getQuarteirao() {
        return quarteirao;
    }

    public void setQuarteirao(String quarteirao) {
        this.quarteirao = quarteirao;
    }

    public String getNumeroPorta() {
        return numeroPorta;
    }

    public void setNumeroPorta(String numeroPorta) {
        this.numeroPorta = numeroPorta;
    }

    public String getCaixaPostal() {
        return caixaPostal;
    }

    public void setCaixaPostal(String caixaPostal) {
        this.caixaPostal = caixaPostal;
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

        LocalizacaoEmpresaDTO localizacaoEmpresaDTO = (LocalizacaoEmpresaDTO) o;
        if (localizacaoEmpresaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), localizacaoEmpresaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LocalizacaoEmpresaDTO{" +
            "id=" + getId() +
            ", pais='" + getPais() + "'" +
            ", provincia='" + getProvincia() + "'" +
            ", municipio='" + getMunicipio() + "'" +
            ", bairro='" + getBairro() + "'" +
            ", rua='" + getRua() + "'" +
            ", quarteirao='" + getQuarteirao() + "'" +
            ", numeroPorta='" + getNumeroPorta() + "'" +
            ", caixaPostal='" + getCaixaPostal() + "'" +
            ", empresa=" + getEmpresaId() +
            ", empresa='" + getEmpresaNome() + "'" +
            "}";
    }
}
