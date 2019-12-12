package com.ravunana.manda.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Produto.
 */
@Entity
@Table(name = "stk_produto")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Produto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "tipo")
    private String tipo;

    
    @Column(name = "ean", unique = true)
    private String ean;

    @NotNull
    @Column(name = "numero", nullable = false, unique = true)
    private String numero;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @Lob
    @Column(name = "imagem")
    private byte[] imagem;

    @Column(name = "imagem_content_type")
    private String imagemContentType;

    @NotNull
    @Column(name = "composto", nullable = false)
    private Boolean composto;

    @DecimalMin(value = "0")
    @Column(name = "estoque_minimo")
    private Double estoqueMinimo;

    @DecimalMin(value = "0")
    @Column(name = "estoque_maximo")
    private Double estoqueMaximo;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "estoque_atual", nullable = false)
    private Double estoqueAtual;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "descricao")
    private String descricao;

    @Column(name = "mostrar_pdv")
    private Boolean mostrarPDV;

    @Column(name = "prazo_medio_entrega")
    private String prazoMedioEntrega;

    @OneToMany(mappedBy = "composto")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CompostoProduto> compostoProdutos = new HashSet<>();

    @OneToMany(mappedBy = "produto")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ConversaoUnidade> conversaoUnidades = new HashSet<>();

    @OneToMany(mappedBy = "produto")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<EstruturaCalculo> estruturaCalculos = new HashSet<>();

    @OneToMany(mappedBy = "produto")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ItemCompra> itemCompras = new HashSet<>();

    @OneToMany(mappedBy = "produto")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ItemVenda> itemVendas = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("produtos")
    private User utilizador;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "stk_produto_imposto",
               joinColumns = @JoinColumn(name = "produto_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "imposto_id", referencedColumnName = "id"))
    private Set<Imposto> impostos = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "stk_produto_fornecedor",
               joinColumns = @JoinColumn(name = "produto_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "fornecedor_id", referencedColumnName = "id"))
    private Set<Fornecedor> fornecedors = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("produtos")
    private LocalArmazenamento localArmazenamento;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("produtos")
    private FamiliaProduto familia;

    @ManyToOne
    @JsonIgnoreProperties("produtos")
    private Empresa empresa;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("produtos")
    private FluxoDocumento estado;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public Produto tipo(String tipo) {
        this.tipo = tipo;
        return this;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getEan() {
        return ean;
    }

    public Produto ean(String ean) {
        this.ean = ean;
        return this;
    }

    public void setEan(String ean) {
        this.ean = ean;
    }

    public String getNumero() {
        return numero;
    }

    public Produto numero(String numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getNome() {
        return nome;
    }

    public Produto nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public byte[] getImagem() {
        return imagem;
    }

    public Produto imagem(byte[] imagem) {
        this.imagem = imagem;
        return this;
    }

    public void setImagem(byte[] imagem) {
        this.imagem = imagem;
    }

    public String getImagemContentType() {
        return imagemContentType;
    }

    public Produto imagemContentType(String imagemContentType) {
        this.imagemContentType = imagemContentType;
        return this;
    }

    public void setImagemContentType(String imagemContentType) {
        this.imagemContentType = imagemContentType;
    }

    public Boolean isComposto() {
        return composto;
    }

    public Produto composto(Boolean composto) {
        this.composto = composto;
        return this;
    }

    public void setComposto(Boolean composto) {
        this.composto = composto;
    }

    public Double getEstoqueMinimo() {
        return estoqueMinimo;
    }

    public Produto estoqueMinimo(Double estoqueMinimo) {
        this.estoqueMinimo = estoqueMinimo;
        return this;
    }

    public void setEstoqueMinimo(Double estoqueMinimo) {
        this.estoqueMinimo = estoqueMinimo;
    }

    public Double getEstoqueMaximo() {
        return estoqueMaximo;
    }

    public Produto estoqueMaximo(Double estoqueMaximo) {
        this.estoqueMaximo = estoqueMaximo;
        return this;
    }

    public void setEstoqueMaximo(Double estoqueMaximo) {
        this.estoqueMaximo = estoqueMaximo;
    }

    public Double getEstoqueAtual() {
        return estoqueAtual;
    }

    public Produto estoqueAtual(Double estoqueAtual) {
        this.estoqueAtual = estoqueAtual;
        return this;
    }

    public void setEstoqueAtual(Double estoqueAtual) {
        this.estoqueAtual = estoqueAtual;
    }

    public String getDescricao() {
        return descricao;
    }

    public Produto descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Boolean isMostrarPDV() {
        return mostrarPDV;
    }

    public Produto mostrarPDV(Boolean mostrarPDV) {
        this.mostrarPDV = mostrarPDV;
        return this;
    }

    public void setMostrarPDV(Boolean mostrarPDV) {
        this.mostrarPDV = mostrarPDV;
    }

    public String getPrazoMedioEntrega() {
        return prazoMedioEntrega;
    }

    public Produto prazoMedioEntrega(String prazoMedioEntrega) {
        this.prazoMedioEntrega = prazoMedioEntrega;
        return this;
    }

    public void setPrazoMedioEntrega(String prazoMedioEntrega) {
        this.prazoMedioEntrega = prazoMedioEntrega;
    }

    public Set<CompostoProduto> getCompostoProdutos() {
        return compostoProdutos;
    }

    public Produto compostoProdutos(Set<CompostoProduto> compostoProdutos) {
        this.compostoProdutos = compostoProdutos;
        return this;
    }

    public Produto addCompostoProduto(CompostoProduto compostoProduto) {
        this.compostoProdutos.add(compostoProduto);
        compostoProduto.setComposto(this);
        return this;
    }

    public Produto removeCompostoProduto(CompostoProduto compostoProduto) {
        this.compostoProdutos.remove(compostoProduto);
        compostoProduto.setComposto(null);
        return this;
    }

    public void setCompostoProdutos(Set<CompostoProduto> compostoProdutos) {
        this.compostoProdutos = compostoProdutos;
    }

    public Set<ConversaoUnidade> getConversaoUnidades() {
        return conversaoUnidades;
    }

    public Produto conversaoUnidades(Set<ConversaoUnidade> conversaoUnidades) {
        this.conversaoUnidades = conversaoUnidades;
        return this;
    }

    public Produto addConversaoUnidade(ConversaoUnidade conversaoUnidade) {
        this.conversaoUnidades.add(conversaoUnidade);
        conversaoUnidade.setProduto(this);
        return this;
    }

    public Produto removeConversaoUnidade(ConversaoUnidade conversaoUnidade) {
        this.conversaoUnidades.remove(conversaoUnidade);
        conversaoUnidade.setProduto(null);
        return this;
    }

    public void setConversaoUnidades(Set<ConversaoUnidade> conversaoUnidades) {
        this.conversaoUnidades = conversaoUnidades;
    }

    public Set<EstruturaCalculo> getEstruturaCalculos() {
        return estruturaCalculos;
    }

    public Produto estruturaCalculos(Set<EstruturaCalculo> estruturaCalculos) {
        this.estruturaCalculos = estruturaCalculos;
        return this;
    }

    public Produto addEstruturaCalculo(EstruturaCalculo estruturaCalculo) {
        this.estruturaCalculos.add(estruturaCalculo);
        estruturaCalculo.setProduto(this);
        return this;
    }

    public Produto removeEstruturaCalculo(EstruturaCalculo estruturaCalculo) {
        this.estruturaCalculos.remove(estruturaCalculo);
        estruturaCalculo.setProduto(null);
        return this;
    }

    public void setEstruturaCalculos(Set<EstruturaCalculo> estruturaCalculos) {
        this.estruturaCalculos = estruturaCalculos;
    }

    public Set<ItemCompra> getItemCompras() {
        return itemCompras;
    }

    public Produto itemCompras(Set<ItemCompra> itemCompras) {
        this.itemCompras = itemCompras;
        return this;
    }

    public Produto addItemCompra(ItemCompra itemCompra) {
        this.itemCompras.add(itemCompra);
        itemCompra.setProduto(this);
        return this;
    }

    public Produto removeItemCompra(ItemCompra itemCompra) {
        this.itemCompras.remove(itemCompra);
        itemCompra.setProduto(null);
        return this;
    }

    public void setItemCompras(Set<ItemCompra> itemCompras) {
        this.itemCompras = itemCompras;
    }

    public Set<ItemVenda> getItemVendas() {
        return itemVendas;
    }

    public Produto itemVendas(Set<ItemVenda> itemVendas) {
        this.itemVendas = itemVendas;
        return this;
    }

    public Produto addItemVenda(ItemVenda itemVenda) {
        this.itemVendas.add(itemVenda);
        itemVenda.setProduto(this);
        return this;
    }

    public Produto removeItemVenda(ItemVenda itemVenda) {
        this.itemVendas.remove(itemVenda);
        itemVenda.setProduto(null);
        return this;
    }

    public void setItemVendas(Set<ItemVenda> itemVendas) {
        this.itemVendas = itemVendas;
    }

    public User getUtilizador() {
        return utilizador;
    }

    public Produto utilizador(User user) {
        this.utilizador = user;
        return this;
    }

    public void setUtilizador(User user) {
        this.utilizador = user;
    }

    public Set<Imposto> getImpostos() {
        return impostos;
    }

    public Produto impostos(Set<Imposto> impostos) {
        this.impostos = impostos;
        return this;
    }

    public Produto addImposto(Imposto imposto) {
        this.impostos.add(imposto);
        imposto.getProdutos().add(this);
        return this;
    }

    public Produto removeImposto(Imposto imposto) {
        this.impostos.remove(imposto);
        imposto.getProdutos().remove(this);
        return this;
    }

    public void setImpostos(Set<Imposto> impostos) {
        this.impostos = impostos;
    }

    public Set<Fornecedor> getFornecedors() {
        return fornecedors;
    }

    public Produto fornecedors(Set<Fornecedor> fornecedors) {
        this.fornecedors = fornecedors;
        return this;
    }

    public Produto addFornecedor(Fornecedor fornecedor) {
        this.fornecedors.add(fornecedor);
        fornecedor.getProdutos().add(this);
        return this;
    }

    public Produto removeFornecedor(Fornecedor fornecedor) {
        this.fornecedors.remove(fornecedor);
        fornecedor.getProdutos().remove(this);
        return this;
    }

    public void setFornecedors(Set<Fornecedor> fornecedors) {
        this.fornecedors = fornecedors;
    }

    public LocalArmazenamento getLocalArmazenamento() {
        return localArmazenamento;
    }

    public Produto localArmazenamento(LocalArmazenamento localArmazenamento) {
        this.localArmazenamento = localArmazenamento;
        return this;
    }

    public void setLocalArmazenamento(LocalArmazenamento localArmazenamento) {
        this.localArmazenamento = localArmazenamento;
    }

    public FamiliaProduto getFamilia() {
        return familia;
    }

    public Produto familia(FamiliaProduto familiaProduto) {
        this.familia = familiaProduto;
        return this;
    }

    public void setFamilia(FamiliaProduto familiaProduto) {
        this.familia = familiaProduto;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public Produto empresa(Empresa empresa) {
        this.empresa = empresa;
        return this;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public FluxoDocumento getEstado() {
        return estado;
    }

    public Produto estado(FluxoDocumento fluxoDocumento) {
        this.estado = fluxoDocumento;
        return this;
    }

    public void setEstado(FluxoDocumento fluxoDocumento) {
        this.estado = fluxoDocumento;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Produto)) {
            return false;
        }
        return id != null && id.equals(((Produto) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Produto{" +
            "id=" + getId() +
            ", tipo='" + getTipo() + "'" +
            ", ean='" + getEan() + "'" +
            ", numero='" + getNumero() + "'" +
            ", nome='" + getNome() + "'" +
            ", imagem='" + getImagem() + "'" +
            ", imagemContentType='" + getImagemContentType() + "'" +
            ", composto='" + isComposto() + "'" +
            ", estoqueMinimo=" + getEstoqueMinimo() +
            ", estoqueMaximo=" + getEstoqueMaximo() +
            ", estoqueAtual=" + getEstoqueAtual() +
            ", descricao='" + getDescricao() + "'" +
            ", mostrarPDV='" + isMostrarPDV() + "'" +
            ", prazoMedioEntrega='" + getPrazoMedioEntrega() + "'" +
            "}";
    }
}
