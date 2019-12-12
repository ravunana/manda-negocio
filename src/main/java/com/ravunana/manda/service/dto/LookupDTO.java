package com.ravunana.manda.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.ravunana.manda.domain.enumeration.EntidadeSistema;

/**
 * A DTO for the {@link com.ravunana.manda.domain.Lookup} entity.
 */
public class LookupDTO implements Serializable {

    private Long id;

    @NotNull
    private String nome;

    private EntidadeSistema entidade;

    private Boolean modificavel;


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

    public EntidadeSistema getEntidade() {
        return entidade;
    }

    public void setEntidade(EntidadeSistema entidade) {
        this.entidade = entidade;
    }

    public Boolean isModificavel() {
        return modificavel;
    }

    public void setModificavel(Boolean modificavel) {
        this.modificavel = modificavel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LookupDTO lookupDTO = (LookupDTO) o;
        if (lookupDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), lookupDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LookupDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", entidade='" + getEntidade() + "'" +
            ", modificavel='" + isModificavel() + "'" +
            "}";
    }
}
