package com.ravunana.manda.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.ravunana.manda.domain.Produto} entity.
 */
public class ProdutoDTO implements Serializable {

    private Long id;

    private String tipo;

    
    private String ean;

    @NotNull
    private String numero;

    @NotNull
    private String nome;

    @Lob
    private byte[] imagem;

    private String imagemContentType;
    @NotNull
    private Boolean composto;

    @DecimalMin(value = "0")
    private Double estoqueMinimo;

    @DecimalMin(value = "0")
    private Double estoqueMaximo;

    @NotNull
    @DecimalMin(value = "0")
    private Double estoqueAtual;

    @Lob
    private String descricao;

    private Boolean mostrarPDV;

    private String prazoMedioEntrega;


    private Long utilizadorId;

    private String utilizadorLogin;

    private Set<ImpostoDTO> impostos = new HashSet<>();

    private Set<FornecedorDTO> fornecedors = new HashSet<>();

    private Long localArmazenamentoId;

    private String localArmazenamentoNome;

    private Long familiaId;

    private String familiaNome;

    private Long empresaId;

    private String empresaNome;

    private Long estadoId;

    private String estadoNome;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getEan() {
        return ean;
    }

    public void setEan(String ean) {
        this.ean = ean;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public byte[] getImagem() {
        return imagem;
    }

    public void setImagem(byte[] imagem) {
        this.imagem = imagem;
    }

    public String getImagemContentType() {
        return imagemContentType;
    }

    public void setImagemContentType(String imagemContentType) {
        this.imagemContentType = imagemContentType;
    }

    public Boolean isComposto() {
        return composto;
    }

    public void setComposto(Boolean composto) {
        this.composto = composto;
    }

    public Double getEstoqueMinimo() {
        return estoqueMinimo;
    }

    public void setEstoqueMinimo(Double estoqueMinimo) {
        this.estoqueMinimo = estoqueMinimo;
    }

    public Double getEstoqueMaximo() {
        return estoqueMaximo;
    }

    public void setEstoqueMaximo(Double estoqueMaximo) {
        this.estoqueMaximo = estoqueMaximo;
    }

    public Double getEstoqueAtual() {
        return estoqueAtual;
    }

    public void setEstoqueAtual(Double estoqueAtual) {
        this.estoqueAtual = estoqueAtual;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Boolean isMostrarPDV() {
        return mostrarPDV;
    }

    public void setMostrarPDV(Boolean mostrarPDV) {
        this.mostrarPDV = mostrarPDV;
    }

    public String getPrazoMedioEntrega() {
        return prazoMedioEntrega;
    }

    public void setPrazoMedioEntrega(String prazoMedioEntrega) {
        this.prazoMedioEntrega = prazoMedioEntrega;
    }

    public Long getUtilizadorId() {
        return utilizadorId;
    }

    public void setUtilizadorId(Long userId) {
        this.utilizadorId = userId;
    }

    public String getUtilizadorLogin() {
        return utilizadorLogin;
    }

    public void setUtilizadorLogin(String userLogin) {
        this.utilizadorLogin = userLogin;
    }

    public Set<ImpostoDTO> getImpostos() {
        return impostos;
    }

    public void setImpostos(Set<ImpostoDTO> impostos) {
        this.impostos = impostos;
    }

    public Set<FornecedorDTO> getFornecedors() {
        return fornecedors;
    }

    public void setFornecedors(Set<FornecedorDTO> fornecedors) {
        this.fornecedors = fornecedors;
    }

    public Long getLocalArmazenamentoId() {
        return localArmazenamentoId;
    }

    public void setLocalArmazenamentoId(Long localArmazenamentoId) {
        this.localArmazenamentoId = localArmazenamentoId;
    }

    public String getLocalArmazenamentoNome() {
        return localArmazenamentoNome;
    }

    public void setLocalArmazenamentoNome(String localArmazenamentoNome) {
        this.localArmazenamentoNome = localArmazenamentoNome;
    }

    public Long getFamiliaId() {
        return familiaId;
    }

    public void setFamiliaId(Long familiaProdutoId) {
        this.familiaId = familiaProdutoId;
    }

    public String getFamiliaNome() {
        return familiaNome;
    }

    public void setFamiliaNome(String familiaProdutoNome) {
        this.familiaNome = familiaProdutoNome;
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

    public Long getEstadoId() {
        return estadoId;
    }

    public void setEstadoId(Long fluxoDocumentoId) {
        this.estadoId = fluxoDocumentoId;
    }

    public String getEstadoNome() {
        return estadoNome;
    }

    public void setEstadoNome(String fluxoDocumentoNome) {
        this.estadoNome = fluxoDocumentoNome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProdutoDTO produtoDTO = (ProdutoDTO) o;
        if (produtoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), produtoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProdutoDTO{" +
            "id=" + getId() +
            ", tipo='" + getTipo() + "'" +
            ", ean='" + getEan() + "'" +
            ", numero='" + getNumero() + "'" +
            ", nome='" + getNome() + "'" +
            ", imagem='" + getImagem() + "'" +
            ", composto='" + isComposto() + "'" +
            ", estoqueMinimo=" + getEstoqueMinimo() +
            ", estoqueMaximo=" + getEstoqueMaximo() +
            ", estoqueAtual=" + getEstoqueAtual() +
            ", descricao='" + getDescricao() + "'" +
            ", mostrarPDV='" + isMostrarPDV() + "'" +
            ", prazoMedioEntrega='" + getPrazoMedioEntrega() + "'" +
            ", utilizador=" + getUtilizadorId() +
            ", utilizador='" + getUtilizadorLogin() + "'" +
            ", localArmazenamento=" + getLocalArmazenamentoId() +
            ", localArmazenamento='" + getLocalArmazenamentoNome() + "'" +
            ", familia=" + getFamiliaId() +
            ", familia='" + getFamiliaNome() + "'" +
            ", empresa=" + getEmpresaId() +
            ", empresa='" + getEmpresaNome() + "'" +
            ", estado=" + getEstadoId() +
            ", estado='" + getEstadoNome() + "'" +
            "}";
    }
}
