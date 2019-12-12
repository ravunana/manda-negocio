package com.ravunana.manda.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.ravunana.manda.domain.Fornecedor} entity.
 */
public class FornecedorDTO implements Serializable {

    private Long id;

    private Boolean certificadoISO9001;

    private Boolean garantiaDefeitoFabrica;

    private Boolean transporte;

    @DecimalMin(value = "0")
    @DecimalMax(value = "10")
    private Double qualidade;

    private Boolean pagamentoPrazo;

    @Lob
    private String metodosPagamento;

    @NotNull
    @DecimalMin(value = "0")
    @DecimalMax(value = "10")
    private Double classificacao;

    @Lob
    private String descricao;

    private Boolean ativo;

    @NotNull
    private String numero;


    private Long pessoaId;

    private String pessoaNome;

    private Long contaId;

    private String contaDescricao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isCertificadoISO9001() {
        return certificadoISO9001;
    }

    public void setCertificadoISO9001(Boolean certificadoISO9001) {
        this.certificadoISO9001 = certificadoISO9001;
    }

    public Boolean isGarantiaDefeitoFabrica() {
        return garantiaDefeitoFabrica;
    }

    public void setGarantiaDefeitoFabrica(Boolean garantiaDefeitoFabrica) {
        this.garantiaDefeitoFabrica = garantiaDefeitoFabrica;
    }

    public Boolean isTransporte() {
        return transporte;
    }

    public void setTransporte(Boolean transporte) {
        this.transporte = transporte;
    }

    public Double getQualidade() {
        return qualidade;
    }

    public void setQualidade(Double qualidade) {
        this.qualidade = qualidade;
    }

    public Boolean isPagamentoPrazo() {
        return pagamentoPrazo;
    }

    public void setPagamentoPrazo(Boolean pagamentoPrazo) {
        this.pagamentoPrazo = pagamentoPrazo;
    }

    public String getMetodosPagamento() {
        return metodosPagamento;
    }

    public void setMetodosPagamento(String metodosPagamento) {
        this.metodosPagamento = metodosPagamento;
    }

    public Double getClassificacao() {
        return classificacao;
    }

    public void setClassificacao(Double classificacao) {
        this.classificacao = classificacao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Long getPessoaId() {
        return pessoaId;
    }

    public void setPessoaId(Long pessoaId) {
        this.pessoaId = pessoaId;
    }

    public String getPessoaNome() {
        return pessoaNome;
    }

    public void setPessoaNome(String pessoaNome) {
        this.pessoaNome = pessoaNome;
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

        FornecedorDTO fornecedorDTO = (FornecedorDTO) o;
        if (fornecedorDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), fornecedorDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FornecedorDTO{" +
            "id=" + getId() +
            ", certificadoISO9001='" + isCertificadoISO9001() + "'" +
            ", garantiaDefeitoFabrica='" + isGarantiaDefeitoFabrica() + "'" +
            ", transporte='" + isTransporte() + "'" +
            ", qualidade=" + getQualidade() +
            ", pagamentoPrazo='" + isPagamentoPrazo() + "'" +
            ", metodosPagamento='" + getMetodosPagamento() + "'" +
            ", classificacao=" + getClassificacao() +
            ", descricao='" + getDescricao() + "'" +
            ", ativo='" + isAtivo() + "'" +
            ", numero='" + getNumero() + "'" +
            ", pessoa=" + getPessoaId() +
            ", pessoa='" + getPessoaNome() + "'" +
            ", conta=" + getContaId() +
            ", conta='" + getContaDescricao() + "'" +
            "}";
    }
}
