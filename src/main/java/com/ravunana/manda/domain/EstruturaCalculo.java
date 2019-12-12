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
 * A EstruturaCalculo.
 */
@Entity
@Table(name = "stk_estrutura_calculo")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class EstruturaCalculo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "custo_materia_prima", precision = 21, scale = 2)
    private BigDecimal custoMateriaPrima;

    @Column(name = "custo_mao_obra", precision = 21, scale = 2)
    private BigDecimal custoMaoObra;

    @Column(name = "custo_embalagem", precision = 21, scale = 2)
    private BigDecimal custoEmbalagem;

    @Column(name = "custo_aquisicao", precision = 21, scale = 2)
    private BigDecimal custoAquisicao;

    @DecimalMin(value = "0")
    @DecimalMax(value = "100")
    @Column(name = "comissao")
    private Double comissao;

    @DecimalMin(value = "0")
    @DecimalMax(value = "100")
    @Column(name = "margem_lucro")
    private Double margemLucro;

    @Column(name = "preco_venda", precision = 21, scale = 2)
    private BigDecimal precoVenda;

    @Column(name = "data")
    private ZonedDateTime data;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("estruturaCalculos")
    private User utilizador;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("estruturaCalculos")
    private Produto produto;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getCustoMateriaPrima() {
        return custoMateriaPrima;
    }

    public EstruturaCalculo custoMateriaPrima(BigDecimal custoMateriaPrima) {
        this.custoMateriaPrima = custoMateriaPrima;
        return this;
    }

    public void setCustoMateriaPrima(BigDecimal custoMateriaPrima) {
        this.custoMateriaPrima = custoMateriaPrima;
    }

    public BigDecimal getCustoMaoObra() {
        return custoMaoObra;
    }

    public EstruturaCalculo custoMaoObra(BigDecimal custoMaoObra) {
        this.custoMaoObra = custoMaoObra;
        return this;
    }

    public void setCustoMaoObra(BigDecimal custoMaoObra) {
        this.custoMaoObra = custoMaoObra;
    }

    public BigDecimal getCustoEmbalagem() {
        return custoEmbalagem;
    }

    public EstruturaCalculo custoEmbalagem(BigDecimal custoEmbalagem) {
        this.custoEmbalagem = custoEmbalagem;
        return this;
    }

    public void setCustoEmbalagem(BigDecimal custoEmbalagem) {
        this.custoEmbalagem = custoEmbalagem;
    }

    public BigDecimal getCustoAquisicao() {
        return custoAquisicao;
    }

    public EstruturaCalculo custoAquisicao(BigDecimal custoAquisicao) {
        this.custoAquisicao = custoAquisicao;
        return this;
    }

    public void setCustoAquisicao(BigDecimal custoAquisicao) {
        this.custoAquisicao = custoAquisicao;
    }

    public Double getComissao() {
        return comissao;
    }

    public EstruturaCalculo comissao(Double comissao) {
        this.comissao = comissao;
        return this;
    }

    public void setComissao(Double comissao) {
        this.comissao = comissao;
    }

    public Double getMargemLucro() {
        return margemLucro;
    }

    public EstruturaCalculo margemLucro(Double margemLucro) {
        this.margemLucro = margemLucro;
        return this;
    }

    public void setMargemLucro(Double margemLucro) {
        this.margemLucro = margemLucro;
    }

    public BigDecimal getPrecoVenda() {
        return precoVenda;
    }

    public EstruturaCalculo precoVenda(BigDecimal precoVenda) {
        this.precoVenda = precoVenda;
        return this;
    }

    public void setPrecoVenda(BigDecimal precoVenda) {
        this.precoVenda = precoVenda;
    }

    public ZonedDateTime getData() {
        return data;
    }

    public EstruturaCalculo data(ZonedDateTime data) {
        this.data = data;
        return this;
    }

    public void setData(ZonedDateTime data) {
        this.data = data;
    }

    public User getUtilizador() {
        return utilizador;
    }

    public EstruturaCalculo utilizador(User user) {
        this.utilizador = user;
        return this;
    }

    public void setUtilizador(User user) {
        this.utilizador = user;
    }

    public Produto getProduto() {
        return produto;
    }

    public EstruturaCalculo produto(Produto produto) {
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
        if (!(o instanceof EstruturaCalculo)) {
            return false;
        }
        return id != null && id.equals(((EstruturaCalculo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "EstruturaCalculo{" +
            "id=" + getId() +
            ", custoMateriaPrima=" + getCustoMateriaPrima() +
            ", custoMaoObra=" + getCustoMaoObra() +
            ", custoEmbalagem=" + getCustoEmbalagem() +
            ", custoAquisicao=" + getCustoAquisicao() +
            ", comissao=" + getComissao() +
            ", margemLucro=" + getMargemLucro() +
            ", precoVenda=" + getPrecoVenda() +
            ", data='" + getData() + "'" +
            "}";
    }
}
