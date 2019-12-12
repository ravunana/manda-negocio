package com.ravunana.manda.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.ravunana.manda.domain.LocalArmazenamento} entity.
 */
public class LocalArmazenamentoDTO implements Serializable {

    private Long id;

    @NotNull
    private String nome;

    @Lob
    private String descricao;


    private Long hierarquiaId;

    private String hierarquiaNome;

    private Long empresaId;

    private String empresaNome;

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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Long getHierarquiaId() {
        return hierarquiaId;
    }

    public void setHierarquiaId(Long localArmazenamentoId) {
        this.hierarquiaId = localArmazenamentoId;
    }

    public String getHierarquiaNome() {
        return hierarquiaNome;
    }

    public void setHierarquiaNome(String localArmazenamentoNome) {
        this.hierarquiaNome = localArmazenamentoNome;
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

        LocalArmazenamentoDTO localArmazenamentoDTO = (LocalArmazenamentoDTO) o;
        if (localArmazenamentoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), localArmazenamentoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LocalArmazenamentoDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", hierarquia=" + getHierarquiaId() +
            ", hierarquia='" + getHierarquiaNome() + "'" +
            ", empresa=" + getEmpresaId() +
            ", empresa='" + getEmpresaNome() + "'" +
            "}";
    }
}
