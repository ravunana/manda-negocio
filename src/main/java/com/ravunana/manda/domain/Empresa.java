package com.ravunana.manda.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Empresa.
 */
@Entity
@Table(name = "core_empresa")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Empresa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "tipo_sociedade")
    private String tipoSociedade;

    @NotNull
    @Column(name = "nome", nullable = false, unique = true)
    private String nome;

    @Lob
    @Column(name = "logotipo")
    private byte[] logotipo;

    @Column(name = "logotipo_content_type")
    private String logotipoContentType;

    @DecimalMin(value = "0")
    @Column(name = "capital_social", precision = 21, scale = 2)
    private BigDecimal capitalSocial;

    @Column(name = "fundacao")
    private LocalDate fundacao;

    @NotNull
    @Size(max = 20)
    @Column(name = "nif", length = 20, nullable = false, unique = true)
    private String nif;

    @NotNull
    @Column(name = "numero_registro_comercial", nullable = false, unique = true)
    private String numeroRegistroComercial;

    @NotNull
    @DecimalMin(value = "0")
    @DecimalMax(value = "100")
    @Column(name = "despesa_fixa", nullable = false)
    private Double despesaFixa;

    @NotNull
    @DecimalMin(value = "0")
    @DecimalMax(value = "100")
    @Column(name = "despesa_variavel", nullable = false)
    private Double despesaVariavel;

    @Column(name = "abertura_exercio")
    private LocalDate aberturaExercio;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "designacao_comercial")
    private String designacaoComercial;

    @Column(name = "sede")
    private Boolean sede;

    @OneToMany(mappedBy = "empresa")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<EscrituracaoContabil> escrituracaoContabils = new HashSet<>();

    @OneToMany(mappedBy = "hierarquia")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Empresa> empresas = new HashSet<>();

    @OneToMany(mappedBy = "empresa")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<LocalizacaoEmpresa> localizacaoEmpresas = new HashSet<>();

    @OneToMany(mappedBy = "empresa")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ContactoEmpresa> contactoEmpresas = new HashSet<>();

    @OneToMany(mappedBy = "empresa")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<LicencaSoftware> licencaSoftwares = new HashSet<>();

    @OneToMany(mappedBy = "empresa")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<LancamentoFinanceiro> lancamentoFinanceiros = new HashSet<>();

    @OneToMany(mappedBy = "empresa")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Produto> produtos = new HashSet<>();

    @OneToMany(mappedBy = "empresa")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<LocalArmazenamento> localArmazenamentos = new HashSet<>();

    @OneToMany(mappedBy = "empresa")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Compra> compras = new HashSet<>();

    @OneToMany(mappedBy = "empresa")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Venda> vendas = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("empresas")
    private User utilizador;

    @ManyToOne
    @JsonIgnoreProperties("empresas")
    private Conta conta;

    @ManyToOne
    @JsonIgnoreProperties("empresas")
    private Empresa hierarquia;

    @ManyToMany(mappedBy = "empresas")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Conta> contas = new HashSet<>();

    @ManyToMany(mappedBy = "empresas")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<CoordenadaBancaria> coordenadaBancarias = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipoSociedade() {
        return tipoSociedade;
    }

    public Empresa tipoSociedade(String tipoSociedade) {
        this.tipoSociedade = tipoSociedade;
        return this;
    }

    public void setTipoSociedade(String tipoSociedade) {
        this.tipoSociedade = tipoSociedade;
    }

    public String getNome() {
        return nome;
    }

    public Empresa nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public byte[] getLogotipo() {
        return logotipo;
    }

    public Empresa logotipo(byte[] logotipo) {
        this.logotipo = logotipo;
        return this;
    }

    public void setLogotipo(byte[] logotipo) {
        this.logotipo = logotipo;
    }

    public String getLogotipoContentType() {
        return logotipoContentType;
    }

    public Empresa logotipoContentType(String logotipoContentType) {
        this.logotipoContentType = logotipoContentType;
        return this;
    }

    public void setLogotipoContentType(String logotipoContentType) {
        this.logotipoContentType = logotipoContentType;
    }

    public BigDecimal getCapitalSocial() {
        return capitalSocial;
    }

    public Empresa capitalSocial(BigDecimal capitalSocial) {
        this.capitalSocial = capitalSocial;
        return this;
    }

    public void setCapitalSocial(BigDecimal capitalSocial) {
        this.capitalSocial = capitalSocial;
    }

    public LocalDate getFundacao() {
        return fundacao;
    }

    public Empresa fundacao(LocalDate fundacao) {
        this.fundacao = fundacao;
        return this;
    }

    public void setFundacao(LocalDate fundacao) {
        this.fundacao = fundacao;
    }

    public String getNif() {
        return nif;
    }

    public Empresa nif(String nif) {
        this.nif = nif;
        return this;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getNumeroRegistroComercial() {
        return numeroRegistroComercial;
    }

    public Empresa numeroRegistroComercial(String numeroRegistroComercial) {
        this.numeroRegistroComercial = numeroRegistroComercial;
        return this;
    }

    public void setNumeroRegistroComercial(String numeroRegistroComercial) {
        this.numeroRegistroComercial = numeroRegistroComercial;
    }

    public Double getDespesaFixa() {
        return despesaFixa;
    }

    public Empresa despesaFixa(Double despesaFixa) {
        this.despesaFixa = despesaFixa;
        return this;
    }

    public void setDespesaFixa(Double despesaFixa) {
        this.despesaFixa = despesaFixa;
    }

    public Double getDespesaVariavel() {
        return despesaVariavel;
    }

    public Empresa despesaVariavel(Double despesaVariavel) {
        this.despesaVariavel = despesaVariavel;
        return this;
    }

    public void setDespesaVariavel(Double despesaVariavel) {
        this.despesaVariavel = despesaVariavel;
    }

    public LocalDate getAberturaExercio() {
        return aberturaExercio;
    }

    public Empresa aberturaExercio(LocalDate aberturaExercio) {
        this.aberturaExercio = aberturaExercio;
        return this;
    }

    public void setAberturaExercio(LocalDate aberturaExercio) {
        this.aberturaExercio = aberturaExercio;
    }

    public String getDesignacaoComercial() {
        return designacaoComercial;
    }

    public Empresa designacaoComercial(String designacaoComercial) {
        this.designacaoComercial = designacaoComercial;
        return this;
    }

    public void setDesignacaoComercial(String designacaoComercial) {
        this.designacaoComercial = designacaoComercial;
    }

    public Boolean isSede() {
        return sede;
    }

    public Empresa sede(Boolean sede) {
        this.sede = sede;
        return this;
    }

    public void setSede(Boolean sede) {
        this.sede = sede;
    }

    public Set<EscrituracaoContabil> getEscrituracaoContabils() {
        return escrituracaoContabils;
    }

    public Empresa escrituracaoContabils(Set<EscrituracaoContabil> escrituracaoContabils) {
        this.escrituracaoContabils = escrituracaoContabils;
        return this;
    }

    public Empresa addEscrituracaoContabil(EscrituracaoContabil escrituracaoContabil) {
        this.escrituracaoContabils.add(escrituracaoContabil);
        escrituracaoContabil.setEmpresa(this);
        return this;
    }

    public Empresa removeEscrituracaoContabil(EscrituracaoContabil escrituracaoContabil) {
        this.escrituracaoContabils.remove(escrituracaoContabil);
        escrituracaoContabil.setEmpresa(null);
        return this;
    }

    public void setEscrituracaoContabils(Set<EscrituracaoContabil> escrituracaoContabils) {
        this.escrituracaoContabils = escrituracaoContabils;
    }

    public Set<Empresa> getEmpresas() {
        return empresas;
    }

    public Empresa empresas(Set<Empresa> empresas) {
        this.empresas = empresas;
        return this;
    }

    public Empresa addEmpresa(Empresa empresa) {
        this.empresas.add(empresa);
        empresa.setHierarquia(this);
        return this;
    }

    public Empresa removeEmpresa(Empresa empresa) {
        this.empresas.remove(empresa);
        empresa.setHierarquia(null);
        return this;
    }

    public void setEmpresas(Set<Empresa> empresas) {
        this.empresas = empresas;
    }

    public Set<LocalizacaoEmpresa> getLocalizacaoEmpresas() {
        return localizacaoEmpresas;
    }

    public Empresa localizacaoEmpresas(Set<LocalizacaoEmpresa> localizacaoEmpresas) {
        this.localizacaoEmpresas = localizacaoEmpresas;
        return this;
    }

    public Empresa addLocalizacaoEmpresa(LocalizacaoEmpresa localizacaoEmpresa) {
        this.localizacaoEmpresas.add(localizacaoEmpresa);
        localizacaoEmpresa.setEmpresa(this);
        return this;
    }

    public Empresa removeLocalizacaoEmpresa(LocalizacaoEmpresa localizacaoEmpresa) {
        this.localizacaoEmpresas.remove(localizacaoEmpresa);
        localizacaoEmpresa.setEmpresa(null);
        return this;
    }

    public void setLocalizacaoEmpresas(Set<LocalizacaoEmpresa> localizacaoEmpresas) {
        this.localizacaoEmpresas = localizacaoEmpresas;
    }

    public Set<ContactoEmpresa> getContactoEmpresas() {
        return contactoEmpresas;
    }

    public Empresa contactoEmpresas(Set<ContactoEmpresa> contactoEmpresas) {
        this.contactoEmpresas = contactoEmpresas;
        return this;
    }

    public Empresa addContactoEmpresa(ContactoEmpresa contactoEmpresa) {
        this.contactoEmpresas.add(contactoEmpresa);
        contactoEmpresa.setEmpresa(this);
        return this;
    }

    public Empresa removeContactoEmpresa(ContactoEmpresa contactoEmpresa) {
        this.contactoEmpresas.remove(contactoEmpresa);
        contactoEmpresa.setEmpresa(null);
        return this;
    }

    public void setContactoEmpresas(Set<ContactoEmpresa> contactoEmpresas) {
        this.contactoEmpresas = contactoEmpresas;
    }

    public Set<LicencaSoftware> getLicencaSoftwares() {
        return licencaSoftwares;
    }

    public Empresa licencaSoftwares(Set<LicencaSoftware> licencaSoftwares) {
        this.licencaSoftwares = licencaSoftwares;
        return this;
    }

    public Empresa addLicencaSoftware(LicencaSoftware licencaSoftware) {
        this.licencaSoftwares.add(licencaSoftware);
        licencaSoftware.setEmpresa(this);
        return this;
    }

    public Empresa removeLicencaSoftware(LicencaSoftware licencaSoftware) {
        this.licencaSoftwares.remove(licencaSoftware);
        licencaSoftware.setEmpresa(null);
        return this;
    }

    public void setLicencaSoftwares(Set<LicencaSoftware> licencaSoftwares) {
        this.licencaSoftwares = licencaSoftwares;
    }

    public Set<LancamentoFinanceiro> getLancamentoFinanceiros() {
        return lancamentoFinanceiros;
    }

    public Empresa lancamentoFinanceiros(Set<LancamentoFinanceiro> lancamentoFinanceiros) {
        this.lancamentoFinanceiros = lancamentoFinanceiros;
        return this;
    }

    public Empresa addLancamentoFinanceiro(LancamentoFinanceiro lancamentoFinanceiro) {
        this.lancamentoFinanceiros.add(lancamentoFinanceiro);
        lancamentoFinanceiro.setEmpresa(this);
        return this;
    }

    public Empresa removeLancamentoFinanceiro(LancamentoFinanceiro lancamentoFinanceiro) {
        this.lancamentoFinanceiros.remove(lancamentoFinanceiro);
        lancamentoFinanceiro.setEmpresa(null);
        return this;
    }

    public void setLancamentoFinanceiros(Set<LancamentoFinanceiro> lancamentoFinanceiros) {
        this.lancamentoFinanceiros = lancamentoFinanceiros;
    }

    public Set<Produto> getProdutos() {
        return produtos;
    }

    public Empresa produtos(Set<Produto> produtos) {
        this.produtos = produtos;
        return this;
    }

    public Empresa addProduto(Produto produto) {
        this.produtos.add(produto);
        produto.setEmpresa(this);
        return this;
    }

    public Empresa removeProduto(Produto produto) {
        this.produtos.remove(produto);
        produto.setEmpresa(null);
        return this;
    }

    public void setProdutos(Set<Produto> produtos) {
        this.produtos = produtos;
    }

    public Set<LocalArmazenamento> getLocalArmazenamentos() {
        return localArmazenamentos;
    }

    public Empresa localArmazenamentos(Set<LocalArmazenamento> localArmazenamentos) {
        this.localArmazenamentos = localArmazenamentos;
        return this;
    }

    public Empresa addLocalArmazenamento(LocalArmazenamento localArmazenamento) {
        this.localArmazenamentos.add(localArmazenamento);
        localArmazenamento.setEmpresa(this);
        return this;
    }

    public Empresa removeLocalArmazenamento(LocalArmazenamento localArmazenamento) {
        this.localArmazenamentos.remove(localArmazenamento);
        localArmazenamento.setEmpresa(null);
        return this;
    }

    public void setLocalArmazenamentos(Set<LocalArmazenamento> localArmazenamentos) {
        this.localArmazenamentos = localArmazenamentos;
    }

    public Set<Compra> getCompras() {
        return compras;
    }

    public Empresa compras(Set<Compra> compras) {
        this.compras = compras;
        return this;
    }

    public Empresa addCompra(Compra compra) {
        this.compras.add(compra);
        compra.setEmpresa(this);
        return this;
    }

    public Empresa removeCompra(Compra compra) {
        this.compras.remove(compra);
        compra.setEmpresa(null);
        return this;
    }

    public void setCompras(Set<Compra> compras) {
        this.compras = compras;
    }

    public Set<Venda> getVendas() {
        return vendas;
    }

    public Empresa vendas(Set<Venda> vendas) {
        this.vendas = vendas;
        return this;
    }

    public Empresa addVenda(Venda venda) {
        this.vendas.add(venda);
        venda.setEmpresa(this);
        return this;
    }

    public Empresa removeVenda(Venda venda) {
        this.vendas.remove(venda);
        venda.setEmpresa(null);
        return this;
    }

    public void setVendas(Set<Venda> vendas) {
        this.vendas = vendas;
    }

    public User getUtilizador() {
        return utilizador;
    }

    public Empresa utilizador(User user) {
        this.utilizador = user;
        return this;
    }

    public void setUtilizador(User user) {
        this.utilizador = user;
    }

    public Conta getConta() {
        return conta;
    }

    public Empresa conta(Conta conta) {
        this.conta = conta;
        return this;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }

    public Empresa getHierarquia() {
        return hierarquia;
    }

    public Empresa hierarquia(Empresa empresa) {
        this.hierarquia = empresa;
        return this;
    }

    public void setHierarquia(Empresa empresa) {
        this.hierarquia = empresa;
    }

    public Set<Conta> getContas() {
        return contas;
    }

    public Empresa contas(Set<Conta> contas) {
        this.contas = contas;
        return this;
    }

    public Empresa addConta(Conta conta) {
        this.contas.add(conta);
        conta.getEmpresas().add(this);
        return this;
    }

    public Empresa removeConta(Conta conta) {
        this.contas.remove(conta);
        conta.getEmpresas().remove(this);
        return this;
    }

    public void setContas(Set<Conta> contas) {
        this.contas = contas;
    }

    public Set<CoordenadaBancaria> getCoordenadaBancarias() {
        return coordenadaBancarias;
    }

    public Empresa coordenadaBancarias(Set<CoordenadaBancaria> coordenadaBancarias) {
        this.coordenadaBancarias = coordenadaBancarias;
        return this;
    }

    public Empresa addCoordenadaBancaria(CoordenadaBancaria coordenadaBancaria) {
        this.coordenadaBancarias.add(coordenadaBancaria);
        coordenadaBancaria.getEmpresas().add(this);
        return this;
    }

    public Empresa removeCoordenadaBancaria(CoordenadaBancaria coordenadaBancaria) {
        this.coordenadaBancarias.remove(coordenadaBancaria);
        coordenadaBancaria.getEmpresas().remove(this);
        return this;
    }

    public void setCoordenadaBancarias(Set<CoordenadaBancaria> coordenadaBancarias) {
        this.coordenadaBancarias = coordenadaBancarias;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Empresa)) {
            return false;
        }
        return id != null && id.equals(((Empresa) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Empresa{" +
            "id=" + getId() +
            ", tipoSociedade='" + getTipoSociedade() + "'" +
            ", nome='" + getNome() + "'" +
            ", logotipo='" + getLogotipo() + "'" +
            ", logotipoContentType='" + getLogotipoContentType() + "'" +
            ", capitalSocial=" + getCapitalSocial() +
            ", fundacao='" + getFundacao() + "'" +
            ", nif='" + getNif() + "'" +
            ", numeroRegistroComercial='" + getNumeroRegistroComercial() + "'" +
            ", despesaFixa=" + getDespesaFixa() +
            ", despesaVariavel=" + getDespesaVariavel() +
            ", aberturaExercio='" + getAberturaExercio() + "'" +
            ", designacaoComercial='" + getDesignacaoComercial() + "'" +
            ", sede='" + isSede() + "'" +
            "}";
    }
}
