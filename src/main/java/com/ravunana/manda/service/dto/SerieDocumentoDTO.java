package com.ravunana.manda.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.ravunana.manda.domain.SerieDocumento} entity.
 */
public class SerieDocumentoDTO implements Serializable {

    private Long id;

    private String serie;

    @Min(value = 1)
    private Integer codigoLancamentoFinanceiro;

    @Min(value = 1)
    private Integer codigoEscrituracaoContabil;

    @Min(value = 1)
    private Integer codigoVenda;

    @Min(value = 1)
    private Integer codigoCompra;

    @Min(value = 1)
    private Integer codigoCliente;

    @Min(value = 1)
    private Integer codigoFornecedor;

    @Min(value = 1)
    private Integer codigoDevolucaoVenda;

    @Min(value = 1)
    private Integer codigoDevolucaoCompra;

    @Min(value = 1)
    private Integer codigoProduto;

    private LocalDate data;


    private Long empresaId;

    private String empresaNome;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public Integer getCodigoLancamentoFinanceiro() {
        return codigoLancamentoFinanceiro;
    }

    public void setCodigoLancamentoFinanceiro(Integer codigoLancamentoFinanceiro) {
        this.codigoLancamentoFinanceiro = codigoLancamentoFinanceiro;
    }

    public Integer getCodigoEscrituracaoContabil() {
        return codigoEscrituracaoContabil;
    }

    public void setCodigoEscrituracaoContabil(Integer codigoEscrituracaoContabil) {
        this.codigoEscrituracaoContabil = codigoEscrituracaoContabil;
    }

    public Integer getCodigoVenda() {
        return codigoVenda;
    }

    public void setCodigoVenda(Integer codigoVenda) {
        this.codigoVenda = codigoVenda;
    }

    public Integer getCodigoCompra() {
        return codigoCompra;
    }

    public void setCodigoCompra(Integer codigoCompra) {
        this.codigoCompra = codigoCompra;
    }

    public Integer getCodigoCliente() {
        return codigoCliente;
    }

    public void setCodigoCliente(Integer codigoCliente) {
        this.codigoCliente = codigoCliente;
    }

    public Integer getCodigoFornecedor() {
        return codigoFornecedor;
    }

    public void setCodigoFornecedor(Integer codigoFornecedor) {
        this.codigoFornecedor = codigoFornecedor;
    }

    public Integer getCodigoDevolucaoVenda() {
        return codigoDevolucaoVenda;
    }

    public void setCodigoDevolucaoVenda(Integer codigoDevolucaoVenda) {
        this.codigoDevolucaoVenda = codigoDevolucaoVenda;
    }

    public Integer getCodigoDevolucaoCompra() {
        return codigoDevolucaoCompra;
    }

    public void setCodigoDevolucaoCompra(Integer codigoDevolucaoCompra) {
        this.codigoDevolucaoCompra = codigoDevolucaoCompra;
    }

    public Integer getCodigoProduto() {
        return codigoProduto;
    }

    public void setCodigoProduto(Integer codigoProduto) {
        this.codigoProduto = codigoProduto;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Long getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(Long empresaId) {
        this.empresaId = empresaId;
    }

    public String getEmpresaNome() {
        return empresaNome;
    }

    public void setEmpresaNome(String empresaNome) {
        this.empresaNome = empresaNome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SerieDocumentoDTO serieDocumentoDTO = (SerieDocumentoDTO) o;
        if (serieDocumentoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), serieDocumentoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SerieDocumentoDTO{" +
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
            ", empresa=" + getEmpresaId() +
            ", empresa='" + getEmpresaNome() + "'" +
            "}";
    }
}
