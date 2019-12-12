package com.ravunana.manda.service.dto;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Lob;
import com.ravunana.manda.domain.enumeration.EntidadeSistema;

/**
 * A DTO for the {@link com.ravunana.manda.domain.EscrituracaoContabil} entity.
 */
public class EscrituracaoContabilDTO implements Serializable {

    private Long id;

    @NotNull
    private String numero;

    
    @Lob
    private String historico;

    @NotNull
    @DecimalMin(value = "0")
    private BigDecimal valor;

    
    private String referencia;

    private EntidadeSistema entidadeReferencia;

    @NotNull
    private String tipo;

    @NotNull
    private LocalDate dataDocumento;

    @NotNull
    private ZonedDateTime data;


    private Long utilizadorId;

    private String utilizadorLogin;

    private Long empresaId;

    private String empresaNome;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getHistorico() {
        return historico;
    }

    public void setHistorico(String historico) {
        this.historico = historico;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public EntidadeSistema getEntidadeReferencia() {
        return entidadeReferencia;
    }

    public void setEntidadeReferencia(EntidadeSistema entidadeReferencia) {
        this.entidadeReferencia = entidadeReferencia;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public LocalDate getDataDocumento() {
        return dataDocumento;
    }

    public void setDataDocumento(LocalDate dataDocumento) {
        this.dataDocumento = dataDocumento;
    }

    public ZonedDateTime getData() {
        return data;
    }

    public void setData(ZonedDateTime data) {
        this.data = data;
    }

    public Long getUtilizadorId() {
        return utilizadorId;
    }

    public void setUtilizadorId(Long userId) {
        this.utilizadorId = userId;
    }

    public String getUtilizadorLogin() {
        return utilizadorLogin;
    }

    public void setUtilizadorLogin(String userLogin) {
        this.utilizadorLogin = userLogin;
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

        EscrituracaoContabilDTO escrituracaoContabilDTO = (EscrituracaoContabilDTO) o;
        if (escrituracaoContabilDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), escrituracaoContabilDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EscrituracaoContabilDTO{" +
            "id=" + getId() +
            ", numero='" + getNumero() + "'" +
            ", historico='" + getHistorico() + "'" +
            ", valor=" + getValor() +
            ", referencia='" + getReferencia() + "'" +
            ", entidadeReferencia='" + getEntidadeReferencia() + "'" +
            ", tipo='" + getTipo() + "'" +
            ", dataDocumento='" + getDataDocumento() + "'" +
            ", data='" + getData() + "'" +
            ", utilizador=" + getUtilizadorId() +
            ", utilizador='" + getUtilizadorLogin() + "'" +
            ", empresa=" + getEmpresaId() +
            ", empresa='" + getEmpresaNome() + "'" +
            "}";
    }
}
