package com.ravunana.manda.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.BigDecimalFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link com.ravunana.manda.domain.Empresa} entity. This class is used
 * in {@link com.ravunana.manda.web.rest.EmpresaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /empresas?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class EmpresaCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter tipoSociedade;

    private StringFilter nome;

    private BigDecimalFilter capitalSocial;

    private LocalDateFilter fundacao;

    private StringFilter nif;

    private StringFilter numeroRegistroComercial;

    private DoubleFilter despesaFixa;

    private DoubleFilter despesaVariavel;

    private LocalDateFilter aberturaExercio;

    private BooleanFilter sede;

    private LongFilter escrituracaoContabilId;

    private LongFilter empresaId;

    private LongFilter localizacaoEmpresaId;

    private LongFilter contactoEmpresaId;

    private LongFilter licencaSoftwareId;

    private LongFilter lancamentoFinanceiroId;

    private LongFilter produtoId;

    private LongFilter localArmazenamentoId;

    private LongFilter compraId;

    private LongFilter vendaId;

    private LongFilter utilizadorId;

    private LongFilter hierarquiaId;

    private LongFilter contaId;

    private LongFilter coordenadaBancariaId;

    public EmpresaCriteria(){
    }

    public EmpresaCriteria(EmpresaCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.tipoSociedade = other.tipoSociedade == null ? null : other.tipoSociedade.copy();
        this.nome = other.nome == null ? null : other.nome.copy();
        this.capitalSocial = other.capitalSocial == null ? null : other.capitalSocial.copy();
        this.fundacao = other.fundacao == null ? null : other.fundacao.copy();
        this.nif = other.nif == null ? null : other.nif.copy();
        this.numeroRegistroComercial = other.numeroRegistroComercial == null ? null : other.numeroRegistroComercial.copy();
        this.despesaFixa = other.despesaFixa == null ? null : other.despesaFixa.copy();
        this.despesaVariavel = other.despesaVariavel == null ? null : other.despesaVariavel.copy();
        this.aberturaExercio = other.aberturaExercio == null ? null : other.aberturaExercio.copy();
        this.sede = other.sede == null ? null : other.sede.copy();
        this.escrituracaoContabilId = other.escrituracaoContabilId == null ? null : other.escrituracaoContabilId.copy();
        this.empresaId = other.empresaId == null ? null : other.empresaId.copy();
        this.localizacaoEmpresaId = other.localizacaoEmpresaId == null ? null : other.localizacaoEmpresaId.copy();
        this.contactoEmpresaId = other.contactoEmpresaId == null ? null : other.contactoEmpresaId.copy();
        this.licencaSoftwareId = other.licencaSoftwareId == null ? null : other.licencaSoftwareId.copy();
        this.lancamentoFinanceiroId = other.lancamentoFinanceiroId == null ? null : other.lancamentoFinanceiroId.copy();
        this.produtoId = other.produtoId == null ? null : other.produtoId.copy();
        this.localArmazenamentoId = other.localArmazenamentoId == null ? null : other.localArmazenamentoId.copy();
        this.compraId = other.compraId == null ? null : other.compraId.copy();
        this.vendaId = other.vendaId == null ? null : other.vendaId.copy();
        this.utilizadorId = other.utilizadorId == null ? null : other.utilizadorId.copy();
        this.contaId = other.contaId == null ? null : other.contaId.copy();
        this.hierarquiaId = other.hierarquiaId == null ? null : other.hierarquiaId.copy();
        this.contaId = other.contaId == null ? null : other.contaId.copy();
        this.coordenadaBancariaId = other.coordenadaBancariaId == null ? null : other.coordenadaBancariaId.copy();
    }

    @Override
    public EmpresaCriteria copy() {
        return new EmpresaCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getTipoSociedade() {
        return tipoSociedade;
    }

    public void setTipoSociedade(StringFilter tipoSociedade) {
        this.tipoSociedade = tipoSociedade;
    }

    public StringFilter getNome() {
        return nome;
    }

    public void setNome(StringFilter nome) {
        this.nome = nome;
    }

    public BigDecimalFilter getCapitalSocial() {
        return capitalSocial;
    }

    public void setCapitalSocial(BigDecimalFilter capitalSocial) {
        this.capitalSocial = capitalSocial;
    }

    public LocalDateFilter getFundacao() {
        return fundacao;
    }

    public void setFundacao(LocalDateFilter fundacao) {
        this.fundacao = fundacao;
    }

    public StringFilter getNif() {
        return nif;
    }

    public void setNif(StringFilter nif) {
        this.nif = nif;
    }

    public StringFilter getNumeroRegistroComercial() {
        return numeroRegistroComercial;
    }

    public void setNumeroRegistroComercial(StringFilter numeroRegistroComercial) {
        this.numeroRegistroComercial = numeroRegistroComercial;
    }

    public DoubleFilter getDespesaFixa() {
        return despesaFixa;
    }

    public void setDespesaFixa(DoubleFilter despesaFixa) {
        this.despesaFixa = despesaFixa;
    }

    public DoubleFilter getDespesaVariavel() {
        return despesaVariavel;
    }

    public void setDespesaVariavel(DoubleFilter despesaVariavel) {
        this.despesaVariavel = despesaVariavel;
    }

    public LocalDateFilter getAberturaExercio() {
        return aberturaExercio;
    }

    public void setAberturaExercio(LocalDateFilter aberturaExercio) {
        this.aberturaExercio = aberturaExercio;
    }

    public BooleanFilter getSede() {
        return sede;
    }

    public void setSede(BooleanFilter sede) {
        this.sede = sede;
    }

    public LongFilter getEscrituracaoContabilId() {
        return escrituracaoContabilId;
    }

    public void setEscrituracaoContabilId(LongFilter escrituracaoContabilId) {
        this.escrituracaoContabilId = escrituracaoContabilId;
    }

    public LongFilter getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(LongFilter empresaId) {
        this.empresaId = empresaId;
    }

    public LongFilter getLocalizacaoEmpresaId() {
        return localizacaoEmpresaId;
    }

    public void setLocalizacaoEmpresaId(LongFilter localizacaoEmpresaId) {
        this.localizacaoEmpresaId = localizacaoEmpresaId;
    }

    public LongFilter getContactoEmpresaId() {
        return contactoEmpresaId;
    }

    public void setContactoEmpresaId(LongFilter contactoEmpresaId) {
        this.contactoEmpresaId = contactoEmpresaId;
    }

    public LongFilter getLicencaSoftwareId() {
        return licencaSoftwareId;
    }

    public void setLicencaSoftwareId(LongFilter licencaSoftwareId) {
        this.licencaSoftwareId = licencaSoftwareId;
    }

    public LongFilter getLancamentoFinanceiroId() {
        return lancamentoFinanceiroId;
    }

    public void setLancamentoFinanceiroId(LongFilter lancamentoFinanceiroId) {
        this.lancamentoFinanceiroId = lancamentoFinanceiroId;
    }

    public LongFilter getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(LongFilter produtoId) {
        this.produtoId = produtoId;
    }

    public LongFilter getLocalArmazenamentoId() {
        return localArmazenamentoId;
    }

    public void setLocalArmazenamentoId(LongFilter localArmazenamentoId) {
        this.localArmazenamentoId = localArmazenamentoId;
    }

    public LongFilter getCompraId() {
        return compraId;
    }

    public void setCompraId(LongFilter compraId) {
        this.compraId = compraId;
    }

    public LongFilter getVendaId() {
        return vendaId;
    }

    public void setVendaId(LongFilter vendaId) {
        this.vendaId = vendaId;
    }

    public LongFilter getUtilizadorId() {
        return utilizadorId;
    }

    public void setUtilizadorId(LongFilter utilizadorId) {
        this.utilizadorId = utilizadorId;
    }

    public LongFilter getHierarquiaId() {
        return hierarquiaId;
    }

    public void setHierarquiaId(LongFilter hierarquiaId) {
        this.hierarquiaId = hierarquiaId;
    }

    public LongFilter getContaId() {
        return contaId;
    }

    public void setContaId(LongFilter contaId) {
        this.contaId = contaId;
    }

    public LongFilter getCoordenadaBancariaId() {
        return coordenadaBancariaId;
    }

    public void setCoordenadaBancariaId(LongFilter coordenadaBancariaId) {
        this.coordenadaBancariaId = coordenadaBancariaId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final EmpresaCriteria that = (EmpresaCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(tipoSociedade, that.tipoSociedade) &&
            Objects.equals(nome, that.nome) &&
            Objects.equals(capitalSocial, that.capitalSocial) &&
            Objects.equals(fundacao, that.fundacao) &&
            Objects.equals(nif, that.nif) &&
            Objects.equals(numeroRegistroComercial, that.numeroRegistroComercial) &&
            Objects.equals(despesaFixa, that.despesaFixa) &&
            Objects.equals(despesaVariavel, that.despesaVariavel) &&
            Objects.equals(aberturaExercio, that.aberturaExercio) &&
            Objects.equals(sede, that.sede) &&
            Objects.equals(escrituracaoContabilId, that.escrituracaoContabilId) &&
            Objects.equals(empresaId, that.empresaId) &&
            Objects.equals(localizacaoEmpresaId, that.localizacaoEmpresaId) &&
            Objects.equals(contactoEmpresaId, that.contactoEmpresaId) &&
            Objects.equals(licencaSoftwareId, that.licencaSoftwareId) &&
            Objects.equals(lancamentoFinanceiroId, that.lancamentoFinanceiroId) &&
            Objects.equals(produtoId, that.produtoId) &&
            Objects.equals(localArmazenamentoId, that.localArmazenamentoId) &&
            Objects.equals(compraId, that.compraId) &&
            Objects.equals(vendaId, that.vendaId) &&
            Objects.equals(utilizadorId, that.utilizadorId) &&
            Objects.equals(contaId, that.contaId) &&
            Objects.equals(hierarquiaId, that.hierarquiaId) &&
            Objects.equals(contaId, that.contaId) &&
            Objects.equals(coordenadaBancariaId, that.coordenadaBancariaId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        tipoSociedade,
        nome,
        capitalSocial,
        fundacao,
        nif,
        numeroRegistroComercial,
        despesaFixa,
        despesaVariavel,
        aberturaExercio,
        sede,
        escrituracaoContabilId,
        empresaId,
        localizacaoEmpresaId,
        contactoEmpresaId,
        licencaSoftwareId,
        lancamentoFinanceiroId,
        produtoId,
        localArmazenamentoId,
        compraId,
        vendaId,
        utilizadorId,
        contaId,
        hierarquiaId,
        contaId,
        coordenadaBancariaId
        );
    }

    @Override
    public String toString() {
        return "EmpresaCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (tipoSociedade != null ? "tipoSociedade=" + tipoSociedade + ", " : "") +
                (nome != null ? "nome=" + nome + ", " : "") +
                (capitalSocial != null ? "capitalSocial=" + capitalSocial + ", " : "") +
                (fundacao != null ? "fundacao=" + fundacao + ", " : "") +
                (nif != null ? "nif=" + nif + ", " : "") +
                (numeroRegistroComercial != null ? "numeroRegistroComercial=" + numeroRegistroComercial + ", " : "") +
                (despesaFixa != null ? "despesaFixa=" + despesaFixa + ", " : "") +
                (despesaVariavel != null ? "despesaVariavel=" + despesaVariavel + ", " : "") +
                (aberturaExercio != null ? "aberturaExercio=" + aberturaExercio + ", " : "") +
                (sede != null ? "sede=" + sede + ", " : "") +
                (escrituracaoContabilId != null ? "escrituracaoContabilId=" + escrituracaoContabilId + ", " : "") +
                (empresaId != null ? "empresaId=" + empresaId + ", " : "") +
                (localizacaoEmpresaId != null ? "localizacaoEmpresaId=" + localizacaoEmpresaId + ", " : "") +
                (contactoEmpresaId != null ? "contactoEmpresaId=" + contactoEmpresaId + ", " : "") +
                (licencaSoftwareId != null ? "licencaSoftwareId=" + licencaSoftwareId + ", " : "") +
                (lancamentoFinanceiroId != null ? "lancamentoFinanceiroId=" + lancamentoFinanceiroId + ", " : "") +
                (produtoId != null ? "produtoId=" + produtoId + ", " : "") +
                (localArmazenamentoId != null ? "localArmazenamentoId=" + localArmazenamentoId + ", " : "") +
                (compraId != null ? "compraId=" + compraId + ", " : "") +
                (vendaId != null ? "vendaId=" + vendaId + ", " : "") +
                (utilizadorId != null ? "utilizadorId=" + utilizadorId + ", " : "") +
                (contaId != null ? "contaId=" + contaId + ", " : "") +
                (hierarquiaId != null ? "hierarquiaId=" + hierarquiaId + ", " : "") +
                (contaId != null ? "contaId=" + contaId + ", " : "") +
                (coordenadaBancariaId != null ? "coordenadaBancariaId=" + coordenadaBancariaId + ", " : "") +
            "}";
    }

}
