package com.ravunana.manda.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.ravunana.manda.domain.FamiliaProduto} entity.
 */
public class FamiliaProdutoDTO implements Serializable {

    private Long id;

    @NotNull
    private String nome;

    @Lob
    private String descricao;


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

    public void setHierarquiaId(Long familiaProdutoId) {
        this.hierarquiaId = familiaProdutoId;
    }

    public String getHierarquiaNome() {
        return hierarquiaNome;
    }

    public void setHierarquiaNome(String familiaProdutoNome) {
        this.hierarquiaNome = familiaProdutoNome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FamiliaProdutoDTO familiaProdutoDTO = (FamiliaProdutoDTO) o;
        if (familiaProdutoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), familiaProdutoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FamiliaProdutoDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", conta=" + getContaId() +
            ", conta='" + getContaDescricao() + "'" +
            ", hierarquia=" + getHierarquiaId() +
            ", hierarquia='" + getHierarquiaNome() + "'" +
            "}";
    }
}
