package com.ravunana.manda.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A VariacaoProduto.
 */
@Entity
@Table(name = "stk_variacao_produto")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class VariacaoProduto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "genero")
    private String genero;

    @Column(name = "cor")
    private String cor;

    @Column(name = "modelo")
    private String modelo;

    @Column(name = "marca")
    private String marca;

    @Column(name = "tamanho")
    private String tamanho;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private Produto produto;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGenero() {
        return genero;
    }

    public VariacaoProduto genero(String genero) {
        this.genero = genero;
        return this;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getCor() {
        return cor;
    }

    public VariacaoProduto cor(String cor) {
        this.cor = cor;
        return this;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getModelo() {
        return modelo;
    }

    public VariacaoProduto modelo(String modelo) {
        this.modelo = modelo;
        return this;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMarca() {
        return marca;
    }

    public VariacaoProduto marca(String marca) {
        this.marca = marca;
        return this;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getTamanho() {
        return tamanho;
    }

    public VariacaoProduto tamanho(String tamanho) {
        this.tamanho = tamanho;
        return this;
    }

    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }

    public Produto getProduto() {
        return produto;
    }

    public VariacaoProduto produto(Produto produto) {
        this.produto = produto;
        return this;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VariacaoProduto)) {
            return false;
        }
        return id != null && id.equals(((VariacaoProduto) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "VariacaoProduto{" +
            "id=" + getId() +
            ", genero='" + getGenero() + "'" +
            ", cor='" + getCor() + "'" +
            ", modelo='" + getModelo() + "'" +
            ", marca='" + getMarca() + "'" +
            ", tamanho='" + getTamanho() + "'" +
            "}";
    }
}
