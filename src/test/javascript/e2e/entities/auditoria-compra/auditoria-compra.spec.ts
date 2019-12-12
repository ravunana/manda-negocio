import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { AuditoriaCompraComponentsPage, AuditoriaCompraDeleteDialog, AuditoriaCompraUpdatePage } from './auditoria-compra.page-object';

const expect = chai.expect;

describe('AuditoriaCompra e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let auditoriaCompraComponentsPage: AuditoriaCompraComponentsPage;
  let auditoriaCompraUpdatePage: AuditoriaCompraUpdatePage;
  let auditoriaCompraDeleteDialog: AuditoriaCompraDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load AuditoriaCompras', async () => {
    await navBarPage.goToEntity('auditoria-compra');
    auditoriaCompraComponentsPage = new AuditoriaCompraComponentsPage();
    await browser.wait(ec.visibilityOf(auditoriaCompraComponentsPage.title), 5000);
    expect(await auditoriaCompraComponentsPage.getTitle()).to.eq('mandaApp.auditoriaCompra.home.title');
  });

  it('should load create AuditoriaCompra page', async () => {
    await auditoriaCompraComponentsPage.clickOnCreateButton();
    auditoriaCompraUpdatePage = new AuditoriaCompraUpdatePage();
    expect(await auditoriaCompraUpdatePage.getPageTitle()).to.eq('mandaApp.auditoriaCompra.home.createOrEditLabel');
    await auditoriaCompraUpdatePage.cancel();
  });

  it('should create and save AuditoriaCompras', async () => {
    const nbButtonsBeforeCreate = await auditoriaCompraComponentsPage.countDeleteButtons();

    await auditoriaCompraComponentsPage.clickOnCreateButton();
    await promise.all([
      auditoriaCompraUpdatePage.setEstadoInput('estado'),
      auditoriaCompraUpdatePage.setDataInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      auditoriaCompraUpdatePage.setMotivoAlteracaoDocumentoInput('motivoAlteracaoDocumento'),
      auditoriaCompraUpdatePage.setOrigemDocumentoInput('origemDocumento'),
      auditoriaCompraUpdatePage.setHashInput('hash'),
      auditoriaCompraUpdatePage.setHashControlInput('hashControl'),
      auditoriaCompraUpdatePage.compraSelectLastOption(),
      auditoriaCompraUpdatePage.utilizadorSelectLastOption()
    ]);
    expect(await auditoriaCompraUpdatePage.getEstadoInput()).to.eq('estado', 'Expected Estado value to be equals to estado');
    expect(await auditoriaCompraUpdatePage.getDataInput()).to.contain('2001-01-01T02:30', 'Expected data value to be equals to 2000-12-31');
    expect(await auditoriaCompraUpdatePage.getMotivoAlteracaoDocumentoInput()).to.eq(
      'motivoAlteracaoDocumento',
      'Expected MotivoAlteracaoDocumento value to be equals to motivoAlteracaoDocumento'
    );
    expect(await auditoriaCompraUpdatePage.getOrigemDocumentoInput()).to.eq(
      'origemDocumento',
      'Expected OrigemDocumento value to be equals to origemDocumento'
    );
    expect(await auditoriaCompraUpdatePage.getHashInput()).to.eq('hash', 'Expected Hash value to be equals to hash');
    expect(await auditoriaCompraUpdatePage.getHashControlInput()).to.eq(
      'hashControl',
      'Expected HashControl value to be equals to hashControl'
    );
    await auditoriaCompraUpdatePage.save();
    expect(await auditoriaCompraUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await auditoriaCompraComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last AuditoriaCompra', async () => {
    const nbButtonsBeforeDelete = await auditoriaCompraComponentsPage.countDeleteButtons();
    await auditoriaCompraComponentsPage.clickOnLastDeleteButton();

    auditoriaCompraDeleteDialog = new AuditoriaCompraDeleteDialog();
    expect(await auditoriaCompraDeleteDialog.getDialogTitle()).to.eq('mandaApp.auditoriaCompra.delete.question');
    await auditoriaCompraDeleteDialog.clickOnConfirmButton();

    expect(await auditoriaCompraComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
