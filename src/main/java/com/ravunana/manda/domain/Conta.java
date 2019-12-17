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
 * A Conta.
 */
@Entity
@Table(name = "ctb_conta")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Conta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "descricao", nullable = false)
    private String descricao;

    @NotNull
    @Column(name = "codigo", nullable = false, unique = true)
    private String codigo;

    @Column(name = "tipo")
    private String tipo;

    @Column(name = "grau")
    private Integer grau;

    @Column(name = "natureza")
    private String natureza;

    @Column(name = "grupo")
    private String grupo;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "conteudo")
    private String conteudo;

    @OneToMany(mappedBy = "contaAgregadora")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Conta> contas = new HashSet<>();

    @OneToMany(mappedBy = "contaDebitar")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ContaDebito> contaDebitos = new HashSet<>();

    @OneToMany(mappedBy = "contaCreditar")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ContaCredito> contaCreditos = new HashSet<>();

    @OneToMany(mappedBy = "conta")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<LancamentoFinanceiro> lancamentoFinanceiros = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "ctb_conta_empresa",
               joinColumns = @JoinColumn(name = "conta_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "empresa_id", referencedColumnName = "id"))
    private Set<Empresa> empresas = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("contas")
    private Conta contaAgregadora;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("contas")
    private ClasseConta classeConta;

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

    public Conta descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCodigo() {
        return codigo;
    }

    public Conta codigo(String codigo) {
        this.codigo = codigo;
        return this;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getTipo() {
        return tipo;
    }

    public Conta tipo(String tipo) {
        this.tipo = tipo;
        return this;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getGrau() {
        return grau;
    }

    public Conta grau(Integer grau) {
        this.grau = grau;
        return this;
    }

    public void setGrau(Integer grau) {
        this.grau = grau;
    }

    public String getNatureza() {
        return natureza;
    }

    public Conta natureza(String natureza) {
        this.natureza = natureza;
        return this;
    }

    public void setNatureza(String natureza) {
        this.natureza = natureza;
    }

    public String getGrupo() {
        return grupo;
    }

    public Conta grupo(String grupo) {
        this.grupo = grupo;
        return this;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public String getConteudo() {
        return conteudo;
    }

    public Conta conteudo(String conteudo) {
        this.conteudo = conteudo;
        return this;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public Set<Conta> getContas() {
        return contas;
    }

    public Conta contas(Set<Conta> contas) {
        this.contas = contas;
        return this;
    }

    public Conta addConta(Conta conta) {
        this.contas.add(conta);
        conta.setContaAgregadora(this);
        return this;
    }

    public Conta removeConta(Conta conta) {
        this.contas.remove(conta);
        conta.setContaAgregadora(null);
        return this;
    }

    public void setContas(Set<Conta> contas) {
        this.contas = contas;
    }

    public Set<ContaDebito> getContaDebitos() {
        return contaDebitos;
    }

    public Conta contaDebitos(Set<ContaDebito> contaDebitos) {
        this.contaDebitos = contaDebitos;
        return this;
    }

    public Conta addContaDebito(ContaDebito contaDebito) {
        this.contaDebitos.add(contaDebito);
        contaDebito.setContaDebitar(this);
        return this;
    }

    public Conta removeContaDebito(ContaDebito contaDebito) {
        this.contaDebitos.remove(contaDebito);
        contaDebito.setContaDebitar(null);
        return this;
    }

    public void setContaDebitos(Set<ContaDebito> contaDebitos) {
        this.contaDebitos = contaDebitos;
    }

    public Set<ContaCredito> getContaCreditos() {
        return contaCreditos;
    }

    public Conta contaCreditos(Set<ContaCredito> contaCreditos) {
        this.contaCreditos = contaCreditos;
        return this;
    }

    public Conta addContaCredito(ContaCredito contaCredito) {
        this.contaCreditos.add(contaCredito);
        contaCredito.setContaCreditar(this);
        return this;
    }

    public Conta removeContaCredito(ContaCredito contaCredito) {
        this.contaCreditos.remove(contaCredito);
        contaCredito.setContaCreditar(null);
        return this;
    }

    public void setContaCreditos(Set<ContaCredito> contaCreditos) {
        this.contaCreditos = contaCreditos;
    }

    public Set<LancamentoFinanceiro> getLancamentoFinanceiros() {
        return lancamentoFinanceiros;
    }

    public Conta lancamentoFinanceiros(Set<LancamentoFinanceiro> lancamentoFinanceiros) {
        this.lancamentoFinanceiros = lancamentoFinanceiros;
        return this;
    }

    public Conta addLancamentoFinanceiro(LancamentoFinanceiro lancamentoFinanceiro) {
        this.lancamentoFinanceiros.add(lancamentoFinanceiro);
        lancamentoFinanceiro.setConta(this);
        return this;
    }

    public Conta removeLancamentoFinanceiro(LancamentoFinanceiro lancamentoFinanceiro) {
        this.lancamentoFinanceiros.remove(lancamentoFinanceiro);
        lancamentoFinanceiro.setConta(null);
        return this;
    }

    public void setLancamentoFinanceiros(Set<LancamentoFinanceiro> lancamentoFinanceiros) {
        this.lancamentoFinanceiros = lancamentoFinanceiros;
    }

    public Set<Empresa> getEmpresas() {
        return empresas;
    }

    public Conta empresas(Set<Empresa> empresas) {
        this.empresas = empresas;
        return this;
    }

    public Conta addEmpresa(Empresa empresa) {
        this.empresas.add(empresa);
        empresa.getContas().add(this);
        return this;
    }

    public Conta removeEmpresa(Empresa empresa) {
        this.empresas.remove(empresa);
        empresa.getContas().remove(this);
        return this;
    }

    public void setEmpresas(Set<Empresa> empresas) {
        this.empresas = empresas;
    }

    public Conta getContaAgregadora() {
        return contaAgregadora;
    }

    public Conta contaAgregadora(Conta conta) {
        this.contaAgregadora = conta;
        return this;
    }

    public void setContaAgregadora(Conta conta) {
        this.contaAgregadora = conta;
    }

    public ClasseConta getClasseConta() {
        return classeConta;
    }

    public Conta classeConta(ClasseConta classeConta) {
        this.classeConta = classeConta;
        return this;
    }

    public void setClasseConta(ClasseConta classeConta) {
        this.classeConta = classeConta;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Conta)) {
            return false;
        }
        return id != null && id.equals(((Conta) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Conta{" +
            "id=" + getId() +
            ", descricao='" + getDescricao() + "'" +
            ", codigo='" + getCodigo() + "'" +
            ", tipo='" + getTipo() + "'" +
            ", grau=" + getGrau() +
            ", natureza='" + getNatureza() + "'" +
            ", grupo='" + getGrupo() + "'" +
            ", conteudo='" + getConteudo() + "'" +
            "}";
    }
}
