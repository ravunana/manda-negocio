package com.ravunana.manda.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Imposto.
 */
@Entity
@Table(name = "tbr_imposto")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Imposto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "tipo_imposto", nullable = false)
    private String tipoImposto;

    @NotNull
    @Column(name = "codigo_imposto", nullable = false)
    private String codigoImposto;

    @NotNull
    @Column(name = "porcentagem", nullable = false)
    private Boolean porcentagem;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "valor", precision = 21, scale = 2, nullable = false)
    private BigDecimal valor;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "descricao")
    private String descricao;

    @Column(name = "pais")
    private String pais;

    @Column(name = "vigencia")
    private LocalDate vigencia;

    @OneToMany(mappedBy = "imposto")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<GrupoTributacaoImposto> grupoTributacaoImpostos = new HashSet<>();

    @OneToMany(mappedBy = "imposto")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<RetencaoFonte> retencaoFontes = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("impostos")
    private Conta conta;

    @ManyToMany(mappedBy = "impostos")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<LancamentoFinanceiro> lancamentos = new HashSet<>();

    @ManyToMany(mappedBy = "impostos")
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

    public String getTipoImposto() {
        return tipoImposto;
    }

    public Imposto tipoImposto(String tipoImposto) {
        this.tipoImposto = tipoImposto;
        return this;
    }

    public void setTipoImposto(String tipoImposto) {
        this.tipoImposto = tipoImposto;
    }

    public String getCodigoImposto() {
        return codigoImposto;
    }

    public Imposto codigoImposto(String codigoImposto) {
        this.codigoImposto = codigoImposto;
        return this;
    }

    public void setCodigoImposto(String codigoImposto) {
        this.codigoImposto = codigoImposto;
    }

    public Boolean isPorcentagem() {
        return porcentagem;
    }

    public Imposto porcentagem(Boolean porcentagem) {
        this.porcentagem = porcentagem;
        return this;
    }

    public void setPorcentagem(Boolean porcentagem) {
        this.porcentagem = porcentagem;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public Imposto valor(BigDecimal valor) {
        this.valor = valor;
        return this;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public Imposto descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getPais() {
        return pais;
    }

    public Imposto pais(String pais) {
        this.pais = pais;
        return this;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public LocalDate getVigencia() {
        return vigencia;
    }

    public Imposto vigencia(LocalDate vigencia) {
        this.vigencia = vigencia;
        return this;
    }

    public void setVigencia(LocalDate vigencia) {
        this.vigencia = vigencia;
    }

    public Set<GrupoTributacaoImposto> getGrupoTributacaoImpostos() {
        return grupoTributacaoImpostos;
    }

    public Imposto grupoTributacaoImpostos(Set<GrupoTributacaoImposto> grupoTributacaoImpostos) {
        this.grupoTributacaoImpostos = grupoTributacaoImpostos;
        return this;
    }

    public Imposto addGrupoTributacaoImposto(GrupoTributacaoImposto grupoTributacaoImposto) {
        this.grupoTributacaoImpostos.add(grupoTributacaoImposto);
        grupoTributacaoImposto.setImposto(this);
        return this;
    }

    public Imposto removeGrupoTributacaoImposto(GrupoTributacaoImposto grupoTributacaoImposto) {
        this.grupoTributacaoImpostos.remove(grupoTributacaoImposto);
        grupoTributacaoImposto.setImposto(null);
        return this;
    }

    public void setGrupoTributacaoImpostos(Set<GrupoTributacaoImposto> grupoTributacaoImpostos) {
        this.grupoTributacaoImpostos = grupoTributacaoImpostos;
    }

    public Set<RetencaoFonte> getRetencaoFontes() {
        return retencaoFontes;
    }

    public Imposto retencaoFontes(Set<RetencaoFonte> retencaoFontes) {
        this.retencaoFontes = retencaoFontes;
        return this;
    }

    public Imposto addRetencaoFonte(RetencaoFonte retencaoFonte) {
        this.retencaoFontes.add(retencaoFonte);
        retencaoFonte.setImposto(this);
        return this;
    }

    public Imposto removeRetencaoFonte(RetencaoFonte retencaoFonte) {
        this.retencaoFontes.remove(retencaoFonte);
        retencaoFonte.setImposto(null);
        return this;
    }

    public void setRetencaoFontes(Set<RetencaoFonte> retencaoFontes) {
        this.retencaoFontes = retencaoFontes;
    }

    public Conta getConta() {
        return conta;
    }

    public Imposto conta(Conta conta) {
        this.conta = conta;
        return this;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }

    public Set<LancamentoFinanceiro> getLancamentos() {
        return lancamentos;
    }

    public Imposto lancamentos(Set<LancamentoFinanceiro> lancamentoFinanceiros) {
        this.lancamentos = lancamentoFinanceiros;
        return this;
    }

    public Imposto addLancamento(LancamentoFinanceiro lancamentoFinanceiro) {
        this.lancamentos.add(lancamentoFinanceiro);
        lancamentoFinanceiro.getImpostos().add(this);
        return this;
    }

    public Imposto removeLancamento(LancamentoFinanceiro lancamentoFinanceiro) {
        this.lancamentos.remove(lancamentoFinanceiro);
        lancamentoFinanceiro.getImpostos().remove(this);
        return this;
    }

    public void setLancamentos(Set<LancamentoFinanceiro> lancamentoFinanceiros) {
        this.lancamentos = lancamentoFinanceiros;
    }

    public Set<Produto> getProdutos() {
        return produtos;
    }

    public Imposto produtos(Set<Produto> produtos) {
        this.produtos = produtos;
        return this;
    }

    public Imposto addProduto(Produto produto) {
        this.produtos.add(produto);
        produto.getImpostos().add(this);
        return this;
    }

    public Imposto removeProduto(Produto produto) {
        this.produtos.remove(produto);
        produto.getImpostos().remove(this);
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
        if (!(o instanceof Imposto)) {
            return false;
        }
        return id != null && id.equals(((Imposto) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Imposto{" +
            "id=" + getId() +
            ", tipoImposto='" + getTipoImposto() + "'" +
            ", codigoImposto='" + getCodigoImposto() + "'" +
            ", porcentagem='" + isPorcentagem() + "'" +
            ", valor=" + getValor() +
            ", descricao='" + getDescricao() + "'" +
            ", pais='" + getPais() + "'" +
            ", vigencia='" + getVigencia() + "'" +
            "}";
    }
}
