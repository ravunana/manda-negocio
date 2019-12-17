package com.ravunana.manda.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import com.ravunana.manda.domain.enumeration.EntidadeSistema;

/**
 * A LancamentoFinanceiro.
 */
@Entity
@Table(name = "fnc_lancamento_financeiro")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class LancamentoFinanceiro implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "tipo_lancamento", nullable = false)
    private String tipoLancamento;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "valor", precision = 21, scale = 2, nullable = false)
    private BigDecimal valor;

    @Column(name = "externo")
    private Boolean externo;

    @NotNull
    @Column(name = "numero", nullable = false, unique = true)
    private String numero;

    
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "descricao", nullable = false)
    private String descricao;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "entidade_documento", nullable = false)
    private EntidadeSistema entidadeDocumento;

    @NotNull
    @Column(name = "numero_documento", nullable = false)
    private String numeroDocumento;

    @OneToMany(mappedBy = "lancamentoFinanceiro")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DetalheLancamento> detalheLancamentos = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("lancamentoFinanceiros")
    private User utilizador;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @NotNull
    @JoinTable(name = "fnc_lancamento_financeiro_imposto",
               joinColumns = @JoinColumn(name = "lancamento_financeiro_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "imposto_id", referencedColumnName = "id"))
    private Set<Imposto> impostos = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("lancamentoFinanceiros")
    private FormaLiquidacao formaLiquidacao;

    @ManyToOne
    @JsonIgnoreProperties("lancamentoFinanceiros")
    private Empresa empresa;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("lancamentoFinanceiros")
    private DocumentoComercial tipoRecibo;

    @ManyToOne
    @JsonIgnoreProperties("lancamentoFinanceiros")
    private Conta conta;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipoLancamento() {
        return tipoLancamento;
    }

    public LancamentoFinanceiro tipoLancamento(String tipoLancamento) {
        this.tipoLancamento = tipoLancamento;
        return this;
    }

    public void setTipoLancamento(String tipoLancamento) {
        this.tipoLancamento = tipoLancamento;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public LancamentoFinanceiro valor(BigDecimal valor) {
        this.valor = valor;
        return this;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Boolean isExterno() {
        return externo;
    }

    public LancamentoFinanceiro externo(Boolean externo) {
        this.externo = externo;
        return this;
    }

    public void setExterno(Boolean externo) {
        this.externo = externo;
    }

    public String getNumero() {
        return numero;
    }

    public LancamentoFinanceiro numero(String numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getDescricao() {
        return descricao;
    }

    public LancamentoFinanceiro descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public EntidadeSistema getEntidadeDocumento() {
        return entidadeDocumento;
    }

    public LancamentoFinanceiro entidadeDocumento(EntidadeSistema entidadeDocumento) {
        this.entidadeDocumento = entidadeDocumento;
        return this;
    }

    public void setEntidadeDocumento(EntidadeSistema entidadeDocumento) {
        this.entidadeDocumento = entidadeDocumento;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public LancamentoFinanceiro numeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
        return this;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public Set<DetalheLancamento> getDetalheLancamentos() {
        return detalheLancamentos;
    }

    public LancamentoFinanceiro detalheLancamentos(Set<DetalheLancamento> detalheLancamentos) {
        this.detalheLancamentos = detalheLancamentos;
        return this;
    }

    public LancamentoFinanceiro addDetalheLancamento(DetalheLancamento detalheLancamento) {
        this.detalheLancamentos.add(detalheLancamento);
        detalheLancamento.setLancamentoFinanceiro(this);
        return this;
    }

    public LancamentoFinanceiro removeDetalheLancamento(DetalheLancamento detalheLancamento) {
        this.detalheLancamentos.remove(detalheLancamento);
        detalheLancamento.setLancamentoFinanceiro(null);
        return this;
    }

    public void setDetalheLancamentos(Set<DetalheLancamento> detalheLancamentos) {
        this.detalheLancamentos = detalheLancamentos;
    }

    public User getUtilizador() {
        return utilizador;
    }

    public LancamentoFinanceiro utilizador(User user) {
        this.utilizador = user;
        return this;
    }

    public void setUtilizador(User user) {
        this.utilizador = user;
    }

    public Set<Imposto> getImpostos() {
        return impostos;
    }

    public LancamentoFinanceiro impostos(Set<Imposto> impostos) {
        this.impostos = impostos;
        return this;
    }

    public LancamentoFinanceiro addImposto(Imposto imposto) {
        this.impostos.add(imposto);
        imposto.getLancamentos().add(this);
        return this;
    }

    public LancamentoFinanceiro removeImposto(Imposto imposto) {
        this.impostos.remove(imposto);
        imposto.getLancamentos().remove(this);
        return this;
    }

    public void setImpostos(Set<Imposto> impostos) {
        this.impostos = impostos;
    }

    public FormaLiquidacao getFormaLiquidacao() {
        return formaLiquidacao;
    }

    public LancamentoFinanceiro formaLiquidacao(FormaLiquidacao formaLiquidacao) {
        this.formaLiquidacao = formaLiquidacao;
        return this;
    }

    public void setFormaLiquidacao(FormaLiquidacao formaLiquidacao) {
        this.formaLiquidacao = formaLiquidacao;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public LancamentoFinanceiro empresa(Empresa empresa) {
        this.empresa = empresa;
        return this;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public DocumentoComercial getTipoRecibo() {
        return tipoRecibo;
    }

    public LancamentoFinanceiro tipoRecibo(DocumentoComercial documentoComercial) {
        this.tipoRecibo = documentoComercial;
        return this;
    }

    public void setTipoRecibo(DocumentoComercial documentoComercial) {
        this.tipoRecibo = documentoComercial;
    }

    public Conta getConta() {
        return conta;
    }

    public LancamentoFinanceiro conta(Conta conta) {
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
        if (!(o instanceof LancamentoFinanceiro)) {
            return false;
        }
        return id != null && id.equals(((LancamentoFinanceiro) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "LancamentoFinanceiro{" +
            "id=" + getId() +
            ", tipoLancamento='" + getTipoLancamento() + "'" +
            ", valor=" + getValor() +
            ", externo='" + isExterno() + "'" +
            ", numero='" + getNumero() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", entidadeDocumento='" + getEntidadeDocumento() + "'" +
            ", numeroDocumento='" + getNumeroDocumento() + "'" +
            "}";
    }
}
