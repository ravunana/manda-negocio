package com.ravunana.manda.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.ravunana.manda.domain.Conta} entity.
 */
public class ContaDTO implements Serializable {

    private Long id;

    @NotNull
    private String descricao;

    @NotNull
    private String codigo;

    private String tipo;

    @Min(value = 1)
    @Max(value = 5)
    private Integer grau;

    private String natureza;

    private String grupo;

    @Lob
    private String conteudo;


    private Set<EmpresaDTO> empresas = new HashSet<>();

    private Long contaAgregadoraId;

    private String contaAgregadoraDescricao;

    private Long classeContaId;

    private String classeContaDescricao;

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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getGrau() {
        return grau;
    }

    public void setGrau(Integer grau) {
        this.grau = grau;
    }

    public String getNatureza() {
        return natureza;
    }

    public void setNatureza(String natureza) {
        this.natureza = natureza;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public Set<EmpresaDTO> getEmpresas() {
        return empresas;
    }

    public void setEmpresas(Set<EmpresaDTO> empresas) {
        this.empresas = empresas;
    }

    public Long getContaAgregadoraId() {
        return contaAgregadoraId;
    }

    public void setContaAgregadoraId(Long contaId) {
        this.contaAgregadoraId = contaId;
    }

    public String getContaAgregadoraDescricao() {
        return contaAgregadoraDescricao;
    }

    public void setContaAgregadoraDescricao(String contaDescricao) {
        this.contaAgregadoraDescricao = contaDescricao;
    }

    public Long getClasseContaId() {
        return classeContaId;
    }

    public void setClasseContaId(Long classeContaId) {
        this.classeContaId = classeContaId;
    }

    public String getClasseContaDescricao() {
        return classeContaDescricao;
    }

    public void setClasseContaDescricao(String classeContaDescricao) {
        this.classeContaDescricao = classeContaDescricao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ContaDTO contaDTO = (ContaDTO) o;
        if (contaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), contaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ContaDTO{" +
            "id=" + getId() +
            ", descricao='" + getDescricao() + "'" +
            ", codigo='" + getCodigo() + "'" +
            ", tipo='" + getTipo() + "'" +
            ", grau=" + getGrau() +
            ", natureza='" + getNatureza() + "'" +
            ", grupo='" + getGrupo() + "'" +
            ", conteudo='" + getConteudo() + "'" +
            ", contaAgregadora=" + getContaAgregadoraId() +
            ", contaAgregadora='" + getContaAgregadoraDescricao() + "'" +
            ", classeConta=" + getClasseContaId() +
            ", classeConta='" + getClasseContaDescricao() + "'" +
            "}";
    }
}
