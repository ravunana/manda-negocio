package com.ravunana.manda.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.ravunana.manda.domain.GrupoTributacaoImposto} entity.
 */
public class GrupoTributacaoImpostoDTO implements Serializable {

    private Long id;

    @NotNull
    private String nome;

    @NotNull
    @DecimalMin(value = "0")
    private Double valor;

    private Double de;

    private Double ate;

    @Min(value = 1)
    @Max(value = 31)
    private Integer limiteLiquidacao;


    private Long impostoId;

    private String impostoCodigoImposto;

    private Long unidadeLimiteId;

    private String unidadeLimiteNome;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Double getDe() {
        return de;
    }

    public void setDe(Double de) {
        this.de = de;
    }

    public Double getAte() {
        return ate;
    }

    public void setAte(Double ate) {
        this.ate = ate;
    }

    public Integer getLimiteLiquidacao() {
        return limiteLiquidacao;
    }

    public void setLimiteLiquidacao(Integer limiteLiquidacao) {
        this.limiteLiquidacao = limiteLiquidacao;
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

    public Long getUnidadeLimiteId() {
        return unidadeLimiteId;
    }

    public void setUnidadeLimiteId(Long unidadeMedidaId) {
        this.unidadeLimiteId = unidadeMedidaId;
    }

    public String getUnidadeLimiteNome() {
        return unidadeLimiteNome;
    }

    public void setUnidadeLimiteNome(String unidadeMedidaNome) {
        this.unidadeLimiteNome = unidadeMedidaNome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        GrupoTributacaoImpostoDTO grupoTributacaoImpostoDTO = (GrupoTributacaoImpostoDTO) o;
        if (grupoTributacaoImpostoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), grupoTributacaoImpostoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "GrupoTributacaoImpostoDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", valor=" + getValor() +
            ", de=" + getDe() +
            ", ate=" + getAte() +
            ", limiteLiquidacao=" + getLimiteLiquidacao() +
            ", imposto=" + getImpostoId() +
            ", imposto='" + getImpostoCodigoImposto() + "'" +
            ", unidadeLimite=" + getUnidadeLimiteId() +
            ", unidadeLimite='" + getUnidadeLimiteNome() + "'" +
            "}";
    }
}
