package com.ravunana.manda.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.ravunana.manda.domain.Moeda} entity.
 */
public class MoedaDTO implements Serializable {

    private Long id;

    @NotNull
    private String nome;

    @NotNull
    @Size(min = 2, max = 10)
    private String codigo;

    private String pais;

    private Boolean nacional;

    private String icon;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public Boolean isNacional() {
        return nacional;
    }

    public void setNacional(Boolean nacional) {
        this.nacional = nacional;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MoedaDTO moedaDTO = (MoedaDTO) o;
        if (moedaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), moedaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MoedaDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", codigo='" + getCodigo() + "'" +
            ", pais='" + getPais() + "'" +
            ", nacional='" + isNacional() + "'" +
            ", icon='" + getIcon() + "'" +
            "}";
    }
}
