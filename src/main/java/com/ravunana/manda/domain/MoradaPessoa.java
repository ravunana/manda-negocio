package com.ravunana.manda.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A MoradaPessoa.
 */
@Entity
@Table(name = "core_morada_pessoa")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MoradaPessoa implements Serializable {

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

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("moradaPessoas")
    private Pessoa pessoa;

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

    public MoradaPessoa pais(String pais) {
        this.pais = pais;
        return this;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getProvincia() {
        return provincia;
    }

    public MoradaPessoa provincia(String provincia) {
        this.provincia = provincia;
        return this;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getMunicipio() {
        return municipio;
    }

    public MoradaPessoa municipio(String municipio) {
        this.municipio = municipio;
        return this;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getBairro() {
        return bairro;
    }

    public MoradaPessoa bairro(String bairro) {
        this.bairro = bairro;
        return this;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getRua() {
        return rua;
    }

    public MoradaPessoa rua(String rua) {
        this.rua = rua;
        return this;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getQuarteirao() {
        return quarteirao;
    }

    public MoradaPessoa quarteirao(String quarteirao) {
        this.quarteirao = quarteirao;
        return this;
    }

    public void setQuarteirao(String quarteirao) {
        this.quarteirao = quarteirao;
    }

    public String getNumeroPorta() {
        return numeroPorta;
    }

    public MoradaPessoa numeroPorta(String numeroPorta) {
        this.numeroPorta = numeroPorta;
        return this;
    }

    public void setNumeroPorta(String numeroPorta) {
        this.numeroPorta = numeroPorta;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public MoradaPessoa pessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
        return this;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MoradaPessoa)) {
            return false;
        }
        return id != null && id.equals(((MoradaPessoa) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MoradaPessoa{" +
            "id=" + getId() +
            ", pais='" + getPais() + "'" +
            ", provincia='" + getProvincia() + "'" +
            ", municipio='" + getMunicipio() + "'" +
            ", bairro='" + getBairro() + "'" +
            ", rua='" + getRua() + "'" +
            ", quarteirao='" + getQuarteirao() + "'" +
            ", numeroPorta='" + getNumeroPorta() + "'" +
            "}";
    }
}
