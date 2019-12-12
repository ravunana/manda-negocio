import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { SerieDocumentoComponentsPage, SerieDocumentoDeleteDialog, SerieDocumentoUpdatePage } from './serie-documento.page-object';

const expect = chai.expect;

describe('SerieDocumento e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let serieDocumentoComponentsPage: SerieDocumentoComponentsPage;
  let serieDocumentoUpdatePage: SerieDocumentoUpdatePage;
  let serieDocumentoDeleteDialog: SerieDocumentoDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load SerieDocumentos', async () => {
    await navBarPage.goToEntity('serie-documento');
    serieDocumentoComponentsPage = new SerieDocumentoComponentsPage();
    await browser.wait(ec.visibilityOf(serieDocumentoComponentsPage.title), 5000);
    expect(await serieDocumentoComponentsPage.getTitle()).to.eq('mandaApp.serieDocumento.home.title');
  });

  it('should load create SerieDocumento page', async () => {
    await serieDocumentoComponentsPage.clickOnCreateButton();
    serieDocumentoUpdatePage = new SerieDocumentoUpdatePage();
    expect(await serieDocumentoUpdatePage.getPageTitle()).to.eq('mandaApp.serieDocumento.home.createOrEditLabel');
    await serieDocumentoUpdatePage.cancel();
  });

  it('should create and save SerieDocumentos', async () => {
    const nbButtonsBeforeCreate = await serieDocumentoComponentsPage.countDeleteButtons();

    await serieDocumentoComponentsPage.clickOnCreateButton();
    await promise.all([
      serieDocumentoUpdatePage.setSerieInput('serie'),
      serieDocumentoUpdatePage.setCodigoLancamentoFinanceiroInput('5'),
      serieDocumentoUpdatePage.setCodigoEscrituracaoContabilInput('5'),
      serieDocumentoUpdatePage.setCodigoVendaInput('5'),
      serieDocumentoUpdatePage.setCodigoCompraInput('5'),
      serieDocumentoUpdatePage.setCodigoClienteInput('5'),
      serieDocumentoUpdatePage.setCodigoFornecedorInput('5'),
      serieDocumentoUpdatePage.setCodigoDevolucaoVendaInput('5'),
      serieDocumentoUpdatePage.setCodigoDevolucaoCompraInput('5'),
      serieDocumentoUpdatePage.setCodigoProdutoInput('5'),
      serieDocumentoUpdatePage.setDataInput('2000-12-31'),
      serieDocumentoUpdatePage.empresaSelectLastOption()
    ]);
    expect(await serieDocumentoUpdatePage.getSerieInput()).to.eq('serie', 'Expected Serie value to be equals to serie');
    expect(await serieDocumentoUpdatePage.getCodigoLancamentoFinanceiroInput()).to.eq(
      '5',
      'Expected codigoLancamentoFinanceiro value to be equals to 5'
    );
    expect(await serieDocumentoUpdatePage.getCodigoEscrituracaoContabilInput()).to.eq(
      '5',
      'Expected codigoEscrituracaoContabil value to be equals to 5'
    );
    expect(await serieDocumentoUpdatePage.getCodigoVendaInput()).to.eq('5', 'Expected codigoVenda value to be equals to 5');
    expect(await serieDocumentoUpdatePage.getCodigoCompraInput()).to.eq('5', 'Expected codigoCompra value to be equals to 5');
    expect(await serieDocumentoUpdatePage.getCodigoClienteInput()).to.eq('5', 'Expected codigoCliente value to be equals to 5');
    expect(await serieDocumentoUpdatePage.getCodigoFornecedorInput()).to.eq('5', 'Expected codigoFornecedor value to be equals to 5');
    expect(await serieDocumentoUpdatePage.getCodigoDevolucaoVendaInput()).to.eq(
      '5',
      'Expected codigoDevolucaoVenda value to be equals to 5'
    );
    expect(await serieDocumentoUpdatePage.getCodigoDevolucaoCompraInput()).to.eq(
      '5',
      'Expected codigoDevolucaoCompra value to be equals to 5'
    );
    expect(await serieDocumentoUpdatePage.getCodigoProdutoInput()).to.eq('5', 'Expected codigoProduto value to be equals to 5');
    expect(await serieDocumentoUpdatePage.getDataInput()).to.eq('2000-12-31', 'Expected data value to be equals to 2000-12-31');
    await serieDocumentoUpdatePage.save();
    expect(await serieDocumentoUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await serieDocumentoComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last SerieDocumento', async () => {
    const nbButtonsBeforeDelete = await serieDocumentoComponentsPage.countDeleteButtons();
    await serieDocumentoComponentsPage.clickOnLastDeleteButton();

    serieDocumentoDeleteDialog = new SerieDocumentoDeleteDialog();
    expect(await serieDocumentoDeleteDialog.getDialogTitle()).to.eq('mandaApp.serieDocumento.delete.question');
    await serieDocumentoDeleteDialog.clickOnConfirmButton();

    expect(await serieDocumentoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
