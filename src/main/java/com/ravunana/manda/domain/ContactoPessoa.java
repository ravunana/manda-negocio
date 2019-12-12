package com.ravunana.manda.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A ContactoPessoa.
 */
@Entity
@Table(name = "core_contacto_pessoa")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ContactoPessoa implements Serializable {

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

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("contactoPessoas")
    private Pessoa pessoa;

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

    public ContactoPessoa tipoContacto(String tipoContacto) {
        this.tipoContacto = tipoContacto;
        return this;
    }

    public void setTipoContacto(String tipoContacto) {
        this.tipoContacto = tipoContacto;
    }

    public String getDescricao() {
        return descricao;
    }

    public ContactoPessoa descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getContacto() {
        return contacto;
    }

    public ContactoPessoa contacto(String contacto) {
        this.contacto = contacto;
        return this;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public ContactoPessoa pessoa(Pessoa pessoa) {
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
        if (!(o instanceof ContactoPessoa)) {
            return false;
        }
        return id != null && id.equals(((ContactoPessoa) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ContactoPessoa{" +
            "id=" + getId() +
            ", tipoContacto='" + getTipoContacto() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", contacto='" + getContacto() + "'" +
            "}";
    }
}
