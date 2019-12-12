package com.ravunana.manda.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.ravunana.manda.domain.enumeration.EntidadeSistema;

/**
 * A DocumentoComercial.
 */
@Entity
@Table(name = "core_documento_comercial")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DocumentoComercial implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "nome", nullable = false, unique = true)
    private String nome;

    @NotNull
    @Column(name = "serie", nullable = false, unique = true)
    private String serie;

    @NotNull
    @Column(name = "padrao", nullable = false)
    private Boolean padrao;

    @NotNull
    @Column(name = "movimenta_estoque", nullable = false)
    private Boolean movimentaEstoque;

    @NotNull
    @Column(name = "movimenta_caixa", nullable = false)
    private Boolean movimentaCaixa;

    @NotNull
    @Column(name = "movimenta_contabilidade", nullable = false)
    private Boolean movimentaContabilidade;

    @Column(name = "cor")
    private String cor;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "descricao")
    private String descricao;

    @Column(name = "mostra_ponto_venda")
    private Boolean mostraPontoVenda;

    @Enumerated(EnumType.STRING)
    @Column(name = "entidade")
    private EntidadeSistema entidade;

    @OneToMany(mappedBy = "tipoRecibo")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<LancamentoFinanceiro> lancamentoFinanceiros = new HashSet<>();

    @OneToMany(mappedBy = "tipoDocumento")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Compra> compras = new HashSet<>();

    @OneToMany(mappedBy = "tipoDocumento")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Venda> vendas = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public DocumentoComercial nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSerie() {
        return serie;
    }

    public DocumentoComercial serie(String serie) {
        this.serie = serie;
        return this;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public Boolean isPadrao() {
        return padrao;
    }

    public DocumentoComercial padrao(Boolean padrao) {
        this.padrao = padrao;
        return this;
    }

    public void setPadrao(Boolean padrao) {
        this.padrao = padrao;
    }

    public Boolean isMovimentaEstoque() {
        return movimentaEstoque;
    }

    public DocumentoComercial movimentaEstoque(Boolean movimentaEstoque) {
        this.movimentaEstoque = movimentaEstoque;
        return this;
    }

    public void setMovimentaEstoque(Boolean movimentaEstoque) {
        this.movimentaEstoque = movimentaEstoque;
    }

    public Boolean isMovimentaCaixa() {
        return movimentaCaixa;
    }

    public DocumentoComercial movimentaCaixa(Boolean movimentaCaixa) {
        this.movimentaCaixa = movimentaCaixa;
        return this;
    }

    public void setMovimentaCaixa(Boolean movimentaCaixa) {
        this.movimentaCaixa = movimentaCaixa;
    }

    public Boolean isMovimentaContabilidade() {
        return movimentaContabilidade;
    }

    public DocumentoComercial movimentaContabilidade(Boolean movimentaContabilidade) {
        this.movimentaContabilidade = movimentaContabilidade;
        return this;
    }

    public void setMovimentaContabilidade(Boolean movimentaContabilidade) {
        this.movimentaContabilidade = movimentaContabilidade;
    }

    public String getCor() {
        return cor;
    }

    public DocumentoComercial cor(String cor) {
        this.cor = cor;
        return this;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getDescricao() {
        return descricao;
    }

    public DocumentoComercial descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Boolean isMostraPontoVenda() {
        return mostraPontoVenda;
    }

    public DocumentoComercial mostraPontoVenda(Boolean mostraPontoVenda) {
        this.mostraPontoVenda = mostraPontoVenda;
        return this;
    }

    public void setMostraPontoVenda(Boolean mostraPontoVenda) {
        this.mostraPontoVenda = mostraPontoVenda;
    }

    public EntidadeSistema getEntidade() {
        return entidade;
    }

    public DocumentoComercial entidade(EntidadeSistema entidade) {
        this.entidade = entidade;
        return this;
    }

    public void setEntidade(EntidadeSistema entidade) {
        this.entidade = entidade;
    }

    public Set<LancamentoFinanceiro> getLancamentoFinanceiros() {
        return lancamentoFinanceiros;
    }

    public DocumentoComercial lancamentoFinanceiros(Set<LancamentoFinanceiro> lancamentoFinanceiros) {
        this.lancamentoFinanceiros = lancamentoFinanceiros;
        return this;
    }

    public DocumentoComercial addLancamentoFinanceiro(LancamentoFinanceiro lancamentoFinanceiro) {
        this.lancamentoFinanceiros.add(lancamentoFinanceiro);
        lancamentoFinanceiro.setTipoRecibo(this);
        return this;
    }

    public DocumentoComercial removeLancamentoFinanceiro(LancamentoFinanceiro lancamentoFinanceiro) {
        this.lancamentoFinanceiros.remove(lancamentoFinanceiro);
        lancamentoFinanceiro.setTipoRecibo(null);
        return this;
    }

    public void setLancamentoFinanceiros(Set<LancamentoFinanceiro> lancamentoFinanceiros) {
        this.lancamentoFinanceiros = lancamentoFinanceiros;
    }

    public Set<Compra> getCompras() {
        return compras;
    }

    public DocumentoComercial compras(Set<Compra> compras) {
        this.compras = compras;
        return this;
    }

    public DocumentoComercial addCompra(Compra compra) {
        this.compras.add(compra);
        compra.setTipoDocumento(this);
        return this;
    }

    public DocumentoComercial removeCompra(Compra compra) {
        this.compras.remove(compra);
        compra.setTipoDocumento(null);
        return this;
    }

    public void setCompras(Set<Compra> compras) {
        this.compras = compras;
    }

    public Set<Venda> getVendas() {
        return vendas;
    }

    public DocumentoComercial vendas(Set<Venda> vendas) {
        this.vendas = vendas;
        return this;
    }

    public DocumentoComercial addVenda(Venda venda) {
        this.vendas.add(venda);
        venda.setTipoDocumento(this);
        return this;
    }

    public DocumentoComercial removeVenda(Venda venda) {
        this.vendas.remove(venda);
        venda.setTipoDocumento(null);
        return this;
    }

    public void setVendas(Set<Venda> vendas) {
        this.vendas = vendas;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DocumentoComercial)) {
            return false;
        }
        return id != null && id.equals(((DocumentoComercial) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "DocumentoComercial{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", serie='" + getSerie() + "'" +
            ", padrao='" + isPadrao() + "'" +
            ", movimentaEstoque='" + isMovimentaEstoque() + "'" +
            ", movimentaCaixa='" + isMovimentaCaixa() + "'" +
            ", movimentaContabilidade='" + isMovimentaContabilidade() + "'" +
            ", cor='" + getCor() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", mostraPontoVenda='" + isMostraPontoVenda() + "'" +
            ", entidade='" + getEntidade() + "'" +
            "}";
    }
}
