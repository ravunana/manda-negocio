import { browser, ExpectedConditions as ec /* , protractor, promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  ContaDebitoComponentsPage,
  /* ContaDebitoDeleteDialog,
   */ ContaDebitoUpdatePage
} from './conta-debito.page-object';

const expect = chai.expect;

describe('ContaDebito e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let contaDebitoComponentsPage: ContaDebitoComponentsPage;
  let contaDebitoUpdatePage: ContaDebitoUpdatePage;
  /* let contaDebitoDeleteDialog: ContaDebitoDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load ContaDebitos', async () => {
    await navBarPage.goToEntity('conta-debito');
    contaDebitoComponentsPage = new ContaDebitoComponentsPage();
    await browser.wait(ec.visibilityOf(contaDebitoComponentsPage.title), 5000);
    expect(await contaDebitoComponentsPage.getTitle()).to.eq('mandaApp.contaDebito.home.title');
  });

  it('should load create ContaDebito page', async () => {
    await contaDebitoComponentsPage.clickOnCreateButton();
    contaDebitoUpdatePage = new ContaDebitoUpdatePage();
    expect(await contaDebitoUpdatePage.getPageTitle()).to.eq('mandaApp.contaDebito.home.createOrEditLabel');
    await contaDebitoUpdatePage.cancel();
  });

  /*  it('should create and save ContaDebitos', async () => {
        const nbButtonsBeforeCreate = await contaDebitoComponentsPage.countDeleteButtons();

        await contaDebitoComponentsPage.clickOnCreateButton();
        await promise.all([
            contaDebitoUpdatePage.setValorInput('5'),
            contaDebitoUpdatePage.setDataInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            contaDebitoUpdatePage.contaDebitarSelectLastOption(),
            contaDebitoUpdatePage.lancamentoDebitoSelectLastOption(),
        ]);
        expect(await contaDebitoUpdatePage.getValorInput()).to.eq('5', 'Expected valor value to be equals to 5');
        expect(await contaDebitoUpdatePage.getDataInput()).to.contain('2001-01-01T02:30', 'Expected data value to be equals to 2000-12-31');
        await contaDebitoUpdatePage.save();
        expect(await contaDebitoUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await contaDebitoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /*  it('should delete last ContaDebito', async () => {
        const nbButtonsBeforeDelete = await contaDebitoComponentsPage.countDeleteButtons();
        await contaDebitoComponentsPage.clickOnLastDeleteButton();

        contaDebitoDeleteDialog = new ContaDebitoDeleteDialog();
        expect(await contaDebitoDeleteDialog.getDialogTitle())
            .to.eq('mandaApp.contaDebito.delete.question');
        await contaDebitoDeleteDialog.clickOnConfirmButton();

        expect(await contaDebitoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
