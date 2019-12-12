package com.ravunana.manda.domain;
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
 * A FamiliaProduto.
 */
@Entity
@Table(name = "stk_familia")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class FamiliaProduto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "nome", nullable = false, unique = true)
    private String nome;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "descricao")
    private String descricao;

    @OneToMany(mappedBy = "hierarquia")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<FamiliaProduto> familiaProdutos = new HashSet<>();

    @OneToMany(mappedBy = "familia")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Produto> produtos = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("familiaProdutos")
    private Conta conta;

    @ManyToOne
    @JsonIgnoreProperties("familiaProdutos")
    private FamiliaProduto hierarquia;

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

    public FamiliaProduto nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public FamiliaProduto descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Set<FamiliaProduto> getFamiliaProdutos() {
        return familiaProdutos;
    }

    public FamiliaProduto familiaProdutos(Set<FamiliaProduto> familiaProdutos) {
        this.familiaProdutos = familiaProdutos;
        return this;
    }

    public FamiliaProduto addFamiliaProduto(FamiliaProduto familiaProduto) {
        this.familiaProdutos.add(familiaProduto);
        familiaProduto.setHierarquia(this);
        return this;
    }

    public FamiliaProduto removeFamiliaProduto(FamiliaProduto familiaProduto) {
        this.familiaProdutos.remove(familiaProduto);
        familiaProduto.setHierarquia(null);
        return this;
    }

    public void setFamiliaProdutos(Set<FamiliaProduto> familiaProdutos) {
        this.familiaProdutos = familiaProdutos;
    }

    public Set<Produto> getProdutos() {
        return produtos;
    }

    public FamiliaProduto produtos(Set<Produto> produtos) {
        this.produtos = produtos;
        return this;
    }

    public FamiliaProduto addProduto(Produto produto) {
        this.produtos.add(produto);
        produto.setFamilia(this);
        return this;
    }

    public FamiliaProduto removeProduto(Produto produto) {
        this.produtos.remove(produto);
        produto.setFamilia(null);
        return this;
    }

    public void setProdutos(Set<Produto> produtos) {
        this.produtos = produtos;
    }

    public Conta getConta() {
        return conta;
    }

    public FamiliaProduto conta(Conta conta) {
        this.conta = conta;
        return this;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }

    public FamiliaProduto getHierarquia() {
        return hierarquia;
    }

    public FamiliaProduto hierarquia(FamiliaProduto familiaProduto) {
        this.hierarquia = familiaProduto;
        return this;
    }

    public void setHierarquia(FamiliaProduto familiaProduto) {
        this.hierarquia = familiaProduto;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FamiliaProduto)) {
            return false;
        }
        return id != null && id.equals(((FamiliaProduto) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "FamiliaProduto{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", descricao='" + getDescricao() + "'" +
            "}";
    }
}
