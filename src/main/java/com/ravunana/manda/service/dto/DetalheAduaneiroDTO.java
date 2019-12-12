package com.ravunana.manda.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.ravunana.manda.domain.DetalheAduaneiro} entity.
 */
public class DetalheAduaneiroDTO implements Serializable {

    private Long id;

    
    private String numeroONU;

    private Double largura;

    private Double altura;

    private Double pesoLiquido;

    private Double pesoBruto;

    private LocalDate dataFabrico;

    private LocalDate dataExpiracao;


    private Long produtoId;

    private String produtoNome;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroONU() {
        return numeroONU;
    }

    public void setNumeroONU(String numeroONU) {
        this.numeroONU = numeroONU;
    }

    public Double getLargura() {
        return largura;
    }

    public void setLargura(Double largura) {
        this.largura = largura;
    }

    public Double getAltura() {
        return altura;
    }

    public void setAltura(Double altura) {
        this.altura = altura;
    }

    public Double getPesoLiquido() {
        return pesoLiquido;
    }

    public void setPesoLiquido(Double pesoLiquido) {
        this.pesoLiquido = pesoLiquido;
    }

    public Double getPesoBruto() {
        return pesoBruto;
    }

    public void setPesoBruto(Double pesoBruto) {
        this.pesoBruto = pesoBruto;
    }

    public LocalDate getDataFabrico() {
        return dataFabrico;
    }

    public void setDataFabrico(LocalDate dataFabrico) {
        this.dataFabrico = dataFabrico;
    }

    public LocalDate getDataExpiracao() {
        return dataExpiracao;
    }

    public void setDataExpiracao(LocalDate dataExpiracao) {
        this.dataExpiracao = dataExpiracao;
    }

    public Long getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Long produtoId) {
        this.produtoId = produtoId;
    }

    public String getProdutoNome() {
        return produtoNome;
    }

    public void setProdutoNome(String produtoNome) {
        this.produtoNome = produtoNome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DetalheAduaneiroDTO detalheAduaneiroDTO = (DetalheAduaneiroDTO) o;
        if (detalheAduaneiroDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), detalheAduaneiroDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DetalheAduaneiroDTO{" +
            "id=" + getId() +
            ", numeroONU='" + getNumeroONU() + "'" +
            ", largura=" + getLargura() +
            ", altura=" + getAltura() +
            ", pesoLiquido=" + getPesoLiquido() +
            ", pesoBruto=" + getPesoBruto() +
            ", dataFabrico='" + getDataFabrico() + "'" +
            ", dataExpiracao='" + getDataExpiracao() + "'" +
            ", produto=" + getProdutoId() +
            ", produto='" + getProdutoNome() + "'" +
            "}";
    }
}
