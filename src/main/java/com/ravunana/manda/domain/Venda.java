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
 * A Venda.
 */
@Entity
@Table(name = "vnd_venda")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Venda implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "numero")
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

    @OneToMany(mappedBy = "venda")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ItemVenda> itemVendas = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("vendas")
    private User vendedor;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("vendas")
    private Cliente cliente;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("vendas")
    private DocumentoComercial tipoDocumento;

    @ManyToOne
    @JsonIgnoreProperties("vendas")
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

    public Venda numero(String numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public ZonedDateTime getData() {
        return data;
    }

    public Venda data(ZonedDateTime data) {
        this.data = data;
        return this;
    }

    public void setData(ZonedDateTime data) {
        this.data = data;
    }

    public String getObservacaoGeral() {
        return observacaoGeral;
    }

    public Venda observacaoGeral(String observacaoGeral) {
        this.observacaoGeral = observacaoGeral;
        return this;
    }

    public void setObservacaoGeral(String observacaoGeral) {
        this.observacaoGeral = observacaoGeral;
    }

    public String getObservacaoInterna() {
        return observacaoInterna;
    }

    public Venda observacaoInterna(String observacaoInterna) {
        this.observacaoInterna = observacaoInterna;
        return this;
    }

    public void setObservacaoInterna(String observacaoInterna) {
        this.observacaoInterna = observacaoInterna;
    }

    public Set<ItemVenda> getItemVendas() {
        return itemVendas;
    }

    public Venda itemVendas(Set<ItemVenda> itemVendas) {
        this.itemVendas = itemVendas;
        return this;
    }

    public Venda addItemVenda(ItemVenda itemVenda) {
        this.itemVendas.add(itemVenda);
        itemVenda.setVenda(this);
        return this;
    }

    public Venda removeItemVenda(ItemVenda itemVenda) {
        this.itemVendas.remove(itemVenda);
        itemVenda.setVenda(null);
        return this;
    }

    public void setItemVendas(Set<ItemVenda> itemVendas) {
        this.itemVendas = itemVendas;
    }

    public User getVendedor() {
        return vendedor;
    }

    public Venda vendedor(User user) {
        this.vendedor = user;
        return this;
    }

    public void setVendedor(User user) {
        this.vendedor = user;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Venda cliente(Cliente cliente) {
        this.cliente = cliente;
        return this;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public DocumentoComercial getTipoDocumento() {
        return tipoDocumento;
    }

    public Venda tipoDocumento(DocumentoComercial documentoComercial) {
        this.tipoDocumento = documentoComercial;
        return this;
    }

    public void setTipoDocumento(DocumentoComercial documentoComercial) {
        this.tipoDocumento = documentoComercial;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public Venda empresa(Empresa empresa) {
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
        if (!(o instanceof Venda)) {
            return false;
        }
        return id != null && id.equals(((Venda) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Venda{" +
            "id=" + getId() +
            ", numero='" + getNumero() + "'" +
            ", data='" + getData() + "'" +
            ", observacaoGeral='" + getObservacaoGeral() + "'" +
            ", observacaoInterna='" + getObservacaoInterna() + "'" +
            "}";
    }
}
