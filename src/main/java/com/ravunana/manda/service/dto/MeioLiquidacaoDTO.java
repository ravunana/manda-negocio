package com.ravunana.manda.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.ravunana.manda.domain.MeioLiquidacao} entity.
 */
public class MeioLiquidacaoDTO implements Serializable {

    private Long id;

    
    private String nome;

    
    private String sigla;

    private String icon;

    private Boolean mostrarPontoVenda;


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

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Boolean isMostrarPontoVenda() {
        return mostrarPontoVenda;
    }

    public void setMostrarPontoVenda(Boolean mostrarPontoVenda) {
        this.mostrarPontoVenda = mostrarPontoVenda;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MeioLiquidacaoDTO meioLiquidacaoDTO = (MeioLiquidacaoDTO) o;
        if (meioLiquidacaoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), meioLiquidacaoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MeioLiquidacaoDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", sigla='" + getSigla() + "'" +
            ", icon='" + getIcon() + "'" +
            ", mostrarPontoVenda='" + isMostrarPontoVenda() + "'" +
            "}";
    }
}
