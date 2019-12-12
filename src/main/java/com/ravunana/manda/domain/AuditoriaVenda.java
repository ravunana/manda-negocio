package com.ravunana.manda.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A AuditoriaVenda.
 */
@Entity
@Table(name = "vnd_auditoria_venda")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AuditoriaVenda implements Serializable {

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

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private Venda venda;

    @ManyToOne
    @JsonIgnoreProperties("auditoriaVendas")
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

    public AuditoriaVenda estado(String estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public ZonedDateTime getData() {
        return data;
    }

    public AuditoriaVenda data(ZonedDateTime data) {
        this.data = data;
        return this;
    }

    public void setData(ZonedDateTime data) {
        this.data = data;
    }

    public String getMotivoAlteracaoDocumento() {
        return motivoAlteracaoDocumento;
    }

    public AuditoriaVenda motivoAlteracaoDocumento(String motivoAlteracaoDocumento) {
        this.motivoAlteracaoDocumento = motivoAlteracaoDocumento;
        return this;
    }

    public void setMotivoAlteracaoDocumento(String motivoAlteracaoDocumento) {
        this.motivoAlteracaoDocumento = motivoAlteracaoDocumento;
    }

    public String getOrigemDocumento() {
        return origemDocumento;
    }

    public AuditoriaVenda origemDocumento(String origemDocumento) {
        this.origemDocumento = origemDocumento;
        return this;
    }

    public void setOrigemDocumento(String origemDocumento) {
        this.origemDocumento = origemDocumento;
    }

    public String getHash() {
        return hash;
    }

    public AuditoriaVenda hash(String hash) {
        this.hash = hash;
        return this;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getHashControl() {
        return hashControl;
    }

    public AuditoriaVenda hashControl(String hashControl) {
        this.hashControl = hashControl;
        return this;
    }

    public void setHashControl(String hashControl) {
        this.hashControl = hashControl;
    }

    public Venda getVenda() {
        return venda;
    }

    public AuditoriaVenda venda(Venda venda) {
        this.venda = venda;
        return this;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }

    public User getUtilizador() {
        return utilizador;
    }

    public AuditoriaVenda utilizador(User user) {
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
        if (!(o instanceof AuditoriaVenda)) {
            return false;
        }
        return id != null && id.equals(((AuditoriaVenda) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "AuditoriaVenda{" +
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
