package com.ravunana.manda.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A SerieDocumento.
 */
@Entity
@Table(name = "core_serie_documento")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SerieDocumento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "serie")
    private String serie;

    @Min(value = 1)
    @Column(name = "codigo_lancamento_financeiro")
    private Integer codigoLancamentoFinanceiro;

    @Min(value = 1)
    @Column(name = "codigo_escrituracao_contabil")
    private Integer codigoEscrituracaoContabil;

    @Min(value = 1)
    @Column(name = "codigo_venda")
    private Integer codigoVenda;

    @Min(value = 1)
    @Column(name = "codigo_compra")
    private Integer codigoCompra;

    @Min(value = 1)
    @Column(name = "codigo_cliente")
    private Integer codigoCliente;

    @Min(value = 1)
    @Column(name = "codigo_fornecedor")
    private Integer codigoFornecedor;

    @Min(value = 1)
    @Column(name = "codigo_devolucao_venda")
    private Integer codigoDevolucaoVenda;

    @Min(value = 1)
    @Column(name = "codigo_devolucao_compra")
    private Integer codigoDevolucaoCompra;

    @Min(value = 1)
    @Column(name = "codigo_produto")
    private Integer codigoProduto;

    @Column(name = "data")
    private LocalDate data;

    @OneToOne
    @JoinColumn(unique = true)
    private Empresa empresa;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSerie() {
        return serie;
    }

    public SerieDocumento serie(String serie) {
        this.serie = serie;
        return this;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public Integer getCodigoLancamentoFinanceiro() {
        return codigoLancamentoFinanceiro;
    }

    public SerieDocumento codigoLancamentoFinanceiro(Integer codigoLancamentoFinanceiro) {
        this.codigoLancamentoFinanceiro = codigoLancamentoFinanceiro;
        return this;
    }

    public void setCodigoLancamentoFinanceiro(Integer codigoLancamentoFinanceiro) {
        this.codigoLancamentoFinanceiro = codigoLancamentoFinanceiro;
    }

    public Integer getCodigoEscrituracaoContabil() {
        return codigoEscrituracaoContabil;
    }

    public SerieDocumento codigoEscrituracaoContabil(Integer codigoEscrituracaoContabil) {
        this.codigoEscrituracaoContabil = codigoEscrituracaoContabil;
        return this;
    }

    public void setCodigoEscrituracaoContabil(Integer codigoEscrituracaoContabil) {
        this.codigoEscrituracaoContabil = codigoEscrituracaoContabil;
    }

    public Integer getCodigoVenda() {
        return codigoVenda;
    }

    public SerieDocumento codigoVenda(Integer codigoVenda) {
        this.codigoVenda = codigoVenda;
        return this;
    }

    public void setCodigoVenda(Integer codigoVenda) {
        this.codigoVenda = codigoVenda;
    }

    public Integer getCodigoCompra() {
        return codigoCompra;
    }

    public SerieDocumento codigoCompra(Integer codigoCompra) {
        this.codigoCompra = codigoCompra;
        return this;
    }

    public void setCodigoCompra(Integer codigoCompra) {
        this.codigoCompra = codigoCompra;
    }

    public Integer getCodigoCliente() {
        return codigoCliente;
    }

    public SerieDocumento codigoCliente(Integer codigoCliente) {
        this.codigoCliente = codigoCliente;
        return this;
    }

    public void setCodigoCliente(Integer codigoCliente) {
        this.codigoCliente = codigoCliente;
    }

    public Integer getCodigoFornecedor() {
        return codigoFornecedor;
    }

    public SerieDocumento codigoFornecedor(Integer codigoFornecedor) {
        this.codigoFornecedor = codigoFornecedor;
        return this;
    }

    public void setCodigoFornecedor(Integer codigoFornecedor) {
        this.codigoFornecedor = codigoFornecedor;
    }

    public Integer getCodigoDevolucaoVenda() {
        return codigoDevolucaoVenda;
    }

    public SerieDocumento codigoDevolucaoVenda(Integer codigoDevolucaoVenda) {
        this.codigoDevolucaoVenda = codigoDevolucaoVenda;
        return this;
    }

    public void setCodigoDevolucaoVenda(Integer codigoDevolucaoVenda) {
        this.codigoDevolucaoVenda = codigoDevolucaoVenda;
    }

    public Integer getCodigoDevolucaoCompra() {
        return codigoDevolucaoCompra;
    }

    public SerieDocumento codigoDevolucaoCompra(Integer codigoDevolucaoCompra) {
        this.codigoDevolucaoCompra = codigoDevolucaoCompra;
        return this;
    }

    public void setCodigoDevolucaoCompra(Integer codigoDevolucaoCompra) {
        this.codigoDevolucaoCompra = codigoDevolucaoCompra;
    }

    public Integer getCodigoProduto() {
        return codigoProduto;
    }

    public SerieDocumento codigoProduto(Integer codigoProduto) {
        this.codigoProduto = codigoProduto;
        return this;
    }

    public void setCodigoProduto(Integer codigoProduto) {
        this.codigoProduto = codigoProduto;
    }

    public LocalDate getData() {
        return data;
    }

    public SerieDocumento data(LocalDate data) {
        this.data = data;
        return this;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public SerieDocumento empresa(Empresa empresa) {
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
        if (!(o instanceof SerieDocumento)) {
            return false;
        }
        return id != null && id.equals(((SerieDocumento) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "SerieDocumento{" +
            "id=" + getId() +
            ", serie='" + getSerie() + "'" +
            ", codigoLancamentoFinanceiro=" + getCodigoLancamentoFinanceiro() +
            ", codigoEscrituracaoContabil=" + getCodigoEscrituracaoContabil() +
            ", codigoVenda=" + getCodigoVenda() +
            ", codigoCompra=" + getCodigoCompra() +
            ", codigoCliente=" + getCodigoCliente() +
            ", codigoFornecedor=" + getCodigoFornecedor() +
            ", codigoDevolucaoVenda=" + getCodigoDevolucaoVenda() +
            ", codigoDevolucaoCompra=" + getCodigoDevolucaoCompra() +
            ", codigoProduto=" + getCodigoProduto() +
            ", data='" + getData() + "'" +
            "}";
    }
}
