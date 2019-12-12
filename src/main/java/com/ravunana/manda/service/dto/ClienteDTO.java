package com.ravunana.manda.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.ravunana.manda.domain.Cliente} entity.
 */
public class ClienteDTO implements Serializable {

    private Long id;

    private Boolean ativo;

    @Lob
    private String perfilProfissional;

    private Double satisfacao;

    private Double frequencia;

    private String canalUsado;

    @NotNull
    private String numero;

    private Boolean autofacturacao;


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

    public Boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public String getPerfilProfissional() {
        return perfilProfissional;
    }

    public void setPerfilProfissional(String perfilProfissional) {
        this.perfilProfissional = perfilProfissional;
    }

    public Double getSatisfacao() {
        return satisfacao;
    }

    public void setSatisfacao(Double satisfacao) {
        this.satisfacao = satisfacao;
    }

    public Double getFrequencia() {
        return frequencia;
    }

    public void setFrequencia(Double frequencia) {
        this.frequencia = frequencia;
    }

    public String getCanalUsado() {
        return canalUsado;
    }

    public void setCanalUsado(String canalUsado) {
        this.canalUsado = canalUsado;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Boolean isAutofacturacao() {
        return autofacturacao;
    }

    public void setAutofacturacao(Boolean autofacturacao) {
        this.autofacturacao = autofacturacao;
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

        ClienteDTO clienteDTO = (ClienteDTO) o;
        if (clienteDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), clienteDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ClienteDTO{" +
            "id=" + getId() +
            ", ativo='" + isAtivo() + "'" +
            ", perfilProfissional='" + getPerfilProfissional() + "'" +
            ", satisfacao=" + getSatisfacao() +
            ", frequencia=" + getFrequencia() +
            ", canalUsado='" + getCanalUsado() + "'" +
            ", numero='" + getNumero() + "'" +
            ", autofacturacao='" + isAutofacturacao() + "'" +
            ", pessoa=" + getPessoaId() +
            ", pessoa='" + getPessoaNome() + "'" +
            ", conta=" + getContaId() +
            ", conta='" + getContaDescricao() + "'" +
            "}";
    }
}
