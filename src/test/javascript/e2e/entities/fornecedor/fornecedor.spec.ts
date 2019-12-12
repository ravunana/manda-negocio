import { browser, ExpectedConditions as ec /* , promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  FornecedorComponentsPage,
  /* FornecedorDeleteDialog,
   */ FornecedorUpdatePage
} from './fornecedor.page-object';

const expect = chai.expect;

describe('Fornecedor e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let fornecedorComponentsPage: FornecedorComponentsPage;
  let fornecedorUpdatePage: FornecedorUpdatePage;
  /* let fornecedorDeleteDialog: FornecedorDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Fornecedors', async () => {
    await navBarPage.goToEntity('fornecedor');
    fornecedorComponentsPage = new FornecedorComponentsPage();
    await browser.wait(ec.visibilityOf(fornecedorComponentsPage.title), 5000);
    expect(await fornecedorComponentsPage.getTitle()).to.eq('mandaApp.fornecedor.home.title');
  });

  it('should load create Fornecedor page', async () => {
    await fornecedorComponentsPage.clickOnCreateButton();
    fornecedorUpdatePage = new FornecedorUpdatePage();
    expect(await fornecedorUpdatePage.getPageTitle()).to.eq('mandaApp.fornecedor.home.createOrEditLabel');
    await fornecedorUpdatePage.cancel();
  });

  /*  it('should create and save Fornecedors', async () => {
        const nbButtonsBeforeCreate = await fornecedorComponentsPage.countDeleteButtons();

        await fornecedorComponentsPage.clickOnCreateButton();
        await promise.all([
            fornecedorUpdatePage.setQualidadeInput('5'),
            fornecedorUpdatePage.setMetodosPagamentoInput('metodosPagamento'),
            fornecedorUpdatePage.setClassificacaoInput('5'),
            fornecedorUpdatePage.setDescricaoInput('descricao'),
            fornecedorUpdatePage.setNumeroInput('numero'),
            fornecedorUpdatePage.pessoaSelectLastOption(),
            fornecedorUpdatePage.contaSelectLastOption(),
        ]);
        const selectedCertificadoISO9001 = fornecedorUpdatePage.getCertificadoISO9001Input();
        if (await selectedCertificadoISO9001.isSelected()) {
            await fornecedorUpdatePage.getCertificadoISO9001Input().click();
            expect(await fornecedorUpdatePage.getCertificadoISO9001Input().isSelected(), 'Expected certificadoISO9001 not to be selected').to.be.false;
        } else {
            await fornecedorUpdatePage.getCertificadoISO9001Input().click();
            expect(await fornecedorUpdatePage.getCertificadoISO9001Input().isSelected(), 'Expected certificadoISO9001 to be selected').to.be.true;
        }
        const selectedGarantiaDefeitoFabrica = fornecedorUpdatePage.getGarantiaDefeitoFabricaInput();
        if (await selectedGarantiaDefeitoFabrica.isSelected()) {
            await fornecedorUpdatePage.getGarantiaDefeitoFabricaInput().click();
            expect(await fornecedorUpdatePage.getGarantiaDefeitoFabricaInput().isSelected(), 'Expected garantiaDefeitoFabrica not to be selected').to.be.false;
        } else {
            await fornecedorUpdatePage.getGarantiaDefeitoFabricaInput().click();
            expect(await fornecedorUpdatePage.getGarantiaDefeitoFabricaInput().isSelected(), 'Expected garantiaDefeitoFabrica to be selected').to.be.true;
        }
        const selectedTransporte = fornecedorUpdatePage.getTransporteInput();
        if (await selectedTransporte.isSelected()) {
            await fornecedorUpdatePage.getTransporteInput().click();
            expect(await fornecedorUpdatePage.getTransporteInput().isSelected(), 'Expected transporte not to be selected').to.be.false;
        } else {
            await fornecedorUpdatePage.getTransporteInput().click();
            expect(await fornecedorUpdatePage.getTransporteInput().isSelected(), 'Expected transporte to be selected').to.be.true;
        }
        expect(await fornecedorUpdatePage.getQualidadeInput()).to.eq('5', 'Expected qualidade value to be equals to 5');
        const selectedPagamentoPrazo = fornecedorUpdatePage.getPagamentoPrazoInput();
        if (await selectedPagamentoPrazo.isSelected()) {
            await fornecedorUpdatePage.getPagamentoPrazoInput().click();
            expect(await fornecedorUpdatePage.getPagamentoPrazoInput().isSelected(), 'Expected pagamentoPrazo not to be selected').to.be.false;
        } else {
            await fornecedorUpdatePage.getPagamentoPrazoInput().click();
            expect(await fornecedorUpdatePage.getPagamentoPrazoInput().isSelected(), 'Expected pagamentoPrazo to be selected').to.be.true;
        }
        expect(await fornecedorUpdatePage.getMetodosPagamentoInput()).to.eq('metodosPagamento', 'Expected MetodosPagamento value to be equals to metodosPagamento');
        expect(await fornecedorUpdatePage.getClassificacaoInput()).to.eq('5', 'Expected classificacao value to be equals to 5');
        expect(await fornecedorUpdatePage.getDescricaoInput()).to.eq('descricao', 'Expected Descricao value to be equals to descricao');
        const selectedAtivo = fornecedorUpdatePage.getAtivoInput();
        if (await selectedAtivo.isSelected()) {
            await fornecedorUpdatePage.getAtivoInput().click();
            expect(await fornecedorUpdatePage.getAtivoInput().isSelected(), 'Expected ativo not to be selected').to.be.false;
        } else {
            await fornecedorUpdatePage.getAtivoInput().click();
            expect(await fornecedorUpdatePage.getAtivoInput().isSelected(), 'Expected ativo to be selected').to.be.true;
        }
        expect(await fornecedorUpdatePage.getNumeroInput()).to.eq('numero', 'Expected Numero value to be equals to numero');
        await fornecedorUpdatePage.save();
        expect(await fornecedorUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await fornecedorComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /*  it('should delete last Fornecedor', async () => {
        const nbButtonsBeforeDelete = await fornecedorComponentsPage.countDeleteButtons();
        await fornecedorComponentsPage.clickOnLastDeleteButton();

        fornecedorDeleteDialog = new FornecedorDeleteDialog();
        expect(await fornecedorDeleteDialog.getDialogTitle())
            .to.eq('mandaApp.fornecedor.delete.question');
        await fornecedorDeleteDialog.clickOnConfirmButton();

        expect(await fornecedorComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
