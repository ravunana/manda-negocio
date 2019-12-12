package com.ravunana.manda.service.dto;
import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the {@link com.ravunana.manda.domain.ItemVenda} entity.
 */
public class ItemVendaDTO implements Serializable {

    private Long id;

    @DecimalMin(value = "1")
    private Double quantidade;

    @DecimalMin(value = "0")
    private BigDecimal valor;

    @DecimalMin(value = "0")
    @DecimalMax(value = "100")
    private Double desconto;

    private ZonedDateTime data;


    private Long vendaId;

    private String vendaNumero;

    private Long produtoId;

    private String produtoNome;

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

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Double getDesconto() {
        return desconto;
    }

    public void setDesconto(Double desconto) {
        this.desconto = desconto;
    }

    public ZonedDateTime getData() {
        return data;
    }

    public void setData(ZonedDateTime data) {
        this.data = data;
    }

    public Long getVendaId() {
        return vendaId;
    }

    public void setVendaId(Long vendaId) {
        this.vendaId = vendaId;
    }

    public String getVendaNumero() {
        return vendaNumero;
    }

    public void setVendaNumero(String vendaNumero) {
        this.vendaNumero = vendaNumero;
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

        ItemVendaDTO itemVendaDTO = (ItemVendaDTO) o;
        if (itemVendaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), itemVendaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ItemVendaDTO{" +
            "id=" + getId() +
            ", quantidade=" + getQuantidade() +
            ", valor=" + getValor() +
            ", desconto=" + getDesconto() +
            ", data='" + getData() + "'" +
            ", venda=" + getVendaId() +
            ", venda='" + getVendaNumero() + "'" +
            ", produto=" + getProdutoId() +
            ", produto='" + getProdutoNome() + "'" +
            ", status=" + getStatusId() +
            ", status='" + getStatusNome() + "'" +
            "}";
    }
}
