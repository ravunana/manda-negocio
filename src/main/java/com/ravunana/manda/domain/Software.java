package com.ravunana.manda.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Software.
 */
@Entity
@Table(name = "rv_software")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Software implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "empresa", nullable = false)
    private String empresa;

    @NotNull
    @Column(name = "tipo_sistema", nullable = false)
    private String tipoSistema;

    @NotNull
    @Column(name = "nif", nullable = false)
    private String nif;

    @NotNull
    @Column(name = "numero_validacao_agt", nullable = false)
    private Integer numeroValidacaoAGT;

    @NotNull
    @Size(max = 255)
    @Column(name = "nome", length = 255, nullable = false)
    private String nome;

    @NotNull
    @Size(max = 30)
    @Column(name = "versao", length = 30, nullable = false)
    private String versao;

    @NotNull
    @Column(name = "hash_code", nullable = false)
    private String hashCode;

    @NotNull
    @Column(name = "hash_control", nullable = false)
    private String hashControl;

    @OneToMany(mappedBy = "software")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<LicencaSoftware> licencaSoftwares = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmpresa() {
        return empresa;
    }

    public Software empresa(String empresa) {
        this.empresa = empresa;
        return this;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getTipoSistema() {
        return tipoSistema;
    }

    public Software tipoSistema(String tipoSistema) {
        this.tipoSistema = tipoSistema;
        return this;
    }

    public void setTipoSistema(String tipoSistema) {
        this.tipoSistema = tipoSistema;
    }

    public String getNif() {
        return nif;
    }

    public Software nif(String nif) {
        this.nif = nif;
        return this;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public Integer getNumeroValidacaoAGT() {
        return numeroValidacaoAGT;
    }

    public Software numeroValidacaoAGT(Integer numeroValidacaoAGT) {
        this.numeroValidacaoAGT = numeroValidacaoAGT;
        return this;
    }

    public void setNumeroValidacaoAGT(Integer numeroValidacaoAGT) {
        this.numeroValidacaoAGT = numeroValidacaoAGT;
    }

    public String getNome() {
        return nome;
    }

    public Software nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getVersao() {
        return versao;
    }

    public Software versao(String versao) {
        this.versao = versao;
        return this;
    }

    public void setVersao(String versao) {
        this.versao = versao;
    }

    public String getHashCode() {
        return hashCode;
    }

    public Software hashCode(String hashCode) {
        this.hashCode = hashCode;
        return this;
    }

    public void setHashCode(String hashCode) {
        this.hashCode = hashCode;
    }

    public String getHashControl() {
        return hashControl;
    }

    public Software hashControl(String hashControl) {
        this.hashControl = hashControl;
        return this;
    }

    public void setHashControl(String hashControl) {
        this.hashControl = hashControl;
    }

    public Set<LicencaSoftware> getLicencaSoftwares() {
        return licencaSoftwares;
    }

    public Software licencaSoftwares(Set<LicencaSoftware> licencaSoftwares) {
        this.licencaSoftwares = licencaSoftwares;
        return this;
    }

    public Software addLicencaSoftware(LicencaSoftware licencaSoftware) {
        this.licencaSoftwares.add(licencaSoftware);
        licencaSoftware.setSoftware(this);
        return this;
    }

    public Software removeLicencaSoftware(LicencaSoftware licencaSoftware) {
        this.licencaSoftwares.remove(licencaSoftware);
        licencaSoftware.setSoftware(null);
        return this;
    }

    public void setLicencaSoftwares(Set<LicencaSoftware> licencaSoftwares) {
        this.licencaSoftwares = licencaSoftwares;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Software)) {
            return false;
        }
        return id != null && id.equals(((Software) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Software{" +
            "id=" + getId() +
            ", empresa='" + getEmpresa() + "'" +
            ", tipoSistema='" + getTipoSistema() + "'" +
            ", nif='" + getNif() + "'" +
            ", numeroValidacaoAGT=" + getNumeroValidacaoAGT() +
            ", nome='" + getNome() + "'" +
            ", versao='" + getVersao() + "'" +
            ", hashCode='" + getHashCode() + "'" +
            ", hashControl='" + getHashControl() + "'" +
            "}";
    }
}
