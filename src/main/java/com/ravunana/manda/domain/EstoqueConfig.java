package com.ravunana.manda.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A EstoqueConfig.
 */
@Entity
@Table(name = "stk_estoque_config")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class EstoqueConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private Produto produto;

    @ManyToOne
    @JsonIgnoreProperties("estoqueConfigs")
    private Conta contaVenda;

    @ManyToOne
    @JsonIgnoreProperties("estoqueConfigs")
    private Conta contaCompra;

    @ManyToOne
    @JsonIgnoreProperties("estoqueConfigs")
    private Conta contaImobilizado;

    @ManyToOne
    @JsonIgnoreProperties("estoqueConfigs")
    private Conta devolucaoCompra;

    @ManyToOne
    @JsonIgnoreProperties("estoqueConfigs")
    private Conta devolucaoVenda;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Produto getProduto() {
        return produto;
    }

    public EstoqueConfig produto(Produto produto) {
        this.produto = produto;
        return this;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Conta getContaVenda() {
        return contaVenda;
    }

    public EstoqueConfig contaVenda(Conta conta) {
        this.contaVenda = conta;
        return this;
    }

    public void setContaVenda(Conta conta) {
        this.contaVenda = conta;
    }

    public Conta getContaCompra() {
        return contaCompra;
    }

    public EstoqueConfig contaCompra(Conta conta) {
        this.contaCompra = conta;
        return this;
    }

    public void setContaCompra(Conta conta) {
        this.contaCompra = conta;
    }

    public Conta getContaImobilizado() {
        return contaImobilizado;
    }

    public EstoqueConfig contaImobilizado(Conta conta) {
        this.contaImobilizado = conta;
        return this;
    }

    public void setContaImobilizado(Conta conta) {
        this.contaImobilizado = conta;
    }

    public Conta getDevolucaoCompra() {
        return devolucaoCompra;
    }

    public EstoqueConfig devolucaoCompra(Conta conta) {
        this.devolucaoCompra = conta;
        return this;
    }

    public void setDevolucaoCompra(Conta conta) {
        this.devolucaoCompra = conta;
    }

    public Conta getDevolucaoVenda() {
        return devolucaoVenda;
    }

    public EstoqueConfig devolucaoVenda(Conta conta) {
        this.devolucaoVenda = conta;
        return this;
    }

    public void setDevolucaoVenda(Conta conta) {
        this.devolucaoVenda = conta;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EstoqueConfig)) {
            return false;
        }
        return id != null && id.equals(((EstoqueConfig) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "EstoqueConfig{" +
            "id=" + getId() +
            "}";
    }
}
