package com.ravunana.manda.service.dto;
import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the {@link com.ravunana.manda.domain.ContaCredito} entity.
 */
public class ContaCreditoDTO implements Serializable {

    private Long id;

    @NotNull
    @DecimalMin(value = "0")
    private BigDecimal valor;

    private ZonedDateTime data;


    private Long contaCreditarId;

    private String contaCreditarDescricao;

    private Long lancamentoCreditoId;

    private String lancamentoCreditoHistorico;

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

    public ZonedDateTime getData() {
        return data;
    }

    public void setData(ZonedDateTime data) {
        this.data = data;
    }

    public Long getContaCreditarId() {
        return contaCreditarId;
    }

    public void setContaCreditarId(Long contaId) {
        this.contaCreditarId = contaId;
    }

    public String getContaCreditarDescricao() {
        return contaCreditarDescricao;
    }

    public void setContaCreditarDescricao(String contaDescricao) {
        this.contaCreditarDescricao = contaDescricao;
    }

    public Long getLancamentoCreditoId() {
        return lancamentoCreditoId;
    }

    public void setLancamentoCreditoId(Long escrituracaoContabilId) {
        this.lancamentoCreditoId = escrituracaoContabilId;
    }

    public String getLancamentoCreditoHistorico() {
        return lancamentoCreditoHistorico;
    }

    public void setLancamentoCreditoHistorico(String escrituracaoContabilHistorico) {
        this.lancamentoCreditoHistorico = escrituracaoContabilHistorico;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ContaCreditoDTO contaCreditoDTO = (ContaCreditoDTO) o;
        if (contaCreditoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), contaCreditoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ContaCreditoDTO{" +
            "id=" + getId() +
            ", valor=" + getValor() +
            ", data='" + getData() + "'" +
            ", contaCreditar=" + getContaCreditarId() +
            ", contaCreditar='" + getContaCreditarDescricao() + "'" +
            ", lancamentoCredito=" + getLancamentoCreditoId() +
            ", lancamentoCredito='" + getLancamentoCreditoHistorico() + "'" +
            "}";
    }
}
