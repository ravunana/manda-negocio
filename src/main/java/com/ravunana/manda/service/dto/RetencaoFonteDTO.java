package com.ravunana.manda.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.ravunana.manda.domain.RetencaoFonte} entity.
 */
public class RetencaoFonteDTO implements Serializable {

    private Long id;

    private String motivoRetencao;

    private String valor;

    private Boolean porcentagem;


    private Long detalheId;

    private Long impostoId;

    private String impostoCodigoImposto;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMotivoRetencao() {
        return motivoRetencao;
    }

    public void setMotivoRetencao(String motivoRetencao) {
        this.motivoRetencao = motivoRetencao;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public Boolean isPorcentagem() {
        return porcentagem;
    }

    public void setPorcentagem(Boolean porcentagem) {
        this.porcentagem = porcentagem;
    }

    public Long getDetalheId() {
        return detalheId;
    }

    public void setDetalheId(Long detalheLancamentoId) {
        this.detalheId = detalheLancamentoId;
    }

    public Long getImpostoId() {
        return impostoId;
    }

    public void setImpostoId(Long impostoId) {
        this.impostoId = impostoId;
    }

    public String getImpostoCodigoImposto() {
        return impostoCodigoImposto;
    }

    public void setImpostoCodigoImposto(String impostoCodigoImposto) {
        this.impostoCodigoImposto = impostoCodigoImposto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RetencaoFonteDTO retencaoFonteDTO = (RetencaoFonteDTO) o;
        if (retencaoFonteDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), retencaoFonteDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RetencaoFonteDTO{" +
            "id=" + getId() +
            ", motivoRetencao='" + getMotivoRetencao() + "'" +
            ", valor='" + getValor() + "'" +
            ", porcentagem='" + isPorcentagem() + "'" +
            ", detalhe=" + getDetalheId() +
            ", imposto=" + getImpostoId() +
            ", imposto='" + getImpostoCodigoImposto() + "'" +
            "}";
    }
}
