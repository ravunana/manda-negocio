package com.ravunana.manda.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * A DetalheLancamento.
 */
@Entity
@Table(name = "fnc_detalhe_lancamento")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DetalheLancamento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "valor", precision = 21, scale = 2, nullable = false)
    private BigDecimal valor;

    @DecimalMin(value = "0")
    @Column(name = "desconto", precision = 21, scale = 2)
    private BigDecimal desconto;

    @DecimalMin(value = "0")
    @DecimalMax(value = "100")
    @Column(name = "juro")
    private Double juro;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "descricao")
    private String descricao;

    @NotNull
    @Column(name = "data", nullable = false)
    private ZonedDateTime data;

    @Column(name = "retencao_fonte")
    private Boolean retencaoFonte;

    @NotNull
    @Column(name = "data_vencimento", nullable = false)
    private LocalDate dataVencimento;

    @Column(name = "liquidado")
    private Boolean liquidado;

    @OneToMany(mappedBy = "detalhe")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<RetencaoFonte> retencaoFontes = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("detalheLancamentos")
    private User utilizador;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("detalheLancamentos")
    private LancamentoFinanceiro lancamentoFinanceiro;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("detalheLancamentos")
    private MeioLiquidacao metodoLiquidacao;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("detalheLancamentos")
    private Moeda moeda;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("detalheLancamentos")
    private CoordenadaBancaria coordenada;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public DetalheLancamento valor(BigDecimal valor) {
        this.valor = valor;
        return this;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public BigDecimal getDesconto() {
        return desconto;
    }

    public DetalheLancamento desconto(BigDecimal desconto) {
        this.desconto = desconto;
        return this;
    }

    public void setDesconto(BigDecimal desconto) {
        this.desconto = desconto;
    }

    public Double getJuro() {
        return juro;
    }

    public DetalheLancamento juro(Double juro) {
        this.juro = juro;
        return this;
    }

    public void setJuro(Double juro) {
        this.juro = juro;
    }

    public String getDescricao() {
        return descricao;
    }

    public DetalheLancamento descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public ZonedDateTime getData() {
        return data;
    }

    public DetalheLancamento data(ZonedDateTime data) {
        this.data = data;
        return this;
    }

    public void setData(ZonedDateTime data) {
        this.data = data;
    }

    public Boolean isRetencaoFonte() {
        return retencaoFonte;
    }

    public DetalheLancamento retencaoFonte(Boolean retencaoFonte) {
        this.retencaoFonte = retencaoFonte;
        return this;
    }

    public void setRetencaoFonte(Boolean retencaoFonte) {
        this.retencaoFonte = retencaoFonte;
    }

    public LocalDate getDataVencimento() {
        return dataVencimento;
    }

    public DetalheLancamento dataVencimento(LocalDate dataVencimento) {
        this.dataVencimento = dataVencimento;
        return this;
    }

    public void setDataVencimento(LocalDate dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public Boolean isLiquidado() {
        return liquidado;
    }

    public DetalheLancamento liquidado(Boolean liquidado) {
        this.liquidado = liquidado;
        return this;
    }

    public void setLiquidado(Boolean liquidado) {
        this.liquidado = liquidado;
    }

    public Set<RetencaoFonte> getRetencaoFontes() {
        return retencaoFontes;
    }

    public DetalheLancamento retencaoFontes(Set<RetencaoFonte> retencaoFontes) {
        this.retencaoFontes = retencaoFontes;
        return this;
    }

    public DetalheLancamento addRetencaoFonte(RetencaoFonte retencaoFonte) {
        this.retencaoFontes.add(retencaoFonte);
        retencaoFonte.setDetalhe(this);
        return this;
    }

    public DetalheLancamento removeRetencaoFonte(RetencaoFonte retencaoFonte) {
        this.retencaoFontes.remove(retencaoFonte);
        retencaoFonte.setDetalhe(null);
        return this;
    }

    public void setRetencaoFontes(Set<RetencaoFonte> retencaoFontes) {
        this.retencaoFontes = retencaoFontes;
    }

    public User getUtilizador() {
        return utilizador;
    }

    public DetalheLancamento utilizador(User user) {
        this.utilizador = user;
        return this;
    }

    public void setUtilizador(User user) {
        this.utilizador = user;
    }

    public LancamentoFinanceiro getLancamentoFinanceiro() {
        return lancamentoFinanceiro;
    }

    public DetalheLancamento lancamentoFinanceiro(LancamentoFinanceiro lancamentoFinanceiro) {
        this.lancamentoFinanceiro = lancamentoFinanceiro;
        return this;
    }

    public void setLancamentoFinanceiro(LancamentoFinanceiro lancamentoFinanceiro) {
        this.lancamentoFinanceiro = lancamentoFinanceiro;
    }

    public MeioLiquidacao getMetodoLiquidacao() {
        return metodoLiquidacao;
    }

    public DetalheLancamento metodoLiquidacao(MeioLiquidacao meioLiquidacao) {
        this.metodoLiquidacao = meioLiquidacao;
        return this;
    }

    public void setMetodoLiquidacao(MeioLiquidacao meioLiquidacao) {
        this.metodoLiquidacao = meioLiquidacao;
    }

    public Moeda getMoeda() {
        return moeda;
    }

    public DetalheLancamento moeda(Moeda moeda) {
        this.moeda = moeda;
        return this;
    }

    public void setMoeda(Moeda moeda) {
        this.moeda = moeda;
    }

    public CoordenadaBancaria getCoordenada() {
        return coordenada;
    }

    public DetalheLancamento coordenada(CoordenadaBancaria coordenadaBancaria) {
        this.coordenada = coordenadaBancaria;
        return this;
    }

    public void setCoordenada(CoordenadaBancaria coordenadaBancaria) {
        this.coordenada = coordenadaBancaria;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DetalheLancamento)) {
            return false;
        }
        return id != null && id.equals(((DetalheLancamento) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "DetalheLancamento{" +
            "id=" + getId() +
            ", valor=" + getValor() +
            ", desconto=" + getDesconto() +
            ", juro=" + getJuro() +
            ", descricao='" + getDescricao() + "'" +
            ", data='" + getData() + "'" +
            ", retencaoFonte='" + isRetencaoFonte() + "'" +
            ", dataVencimento='" + getDataVencimento() + "'" +
            ", liquidado='" + isLiquidado() + "'" +
            "}";
    }
}
