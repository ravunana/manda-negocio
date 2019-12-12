package com.ravunana.manda.service.dto;
import java.io.Serializable;
import java.util.Objects;
import com.ravunana.manda.domain.enumeration.LookupType;

/**
 * A DTO for the {@link com.ravunana.manda.domain.LookupItem} entity.
 */
public class LookupItemDTO implements Serializable {

    private Long id;

    private String valor;

    private String nome;

    private LookupType type;

    private Integer ordem;


    private Long lookupId;

    private String lookupNome;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LookupType getType() {
        return type;
    }

    public void setType(LookupType type) {
        this.type = type;
    }

    public Integer getOrdem() {
        return ordem;
    }

    public void setOrdem(Integer ordem) {
        this.ordem = ordem;
    }

    public Long getLookupId() {
        return lookupId;
    }

    public void setLookupId(Long lookupId) {
        this.lookupId = lookupId;
    }

    public String getLookupNome() {
        return lookupNome;
    }

    public void setLookupNome(String lookupNome) {
        this.lookupNome = lookupNome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LookupItemDTO lookupItemDTO = (LookupItemDTO) o;
        if (lookupItemDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), lookupItemDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LookupItemDTO{" +
            "id=" + getId() +
            ", valor='" + getValor() + "'" +
            ", nome='" + getNome() + "'" +
            ", type='" + getType() + "'" +
            ", ordem=" + getOrdem() +
            ", lookup=" + getLookupId() +
            ", lookup='" + getLookupNome() + "'" +
            "}";
    }
}
