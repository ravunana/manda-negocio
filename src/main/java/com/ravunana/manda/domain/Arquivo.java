package com.ravunana.manda.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

import com.ravunana.manda.domain.enumeration.EntidadeSistema;

/**
 * A Arquivo.
 */
@Entity
@Table(name = "core_arquivo")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Arquivo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "descricao")
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(name = "entidade")
    private EntidadeSistema entidade;

    @Lob
    @Column(name = "anexo")
    private byte[] anexo;

    @Column(name = "anexo_content_type")
    private String anexoContentType;

    @Column(name = "codigo_entidade")
    private String codigoEntidade;

    @Column(name = "data")
    private ZonedDateTime data;

    @ManyToOne
    @JsonIgnoreProperties("arquivos")
    private User utilizador;

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

    public Arquivo descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public EntidadeSistema getEntidade() {
        return entidade;
    }

    public Arquivo entidade(EntidadeSistema entidade) {
        this.entidade = entidade;
        return this;
    }

    public void setEntidade(EntidadeSistema entidade) {
        this.entidade = entidade;
    }

    public byte[] getAnexo() {
        return anexo;
    }

    public Arquivo anexo(byte[] anexo) {
        this.anexo = anexo;
        return this;
    }

    public void setAnexo(byte[] anexo) {
        this.anexo = anexo;
    }

    public String getAnexoContentType() {
        return anexoContentType;
    }

    public Arquivo anexoContentType(String anexoContentType) {
        this.anexoContentType = anexoContentType;
        return this;
    }

    public void setAnexoContentType(String anexoContentType) {
        this.anexoContentType = anexoContentType;
    }

    public String getCodigoEntidade() {
        return codigoEntidade;
    }

    public Arquivo codigoEntidade(String codigoEntidade) {
        this.codigoEntidade = codigoEntidade;
        return this;
    }

    public void setCodigoEntidade(String codigoEntidade) {
        this.codigoEntidade = codigoEntidade;
    }

    public ZonedDateTime getData() {
        return data;
    }

    public Arquivo data(ZonedDateTime data) {
        this.data = data;
        return this;
    }

    public void setData(ZonedDateTime data) {
        this.data = data;
    }

    public User getUtilizador() {
        return utilizador;
    }

    public Arquivo utilizador(User user) {
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
        if (!(o instanceof Arquivo)) {
            return false;
        }
        return id != null && id.equals(((Arquivo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Arquivo{" +
            "id=" + getId() +
            ", descricao='" + getDescricao() + "'" +
            ", entidade='" + getEntidade() + "'" +
            ", anexo='" + getAnexo() + "'" +
            ", anexoContentType='" + getAnexoContentType() + "'" +
            ", codigoEntidade='" + getCodigoEntidade() + "'" +
            ", data='" + getData() + "'" +
            "}";
    }
}
