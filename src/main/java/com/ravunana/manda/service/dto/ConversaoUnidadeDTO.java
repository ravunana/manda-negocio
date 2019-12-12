package com.ravunana.manda.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.ravunana.manda.domain.ConversaoUnidade} entity.
 */
public class ConversaoUnidadeDTO implements Serializable {

    private Long id;

    @NotNull
    @DecimalMin(value = "1")
    private Double valorEntrada;

    @NotNull
    @DecimalMin(value = "1")
    private Double valorSaida;


    private Long entradaId;

    private String entradaNome;

    private Long saidaId;

    private String saidaNome;

    private Long produtoId;

    private String produtoNome;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getValorEntrada() {
        return valorEntrada;
    }

    public void setValorEntrada(Double valorEntrada) {
        this.valorEntrada = valorEntrada;
    }

    public Double getValorSaida() {
        return valorSaida;
    }

    public void setValorSaida(Double valorSaida) {
        this.valorSaida = valorSaida;
    }

    public Long getEntradaId() {
        return entradaId;
    }

    public void setEntradaId(Long unidadeMedidaId) {
        this.entradaId = unidadeMedidaId;
    }

    public String getEntradaNome() {
        return entradaNome;
    }

    public void setEntradaNome(String unidadeMedidaNome) {
        this.entradaNome = unidadeMedidaNome;
    }

    public Long getSaidaId() {
        return saidaId;
    }

    public void setSaidaId(Long unidadeMedidaId) {
        this.saidaId = unidadeMedidaId;
    }

    public String getSaidaNome() {
        return saidaNome;
    }

    public void setSaidaNome(String unidadeMedidaNome) {
        this.saidaNome = unidadeMedidaNome;
    }

    public Long getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Long produtoId) {
        this.produtoId = produtoId;
    }

    public String getProdutoNome() {
        return produtoNome;
    }

    public void setProdutoNome(String produtoNome) {
        this.produtoNome = produtoNome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ConversaoUnidadeDTO conversaoUnidadeDTO = (ConversaoUnidadeDTO) o;
        if (conversaoUnidadeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), conversaoUnidadeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ConversaoUnidadeDTO{" +
            "id=" + getId() +
            ", valorEntrada=" + getValorEntrada() +
            ", valorSaida=" + getValorSaida() +
            ", entrada=" + getEntradaId() +
            ", entrada='" + getEntradaNome() + "'" +
            ", saida=" + getSaidaId() +
            ", saida='" + getSaidaNome() + "'" +
            ", produto=" + getProdutoId() +
            ", produto='" + getProdutoNome() + "'" +
            "}";
    }
}
