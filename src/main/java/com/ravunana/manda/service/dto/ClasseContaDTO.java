package com.ravunana.manda.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.ravunana.manda.domain.ClasseConta} entity.
 */
public class ClasseContaDTO implements Serializable {

    private Long id;

    @NotNull
    private String descricao;

    @NotNull
    private String codigo;


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

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ClasseContaDTO classeContaDTO = (ClasseContaDTO) o;
        if (classeContaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), classeContaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ClasseContaDTO{" +
            "id=" + getId() +
            ", descricao='" + getDescricao() + "'" +
            ", codigo='" + getCodigo() + "'" +
            "}";
    }
}
