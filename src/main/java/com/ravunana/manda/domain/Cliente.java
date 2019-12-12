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
 * A Cliente.
 */
@Entity
@Table(name = "vnd_cliente")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "ativo")
    private Boolean ativo;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "perfil_profissional")
    private String perfilProfissional;

    @Column(name = "satisfacao")
    private Double satisfacao;

    @Column(name = "frequencia")
    private Double frequencia;

    @Column(name = "canal_usado")
    private String canalUsado;

    @NotNull
    @Column(name = "numero", nullable = false, unique = true)
    private String numero;

    @Column(name = "autofacturacao")
    private Boolean autofacturacao;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private Pessoa pessoa;

    @OneToMany(mappedBy = "cliente")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Venda> vendas = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("clientes")
    private Conta conta;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isAtivo() {
        return ativo;
    }

    public Cliente ativo(Boolean ativo) {
        this.ativo = ativo;
        return this;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public String getPerfilProfissional() {
        return perfilProfissional;
    }

    public Cliente perfilProfissional(String perfilProfissional) {
        this.perfilProfissional = perfilProfissional;
        return this;
    }

    public void setPerfilProfissional(String perfilProfissional) {
        this.perfilProfissional = perfilProfissional;
    }

    public Double getSatisfacao() {
        return satisfacao;
    }

    public Cliente satisfacao(Double satisfacao) {
        this.satisfacao = satisfacao;
        return this;
    }

    public void setSatisfacao(Double satisfacao) {
        this.satisfacao = satisfacao;
    }

    public Double getFrequencia() {
        return frequencia;
    }

    public Cliente frequencia(Double frequencia) {
        this.frequencia = frequencia;
        return this;
    }

    public void setFrequencia(Double frequencia) {
        this.frequencia = frequencia;
    }

    public String getCanalUsado() {
        return canalUsado;
    }

    public Cliente canalUsado(String canalUsado) {
        this.canalUsado = canalUsado;
        return this;
    }

    public void setCanalUsado(String canalUsado) {
        this.canalUsado = canalUsado;
    }

    public String getNumero() {
        return numero;
    }

    public Cliente numero(String numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Boolean isAutofacturacao() {
        return autofacturacao;
    }

    public Cliente autofacturacao(Boolean autofacturacao) {
        this.autofacturacao = autofacturacao;
        return this;
    }

    public void setAutofacturacao(Boolean autofacturacao) {
        this.autofacturacao = autofacturacao;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public Cliente pessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
        return this;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public Set<Venda> getVendas() {
        return vendas;
    }

    public Cliente vendas(Set<Venda> vendas) {
        this.vendas = vendas;
        return this;
    }

    public Cliente addVenda(Venda venda) {
        this.vendas.add(venda);
        venda.setCliente(this);
        return this;
    }

    public Cliente removeVenda(Venda venda) {
        this.vendas.remove(venda);
        venda.setCliente(null);
        return this;
    }

    public void setVendas(Set<Venda> vendas) {
        this.vendas = vendas;
    }

    public Conta getConta() {
        return conta;
    }

    public Cliente conta(Conta conta) {
        this.conta = conta;
        return this;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Cliente)) {
            return false;
        }
        return id != null && id.equals(((Cliente) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Cliente{" +
            "id=" + getId() +
            ", ativo='" + isAtivo() + "'" +
            ", perfilProfissional='" + getPerfilProfissional() + "'" +
            ", satisfacao=" + getSatisfacao() +
            ", frequencia=" + getFrequencia() +
            ", canalUsado='" + getCanalUsado() + "'" +
            ", numero='" + getNumero() + "'" +
            ", autofacturacao='" + isAutofacturacao() + "'" +
            "}";
    }
}
