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
 * A LocalArmazenamento.
 */
@Entity
@Table(name = "stk_local_armazenamento")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class LocalArmazenamento implements Serializable {

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

    @OneToMany(mappedBy = "localArmazenamento")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Produto> produtos = new HashSet<>();

    @OneToMany(mappedBy = "hierarquia")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<LocalArmazenamento> localArmazenamentos = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("localArmazenamentos")
    private LocalArmazenamento hierarquia;

    @ManyToOne
    @JsonIgnoreProperties("localArmazenamentos")
    private Empresa empresa;

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

    public LocalArmazenamento nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public LocalArmazenamento descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Set<Produto> getProdutos() {
        return produtos;
    }

    public LocalArmazenamento produtos(Set<Produto> produtos) {
        this.produtos = produtos;
        return this;
    }

    public LocalArmazenamento addProduto(Produto produto) {
        this.produtos.add(produto);
        produto.setLocalArmazenamento(this);
        return this;
    }

    public LocalArmazenamento removeProduto(Produto produto) {
        this.produtos.remove(produto);
        produto.setLocalArmazenamento(null);
        return this;
    }

    public void setProdutos(Set<Produto> produtos) {
        this.produtos = produtos;
    }

    public Set<LocalArmazenamento> getLocalArmazenamentos() {
        return localArmazenamentos;
    }

    public LocalArmazenamento localArmazenamentos(Set<LocalArmazenamento> localArmazenamentos) {
        this.localArmazenamentos = localArmazenamentos;
        return this;
    }

    public LocalArmazenamento addLocalArmazenamento(LocalArmazenamento localArmazenamento) {
        this.localArmazenamentos.add(localArmazenamento);
        localArmazenamento.setHierarquia(this);
        return this;
    }

    public LocalArmazenamento removeLocalArmazenamento(LocalArmazenamento localArmazenamento) {
        this.localArmazenamentos.remove(localArmazenamento);
        localArmazenamento.setHierarquia(null);
        return this;
    }

    public void setLocalArmazenamentos(Set<LocalArmazenamento> localArmazenamentos) {
        this.localArmazenamentos = localArmazenamentos;
    }

    public LocalArmazenamento getHierarquia() {
        return hierarquia;
    }

    public LocalArmazenamento hierarquia(LocalArmazenamento localArmazenamento) {
        this.hierarquia = localArmazenamento;
        return this;
    }

    public void setHierarquia(LocalArmazenamento localArmazenamento) {
        this.hierarquia = localArmazenamento;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public LocalArmazenamento empresa(Empresa empresa) {
        this.empresa = empresa;
        return this;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LocalArmazenamento)) {
            return false;
        }
        return id != null && id.equals(((LocalArmazenamento) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "LocalArmazenamento{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", descricao='" + getDescricao() + "'" +
            "}";
    }
}
