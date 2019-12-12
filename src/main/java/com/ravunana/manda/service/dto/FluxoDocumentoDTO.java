package com.ravunana.manda.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;
import com.ravunana.manda.domain.enumeration.EntidadeSistema;

/**
 * A DTO for the {@link com.ravunana.manda.domain.FluxoDocumento} entity.
 */
public class FluxoDocumentoDTO implements Serializable {

    private Long id;

    @NotNull
    private String nome;

    private Boolean aumentaEstoque;

    private Boolean aumentaValorCaixa;

    private String cor;

    @NotNull
    private Boolean padrao;

    @Lob
    private String descricao;

    @NotNull
    private EntidadeSistema entidade;


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

    public Boolean isAumentaEstoque() {
        return aumentaEstoque;
    }

    public void setAumentaEstoque(Boolean aumentaEstoque) {
        this.aumentaEstoque = aumentaEstoque;
    }

    public Boolean isAumentaValorCaixa() {
        return aumentaValorCaixa;
    }

    public void setAumentaValorCaixa(Boolean aumentaValorCaixa) {
        this.aumentaValorCaixa = aumentaValorCaixa;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public Boolean isPadrao() {
        return padrao;
    }

    public void setPadrao(Boolean padrao) {
        this.padrao = padrao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public EntidadeSistema getEntidade() {
        return entidade;
    }

    public void setEntidade(EntidadeSistema entidade) {
        this.entidade = entidade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FluxoDocumentoDTO fluxoDocumentoDTO = (FluxoDocumentoDTO) o;
        if (fluxoDocumentoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), fluxoDocumentoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FluxoDocumentoDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", aumentaEstoque='" + isAumentaEstoque() + "'" +
            ", aumentaValorCaixa='" + isAumentaValorCaixa() + "'" +
            ", cor='" + getCor() + "'" +
            ", padrao='" + isPadrao() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", entidade='" + getEntidade() + "'" +
            "}";
    }
}
