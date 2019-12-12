import { browser, ExpectedConditions as ec /* , protractor, promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  AuditoriaVendaComponentsPage,
  /* AuditoriaVendaDeleteDialog,
   */ AuditoriaVendaUpdatePage
} from './auditoria-venda.page-object';

const expect = chai.expect;

describe('AuditoriaVenda e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let auditoriaVendaComponentsPage: AuditoriaVendaComponentsPage;
  let auditoriaVendaUpdatePage: AuditoriaVendaUpdatePage;
  /* let auditoriaVendaDeleteDialog: AuditoriaVendaDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load AuditoriaVendas', async () => {
    await navBarPage.goToEntity('auditoria-venda');
    auditoriaVendaComponentsPage = new AuditoriaVendaComponentsPage();
    await browser.wait(ec.visibilityOf(auditoriaVendaComponentsPage.title), 5000);
    expect(await auditoriaVendaComponentsPage.getTitle()).to.eq('mandaApp.auditoriaVenda.home.title');
  });

  it('should load create AuditoriaVenda page', async () => {
    await auditoriaVendaComponentsPage.clickOnCreateButton();
    auditoriaVendaUpdatePage = new AuditoriaVendaUpdatePage();
    expect(await auditoriaVendaUpdatePage.getPageTitle()).to.eq('mandaApp.auditoriaVenda.home.createOrEditLabel');
    await auditoriaVendaUpdatePage.cancel();
  });

  /*  it('should create and save AuditoriaVendas', async () => {
        const nbButtonsBeforeCreate = await auditoriaVendaComponentsPage.countDeleteButtons();

        await auditoriaVendaComponentsPage.clickOnCreateButton();
        await promise.all([
            auditoriaVendaUpdatePage.setEstadoInput('estado'),
            auditoriaVendaUpdatePage.setDataInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            auditoriaVendaUpdatePage.setMotivoAlteracaoDocumentoInput('motivoAlteracaoDocumento'),
            auditoriaVendaUpdatePage.setOrigemDocumentoInput('origemDocumento'),
            auditoriaVendaUpdatePage.setHashInput('hash'),
            auditoriaVendaUpdatePage.setHashControlInput('hashControl'),
            auditoriaVendaUpdatePage.vendaSelectLastOption(),
            auditoriaVendaUpdatePage.utilizadorSelectLastOption(),
        ]);
        expect(await auditoriaVendaUpdatePage.getEstadoInput()).to.eq('estado', 'Expected Estado value to be equals to estado');
        expect(await auditoriaVendaUpdatePage.getDataInput()).to.contain('2001-01-01T02:30', 'Expected data value to be equals to 2000-12-31');
        expect(await auditoriaVendaUpdatePage.getMotivoAlteracaoDocumentoInput()).to.eq('motivoAlteracaoDocumento', 'Expected MotivoAlteracaoDocumento value to be equals to motivoAlteracaoDocumento');
        expect(await auditoriaVendaUpdatePage.getOrigemDocumentoInput()).to.eq('origemDocumento', 'Expected OrigemDocumento value to be equals to origemDocumento');
        expect(await auditoriaVendaUpdatePage.getHashInput()).to.eq('hash', 'Expected Hash value to be equals to hash');
        expect(await auditoriaVendaUpdatePage.getHashControlInput()).to.eq('hashControl', 'Expected HashControl value to be equals to hashControl');
        await auditoriaVendaUpdatePage.save();
        expect(await auditoriaVendaUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await auditoriaVendaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /*  it('should delete last AuditoriaVenda', async () => {
        const nbButtonsBeforeDelete = await auditoriaVendaComponentsPage.countDeleteButtons();
        await auditoriaVendaComponentsPage.clickOnLastDeleteButton();

        auditoriaVendaDeleteDialog = new AuditoriaVendaDeleteDialog();
        expect(await auditoriaVendaDeleteDialog.getDialogTitle())
            .to.eq('mandaApp.auditoriaVenda.delete.question');
        await auditoriaVendaDeleteDialog.clickOnConfirmButton();

        expect(await auditoriaVendaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
