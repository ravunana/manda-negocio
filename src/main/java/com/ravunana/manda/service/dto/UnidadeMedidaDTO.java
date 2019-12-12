package com.ravunana.manda.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.ravunana.manda.domain.UnidadeMedida} entity.
 */
public class UnidadeMedidaDTO implements Serializable {

    private Long id;

    @NotNull
    private String nome;

    @NotNull
    private String sigla;

    @NotNull
    @DecimalMin(value = "0")
    private Double valor;


    private Long unidadeConversaoId;

    private String unidadeConversaoNome;

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

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Long getUnidadeConversaoId() {
        return unidadeConversaoId;
    }

    public void setUnidadeConversaoId(Long unidadeMedidaId) {
        this.unidadeConversaoId = unidadeMedidaId;
    }

    public String getUnidadeConversaoNome() {
        return unidadeConversaoNome;
    }

    public void setUnidadeConversaoNome(String unidadeMedidaNome) {
        this.unidadeConversaoNome = unidadeMedidaNome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UnidadeMedidaDTO unidadeMedidaDTO = (UnidadeMedidaDTO) o;
        if (unidadeMedidaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), unidadeMedidaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UnidadeMedidaDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", sigla='" + getSigla() + "'" +
            ", valor=" + getValor() +
            ", unidadeConversao=" + getUnidadeConversaoId() +
            ", unidadeConversao='" + getUnidadeConversaoNome() + "'" +
            "}";
    }
}
