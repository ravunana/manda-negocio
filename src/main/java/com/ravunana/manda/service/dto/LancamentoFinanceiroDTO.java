package com.ravunana.manda.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import javax.persistence.Lob;
import com.ravunana.manda.domain.enumeration.EntidadeSistema;

/**
 * A DTO for the {@link com.ravunana.manda.domain.LancamentoFinanceiro} entity.
 */
public class LancamentoFinanceiroDTO implements Serializable {

    private Long id;

    @NotNull
    private String tipoLancamento;

    @NotNull
    @DecimalMin(value = "0")
    private BigDecimal valor;

    private Boolean externo;

    @NotNull
    private String numero;

    
    @Lob
    private String descricao;

    @NotNull
    private EntidadeSistema entidadeDocumento;

    @NotNull
    private String numeroDocumento;


    private Long utilizadorId;

    private String utilizadorLogin;

    private Set<ImpostoDTO> impostos = new HashSet<>();

    private Long formaLiquidacaoId;

    private String formaLiquidacaoNome;

    private Long empresaId;

    private String empresaNome;

    private Long tipoReciboId;

    private String tipoReciboNome;

    private Long contaId;

    private String contaDescricao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipoLancamento() {
        return tipoLancamento;
    }

    public void setTipoLancamento(String tipoLancamento) {
        this.tipoLancamento = tipoLancamento;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Boolean isExterno() {
        return externo;
    }

    public void setExterno(Boolean externo) {
        this.externo = externo;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public EntidadeSistema getEntidadeDocumento() {
        return entidadeDocumento;
    }

    public void setEntidadeDocumento(EntidadeSistema entidadeDocumento) {
        this.entidadeDocumento = entidadeDocumento;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
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

    public Set<ImpostoDTO> getImpostos() {
        return impostos;
    }

    public void setImpostos(Set<ImpostoDTO> impostos) {
        this.impostos = impostos;
    }

    public Long getFormaLiquidacaoId() {
        return formaLiquidacaoId;
    }

    public void setFormaLiquidacaoId(Long formaLiquidacaoId) {
        this.formaLiquidacaoId = formaLiquidacaoId;
    }

    public String getFormaLiquidacaoNome() {
        return formaLiquidacaoNome;
    }

    public void setFormaLiquidacaoNome(String formaLiquidacaoNome) {
        this.formaLiquidacaoNome = formaLiquidacaoNome;
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

    public Long getTipoReciboId() {
        return tipoReciboId;
    }

    public void setTipoReciboId(Long documentoComercialId) {
        this.tipoReciboId = documentoComercialId;
    }

    public String getTipoReciboNome() {
        return tipoReciboNome;
    }

    public void setTipoReciboNome(String documentoComercialNome) {
        this.tipoReciboNome = documentoComercialNome;
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

        LancamentoFinanceiroDTO lancamentoFinanceiroDTO = (LancamentoFinanceiroDTO) o;
        if (lancamentoFinanceiroDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), lancamentoFinanceiroDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LancamentoFinanceiroDTO{" +
            "id=" + getId() +
            ", tipoLancamento='" + getTipoLancamento() + "'" +
            ", valor=" + getValor() +
            ", externo='" + isExterno() + "'" +
            ", numero='" + getNumero() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", entidadeDocumento='" + getEntidadeDocumento() + "'" +
            ", numeroDocumento='" + getNumeroDocumento() + "'" +
            ", utilizador=" + getUtilizadorId() +
            ", utilizador='" + getUtilizadorLogin() + "'" +
            ", formaLiquidacao=" + getFormaLiquidacaoId() +
            ", formaLiquidacao='" + getFormaLiquidacaoNome() + "'" +
            ", empresa=" + getEmpresaId() +
            ", empresa='" + getEmpresaNome() + "'" +
            ", tipoRecibo=" + getTipoReciboId() +
            ", tipoRecibo='" + getTipoReciboNome() + "'" +
            ", conta=" + getContaId() +
            ", conta='" + getContaDescricao() + "'" +
            "}";
    }
}
