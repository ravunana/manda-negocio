package com.ravunana.manda.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the {@link com.ravunana.manda.domain.CompostoProduto} entity.
 */
public class CompostoProdutoDTO implements Serializable {

    private Long id;

    @NotNull
    @DecimalMin(value = "1")
    private Double quantidade;

    private BigDecimal valor;

    private Boolean permanente;


    private Long produtoId;

    private String produtoNome;

    private Long unidadeId;

    private String unidadeNome;

    private Long compostoId;

    private String compostoNome;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Double quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Boolean isPermanente() {
        return permanente;
    }

    public void setPermanente(Boolean permanente) {
        this.permanente = permanente;
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

    public Long getUnidadeId() {
        return unidadeId;
    }

    public void setUnidadeId(Long unidadeMedidaId) {
        this.unidadeId = unidadeMedidaId;
    }

    public String getUnidadeNome() {
        return unidadeNome;
    }

    public void setUnidadeNome(String unidadeMedidaNome) {
        this.unidadeNome = unidadeMedidaNome;
    }

    public Long getCompostoId() {
        return compostoId;
    }

    public void setCompostoId(Long produtoId) {
        this.compostoId = produtoId;
    }

    public String getCompostoNome() {
        return compostoNome;
    }

    public void setCompostoNome(String produtoNome) {
        this.compostoNome = produtoNome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CompostoProdutoDTO compostoProdutoDTO = (CompostoProdutoDTO) o;
        if (compostoProdutoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), compostoProdutoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CompostoProdutoDTO{" +
            "id=" + getId() +
            ", quantidade=" + getQuantidade() +
            ", valor=" + getValor() +
            ", permanente='" + isPermanente() + "'" +
            ", produto=" + getProdutoId() +
            ", produto='" + getProdutoNome() + "'" +
            ", unidade=" + getUnidadeId() +
            ", unidade='" + getUnidadeNome() + "'" +
            ", composto=" + getCompostoId() +
            ", composto='" + getCompostoNome() + "'" +
            "}";
    }
}
