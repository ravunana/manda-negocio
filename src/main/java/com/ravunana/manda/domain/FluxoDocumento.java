package com.ravunana.manda.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.ravunana.manda.domain.enumeration.EntidadeSistema;

/**
 * A FluxoDocumento.
 */
@Entity
@Table(name = "core_fluxo_documento")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class FluxoDocumento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "nome", nullable = false, unique = true)
    private String nome;

    @Column(name = "aumenta_estoque")
    private Boolean aumentaEstoque;

    @Column(name = "aumenta_valor_caixa")
    private Boolean aumentaValorCaixa;

    @Column(name = "cor")
    private String cor;

    @NotNull
    @Column(name = "padrao", nullable = false)
    private Boolean padrao;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "descricao")
    private String descricao;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "entidade", nullable = false)
    private EntidadeSistema entidade;

    @OneToMany(mappedBy = "estado")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Produto> produtos = new HashSet<>();

    @OneToMany(mappedBy = "status")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ItemCompra> itemCompras = new HashSet<>();

    @OneToMany(mappedBy = "status")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ItemVenda> itemVendas = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public FluxoDocumento nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Boolean isAumentaEstoque() {
        return aumentaEstoque;
    }

    public FluxoDocumento aumentaEstoque(Boolean aumentaEstoque) {
        this.aumentaEstoque = aumentaEstoque;
        return this;
    }

    public void setAumentaEstoque(Boolean aumentaEstoque) {
        this.aumentaEstoque = aumentaEstoque;
    }

    public Boolean isAumentaValorCaixa() {
        return aumentaValorCaixa;
    }

    public FluxoDocumento aumentaValorCaixa(Boolean aumentaValorCaixa) {
        this.aumentaValorCaixa = aumentaValorCaixa;
        return this;
    }

    public void setAumentaValorCaixa(Boolean aumentaValorCaixa) {
        this.aumentaValorCaixa = aumentaValorCaixa;
    }

    public String getCor() {
        return cor;
    }

    public FluxoDocumento cor(String cor) {
        this.cor = cor;
        return this;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public Boolean isPadrao() {
        return padrao;
    }

    public FluxoDocumento padrao(Boolean padrao) {
        this.padrao = padrao;
        return this;
    }

    public void setPadrao(Boolean padrao) {
        this.padrao = padrao;
    }

    public String getDescricao() {
        return descricao;
    }

    public FluxoDocumento descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public EntidadeSistema getEntidade() {
        return entidade;
    }

    public FluxoDocumento entidade(EntidadeSistema entidade) {
        this.entidade = entidade;
        return this;
    }

    public void setEntidade(EntidadeSistema entidade) {
        this.entidade = entidade;
    }

    public Set<Produto> getProdutos() {
        return produtos;
    }

    public FluxoDocumento produtos(Set<Produto> produtos) {
        this.produtos = produtos;
        return this;
    }

    public FluxoDocumento addProduto(Produto produto) {
        this.produtos.add(produto);
        produto.setEstado(this);
        return this;
    }

    public FluxoDocumento removeProduto(Produto produto) {
        this.produtos.remove(produto);
        produto.setEstado(null);
        return this;
    }

    public void setProdutos(Set<Produto> produtos) {
        this.produtos = produtos;
    }

    public Set<ItemCompra> getItemCompras() {
        return itemCompras;
    }

    public FluxoDocumento itemCompras(Set<ItemCompra> itemCompras) {
        this.itemCompras = itemCompras;
        return this;
    }

    public FluxoDocumento addItemCompra(ItemCompra itemCompra) {
        this.itemCompras.add(itemCompra);
        itemCompra.setStatus(this);
        return this;
    }

    public FluxoDocumento removeItemCompra(ItemCompra itemCompra) {
        this.itemCompras.remove(itemCompra);
        itemCompra.setStatus(null);
        return this;
    }

    public void setItemCompras(Set<ItemCompra> itemCompras) {
        this.itemCompras = itemCompras;
    }

    public Set<ItemVenda> getItemVendas() {
        return itemVendas;
    }

    public FluxoDocumento itemVendas(Set<ItemVenda> itemVendas) {
        this.itemVendas = itemVendas;
        return this;
    }

    public FluxoDocumento addItemVenda(ItemVenda itemVenda) {
        this.itemVendas.add(itemVenda);
        itemVenda.setStatus(this);
        return this;
    }

    public FluxoDocumento removeItemVenda(ItemVenda itemVenda) {
        this.itemVendas.remove(itemVenda);
        itemVenda.setStatus(null);
        return this;
    }

    public void setItemVendas(Set<ItemVenda> itemVendas) {
        this.itemVendas = itemVendas;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FluxoDocumento)) {
            return false;
        }
        return id != null && id.equals(((FluxoDocumento) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "FluxoDocumento{" +
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
