package com.ravunana.manda.service.dto;
import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.ravunana.manda.domain.ItemCompra} entity.
 */
public class ItemCompraDTO implements Serializable {

    private Long id;

    @DecimalMin(value = "1")
    private Double quantidade;

    @DecimalMin(value = "0")
    @DecimalMax(value = "100")
    private Double desconto;

    private ZonedDateTime dataSolicitacao;

    private ZonedDateTime dataEntrega;

    @Lob
    private String descricao;

    private BigDecimal valor;

    private String solicitante;


    private Long compraId;

    private String compraNumero;

    private Long produtoId;

    private String produtoNome;

    private Long fornecedorId;

    private String fornecedorNumero;

    private Long statusId;

    private String statusNome;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Double quantidade) {
        this.quantidade = quantidade;
    }

    public Double getDesconto() {
        return desconto;
    }

    public void setDesconto(Double desconto) {
        this.desconto = desconto;
    }

    public ZonedDateTime getDataSolicitacao() {
        return dataSolicitacao;
    }

    public void setDataSolicitacao(ZonedDateTime dataSolicitacao) {
        this.dataSolicitacao = dataSolicitacao;
    }

    public ZonedDateTime getDataEntrega() {
        return dataEntrega;
    }

    public void setDataEntrega(ZonedDateTime dataEntrega) {
        this.dataEntrega = dataEntrega;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getSolicitante() {
        return solicitante;
    }

    public void setSolicitante(String solicitante) {
        this.solicitante = solicitante;
    }

    public Long getCompraId() {
        return compraId;
    }

    public void setCompraId(Long compraId) {
        this.compraId = compraId;
    }

    public String getCompraNumero() {
        return compraNumero;
    }

    public void setCompraNumero(String compraNumero) {
        this.compraNumero = compraNumero;
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

    public Long getFornecedorId() {
        return fornecedorId;
    }

    public void setFornecedorId(Long fornecedorId) {
        this.fornecedorId = fornecedorId;
    }

    public String getFornecedorNumero() {
        return fornecedorNumero;
    }

    public void setFornecedorNumero(String fornecedorNumero) {
        this.fornecedorNumero = fornecedorNumero;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long fluxoDocumentoId) {
        this.statusId = fluxoDocumentoId;
    }

    public String getStatusNome() {
        return statusNome;
    }

    public void setStatusNome(String fluxoDocumentoNome) {
        this.statusNome = fluxoDocumentoNome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ItemCompraDTO itemCompraDTO = (ItemCompraDTO) o;
        if (itemCompraDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), itemCompraDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ItemCompraDTO{" +
            "id=" + getId() +
            ", quantidade=" + getQuantidade() +
            ", desconto=" + getDesconto() +
            ", dataSolicitacao='" + getDataSolicitacao() + "'" +
            ", dataEntrega='" + getDataEntrega() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", valor=" + getValor() +
            ", solicitante='" + getSolicitante() + "'" +
            ", compra=" + getCompraId() +
            ", compra='" + getCompraNumero() + "'" +
            ", produto=" + getProdutoId() +
            ", produto='" + getProdutoNome() + "'" +
            ", fornecedor=" + getFornecedorId() +
            ", fornecedor='" + getFornecedorNumero() + "'" +
            ", status=" + getStatusId() +
            ", status='" + getStatusNome() + "'" +
            "}";
    }
}
