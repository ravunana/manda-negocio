package com.ravunana.manda.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

/**
 * A ContaDebito.
 */
@Entity
@Table(name = "ctb_conta_debito")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ContaDebito implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "valor", precision = 21, scale = 2, nullable = false)
    private BigDecimal valor;

    @Column(name = "data")
    private ZonedDateTime data;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("contaDebitos")
    private Conta contaDebitar;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("contaDebitos")
    private EscrituracaoContabil lancamentoDebito;

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

    public ContaDebito valor(BigDecimal valor) {
        this.valor = valor;
        return this;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public ZonedDateTime getData() {
        return data;
    }

    public ContaDebito data(ZonedDateTime data) {
        this.data = data;
        return this;
    }

    public void setData(ZonedDateTime data) {
        this.data = data;
    }

    public Conta getContaDebitar() {
        return contaDebitar;
    }

    public ContaDebito contaDebitar(Conta conta) {
        this.contaDebitar = conta;
        return this;
    }

    public void setContaDebitar(Conta conta) {
        this.contaDebitar = conta;
    }

    public EscrituracaoContabil getLancamentoDebito() {
        return lancamentoDebito;
    }

    public ContaDebito lancamentoDebito(EscrituracaoContabil escrituracaoContabil) {
        this.lancamentoDebito = escrituracaoContabil;
        return this;
    }

    public void setLancamentoDebito(EscrituracaoContabil escrituracaoContabil) {
        this.lancamentoDebito = escrituracaoContabil;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ContaDebito)) {
            return false;
        }
        return id != null && id.equals(((ContaDebito) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ContaDebito{" +
            "id=" + getId() +
            ", valor=" + getValor() +
            ", data='" + getData() + "'" +
            "}";
    }
}
