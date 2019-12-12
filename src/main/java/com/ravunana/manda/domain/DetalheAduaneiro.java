package com.ravunana.manda.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A DetalheAduaneiro.
 */
@Entity
@Table(name = "stk_detalhe_aduaneiro")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DetalheAduaneiro implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    
    @Column(name = "numero_onu", unique = true)
    private String numeroONU;

    @Column(name = "largura")
    private Double largura;

    @Column(name = "altura")
    private Double altura;

    @Column(name = "peso_liquido")
    private Double pesoLiquido;

    @Column(name = "peso_bruto")
    private Double pesoBruto;

    @Column(name = "data_fabrico")
    private LocalDate dataFabrico;

    @Column(name = "data_expiracao")
    private LocalDate dataExpiracao;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private Produto produto;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroONU() {
        return numeroONU;
    }

    public DetalheAduaneiro numeroONU(String numeroONU) {
        this.numeroONU = numeroONU;
        return this;
    }

    public void setNumeroONU(String numeroONU) {
        this.numeroONU = numeroONU;
    }

    public Double getLargura() {
        return largura;
    }

    public DetalheAduaneiro largura(Double largura) {
        this.largura = largura;
        return this;
    }

    public void setLargura(Double largura) {
        this.largura = largura;
    }

    public Double getAltura() {
        return altura;
    }

    public DetalheAduaneiro altura(Double altura) {
        this.altura = altura;
        return this;
    }

    public void setAltura(Double altura) {
        this.altura = altura;
    }

    public Double getPesoLiquido() {
        return pesoLiquido;
    }

    public DetalheAduaneiro pesoLiquido(Double pesoLiquido) {
        this.pesoLiquido = pesoLiquido;
        return this;
    }

    public void setPesoLiquido(Double pesoLiquido) {
        this.pesoLiquido = pesoLiquido;
    }

    public Double getPesoBruto() {
        return pesoBruto;
    }

    public DetalheAduaneiro pesoBruto(Double pesoBruto) {
        this.pesoBruto = pesoBruto;
        return this;
    }

    public void setPesoBruto(Double pesoBruto) {
        this.pesoBruto = pesoBruto;
    }

    public LocalDate getDataFabrico() {
        return dataFabrico;
    }

    public DetalheAduaneiro dataFabrico(LocalDate dataFabrico) {
        this.dataFabrico = dataFabrico;
        return this;
    }

    public void setDataFabrico(LocalDate dataFabrico) {
        this.dataFabrico = dataFabrico;
    }

    public LocalDate getDataExpiracao() {
        return dataExpiracao;
    }

    public DetalheAduaneiro dataExpiracao(LocalDate dataExpiracao) {
        this.dataExpiracao = dataExpiracao;
        return this;
    }

    public void setDataExpiracao(LocalDate dataExpiracao) {
        this.dataExpiracao = dataExpiracao;
    }

    public Produto getProduto() {
        return produto;
    }

    public DetalheAduaneiro produto(Produto produto) {
        this.produto = produto;
        return this;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DetalheAduaneiro)) {
            return false;
        }
        return id != null && id.equals(((DetalheAduaneiro) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "DetalheAduaneiro{" +
            "id=" + getId() +
            ", numeroONU='" + getNumeroONU() + "'" +
            ", largura=" + getLargura() +
            ", altura=" + getAltura() +
            ", pesoLiquido=" + getPesoLiquido() +
            ", pesoBruto=" + getPesoBruto() +
            ", dataFabrico='" + getDataFabrico() + "'" +
            ", dataExpiracao='" + getDataExpiracao() + "'" +
            "}";
    }
}
