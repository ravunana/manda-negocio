import { browser, ExpectedConditions as ec /* , protractor, promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  CompraComponentsPage,
  /* CompraDeleteDialog,
   */ CompraUpdatePage
} from './compra.page-object';

const expect = chai.expect;

describe('Compra e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let compraComponentsPage: CompraComponentsPage;
  let compraUpdatePage: CompraUpdatePage;
  /* let compraDeleteDialog: CompraDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Compras', async () => {
    await navBarPage.goToEntity('compra');
    compraComponentsPage = new CompraComponentsPage();
    await browser.wait(ec.visibilityOf(compraComponentsPage.title), 5000);
    expect(await compraComponentsPage.getTitle()).to.eq('mandaApp.compra.home.title');
  });

  it('should load create Compra page', async () => {
    await compraComponentsPage.clickOnCreateButton();
    compraUpdatePage = new CompraUpdatePage();
    expect(await compraUpdatePage.getPageTitle()).to.eq('mandaApp.compra.home.createOrEditLabel');
    await compraUpdatePage.cancel();
  });

  /*  it('should create and save Compras', async () => {
        const nbButtonsBeforeCreate = await compraComponentsPage.countDeleteButtons();

        await compraComponentsPage.clickOnCreateButton();
        await promise.all([
            compraUpdatePage.setNumeroInput('numero'),
            compraUpdatePage.setDataInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            compraUpdatePage.setObservacaoGeralInput('observacaoGeral'),
            compraUpdatePage.setObservacaoInternaInput('observacaoInterna'),
            compraUpdatePage.utilizadorSelectLastOption(),
            compraUpdatePage.tipoDocumentoSelectLastOption(),
            compraUpdatePage.empresaSelectLastOption(),
        ]);
        expect(await compraUpdatePage.getNumeroInput()).to.eq('numero', 'Expected Numero value to be equals to numero');
        expect(await compraUpdatePage.getDataInput()).to.contain('2001-01-01T02:30', 'Expected data value to be equals to 2000-12-31');
        expect(await compraUpdatePage.getObservacaoGeralInput()).to.eq('observacaoGeral', 'Expected ObservacaoGeral value to be equals to observacaoGeral');
        expect(await compraUpdatePage.getObservacaoInternaInput()).to.eq('observacaoInterna', 'Expected ObservacaoInterna value to be equals to observacaoInterna');
        await compraUpdatePage.save();
        expect(await compraUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await compraComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /*  it('should delete last Compra', async () => {
        const nbButtonsBeforeDelete = await compraComponentsPage.countDeleteButtons();
        await compraComponentsPage.clickOnLastDeleteButton();

        compraDeleteDialog = new CompraDeleteDialog();
        expect(await compraDeleteDialog.getDialogTitle())
            .to.eq('mandaApp.compra.delete.question');
        await compraDeleteDialog.clickOnConfirmButton();

        expect(await compraComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
