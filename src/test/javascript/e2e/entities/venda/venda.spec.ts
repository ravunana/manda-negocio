import { browser, ExpectedConditions as ec /* , protractor, promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  VendaComponentsPage,
  /* VendaDeleteDialog,
   */ VendaUpdatePage
} from './venda.page-object';

const expect = chai.expect;

describe('Venda e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let vendaComponentsPage: VendaComponentsPage;
  let vendaUpdatePage: VendaUpdatePage;
  /* let vendaDeleteDialog: VendaDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Vendas', async () => {
    await navBarPage.goToEntity('venda');
    vendaComponentsPage = new VendaComponentsPage();
    await browser.wait(ec.visibilityOf(vendaComponentsPage.title), 5000);
    expect(await vendaComponentsPage.getTitle()).to.eq('mandaApp.venda.home.title');
  });

  it('should load create Venda page', async () => {
    await vendaComponentsPage.clickOnCreateButton();
    vendaUpdatePage = new VendaUpdatePage();
    expect(await vendaUpdatePage.getPageTitle()).to.eq('mandaApp.venda.home.createOrEditLabel');
    await vendaUpdatePage.cancel();
  });

  /*  it('should create and save Vendas', async () => {
        const nbButtonsBeforeCreate = await vendaComponentsPage.countDeleteButtons();

        await vendaComponentsPage.clickOnCreateButton();
        await promise.all([
            vendaUpdatePage.setNumeroInput('numero'),
            vendaUpdatePage.setDataInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            vendaUpdatePage.setObservacaoGeralInput('observacaoGeral'),
            vendaUpdatePage.setObservacaoInternaInput('observacaoInterna'),
            vendaUpdatePage.vendedorSelectLastOption(),
            vendaUpdatePage.clienteSelectLastOption(),
            vendaUpdatePage.tipoDocumentoSelectLastOption(),
            vendaUpdatePage.empresaSelectLastOption(),
        ]);
        expect(await vendaUpdatePage.getNumeroInput()).to.eq('numero', 'Expected Numero value to be equals to numero');
        expect(await vendaUpdatePage.getDataInput()).to.contain('2001-01-01T02:30', 'Expected data value to be equals to 2000-12-31');
        expect(await vendaUpdatePage.getObservacaoGeralInput()).to.eq('observacaoGeral', 'Expected ObservacaoGeral value to be equals to observacaoGeral');
        expect(await vendaUpdatePage.getObservacaoInternaInput()).to.eq('observacaoInterna', 'Expected ObservacaoInterna value to be equals to observacaoInterna');
        await vendaUpdatePage.save();
        expect(await vendaUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await vendaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /*  it('should delete last Venda', async () => {
        const nbButtonsBeforeDelete = await vendaComponentsPage.countDeleteButtons();
        await vendaComponentsPage.clickOnLastDeleteButton();

        vendaDeleteDialog = new VendaDeleteDialog();
        expect(await vendaDeleteDialog.getDialogTitle())
            .to.eq('mandaApp.venda.delete.question');
        await vendaDeleteDialog.clickOnConfirmButton();

        expect(await vendaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
