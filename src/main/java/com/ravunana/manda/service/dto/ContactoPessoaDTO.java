package com.ravunana.manda.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.ravunana.manda.domain.ContactoPessoa} entity.
 */
public class ContactoPessoaDTO implements Serializable {

    private Long id;

    @NotNull
    private String tipoContacto;

    private String descricao;

    @NotNull
    private String contacto;


    private Long pessoaId;

    private String pessoaNome;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipoContacto() {
        return tipoContacto;
    }

    public void setTipoContacto(String tipoContacto) {
        this.tipoContacto = tipoContacto;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ContactoPessoaDTO contactoPessoaDTO = (ContactoPessoaDTO) o;
        if (contactoPessoaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), contactoPessoaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ContactoPessoaDTO{" +
            "id=" + getId() +
            ", tipoContacto='" + getTipoContacto() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", contacto='" + getContacto() + "'" +
            ", pessoa=" + getPessoaId() +
            ", pessoa='" + getPessoaNome() + "'" +
            "}";
    }
}
