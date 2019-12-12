package com.ravunana.manda.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.ravunana.manda.domain.VariacaoProduto} entity.
 */
public class VariacaoProdutoDTO implements Serializable {

    private Long id;

    private String genero;

    private String cor;

    private String modelo;

    private String marca;

    private String tamanho;


    private Long produtoId;

    private String produtoNome;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getTamanho() {
        return tamanho;
    }

    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
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

        VariacaoProdutoDTO variacaoProdutoDTO = (VariacaoProdutoDTO) o;
        if (variacaoProdutoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), variacaoProdutoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "VariacaoProdutoDTO{" +
            "id=" + getId() +
            ", genero='" + getGenero() + "'" +
            ", cor='" + getCor() + "'" +
            ", modelo='" + getModelo() + "'" +
            ", marca='" + getMarca() + "'" +
            ", tamanho='" + getTamanho() + "'" +
            ", produto=" + getProdutoId() +
            ", produto='" + getProdutoNome() + "'" +
            "}";
    }
}
