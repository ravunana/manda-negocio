package com.ravunana.manda.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A ContactoEmpresa.
 */
@Entity
@Table(name = "core_contacto_empresa")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ContactoEmpresa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "tipo_contacto", nullable = false)
    private String tipoContacto;

    @Column(name = "descricao")
    private String descricao;

    @NotNull
    @Column(name = "contacto", nullable = false, unique = true)
    private String contacto;

    @Column(name = "padrao")
    private Boolean padrao;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("contactoEmpresas")
    private Empresa empresa;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipoContacto() {
        return tipoContacto;
    }

    public ContactoEmpresa tipoContacto(String tipoContacto) {
        this.tipoContacto = tipoContacto;
        return this;
    }

    public void setTipoContacto(String tipoContacto) {
        this.tipoContacto = tipoContacto;
    }

    public String getDescricao() {
        return descricao;
    }

    public ContactoEmpresa descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getContacto() {
        return contacto;
    }

    public ContactoEmpresa contacto(String contacto) {
        this.contacto = contacto;
        return this;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public Boolean isPadrao() {
        return padrao;
    }

    public ContactoEmpresa padrao(Boolean padrao) {
        this.padrao = padrao;
        return this;
    }

    public void setPadrao(Boolean padrao) {
        this.padrao = padrao;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public ContactoEmpresa empresa(Empresa empresa) {
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
        if (!(o instanceof ContactoEmpresa)) {
            return false;
        }
        return id != null && id.equals(((ContactoEmpresa) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ContactoEmpresa{" +
            "id=" + getId() +
            ", tipoContacto='" + getTipoContacto() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", contacto='" + getContacto() + "'" +
            ", padrao='" + isPadrao() + "'" +
            "}";
    }
}
