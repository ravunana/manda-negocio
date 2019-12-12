package com.ravunana.manda.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Fornecedor.
 */
@Entity
@Table(name = "cmp_fornecedor")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Fornecedor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "certificado_iso_9001")
    private Boolean certificadoISO9001;

    @Column(name = "garantia_defeito_fabrica")
    private Boolean garantiaDefeitoFabrica;

    @Column(name = "transporte")
    private Boolean transporte;

    @DecimalMin(value = "0")
    @DecimalMax(value = "10")
    @Column(name = "qualidade")
    private Double qualidade;

    @Column(name = "pagamento_prazo")
    private Boolean pagamentoPrazo;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "metodos_pagamento")
    private String metodosPagamento;

    @NotNull
    @DecimalMin(value = "0")
    @DecimalMax(value = "10")
    @Column(name = "classificacao", nullable = false)
    private Double classificacao;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "descricao")
    private String descricao;

    @Column(name = "ativo")
    private Boolean ativo;

    @NotNull
    @Column(name = "numero", nullable = false, unique = true)
    private String numero;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private Pessoa pessoa;

    @OneToMany(mappedBy = "fornecedor")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ItemCompra> itemCompras = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("fornecedors")
    private Conta conta;

    @ManyToMany(mappedBy = "fornecedors")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Produto> produtos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isCertificadoISO9001() {
        return certificadoISO9001;
    }

    public Fornecedor certificadoISO9001(Boolean certificadoISO9001) {
        this.certificadoISO9001 = certificadoISO9001;
        return this;
    }

    public void setCertificadoISO9001(Boolean certificadoISO9001) {
        this.certificadoISO9001 = certificadoISO9001;
    }

    public Boolean isGarantiaDefeitoFabrica() {
        return garantiaDefeitoFabrica;
    }

    public Fornecedor garantiaDefeitoFabrica(Boolean garantiaDefeitoFabrica) {
        this.garantiaDefeitoFabrica = garantiaDefeitoFabrica;
        return this;
    }

    public void setGarantiaDefeitoFabrica(Boolean garantiaDefeitoFabrica) {
        this.garantiaDefeitoFabrica = garantiaDefeitoFabrica;
    }

    public Boolean isTransporte() {
        return transporte;
    }

    public Fornecedor transporte(Boolean transporte) {
        this.transporte = transporte;
        return this;
    }

    public void setTransporte(Boolean transporte) {
        this.transporte = transporte;
    }

    public Double getQualidade() {
        return qualidade;
    }

    public Fornecedor qualidade(Double qualidade) {
        this.qualidade = qualidade;
        return this;
    }

    public void setQualidade(Double qualidade) {
        this.qualidade = qualidade;
    }

    public Boolean isPagamentoPrazo() {
        return pagamentoPrazo;
    }

    public Fornecedor pagamentoPrazo(Boolean pagamentoPrazo) {
        this.pagamentoPrazo = pagamentoPrazo;
        return this;
    }

    public void setPagamentoPrazo(Boolean pagamentoPrazo) {
        this.pagamentoPrazo = pagamentoPrazo;
    }

    public String getMetodosPagamento() {
        return metodosPagamento;
    }

    public Fornecedor metodosPagamento(String metodosPagamento) {
        this.metodosPagamento = metodosPagamento;
        return this;
    }

    public void setMetodosPagamento(String metodosPagamento) {
        this.metodosPagamento = metodosPagamento;
    }

    public Double getClassificacao() {
        return classificacao;
    }

    public Fornecedor classificacao(Double classificacao) {
        this.classificacao = classificacao;
        return this;
    }

    public void setClassificacao(Double classificacao) {
        this.classificacao = classificacao;
    }

    public String getDescricao() {
        return descricao;
    }

    public Fornecedor descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Boolean isAtivo() {
        return ativo;
    }

    public Fornecedor ativo(Boolean ativo) {
        this.ativo = ativo;
        return this;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public String getNumero() {
        return numero;
    }

    public Fornecedor numero(String numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public Fornecedor pessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
        return this;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public Set<ItemCompra> getItemCompras() {
        return itemCompras;
    }

    public Fornecedor itemCompras(Set<ItemCompra> itemCompras) {
        this.itemCompras = itemCompras;
        return this;
    }

    public Fornecedor addItemCompra(ItemCompra itemCompra) {
        this.itemCompras.add(itemCompra);
        itemCompra.setFornecedor(this);
        return this;
    }

    public Fornecedor removeItemCompra(ItemCompra itemCompra) {
        this.itemCompras.remove(itemCompra);
        itemCompra.setFornecedor(null);
        return this;
    }

    public void setItemCompras(Set<ItemCompra> itemCompras) {
        this.itemCompras = itemCompras;
    }

    public Conta getConta() {
        return conta;
    }

    public Fornecedor conta(Conta conta) {
        this.conta = conta;
        return this;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }

    public Set<Produto> getProdutos() {
        return produtos;
    }

    public Fornecedor produtos(Set<Produto> produtos) {
        this.produtos = produtos;
        return this;
    }

    public Fornecedor addProduto(Produto produto) {
        this.produtos.add(produto);
        produto.getFornecedors().add(this);
        return this;
    }

    public Fornecedor removeProduto(Produto produto) {
        this.produtos.remove(produto);
        produto.getFornecedors().remove(this);
        return this;
    }

    public void setProdutos(Set<Produto> produtos) {
        this.produtos = produtos;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Fornecedor)) {
            return false;
        }
        return id != null && id.equals(((Fornecedor) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Fornecedor{" +
            "id=" + getId() +
            ", certificadoISO9001='" + isCertificadoISO9001() + "'" +
            ", garantiaDefeitoFabrica='" + isGarantiaDefeitoFabrica() + "'" +
            ", transporte='" + isTransporte() + "'" +
            ", qualidade=" + getQualidade() +
            ", pagamentoPrazo='" + isPagamentoPrazo() + "'" +
            ", metodosPagamento='" + getMetodosPagamento() + "'" +
            ", classificacao=" + getClassificacao() +
            ", descricao='" + getDescricao() + "'" +
            ", ativo='" + isAtivo() + "'" +
            ", numero='" + getNumero() + "'" +
            "}";
    }
}
