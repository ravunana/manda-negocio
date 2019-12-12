package com.ravunana.manda.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A MeioLiquidacao.
 */
@Entity
@Table(name = "fnc_meio_liquidacao")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MeioLiquidacao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    
    @Column(name = "nome", unique = true)
    private String nome;

    
    @Column(name = "sigla", unique = true)
    private String sigla;

    @Column(name = "icon")
    private String icon;

    @Column(name = "mostrar_ponto_venda")
    private Boolean mostrarPontoVenda;

    @OneToMany(mappedBy = "metodoLiquidacao")
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

    public MeioLiquidacao nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSigla() {
        return sigla;
    }

    public MeioLiquidacao sigla(String sigla) {
        this.sigla = sigla;
        return this;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getIcon() {
        return icon;
    }

    public MeioLiquidacao icon(String icon) {
        this.icon = icon;
        return this;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Boolean isMostrarPontoVenda() {
        return mostrarPontoVenda;
    }

    public MeioLiquidacao mostrarPontoVenda(Boolean mostrarPontoVenda) {
        this.mostrarPontoVenda = mostrarPontoVenda;
        return this;
    }

    public void setMostrarPontoVenda(Boolean mostrarPontoVenda) {
        this.mostrarPontoVenda = mostrarPontoVenda;
    }

    public Set<DetalheLancamento> getDetalheLancamentos() {
        return detalheLancamentos;
    }

    public MeioLiquidacao detalheLancamentos(Set<DetalheLancamento> detalheLancamentos) {
        this.detalheLancamentos = detalheLancamentos;
        return this;
    }

    public MeioLiquidacao addDetalheLancamento(DetalheLancamento detalheLancamento) {
        this.detalheLancamentos.add(detalheLancamento);
        detalheLancamento.setMetodoLiquidacao(this);
        return this;
    }

    public MeioLiquidacao removeDetalheLancamento(DetalheLancamento detalheLancamento) {
        this.detalheLancamentos.remove(detalheLancamento);
        detalheLancamento.setMetodoLiquidacao(null);
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
        if (!(o instanceof MeioLiquidacao)) {
            return false;
        }
        return id != null && id.equals(((MeioLiquidacao) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MeioLiquidacao{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", sigla='" + getSigla() + "'" +
            ", icon='" + getIcon() + "'" +
            ", mostrarPontoVenda='" + isMostrarPontoVenda() + "'" +
            "}";
    }
}
