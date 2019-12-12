package com.ravunana.manda.service.dto;
import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the {@link com.ravunana.manda.domain.ContaDebito} entity.
 */
public class ContaDebitoDTO implements Serializable {

    private Long id;

    @NotNull
    @DecimalMin(value = "0")
    private BigDecimal valor;

    private ZonedDateTime data;


    private Long contaDebitarId;

    private String contaDebitarDescricao;

    private Long lancamentoDebitoId;

    private String lancamentoDebitoHistorico;

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

    public Long getContaDebitarId() {
        return contaDebitarId;
    }

    public void setContaDebitarId(Long contaId) {
        this.contaDebitarId = contaId;
    }

    public String getContaDebitarDescricao() {
        return contaDebitarDescricao;
    }

    public void setContaDebitarDescricao(String contaDescricao) {
        this.contaDebitarDescricao = contaDescricao;
    }

    public Long getLancamentoDebitoId() {
        return lancamentoDebitoId;
    }

    public void setLancamentoDebitoId(Long escrituracaoContabilId) {
        this.lancamentoDebitoId = escrituracaoContabilId;
    }

    public String getLancamentoDebitoHistorico() {
        return lancamentoDebitoHistorico;
    }

    public void setLancamentoDebitoHistorico(String escrituracaoContabilHistorico) {
        this.lancamentoDebitoHistorico = escrituracaoContabilHistorico;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ContaDebitoDTO contaDebitoDTO = (ContaDebitoDTO) o;
        if (contaDebitoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), contaDebitoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ContaDebitoDTO{" +
            "id=" + getId() +
            ", valor=" + getValor() +
            ", data='" + getData() + "'" +
            ", contaDebitar=" + getContaDebitarId() +
            ", contaDebitar='" + getContaDebitarDescricao() + "'" +
            ", lancamentoDebito=" + getLancamentoDebitoId() +
            ", lancamentoDebito='" + getLancamentoDebitoHistorico() + "'" +
            "}";
    }
}
