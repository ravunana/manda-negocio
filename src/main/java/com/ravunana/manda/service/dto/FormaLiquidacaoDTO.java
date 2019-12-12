package com.ravunana.manda.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.ravunana.manda.domain.FormaLiquidacao} entity.
 */
public class FormaLiquidacaoDTO implements Serializable {

    private Long id;

    @NotNull
    private String nome;

    @NotNull
    @DecimalMin(value = "0")
    @DecimalMax(value = "100")
    private Double juro;

    @NotNull
    @Min(value = 0)
    private Integer prazoLiquidacao;

    @NotNull
    @Min(value = 1)
    private Integer quantidade;

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

    public Double getJuro() {
        return juro;
    }

    public void setJuro(Double juro) {
        this.juro = juro;
    }

    public Integer getPrazoLiquidacao() {
        return prazoLiquidacao;
    }

    public void setPrazoLiquidacao(Integer prazoLiquidacao) {
        this.prazoLiquidacao = prazoLiquidacao;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
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

        FormaLiquidacaoDTO formaLiquidacaoDTO = (FormaLiquidacaoDTO) o;
        if (formaLiquidacaoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), formaLiquidacaoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FormaLiquidacaoDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", juro=" + getJuro() +
            ", prazoLiquidacao=" + getPrazoLiquidacao() +
            ", quantidade=" + getQuantidade() +
            ", icon='" + getIcon() + "'" +
            "}";
    }
}
