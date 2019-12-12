package com.ravunana.manda.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;
import com.ravunana.manda.domain.enumeration.EntidadeSistema;

/**
 * A DTO for the {@link com.ravunana.manda.domain.DocumentoComercial} entity.
 */
public class DocumentoComercialDTO implements Serializable {

    private Long id;

    @NotNull
    private String nome;

    @NotNull
    private String serie;

    @NotNull
    private Boolean padrao;

    @NotNull
    private Boolean movimentaEstoque;

    @NotNull
    private Boolean movimentaCaixa;

    @NotNull
    private Boolean movimentaContabilidade;

    private String cor;

    @Lob
    private String descricao;

    private Boolean mostraPontoVenda;

    private EntidadeSistema entidade;


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

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public Boolean isPadrao() {
        return padrao;
    }

    public void setPadrao(Boolean padrao) {
        this.padrao = padrao;
    }

    public Boolean isMovimentaEstoque() {
        return movimentaEstoque;
    }

    public void setMovimentaEstoque(Boolean movimentaEstoque) {
        this.movimentaEstoque = movimentaEstoque;
    }

    public Boolean isMovimentaCaixa() {
        return movimentaCaixa;
    }

    public void setMovimentaCaixa(Boolean movimentaCaixa) {
        this.movimentaCaixa = movimentaCaixa;
    }

    public Boolean isMovimentaContabilidade() {
        return movimentaContabilidade;
    }

    public void setMovimentaContabilidade(Boolean movimentaContabilidade) {
        this.movimentaContabilidade = movimentaContabilidade;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Boolean isMostraPontoVenda() {
        return mostraPontoVenda;
    }

    public void setMostraPontoVenda(Boolean mostraPontoVenda) {
        this.mostraPontoVenda = mostraPontoVenda;
    }

    public EntidadeSistema getEntidade() {
        return entidade;
    }

    public void setEntidade(EntidadeSistema entidade) {
        this.entidade = entidade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DocumentoComercialDTO documentoComercialDTO = (DocumentoComercialDTO) o;
        if (documentoComercialDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), documentoComercialDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DocumentoComercialDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", serie='" + getSerie() + "'" +
            ", padrao='" + isPadrao() + "'" +
            ", movimentaEstoque='" + isMovimentaEstoque() + "'" +
            ", movimentaCaixa='" + isMovimentaCaixa() + "'" +
            ", movimentaContabilidade='" + isMovimentaContabilidade() + "'" +
            ", cor='" + getCor() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", mostraPontoVenda='" + isMostraPontoVenda() + "'" +
            ", entidade='" + getEntidade() + "'" +
            "}";
    }
}
