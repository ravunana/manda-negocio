package com.ravunana.manda.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.ravunana.manda.domain.RelacionamentoPessoa} entity.
 */
public class RelacionamentoPessoaDTO implements Serializable {

    private Long id;

    @NotNull
    private String grauParentesco;


    private Long deId;

    private String deNome;

    private Long paraId;

    private String paraNome;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGrauParentesco() {
        return grauParentesco;
    }

    public void setGrauParentesco(String grauParentesco) {
        this.grauParentesco = grauParentesco;
    }

    public Long getDeId() {
        return deId;
    }

    public void setDeId(Long pessoaId) {
        this.deId = pessoaId;
    }

    public String getDeNome() {
        return deNome;
    }

    public void setDeNome(String pessoaNome) {
        this.deNome = pessoaNome;
    }

    public Long getParaId() {
        return paraId;
    }

    public void setParaId(Long pessoaId) {
        this.paraId = pessoaId;
    }

    public String getParaNome() {
        return paraNome;
    }

    public void setParaNome(String pessoaNome) {
        this.paraNome = pessoaNome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RelacionamentoPessoaDTO relacionamentoPessoaDTO = (RelacionamentoPessoaDTO) o;
        if (relacionamentoPessoaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), relacionamentoPessoaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RelacionamentoPessoaDTO{" +
            "id=" + getId() +
            ", grauParentesco='" + getGrauParentesco() + "'" +
            ", de=" + getDeId() +
            ", de='" + getDeNome() + "'" +
            ", para=" + getParaId() +
            ", para='" + getParaNome() + "'" +
            "}";
    }
}
