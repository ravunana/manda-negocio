package com.ravunana.manda.service.dto;
import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.ravunana.manda.domain.DevolucaoVenda} entity.
 */
public class DevolucaoVendaDTO implements Serializable {

    private Long id;

    @DecimalMin(value = "1")
    private Double quantidade;

    @DecimalMin(value = "0")
    private Double valor;

    @DecimalMin(value = "0")
    private Double desconto;

    private ZonedDateTime data;

    @Lob
    private String descricao;


    private Long itemId;

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

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemVendaId) {
        this.itemId = itemVendaId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DevolucaoVendaDTO devolucaoVendaDTO = (DevolucaoVendaDTO) o;
        if (devolucaoVendaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), devolucaoVendaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DevolucaoVendaDTO{" +
            "id=" + getId() +
            ", quantidade=" + getQuantidade() +
            ", valor=" + getValor() +
            ", desconto=" + getDesconto() +
            ", data='" + getData() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", item=" + getItemId() +
            "}";
    }
}
