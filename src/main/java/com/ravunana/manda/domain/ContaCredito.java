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
 * A ContaCredito.
 */
@Entity
@Table(name = "ctb_conta_credito")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ContaCredito implements Serializable {

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
    @JsonIgnoreProperties("contaCreditos")
    private Conta contaCreditar;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("contaCreditos")
    private EscrituracaoContabil lancamentoCredito;

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

    public ContaCredito valor(BigDecimal valor) {
        this.valor = valor;
        return this;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public ZonedDateTime getData() {
        return data;
    }

    public ContaCredito data(ZonedDateTime data) {
        this.data = data;
        return this;
    }

    public void setData(ZonedDateTime data) {
        this.data = data;
    }

    public Conta getContaCreditar() {
        return contaCreditar;
    }

    public ContaCredito contaCreditar(Conta conta) {
        this.contaCreditar = conta;
        return this;
    }

    public void setContaCreditar(Conta conta) {
        this.contaCreditar = conta;
    }

    public EscrituracaoContabil getLancamentoCredito() {
        return lancamentoCredito;
    }

    public ContaCredito lancamentoCredito(EscrituracaoContabil escrituracaoContabil) {
        this.lancamentoCredito = escrituracaoContabil;
        return this;
    }

    public void setLancamentoCredito(EscrituracaoContabil escrituracaoContabil) {
        this.lancamentoCredito = escrituracaoContabil;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ContaCredito)) {
            return false;
        }
        return id != null && id.equals(((ContaCredito) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ContaCredito{" +
            "id=" + getId() +
            ", valor=" + getValor() +
            ", data='" + getData() + "'" +
            "}";
    }
}
