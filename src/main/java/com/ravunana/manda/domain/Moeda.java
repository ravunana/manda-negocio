package com.ravunana.manda.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Moeda.
 */
@Entity
@Table(name = "fnc_moeda")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Moeda implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "nome", nullable = false, unique = true)
    private String nome;

    @NotNull
    @Size(min = 2, max = 10)
    @Column(name = "codigo", length = 10, nullable = false, unique = true)
    private String codigo;

    @Column(name = "pais")
    private String pais;

    @Column(name = "nacional")
    private Boolean nacional;

    @Column(name = "icon")
    private String icon;

    @OneToMany(mappedBy = "moeda")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DetalheLancamento> detalheLancamentos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public Moeda nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCodigo() {
        return codigo;
    }

    public Moeda codigo(String codigo) {
        this.codigo = codigo;
        return this;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getPais() {
        return pais;
    }

    public Moeda pais(String pais) {
        this.pais = pais;
        return this;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public Boolean isNacional() {
        return nacional;
    }

    public Moeda nacional(Boolean nacional) {
        this.nacional = nacional;
        return this;
    }

    public void setNacional(Boolean nacional) {
        this.nacional = nacional;
    }

    public String getIcon() {
        return icon;
    }

    public Moeda icon(String icon) {
        this.icon = icon;
        return this;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Set<DetalheLancamento> getDetalheLancamentos() {
        return detalheLancamentos;
    }

    public Moeda detalheLancamentos(Set<DetalheLancamento> detalheLancamentos) {
        this.detalheLancamentos = detalheLancamentos;
        return this;
    }

    public Moeda addDetalheLancamento(DetalheLancamento detalheLancamento) {
        this.detalheLancamentos.add(detalheLancamento);
        detalheLancamento.setMoeda(this);
        return this;
    }

    public Moeda removeDetalheLancamento(DetalheLancamento detalheLancamento) {
        this.detalheLancamentos.remove(detalheLancamento);
        detalheLancamento.setMoeda(null);
        return this;
    }

    public void setDetalheLancamentos(Set<DetalheLancamento> detalheLancamentos) {
        this.detalheLancamentos = detalheLancamentos;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Moeda)) {
            return false;
        }
        return id != null && id.equals(((Moeda) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Moeda{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", codigo='" + getCodigo() + "'" +
            ", pais='" + getPais() + "'" +
            ", nacional='" + isNacional() + "'" +
            ", icon='" + getIcon() + "'" +
            "}";
    }
}
