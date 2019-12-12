package com.ravunana.manda.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.ravunana.manda.domain.Empresa} entity.
 */
public class EmpresaDTO implements Serializable {

    private Long id;

    private String tipoSociedade;

    @NotNull
    private String nome;

    @Lob
    private byte[] logotipo;

    private String logotipoContentType;
    @DecimalMin(value = "0")
    private BigDecimal capitalSocial;

    private LocalDate fundacao;

    @NotNull
    @Size(max = 20)
    private String nif;

    @NotNull
    private String numeroRegistroComercial;

    @NotNull
    @DecimalMin(value = "0")
    @DecimalMax(value = "100")
    private Double despesaFixa;

    @NotNull
    @DecimalMin(value = "0")
    @DecimalMax(value = "100")
    private Double despesaVariavel;

    private LocalDate aberturaExercio;

    @Lob
    private String designacaoComercial;

    private Boolean sede;


    private Long utilizadorId;

    private String utilizadorLogin;

    private Long contaId;

    private String contaDescricao;

    private Long hierarquiaId;

    private String hierarquiaNome;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipoSociedade() {
        return tipoSociedade;
    }

    public void setTipoSociedade(String tipoSociedade) {
        this.tipoSociedade = tipoSociedade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public byte[] getLogotipo() {
        return logotipo;
    }

    public void setLogotipo(byte[] logotipo) {
        this.logotipo = logotipo;
    }

    public String getLogotipoContentType() {
        return logotipoContentType;
    }

    public void setLogotipoContentType(String logotipoContentType) {
        this.logotipoContentType = logotipoContentType;
    }

    public BigDecimal getCapitalSocial() {
        return capitalSocial;
    }

    public void setCapitalSocial(BigDecimal capitalSocial) {
        this.capitalSocial = capitalSocial;
    }

    public LocalDate getFundacao() {
        return fundacao;
    }

    public void setFundacao(LocalDate fundacao) {
        this.fundacao = fundacao;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getNumeroRegistroComercial() {
        return numeroRegistroComercial;
    }

    public void setNumeroRegistroComercial(String numeroRegistroComercial) {
        this.numeroRegistroComercial = numeroRegistroComercial;
    }

    public Double getDespesaFixa() {
        return despesaFixa;
    }

    public void setDespesaFixa(Double despesaFixa) {
        this.despesaFixa = despesaFixa;
    }

    public Double getDespesaVariavel() {
        return despesaVariavel;
    }

    public void setDespesaVariavel(Double despesaVariavel) {
        this.despesaVariavel = despesaVariavel;
    }

    public LocalDate getAberturaExercio() {
        return aberturaExercio;
    }

    public void setAberturaExercio(LocalDate aberturaExercio) {
        this.aberturaExercio = aberturaExercio;
    }

    public String getDesignacaoComercial() {
        return designacaoComercial;
    }

    public void setDesignacaoComercial(String designacaoComercial) {
        this.designacaoComercial = designacaoComercial;
    }

    public Boolean isSede() {
        return sede;
    }

    public void setSede(Boolean sede) {
        this.sede = sede;
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

    public Long getHierarquiaId() {
        return hierarquiaId;
    }

    public void setHierarquiaId(Long empresaId) {
        this.hierarquiaId = empresaId;
    }

    public String getHierarquiaNome() {
        return hierarquiaNome;
    }

    public void setHierarquiaNome(String empresaNome) {
        this.hierarquiaNome = empresaNome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EmpresaDTO empresaDTO = (EmpresaDTO) o;
        if (empresaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), empresaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EmpresaDTO{" +
            "id=" + getId() +
            ", tipoSociedade='" + getTipoSociedade() + "'" +
            ", nome='" + getNome() + "'" +
            ", logotipo='" + getLogotipo() + "'" +
            ", capitalSocial=" + getCapitalSocial() +
            ", fundacao='" + getFundacao() + "'" +
            ", nif='" + getNif() + "'" +
            ", numeroRegistroComercial='" + getNumeroRegistroComercial() + "'" +
            ", despesaFixa=" + getDespesaFixa() +
            ", despesaVariavel=" + getDespesaVariavel() +
            ", aberturaExercio='" + getAberturaExercio() + "'" +
            ", designacaoComercial='" + getDesignacaoComercial() + "'" +
            ", sede='" + isSede() + "'" +
            ", utilizador=" + getUtilizadorId() +
            ", utilizador='" + getUtilizadorLogin() + "'" +
            ", conta=" + getContaId() +
            ", conta='" + getContaDescricao() + "'" +
            ", hierarquia=" + getHierarquiaId() +
            ", hierarquia='" + getHierarquiaNome() + "'" +
            "}";
    }
}
