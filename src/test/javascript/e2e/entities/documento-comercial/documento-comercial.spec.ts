import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  DocumentoComercialComponentsPage,
  DocumentoComercialDeleteDialog,
  DocumentoComercialUpdatePage
} from './documento-comercial.page-object';

const expect = chai.expect;

describe('DocumentoComercial e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let documentoComercialComponentsPage: DocumentoComercialComponentsPage;
  let documentoComercialUpdatePage: DocumentoComercialUpdatePage;
  let documentoComercialDeleteDialog: DocumentoComercialDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load DocumentoComercials', async () => {
    await navBarPage.goToEntity('documento-comercial');
    documentoComercialComponentsPage = new DocumentoComercialComponentsPage();
    await browser.wait(ec.visibilityOf(documentoComercialComponentsPage.title), 5000);
    expect(await documentoComercialComponentsPage.getTitle()).to.eq('mandaApp.documentoComercial.home.title');
  });

  it('should load create DocumentoComercial page', async () => {
    await documentoComercialComponentsPage.clickOnCreateButton();
    documentoComercialUpdatePage = new DocumentoComercialUpdatePage();
    expect(await documentoComercialUpdatePage.getPageTitle()).to.eq('mandaApp.documentoComercial.home.createOrEditLabel');
    await documentoComercialUpdatePage.cancel();
  });

  it('should create and save DocumentoComercials', async () => {
    const nbButtonsBeforeCreate = await documentoComercialComponentsPage.countDeleteButtons();

    await documentoComercialComponentsPage.clickOnCreateButton();
    await promise.all([
      documentoComercialUpdatePage.setNomeInput('nome'),
      documentoComercialUpdatePage.setSerieInput('serie'),
      documentoComercialUpdatePage.setCorInput('cor'),
      documentoComercialUpdatePage.setDescricaoInput('descricao'),
      documentoComercialUpdatePage.entidadeSelectLastOption()
    ]);
    expect(await documentoComercialUpdatePage.getNomeInput()).to.eq('nome', 'Expected Nome value to be equals to nome');
    expect(await documentoComercialUpdatePage.getSerieInput()).to.eq('serie', 'Expected Serie value to be equals to serie');
    const selectedPadrao = documentoComercialUpdatePage.getPadraoInput();
    if (await selectedPadrao.isSelected()) {
      await documentoComercialUpdatePage.getPadraoInput().click();
      expect(await documentoComercialUpdatePage.getPadraoInput().isSelected(), 'Expected padrao not to be selected').to.be.false;
    } else {
      await documentoComercialUpdatePage.getPadraoInput().click();
      expect(await documentoComercialUpdatePage.getPadraoInput().isSelected(), 'Expected padrao to be selected').to.be.true;
    }
    const selectedMovimentaEstoque = documentoComercialUpdatePage.getMovimentaEstoqueInput();
    if (await selectedMovimentaEstoque.isSelected()) {
      await documentoComercialUpdatePage.getMovimentaEstoqueInput().click();
      expect(await documentoComercialUpdatePage.getMovimentaEstoqueInput().isSelected(), 'Expected movimentaEstoque not to be selected').to
        .be.false;
    } else {
      await documentoComercialUpdatePage.getMovimentaEstoqueInput().click();
      expect(await documentoComercialUpdatePage.getMovimentaEstoqueInput().isSelected(), 'Expected movimentaEstoque to be selected').to.be
        .true;
    }
    const selectedMovimentaCaixa = documentoComercialUpdatePage.getMovimentaCaixaInput();
    if (await selectedMovimentaCaixa.isSelected()) {
      await documentoComercialUpdatePage.getMovimentaCaixaInput().click();
      expect(await documentoComercialUpdatePage.getMovimentaCaixaInput().isSelected(), 'Expected movimentaCaixa not to be selected').to.be
        .false;
    } else {
      await documentoComercialUpdatePage.getMovimentaCaixaInput().click();
      expect(await documentoComercialUpdatePage.getMovimentaCaixaInput().isSelected(), 'Expected movimentaCaixa to be selected').to.be.true;
    }
    const selectedMovimentaContabilidade = documentoComercialUpdatePage.getMovimentaContabilidadeInput();
    if (await selectedMovimentaContabilidade.isSelected()) {
      await documentoComercialUpdatePage.getMovimentaContabilidadeInput().click();
      expect(
        await documentoComercialUpdatePage.getMovimentaContabilidadeInput().isSelected(),
        'Expected movimentaContabilidade not to be selected'
      ).to.be.false;
    } else {
      await documentoComercialUpdatePage.getMovimentaContabilidadeInput().click();
      expect(
        await documentoComercialUpdatePage.getMovimentaContabilidadeInput().isSelected(),
        'Expected movimentaContabilidade to be selected'
      ).to.be.true;
    }
    expect(await documentoComercialUpdatePage.getCorInput()).to.eq('cor', 'Expected Cor value to be equals to cor');
    expect(await documentoComercialUpdatePage.getDescricaoInput()).to.eq('descricao', 'Expected Descricao value to be equals to descricao');
    const selectedMostraPontoVenda = documentoComercialUpdatePage.getMostraPontoVendaInput();
    if (await selectedMostraPontoVenda.isSelected()) {
      await documentoComercialUpdatePage.getMostraPontoVendaInput().click();
      expect(await documentoComercialUpdatePage.getMostraPontoVendaInput().isSelected(), 'Expected mostraPontoVenda not to be selected').to
        .be.false;
    } else {
      await documentoComercialUpdatePage.getMostraPontoVendaInput().click();
      expect(await documentoComercialUpdatePage.getMostraPontoVendaInput().isSelected(), 'Expected mostraPontoVenda to be selected').to.be
        .true;
    }
    await documentoComercialUpdatePage.save();
    expect(await documentoComercialUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await documentoComercialComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last DocumentoComercial', async () => {
    const nbButtonsBeforeDelete = await documentoComercialComponentsPage.countDeleteButtons();
    await documentoComercialComponentsPage.clickOnLastDeleteButton();

    documentoComercialDeleteDialog = new DocumentoComercialDeleteDialog();
    expect(await documentoComercialDeleteDialog.getDialogTitle()).to.eq('mandaApp.documentoComercial.delete.question');
    await documentoComercialDeleteDialog.clickOnConfirmButton();

    expect(await documentoComercialComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
