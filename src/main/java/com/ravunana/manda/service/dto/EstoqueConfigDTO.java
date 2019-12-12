package com.ravunana.manda.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.ravunana.manda.domain.EstoqueConfig} entity.
 */
public class EstoqueConfigDTO implements Serializable {

    private Long id;


    private Long produtoId;

    private String produtoNome;

    private Long contaVendaId;

    private String contaVendaDescricao;

    private Long contaCompraId;

    private String contaCompraDescricao;

    private Long contaImobilizadoId;

    private String contaImobilizadoDescricao;

    private Long devolucaoCompraId;

    private String devolucaoCompraDescricao;

    private Long devolucaoVendaId;

    private String devolucaoVendaDescricao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getContaVendaId() {
        return contaVendaId;
    }

    public void setContaVendaId(Long contaId) {
        this.contaVendaId = contaId;
    }

    public String getContaVendaDescricao() {
        return contaVendaDescricao;
    }

    public void setContaVendaDescricao(String contaDescricao) {
        this.contaVendaDescricao = contaDescricao;
    }

    public Long getContaCompraId() {
        return contaCompraId;
    }

    public void setContaCompraId(Long contaId) {
        this.contaCompraId = contaId;
    }

    public String getContaCompraDescricao() {
        return contaCompraDescricao;
    }

    public void setContaCompraDescricao(String contaDescricao) {
        this.contaCompraDescricao = contaDescricao;
    }

    public Long getContaImobilizadoId() {
        return contaImobilizadoId;
    }

    public void setContaImobilizadoId(Long contaId) {
        this.contaImobilizadoId = contaId;
    }

    public String getContaImobilizadoDescricao() {
        return contaImobilizadoDescricao;
    }

    public void setContaImobilizadoDescricao(String contaDescricao) {
        this.contaImobilizadoDescricao = contaDescricao;
    }

    public Long getDevolucaoCompraId() {
        return devolucaoCompraId;
    }

    public void setDevolucaoCompraId(Long contaId) {
        this.devolucaoCompraId = contaId;
    }

    public String getDevolucaoCompraDescricao() {
        return devolucaoCompraDescricao;
    }

    public void setDevolucaoCompraDescricao(String contaDescricao) {
        this.devolucaoCompraDescricao = contaDescricao;
    }

    public Long getDevolucaoVendaId() {
        return devolucaoVendaId;
    }

    public void setDevolucaoVendaId(Long contaId) {
        this.devolucaoVendaId = contaId;
    }

    public String getDevolucaoVendaDescricao() {
        return devolucaoVendaDescricao;
    }

    public void setDevolucaoVendaDescricao(String contaDescricao) {
        this.devolucaoVendaDescricao = contaDescricao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EstoqueConfigDTO estoqueConfigDTO = (EstoqueConfigDTO) o;
        if (estoqueConfigDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), estoqueConfigDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EstoqueConfigDTO{" +
            "id=" + getId() +
            ", produto=" + getProdutoId() +
            ", produto='" + getProdutoNome() + "'" +
            ", contaVenda=" + getContaVendaId() +
            ", contaVenda='" + getContaVendaDescricao() + "'" +
            ", contaCompra=" + getContaCompraId() +
            ", contaCompra='" + getContaCompraDescricao() + "'" +
            ", contaImobilizado=" + getContaImobilizadoId() +
            ", contaImobilizado='" + getContaImobilizadoDescricao() + "'" +
            ", devolucaoCompra=" + getDevolucaoCompraId() +
            ", devolucaoCompra='" + getDevolucaoCompraDescricao() + "'" +
            ", devolucaoVenda=" + getDevolucaoVendaId() +
            ", devolucaoVenda='" + getDevolucaoVendaDescricao() + "'" +
            "}";
    }
}
