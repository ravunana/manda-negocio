package com.ravunana.manda.service.dto;
import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the {@link com.ravunana.manda.domain.EstruturaCalculo} entity.
 */
public class EstruturaCalculoDTO implements Serializable {

    private Long id;

    private BigDecimal custoMateriaPrima;

    private BigDecimal custoMaoObra;

    private BigDecimal custoEmbalagem;

    private BigDecimal custoAquisicao;

    @DecimalMin(value = "0")
    @DecimalMax(value = "100")
    private Double comissao;

    @DecimalMin(value = "0")
    @DecimalMax(value = "100")
    private Double margemLucro;

    private BigDecimal precoVenda;

    private ZonedDateTime data;


    private Long utilizadorId;

    private String utilizadorLogin;

    private Long produtoId;

    private String produtoNome;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getCustoMateriaPrima() {
        return custoMateriaPrima;
    }

    public void setCustoMateriaPrima(BigDecimal custoMateriaPrima) {
        this.custoMateriaPrima = custoMateriaPrima;
    }

    public BigDecimal getCustoMaoObra() {
        return custoMaoObra;
    }

    public void setCustoMaoObra(BigDecimal custoMaoObra) {
        this.custoMaoObra = custoMaoObra;
    }

    public BigDecimal getCustoEmbalagem() {
        return custoEmbalagem;
    }

    public void setCustoEmbalagem(BigDecimal custoEmbalagem) {
        this.custoEmbalagem = custoEmbalagem;
    }

    public BigDecimal getCustoAquisicao() {
        return custoAquisicao;
    }

    public void setCustoAquisicao(BigDecimal custoAquisicao) {
        this.custoAquisicao = custoAquisicao;
    }

    public Double getComissao() {
        return comissao;
    }

    public void setComissao(Double comissao) {
        this.comissao = comissao;
    }

    public Double getMargemLucro() {
        return margemLucro;
    }

    public void setMargemLucro(Double margemLucro) {
        this.margemLucro = margemLucro;
    }

    public BigDecimal getPrecoVenda() {
        return precoVenda;
    }

    public void setPrecoVenda(BigDecimal precoVenda) {
        this.precoVenda = precoVenda;
    }

    public ZonedDateTime getData() {
        return data;
    }

    public void setData(ZonedDateTime data) {
        this.data = data;
    }

    public Long getUtilizadorId() {
        return utilizadorId;
    }

    public void setUtilizadorId(Long userId) {
        this.utilizadorId = userId;
    }

    public String getUtilizadorLogin() {
        return utilizadorLogin;
    }

    public void setUtilizadorLogin(String userLogin) {
        this.utilizadorLogin = userLogin;
    }

    public Long getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Long produtoId) {
        this.produtoId = produtoId;
    }

    public String getProdutoNome() {
        return produtoNome;
    }

    public void setProdutoNome(String produtoNome) {
        this.produtoNome = produtoNome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EstruturaCalculoDTO estruturaCalculoDTO = (EstruturaCalculoDTO) o;
        if (estruturaCalculoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), estruturaCalculoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EstruturaCalculoDTO{" +
            "id=" + getId() +
            ", custoMateriaPrima=" + getCustoMateriaPrima() +
            ", custoMaoObra=" + getCustoMaoObra() +
            ", custoEmbalagem=" + getCustoEmbalagem() +
            ", custoAquisicao=" + getCustoAquisicao() +
            ", comissao=" + getComissao() +
            ", margemLucro=" + getMargemLucro() +
            ", precoVenda=" + getPrecoVenda() +
            ", data='" + getData() + "'" +
            ", utilizador=" + getUtilizadorId() +
            ", utilizador='" + getUtilizadorLogin() + "'" +
            ", produto=" + getProdutoId() +
            ", produto='" + getProdutoNome() + "'" +
            "}";
    }
}
