package com.ravunana.manda.service.dto;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.ravunana.manda.domain.RegraMovimentacaoCredito} entity.
 */
public class RegraMovimentacaoCreditoDTO implements Serializable {

    private Long id;

    @Lob
    private String descricao;


    private Long contaId;

    private String contaDescricao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Long getContaId() {
        return contaId;
    }

    public void setContaId(Long contaId) {
        this.contaId = contaId;
    }

    public String getContaDescricao() {
        return contaDescricao;
    }

    public void setContaDescricao(String contaDescricao) {
        this.contaDescricao = contaDescricao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RegraMovimentacaoCreditoDTO regraMovimentacaoCreditoDTO = (RegraMovimentacaoCreditoDTO) o;
        if (regraMovimentacaoCreditoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), regraMovimentacaoCreditoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RegraMovimentacaoCreditoDTO{" +
            "id=" + getId() +
            ", descricao='" + getDescricao() + "'" +
            ", conta=" + getContaId() +
            ", conta='" + getContaDescricao() + "'" +
            "}";
    }
}
