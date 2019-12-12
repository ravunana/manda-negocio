package com.ravunana.manda.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.ravunana.manda.domain.enumeration.EntidadeSistema;

/**
 * A Lookup.
 */
@Entity
@Table(name = "core_lookup")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Lookup implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "nome", nullable = false, unique = true)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(name = "entidade")
    private EntidadeSistema entidade;

    @Column(name = "modificavel")
    private Boolean modificavel;

    @OneToMany(mappedBy = "lookup")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<LookupItem> lookupItems = new HashSet<>();

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

    public Lookup nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public EntidadeSistema getEntidade() {
        return entidade;
    }

    public Lookup entidade(EntidadeSistema entidade) {
        this.entidade = entidade;
        return this;
    }

    public void setEntidade(EntidadeSistema entidade) {
        this.entidade = entidade;
    }

    public Boolean isModificavel() {
        return modificavel;
    }

    public Lookup modificavel(Boolean modificavel) {
        this.modificavel = modificavel;
        return this;
    }

    public void setModificavel(Boolean modificavel) {
        this.modificavel = modificavel;
    }

    public Set<LookupItem> getLookupItems() {
        return lookupItems;
    }

    public Lookup lookupItems(Set<LookupItem> lookupItems) {
        this.lookupItems = lookupItems;
        return this;
    }

    public Lookup addLookupItem(LookupItem lookupItem) {
        this.lookupItems.add(lookupItem);
        lookupItem.setLookup(this);
        return this;
    }

    public Lookup removeLookupItem(LookupItem lookupItem) {
        this.lookupItems.remove(lookupItem);
        lookupItem.setLookup(null);
        return this;
    }

    public void setLookupItems(Set<LookupItem> lookupItems) {
        this.lookupItems = lookupItems;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Lookup)) {
            return false;
        }
        return id != null && id.equals(((Lookup) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Lookup{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", entidade='" + getEntidade() + "'" +
            ", modificavel='" + isModificavel() + "'" +
            "}";
    }
}
