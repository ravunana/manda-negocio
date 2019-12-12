package com.ravunana.manda.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A LocalizacaoEmpresa.
 */
@Entity
@Table(name = "core_localizacao_empresa")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class LocalizacaoEmpresa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "pais")
    private String pais;

    @Column(name = "provincia")
    private String provincia;

    @Column(name = "municipio")
    private String municipio;

    @NotNull
    @Column(name = "bairro", nullable = false)
    private String bairro;

    @NotNull
    @Size(max = 200)
    @Column(name = "rua", length = 200, nullable = false)
    private String rua;

    @Size(max = 10)
    @Column(name = "quarteirao", length = 10)
    private String quarteirao;

    @Size(max = 10)
    @Column(name = "numero_porta", length = 10)
    private String numeroPorta;

    @Column(name = "caixa_postal")
    private String caixaPostal;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("localizacaoEmpresas")
    private Empresa empresa;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPais() {
        return pais;
    }

    public LocalizacaoEmpresa pais(String pais) {
        this.pais = pais;
        return this;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getProvincia() {
        return provincia;
    }

    public LocalizacaoEmpresa provincia(String provincia) {
        this.provincia = provincia;
        return this;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getMunicipio() {
        return municipio;
    }

    public LocalizacaoEmpresa municipio(String municipio) {
        this.municipio = municipio;
        return this;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getBairro() {
        return bairro;
    }

    public LocalizacaoEmpresa bairro(String bairro) {
        this.bairro = bairro;
        return this;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getRua() {
        return rua;
    }

    public LocalizacaoEmpresa rua(String rua) {
        this.rua = rua;
        return this;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getQuarteirao() {
        return quarteirao;
    }

    public LocalizacaoEmpresa quarteirao(String quarteirao) {
        this.quarteirao = quarteirao;
        return this;
    }

    public void setQuarteirao(String quarteirao) {
        this.quarteirao = quarteirao;
    }

    public String getNumeroPorta() {
        return numeroPorta;
    }

    public LocalizacaoEmpresa numeroPorta(String numeroPorta) {
        this.numeroPorta = numeroPorta;
        return this;
    }

    public void setNumeroPorta(String numeroPorta) {
        this.numeroPorta = numeroPorta;
    }

    public String getCaixaPostal() {
        return caixaPostal;
    }

    public LocalizacaoEmpresa caixaPostal(String caixaPostal) {
        this.caixaPostal = caixaPostal;
        return this;
    }

    public void setCaixaPostal(String caixaPostal) {
        this.caixaPostal = caixaPostal;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public LocalizacaoEmpresa empresa(Empresa empresa) {
        this.empresa = empresa;
        return this;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LocalizacaoEmpresa)) {
            return false;
        }
        return id != null && id.equals(((LocalizacaoEmpresa) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "LocalizacaoEmpresa{" +
            "id=" + getId() +
            ", pais='" + getPais() + "'" +
            ", provincia='" + getProvincia() + "'" +
            ", municipio='" + getMunicipio() + "'" +
            ", bairro='" + getBairro() + "'" +
            ", rua='" + getRua() + "'" +
            ", quarteirao='" + getQuarteirao() + "'" +
            ", numeroPorta='" + getNumeroPorta() + "'" +
            ", caixaPostal='" + getCaixaPostal() + "'" +
            "}";
    }
}
