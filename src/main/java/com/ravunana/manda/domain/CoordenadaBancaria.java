package com.ravunana.manda.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A CoordenadaBancaria.
 */
@Entity
@Table(name = "core_coordenada_bancaria")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CoordenadaBancaria implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "descricao", nullable = false)
    private String descricao;

    @NotNull
    @Column(name = "proprietario", nullable = false)
    private String proprietario;

    @NotNull
    @Column(name = "numero_conta", nullable = false, unique = true)
    private String numeroConta;

    
    @Column(name = "iban", unique = true)
    private String iban;

    @NotNull
    @Column(name = "ativo", nullable = false)
    private Boolean ativo;

    @Column(name = "mostrar_documento")
    private Boolean mostrarDocumento;

    @Column(name = "mostrar_ponto_venda")
    private Boolean mostrarPontoVenda;

    @Column(name = "padrao_recebimento")
    private Boolean padraoRecebimento;

    @Column(name = "padrao_pagamento")
    private Boolean padraoPagamento;

    @OneToMany(mappedBy = "coordenada")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DetalheLancamento> detalheLancamentos = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("coordenadaBancarias")
    private Conta conta;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @NotNull
    @JoinTable(name = "core_coordenada_bancaria_empresa",
               joinColumns = @JoinColumn(name = "coordenada_bancaria_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "empresa_id", referencedColumnName = "id"))
    private Set<Empresa> empresas = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public CoordenadaBancaria descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getProprietario() {
        return proprietario;
    }

    public CoordenadaBancaria proprietario(String proprietario) {
        this.proprietario = proprietario;
        return this;
    }

    public void setProprietario(String proprietario) {
        this.proprietario = proprietario;
    }

    public String getNumeroConta() {
        return numeroConta;
    }

    public CoordenadaBancaria numeroConta(String numeroConta) {
        this.numeroConta = numeroConta;
        return this;
    }

    public void setNumeroConta(String numeroConta) {
        this.numeroConta = numeroConta;
    }

    public String getIban() {
        return iban;
    }

    public CoordenadaBancaria iban(String iban) {
        this.iban = iban;
        return this;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public Boolean isAtivo() {
        return ativo;
    }

    public CoordenadaBancaria ativo(Boolean ativo) {
        this.ativo = ativo;
        return this;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Boolean isMostrarDocumento() {
        return mostrarDocumento;
    }

    public CoordenadaBancaria mostrarDocumento(Boolean mostrarDocumento) {
        this.mostrarDocumento = mostrarDocumento;
        return this;
    }

    public void setMostrarDocumento(Boolean mostrarDocumento) {
        this.mostrarDocumento = mostrarDocumento;
    }

    public Boolean isMostrarPontoVenda() {
        return mostrarPontoVenda;
    }

    public CoordenadaBancaria mostrarPontoVenda(Boolean mostrarPontoVenda) {
        this.mostrarPontoVenda = mostrarPontoVenda;
        return this;
    }

    public void setMostrarPontoVenda(Boolean mostrarPontoVenda) {
        this.mostrarPontoVenda = mostrarPontoVenda;
    }

    public Boolean isPadraoRecebimento() {
        return padraoRecebimento;
    }

    public CoordenadaBancaria padraoRecebimento(Boolean padraoRecebimento) {
        this.padraoRecebimento = padraoRecebimento;
        return this;
    }

    public void setPadraoRecebimento(Boolean padraoRecebimento) {
        this.padraoRecebimento = padraoRecebimento;
    }

    public Boolean isPadraoPagamento() {
        return padraoPagamento;
    }

    public CoordenadaBancaria padraoPagamento(Boolean padraoPagamento) {
        this.padraoPagamento = padraoPagamento;
        return this;
    }

    public void setPadraoPagamento(Boolean padraoPagamento) {
        this.padraoPagamento = padraoPagamento;
    }

    public Set<DetalheLancamento> getDetalheLancamentos() {
        return detalheLancamentos;
    }

    public CoordenadaBancaria detalheLancamentos(Set<DetalheLancamento> detalheLancamentos) {
        this.detalheLancamentos = detalheLancamentos;
        return this;
    }

    public CoordenadaBancaria addDetalheLancamento(DetalheLancamento detalheLancamento) {
        this.detalheLancamentos.add(detalheLancamento);
        detalheLancamento.setCoordenada(this);
        return this;
    }

    public CoordenadaBancaria removeDetalheLancamento(DetalheLancamento detalheLancamento) {
        this.detalheLancamentos.remove(detalheLancamento);
        detalheLancamento.setCoordenada(null);
        return this;
    }

    public void setDetalheLancamentos(Set<DetalheLancamento> detalheLancamentos) {
        this.detalheLancamentos = detalheLancamentos;
    }

    public Conta getConta() {
        return conta;
    }

    public CoordenadaBancaria conta(Conta conta) {
        this.conta = conta;
        return this;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }

    public Set<Empresa> getEmpresas() {
        return empresas;
    }

    public CoordenadaBancaria empresas(Set<Empresa> empresas) {
        this.empresas = empresas;
        return this;
    }

    public CoordenadaBancaria addEmpresa(Empresa empresa) {
        this.empresas.add(empresa);
        empresa.getCoordenadaBancarias().add(this);
        return this;
    }

    public CoordenadaBancaria removeEmpresa(Empresa empresa) {
        this.empresas.remove(empresa);
        empresa.getCoordenadaBancarias().remove(this);
        return this;
    }

    public void setEmpresas(Set<Empresa> empresas) {
        this.empresas = empresas;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CoordenadaBancaria)) {
            return false;
        }
        return id != null && id.equals(((CoordenadaBancaria) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CoordenadaBancaria{" +
            "id=" + getId() +
            ", descricao='" + getDescricao() + "'" +
            ", proprietario='" + getProprietario() + "'" +
            ", numeroConta='" + getNumeroConta() + "'" +
            ", iban='" + getIban() + "'" +
            ", ativo='" + isAtivo() + "'" +
            ", mostrarDocumento='" + isMostrarDocumento() + "'" +
            ", mostrarPontoVenda='" + isMostrarPontoVenda() + "'" +
            ", padraoRecebimento='" + isPadraoRecebimento() + "'" +
            ", padraoPagamento='" + isPadraoPagamento() + "'" +
            "}";
    }
}
