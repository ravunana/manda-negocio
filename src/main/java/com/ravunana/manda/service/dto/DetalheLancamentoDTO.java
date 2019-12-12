package com.ravunana.manda.service.dto;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.ravunana.manda.domain.DetalheLancamento} entity.
 */
public class DetalheLancamentoDTO implements Serializable {

    private Long id;

    @NotNull
    @DecimalMin(value = "0")
    private BigDecimal valor;

    @DecimalMin(value = "0")
    private BigDecimal desconto;

    @DecimalMin(value = "0")
    @DecimalMax(value = "100")
    private Double juro;

    @Lob
    private String descricao;

    @NotNull
    private ZonedDateTime data;

    private Boolean retencaoFonte;

    @NotNull
    private LocalDate dataVencimento;

    private Boolean liquidado;


    private Long utilizadorId;

    private String utilizadorLogin;

    private Long lancamentoFinanceiroId;

    private String lancamentoFinanceiroNumero;

    private Long metodoLiquidacaoId;

    private String metodoLiquidacaoNome;

    private Long moedaId;

    private String moedaCodigo;

    private Long coordenadaId;

    private String coordenadaDescricao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public BigDecimal getDesconto() {
        return desconto;
    }

    public void setDesconto(BigDecimal desconto) {
        this.desconto = desconto;
    }

    public Double getJuro() {
        return juro;
    }

    public void setJuro(Double juro) {
        this.juro = juro;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public ZonedDateTime getData() {
        return data;
    }

    public void setData(ZonedDateTime data) {
        this.data = data;
    }

    public Boolean isRetencaoFonte() {
        return retencaoFonte;
    }

    public void setRetencaoFonte(Boolean retencaoFonte) {
        this.retencaoFonte = retencaoFonte;
    }

    public LocalDate getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(LocalDate dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public Boolean isLiquidado() {
        return liquidado;
    }

    public void setLiquidado(Boolean liquidado) {
        this.liquidado = liquidado;
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

    public Long getLancamentoFinanceiroId() {
        return lancamentoFinanceiroId;
    }

    public void setLancamentoFinanceiroId(Long lancamentoFinanceiroId) {
        this.lancamentoFinanceiroId = lancamentoFinanceiroId;
    }

    public String getLancamentoFinanceiroNumero() {
        return lancamentoFinanceiroNumero;
    }

    public void setLancamentoFinanceiroNumero(String lancamentoFinanceiroNumero) {
        this.lancamentoFinanceiroNumero = lancamentoFinanceiroNumero;
    }

    public Long getMetodoLiquidacaoId() {
        return metodoLiquidacaoId;
    }

    public void setMetodoLiquidacaoId(Long meioLiquidacaoId) {
        this.metodoLiquidacaoId = meioLiquidacaoId;
    }

    public String getMetodoLiquidacaoNome() {
        return metodoLiquidacaoNome;
    }

    public void setMetodoLiquidacaoNome(String meioLiquidacaoNome) {
        this.metodoLiquidacaoNome = meioLiquidacaoNome;
    }

    public Long getMoedaId() {
        return moedaId;
    }

    public void setMoedaId(Long moedaId) {
        this.moedaId = moedaId;
    }

    public String getMoedaCodigo() {
        return moedaCodigo;
    }

    public void setMoedaCodigo(String moedaCodigo) {
        this.moedaCodigo = moedaCodigo;
    }

    public Long getCoordenadaId() {
        return coordenadaId;
    }

    public void setCoordenadaId(Long coordenadaBancariaId) {
        this.coordenadaId = coordenadaBancariaId;
    }

    public String getCoordenadaDescricao() {
        return coordenadaDescricao;
    }

    public void setCoordenadaDescricao(String coordenadaBancariaDescricao) {
        this.coordenadaDescricao = coordenadaBancariaDescricao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DetalheLancamentoDTO detalheLancamentoDTO = (DetalheLancamentoDTO) o;
        if (detalheLancamentoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), detalheLancamentoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DetalheLancamentoDTO{" +
            "id=" + getId() +
            ", valor=" + getValor() +
            ", desconto=" + getDesconto() +
            ", juro=" + getJuro() +
            ", descricao='" + getDescricao() + "'" +
            ", data='" + getData() + "'" +
            ", retencaoFonte='" + isRetencaoFonte() + "'" +
            ", dataVencimento='" + getDataVencimento() + "'" +
            ", liquidado='" + isLiquidado() + "'" +
            ", utilizador=" + getUtilizadorId() +
            ", utilizador='" + getUtilizadorLogin() + "'" +
            ", lancamentoFinanceiro=" + getLancamentoFinanceiroId() +
            ", lancamentoFinanceiro='" + getLancamentoFinanceiroNumero() + "'" +
            ", metodoLiquidacao=" + getMetodoLiquidacaoId() +
            ", metodoLiquidacao='" + getMetodoLiquidacaoNome() + "'" +
            ", moeda=" + getMoedaId() +
            ", moeda='" + getMoedaCodigo() + "'" +
            ", coordenada=" + getCoordenadaId() +
            ", coordenada='" + getCoordenadaDescricao() + "'" +
            "}";
    }
}
