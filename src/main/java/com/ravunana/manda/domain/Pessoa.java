package com.ravunana.manda.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Pessoa.
 */
@Entity
@Table(name = "core_pessoa")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Pessoa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "tipo_pessoa", nullable = false)
    private String tipoPessoa;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    
    @Column(name = "nif", unique = true)
    private String nif;

    @Lob
    @Column(name = "imagem")
    private byte[] imagem;

    @Column(name = "imagem_content_type")
    private String imagemContentType;

    @OneToMany(mappedBy = "pessoa")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<MoradaPessoa> moradaPessoas = new HashSet<>();

    @OneToMany(mappedBy = "pessoa")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ContactoPessoa> contactoPessoas = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("pessoas")
    private User utilizador;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipoPessoa() {
        return tipoPessoa;
    }

    public Pessoa tipoPessoa(String tipoPessoa) {
        this.tipoPessoa = tipoPessoa;
        return this;
    }

    public void setTipoPessoa(String tipoPessoa) {
        this.tipoPessoa = tipoPessoa;
    }

    public String getNome() {
        return nome;
    }

    public Pessoa nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNif() {
        return nif;
    }

    public Pessoa nif(String nif) {
        this.nif = nif;
        return this;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public byte[] getImagem() {
        return imagem;
    }

    public Pessoa imagem(byte[] imagem) {
        this.imagem = imagem;
        return this;
    }

    public void setImagem(byte[] imagem) {
        this.imagem = imagem;
    }

    public String getImagemContentType() {
        return imagemContentType;
    }

    public Pessoa imagemContentType(String imagemContentType) {
        this.imagemContentType = imagemContentType;
        return this;
    }

    public void setImagemContentType(String imagemContentType) {
        this.imagemContentType = imagemContentType;
    }

    public Set<MoradaPessoa> getMoradaPessoas() {
        return moradaPessoas;
    }

    public Pessoa moradaPessoas(Set<MoradaPessoa> moradaPessoas) {
        this.moradaPessoas = moradaPessoas;
        return this;
    }

    public Pessoa addMoradaPessoa(MoradaPessoa moradaPessoa) {
        this.moradaPessoas.add(moradaPessoa);
        moradaPessoa.setPessoa(this);
        return this;
    }

    public Pessoa removeMoradaPessoa(MoradaPessoa moradaPessoa) {
        this.moradaPessoas.remove(moradaPessoa);
        moradaPessoa.setPessoa(null);
        return this;
    }

    public void setMoradaPessoas(Set<MoradaPessoa> moradaPessoas) {
        this.moradaPessoas = moradaPessoas;
    }

    public Set<ContactoPessoa> getContactoPessoas() {
        return contactoPessoas;
    }

    public Pessoa contactoPessoas(Set<ContactoPessoa> contactoPessoas) {
        this.contactoPessoas = contactoPessoas;
        return this;
    }

    public Pessoa addContactoPessoa(ContactoPessoa contactoPessoa) {
        this.contactoPessoas.add(contactoPessoa);
        contactoPessoa.setPessoa(this);
        return this;
    }

    public Pessoa removeContactoPessoa(ContactoPessoa contactoPessoa) {
        this.contactoPessoas.remove(contactoPessoa);
        contactoPessoa.setPessoa(null);
        return this;
    }

    public void setContactoPessoas(Set<ContactoPessoa> contactoPessoas) {
        this.contactoPessoas = contactoPessoas;
    }

    public User getUtilizador() {
        return utilizador;
    }

    public Pessoa utilizador(User user) {
        this.utilizador = user;
        return this;
    }

    public void setUtilizador(User user) {
        this.utilizador = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Pessoa)) {
            return false;
        }
        return id != null && id.equals(((Pessoa) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Pessoa{" +
            "id=" + getId() +
            ", tipoPessoa='" + getTipoPessoa() + "'" +
            ", nome='" + getNome() + "'" +
            ", nif='" + getNif() + "'" +
            ", imagem='" + getImagem() + "'" +
            ", imagemContentType='" + getImagemContentType() + "'" +
            "}";
    }
}
