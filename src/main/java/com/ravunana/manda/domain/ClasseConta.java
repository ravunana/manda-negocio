package com.ravunana.manda.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A ClasseConta.
 */
@Entity
@Table(name = "ctb_classe")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ClasseConta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "descricao", nullable = false, unique = true)
    private String descricao;

    @NotNull
    @Column(name = "codigo", nullable = false, unique = true)
    private String codigo;

    @OneToMany(mappedBy = "classeConta")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Conta> contas = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public ClasseConta descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCodigo() {
        return codigo;
    }

    public ClasseConta codigo(String codigo) {
        this.codigo = codigo;
        return this;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Set<Conta> getContas() {
        return contas;
    }

    public ClasseConta contas(Set<Conta> contas) {
        this.contas = contas;
        return this;
    }

    public ClasseConta addConta(Conta conta) {
        this.contas.add(conta);
        conta.setClasseConta(this);
        return this;
    }

    public ClasseConta removeConta(Conta conta) {
        this.contas.remove(conta);
        conta.setClasseConta(null);
        return this;
    }

    public void setContas(Set<Conta> contas) {
        this.contas = contas;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ClasseConta)) {
            return false;
        }
        return id != null && id.equals(((ClasseConta) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ClasseConta{" +
            "id=" + getId() +
            ", descricao='" + getDescricao() + "'" +
            ", codigo='" + getCodigo() + "'" +
            "}";
    }
}
