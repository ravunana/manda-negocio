import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { FluxoDocumentoComponentsPage, FluxoDocumentoDeleteDialog, FluxoDocumentoUpdatePage } from './fluxo-documento.page-object';

const expect = chai.expect;

describe('FluxoDocumento e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let fluxoDocumentoComponentsPage: FluxoDocumentoComponentsPage;
  let fluxoDocumentoUpdatePage: FluxoDocumentoUpdatePage;
  let fluxoDocumentoDeleteDialog: FluxoDocumentoDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load FluxoDocumentos', async () => {
    await navBarPage.goToEntity('fluxo-documento');
    fluxoDocumentoComponentsPage = new FluxoDocumentoComponentsPage();
    await browser.wait(ec.visibilityOf(fluxoDocumentoComponentsPage.title), 5000);
    expect(await fluxoDocumentoComponentsPage.getTitle()).to.eq('mandaApp.fluxoDocumento.home.title');
  });

  it('should load create FluxoDocumento page', async () => {
    await fluxoDocumentoComponentsPage.clickOnCreateButton();
    fluxoDocumentoUpdatePage = new FluxoDocumentoUpdatePage();
    expect(await fluxoDocumentoUpdatePage.getPageTitle()).to.eq('mandaApp.fluxoDocumento.home.createOrEditLabel');
    await fluxoDocumentoUpdatePage.cancel();
  });

  it('should create and save FluxoDocumentos', async () => {
    const nbButtonsBeforeCreate = await fluxoDocumentoComponentsPage.countDeleteButtons();

    await fluxoDocumentoComponentsPage.clickOnCreateButton();
    await promise.all([
      fluxoDocumentoUpdatePage.setNomeInput('nome'),
      fluxoDocumentoUpdatePage.setCorInput('cor'),
      fluxoDocumentoUpdatePage.setDescricaoInput('descricao'),
      fluxoDocumentoUpdatePage.entidadeSelectLastOption()
    ]);
    expect(await fluxoDocumentoUpdatePage.getNomeInput()).to.eq('nome', 'Expected Nome value to be equals to nome');
    const selectedAumentaEstoque = fluxoDocumentoUpdatePage.getAumentaEstoqueInput();
    if (await selectedAumentaEstoque.isSelected()) {
      await fluxoDocumentoUpdatePage.getAumentaEstoqueInput().click();
      expect(await fluxoDocumentoUpdatePage.getAumentaEstoqueInput().isSelected(), 'Expected aumentaEstoque not to be selected').to.be
        .false;
    } else {
      await fluxoDocumentoUpdatePage.getAumentaEstoqueInput().click();
      expect(await fluxoDocumentoUpdatePage.getAumentaEstoqueInput().isSelected(), 'Expected aumentaEstoque to be selected').to.be.true;
    }
    const selectedAumentaValorCaixa = fluxoDocumentoUpdatePage.getAumentaValorCaixaInput();
    if (await selectedAumentaValorCaixa.isSelected()) {
      await fluxoDocumentoUpdatePage.getAumentaValorCaixaInput().click();
      expect(await fluxoDocumentoUpdatePage.getAumentaValorCaixaInput().isSelected(), 'Expected aumentaValorCaixa not to be selected').to.be
        .false;
    } else {
      await fluxoDocumentoUpdatePage.getAumentaValorCaixaInput().click();
      expect(await fluxoDocumentoUpdatePage.getAumentaValorCaixaInput().isSelected(), 'Expected aumentaValorCaixa to be selected').to.be
        .true;
    }
    expect(await fluxoDocumentoUpdatePage.getCorInput()).to.eq('cor', 'Expected Cor value to be equals to cor');
    const selectedPadrao = fluxoDocumentoUpdatePage.getPadraoInput();
    if (await selectedPadrao.isSelected()) {
      await fluxoDocumentoUpdatePage.getPadraoInput().click();
      expect(await fluxoDocumentoUpdatePage.getPadraoInput().isSelected(), 'Expected padrao not to be selected').to.be.false;
    } else {
      await fluxoDocumentoUpdatePage.getPadraoInput().click();
      expect(await fluxoDocumentoUpdatePage.getPadraoInput().isSelected(), 'Expected padrao to be selected').to.be.true;
    }
    expect(await fluxoDocumentoUpdatePage.getDescricaoInput()).to.eq('descricao', 'Expected Descricao value to be equals to descricao');
    await fluxoDocumentoUpdatePage.save();
    expect(await fluxoDocumentoUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await fluxoDocumentoComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last FluxoDocumento', async () => {
    const nbButtonsBeforeDelete = await fluxoDocumentoComponentsPage.countDeleteButtons();
    await fluxoDocumentoComponentsPage.clickOnLastDeleteButton();

    fluxoDocumentoDeleteDialog = new FluxoDocumentoDeleteDialog();
    expect(await fluxoDocumentoDeleteDialog.getDialogTitle()).to.eq('mandaApp.fluxoDocumento.delete.question');
    await fluxoDocumentoDeleteDialog.clickOnConfirmButton();

    expect(await fluxoDocumentoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
