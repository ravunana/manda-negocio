package com.ravunana.manda.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

/**
 * A LicencaSoftware.
 */
@Entity
@Table(name = "rv_licensa")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class LicencaSoftware implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "tipo_subscricao", nullable = false)
    private String tipoSubscricao;

    @NotNull
    @Column(name = "inicio", nullable = false)
    private ZonedDateTime inicio;

    @NotNull
    @Column(name = "fim", nullable = false)
    private ZonedDateTime fim;

    @Column(name = "data")
    private ZonedDateTime data;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "valor", precision = 21, scale = 2, nullable = false)
    private BigDecimal valor;

    @NotNull
    @Column(name = "codigo", nullable = false, unique = true)
    private String codigo;

    @Min(value = 1)
    @Column(name = "numero_usuario")
    private Integer numeroUsuario;

    @Min(value = 1)
    @Column(name = "numero_empresa")
    private Integer numeroEmpresa;

    @ManyToOne
    @JsonIgnoreProperties("licencaSoftwares")
    private Software software;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("licencaSoftwares")
    private Empresa empresa;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipoSubscricao() {
        return tipoSubscricao;
    }

    public LicencaSoftware tipoSubscricao(String tipoSubscricao) {
        this.tipoSubscricao = tipoSubscricao;
        return this;
    }

    public void setTipoSubscricao(String tipoSubscricao) {
        this.tipoSubscricao = tipoSubscricao;
    }

    public ZonedDateTime getInicio() {
        return inicio;
    }

    public LicencaSoftware inicio(ZonedDateTime inicio) {
        this.inicio = inicio;
        return this;
    }

    public void setInicio(ZonedDateTime inicio) {
        this.inicio = inicio;
    }

    public ZonedDateTime getFim() {
        return fim;
    }

    public LicencaSoftware fim(ZonedDateTime fim) {
        this.fim = fim;
        return this;
    }

    public void setFim(ZonedDateTime fim) {
        this.fim = fim;
    }

    public ZonedDateTime getData() {
        return data;
    }

    public LicencaSoftware data(ZonedDateTime data) {
        this.data = data;
        return this;
    }

    public void setData(ZonedDateTime data) {
        this.data = data;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public LicencaSoftware valor(BigDecimal valor) {
        this.valor = valor;
        return this;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getCodigo() {
        return codigo;
    }

    public LicencaSoftware codigo(String codigo) {
        this.codigo = codigo;
        return this;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Integer getNumeroUsuario() {
        return numeroUsuario;
    }

    public LicencaSoftware numeroUsuario(Integer numeroUsuario) {
        this.numeroUsuario = numeroUsuario;
        return this;
    }

    public void setNumeroUsuario(Integer numeroUsuario) {
        this.numeroUsuario = numeroUsuario;
    }

    public Integer getNumeroEmpresa() {
        return numeroEmpresa;
    }

    public LicencaSoftware numeroEmpresa(Integer numeroEmpresa) {
        this.numeroEmpresa = numeroEmpresa;
        return this;
    }

    public void setNumeroEmpresa(Integer numeroEmpresa) {
        this.numeroEmpresa = numeroEmpresa;
    }

    public Software getSoftware() {
        return software;
    }

    public LicencaSoftware software(Software software) {
        this.software = software;
        return this;
    }

    public void setSoftware(Software software) {
        this.software = software;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public LicencaSoftware empresa(Empresa empresa) {
        this.empresa = empresa;
        return this;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LicencaSoftware)) {
            return false;
        }
        return id != null && id.equals(((LicencaSoftware) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "LicencaSoftware{" +
            "id=" + getId() +
            ", tipoSubscricao='" + getTipoSubscricao() + "'" +
            ", inicio='" + getInicio() + "'" +
            ", fim='" + getFim() + "'" +
            ", data='" + getData() + "'" +
            ", valor=" + getValor() +
            ", codigo='" + getCodigo() + "'" +
            ", numeroUsuario=" + getNumeroUsuario() +
            ", numeroEmpresa=" + getNumeroEmpresa() +
            "}";
    }
}
