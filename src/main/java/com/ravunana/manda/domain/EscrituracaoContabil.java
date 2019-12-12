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

import com.ravunana.manda.domain.enumeration.EntidadeSistema;

/**
 * A EscrituracaoContabil.
 */
@Entity
@Table(name = "ctb_escrituracao_contabil")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class EscrituracaoContabil implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "numero", nullable = false, unique = true)
    private String numero;

    
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "historico", nullable = false)
    private String historico;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "valor", precision = 21, scale = 2, nullable = false)
    private BigDecimal valor;

    
    @Column(name = "referencia", unique = true)
    private String referencia;

    @Enumerated(EnumType.STRING)
    @Column(name = "entidade_referencia")
    private EntidadeSistema entidadeReferencia;

    @NotNull
    @Column(name = "tipo", nullable = false)
    private String tipo;

    @NotNull
    @Column(name = "data_documento", nullable = false)
    private LocalDate dataDocumento;

    @NotNull
    @Column(name = "data", nullable = false)
    private ZonedDateTime data;

    @OneToMany(mappedBy = "lancamentoDebito")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ContaDebito> contaDebitos = new HashSet<>();

    @OneToMany(mappedBy = "lancamentoCredito")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ContaCredito> contaCreditos = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("escrituracaoContabils")
    private User utilizador;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("escrituracaoContabils")
    private Empresa empresa;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public EscrituracaoContabil numero(String numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getHistorico() {
        return historico;
    }

    public EscrituracaoContabil historico(String historico) {
        this.historico = historico;
        return this;
    }

    public void setHistorico(String historico) {
        this.historico = historico;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public EscrituracaoContabil valor(BigDecimal valor) {
        this.valor = valor;
        return this;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getReferencia() {
        return referencia;
    }

    public EscrituracaoContabil referencia(String referencia) {
        this.referencia = referencia;
        return this;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public EntidadeSistema getEntidadeReferencia() {
        return entidadeReferencia;
    }

    public EscrituracaoContabil entidadeReferencia(EntidadeSistema entidadeReferencia) {
        this.entidadeReferencia = entidadeReferencia;
        return this;
    }

    public void setEntidadeReferencia(EntidadeSistema entidadeReferencia) {
        this.entidadeReferencia = entidadeReferencia;
    }

    public String getTipo() {
        return tipo;
    }

    public EscrituracaoContabil tipo(String tipo) {
        this.tipo = tipo;
        return this;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public LocalDate getDataDocumento() {
        return dataDocumento;
    }

    public EscrituracaoContabil dataDocumento(LocalDate dataDocumento) {
        this.dataDocumento = dataDocumento;
        return this;
    }

    public void setDataDocumento(LocalDate dataDocumento) {
        this.dataDocumento = dataDocumento;
    }

    public ZonedDateTime getData() {
        return data;
    }

    public EscrituracaoContabil data(ZonedDateTime data) {
        this.data = data;
        return this;
    }

    public void setData(ZonedDateTime data) {
        this.data = data;
    }

    public Set<ContaDebito> getContaDebitos() {
        return contaDebitos;
    }

    public EscrituracaoContabil contaDebitos(Set<ContaDebito> contaDebitos) {
        this.contaDebitos = contaDebitos;
        return this;
    }

    public EscrituracaoContabil addContaDebito(ContaDebito contaDebito) {
        this.contaDebitos.add(contaDebito);
        contaDebito.setLancamentoDebito(this);
        return this;
    }

    public EscrituracaoContabil removeContaDebito(ContaDebito contaDebito) {
        this.contaDebitos.remove(contaDebito);
        contaDebito.setLancamentoDebito(null);
        return this;
    }

    public void setContaDebitos(Set<ContaDebito> contaDebitos) {
        this.contaDebitos = contaDebitos;
    }

    public Set<ContaCredito> getContaCreditos() {
        return contaCreditos;
    }

    public EscrituracaoContabil contaCreditos(Set<ContaCredito> contaCreditos) {
        this.contaCreditos = contaCreditos;
        return this;
    }

    public EscrituracaoContabil addContaCredito(ContaCredito contaCredito) {
        this.contaCreditos.add(contaCredito);
        contaCredito.setLancamentoCredito(this);
        return this;
    }

    public EscrituracaoContabil removeContaCredito(ContaCredito contaCredito) {
        this.contaCreditos.remove(contaCredito);
        contaCredito.setLancamentoCredito(null);
        return this;
    }

    public void setContaCreditos(Set<ContaCredito> contaCreditos) {
        this.contaCreditos = contaCreditos;
    }

    public User getUtilizador() {
        return utilizador;
    }

    public EscrituracaoContabil utilizador(User user) {
        this.utilizador = user;
        return this;
    }

    public void setUtilizador(User user) {
        this.utilizador = user;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public EscrituracaoContabil empresa(Empresa empresa) {
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
        if (!(o instanceof EscrituracaoContabil)) {
            return false;
        }
        return id != null && id.equals(((EscrituracaoContabil) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "EscrituracaoContabil{" +
            "id=" + getId() +
            ", numero='" + getNumero() + "'" +
            ", historico='" + getHistorico() + "'" +
            ", valor=" + getValor() +
            ", referencia='" + getReferencia() + "'" +
            ", entidadeReferencia='" + getEntidadeReferencia() + "'" +
            ", tipo='" + getTipo() + "'" +
            ", dataDocumento='" + getDataDocumento() + "'" +
            ", data='" + getData() + "'" +
            "}";
    }
}
