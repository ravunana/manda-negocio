package com.ravunana.manda.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A RelacionamentoPessoa.
 */
@Entity
@Table(name = "core_relacionamento_pessoa")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RelacionamentoPessoa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "grau_parentesco", nullable = false)
    private String grauParentesco;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("relacionamentoPessoas")
    private Pessoa de;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("relacionamentoPessoas")
    private Pessoa para;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGrauParentesco() {
        return grauParentesco;
    }

    public RelacionamentoPessoa grauParentesco(String grauParentesco) {
        this.grauParentesco = grauParentesco;
        return this;
    }

    public void setGrauParentesco(String grauParentesco) {
        this.grauParentesco = grauParentesco;
    }

    public Pessoa getDe() {
        return de;
    }

    public RelacionamentoPessoa de(Pessoa pessoa) {
        this.de = pessoa;
        return this;
    }

    public void setDe(Pessoa pessoa) {
        this.de = pessoa;
    }

    public Pessoa getPara() {
        return para;
    }

    public RelacionamentoPessoa para(Pessoa pessoa) {
        this.para = pessoa;
        return this;
    }

    public void setPara(Pessoa pessoa) {
        this.para = pessoa;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RelacionamentoPessoa)) {
            return false;
        }
        return id != null && id.equals(((RelacionamentoPessoa) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "RelacionamentoPessoa{" +
            "id=" + getId() +
            ", grauParentesco='" + getGrauParentesco() + "'" +
            "}";
    }
}
