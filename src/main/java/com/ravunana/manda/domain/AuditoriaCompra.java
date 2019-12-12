package com.ravunana.manda.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A AuditoriaCompra.
 */
@Entity
@Table(name = "cmp_auditoria_compra")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AuditoriaCompra implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "estado")
    private String estado;

    @Column(name = "data")
    private ZonedDateTime data;

    @Column(name = "motivo_alteracao_documento")
    private String motivoAlteracaoDocumento;

    @Column(name = "origem_documento")
    private String origemDocumento;

    @Column(name = "hash")
    private String hash;

    @Column(name = "hash_control")
    private String hashControl;

    @OneToOne
    @JoinColumn(unique = true)
    private Compra compra;

    @ManyToOne
    @JsonIgnoreProperties("auditoriaCompras")
    private User utilizador;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEstado() {
        return estado;
    }

    public AuditoriaCompra estado(String estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public ZonedDateTime getData() {
        return data;
    }

    public AuditoriaCompra data(ZonedDateTime data) {
        this.data = data;
        return this;
    }

    public void setData(ZonedDateTime data) {
        this.data = data;
    }

    public String getMotivoAlteracaoDocumento() {
        return motivoAlteracaoDocumento;
    }

    public AuditoriaCompra motivoAlteracaoDocumento(String motivoAlteracaoDocumento) {
        this.motivoAlteracaoDocumento = motivoAlteracaoDocumento;
        return this;
    }

    public void setMotivoAlteracaoDocumento(String motivoAlteracaoDocumento) {
        this.motivoAlteracaoDocumento = motivoAlteracaoDocumento;
    }

    public String getOrigemDocumento() {
        return origemDocumento;
    }

    public AuditoriaCompra origemDocumento(String origemDocumento) {
        this.origemDocumento = origemDocumento;
        return this;
    }

    public void setOrigemDocumento(String origemDocumento) {
        this.origemDocumento = origemDocumento;
    }

    public String getHash() {
        return hash;
    }

    public AuditoriaCompra hash(String hash) {
        this.hash = hash;
        return this;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getHashControl() {
        return hashControl;
    }

    public AuditoriaCompra hashControl(String hashControl) {
        this.hashControl = hashControl;
        return this;
    }

    public void setHashControl(String hashControl) {
        this.hashControl = hashControl;
    }

    public Compra getCompra() {
        return compra;
    }

    public AuditoriaCompra compra(Compra compra) {
        this.compra = compra;
        return this;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
    }

    public User getUtilizador() {
        return utilizador;
    }

    public AuditoriaCompra utilizador(User user) {
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
        if (!(o instanceof AuditoriaCompra)) {
            return false;
        }
        return id != null && id.equals(((AuditoriaCompra) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "AuditoriaCompra{" +
            "id=" + getId() +
            ", estado='" + getEstado() + "'" +
            ", data='" + getData() + "'" +
            ", motivoAlteracaoDocumento='" + getMotivoAlteracaoDocumento() + "'" +
            ", origemDocumento='" + getOrigemDocumento() + "'" +
            ", hash='" + getHash() + "'" +
            ", hashControl='" + getHashControl() + "'" +
            "}";
    }
}
