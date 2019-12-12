package com.ravunana.manda.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * A Compra.
 */
@Entity
@Table(name = "cmp_compra")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Compra implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "numero", nullable = false, unique = true)
    private String numero;

    @Column(name = "data")
    private ZonedDateTime data;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "observacao_geral")
    private String observacaoGeral;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "observacao_interna")
    private String observacaoInterna;

    @OneToMany(mappedBy = "compra")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ItemCompra> itemCompras = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("compras")
    private User utilizador;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("compras")
    private DocumentoComercial tipoDocumento;

    @ManyToOne
    @JsonIgnoreProperties("compras")
    private Empresa empresa;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public Compra numero(String numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public ZonedDateTime getData() {
        return data;
    }

    public Compra data(ZonedDateTime data) {
        this.data = data;
        return this;
    }

    public void setData(ZonedDateTime data) {
        this.data = data;
    }

    public String getObservacaoGeral() {
        return observacaoGeral;
    }

    public Compra observacaoGeral(String observacaoGeral) {
        this.observacaoGeral = observacaoGeral;
        return this;
    }

    public void setObservacaoGeral(String observacaoGeral) {
        this.observacaoGeral = observacaoGeral;
    }

    public String getObservacaoInterna() {
        return observacaoInterna;
    }

    public Compra observacaoInterna(String observacaoInterna) {
        this.observacaoInterna = observacaoInterna;
        return this;
    }

    public void setObservacaoInterna(String observacaoInterna) {
        this.observacaoInterna = observacaoInterna;
    }

    public Set<ItemCompra> getItemCompras() {
        return itemCompras;
    }

    public Compra itemCompras(Set<ItemCompra> itemCompras) {
        this.itemCompras = itemCompras;
        return this;
    }

    public Compra addItemCompra(ItemCompra itemCompra) {
        this.itemCompras.add(itemCompra);
        itemCompra.setCompra(this);
        return this;
    }

    public Compra removeItemCompra(ItemCompra itemCompra) {
        this.itemCompras.remove(itemCompra);
        itemCompra.setCompra(null);
        return this;
    }

    public void setItemCompras(Set<ItemCompra> itemCompras) {
        this.itemCompras = itemCompras;
    }

    public User getUtilizador() {
        return utilizador;
    }

    public Compra utilizador(User user) {
        this.utilizador = user;
        return this;
    }

    public void setUtilizador(User user) {
        this.utilizador = user;
    }

    public DocumentoComercial getTipoDocumento() {
        return tipoDocumento;
    }

    public Compra tipoDocumento(DocumentoComercial documentoComercial) {
        this.tipoDocumento = documentoComercial;
        return this;
    }

    public void setTipoDocumento(DocumentoComercial documentoComercial) {
        this.tipoDocumento = documentoComercial;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public Compra empresa(Empresa empresa) {
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
        if (!(o instanceof Compra)) {
            return false;
        }
        return id != null && id.equals(((Compra) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Compra{" +
            "id=" + getId() +
            ", numero='" + getNumero() + "'" +
            ", data='" + getData() + "'" +
            ", observacaoGeral='" + getObservacaoGeral() + "'" +
            ", observacaoInterna='" + getObservacaoInterna() + "'" +
            "}";
    }
}
