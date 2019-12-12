import { browser, ExpectedConditions as ec /* , protractor, promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  ContaCreditoComponentsPage,
  /* ContaCreditoDeleteDialog,
   */ ContaCreditoUpdatePage
} from './conta-credito.page-object';

const expect = chai.expect;

describe('ContaCredito e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let contaCreditoComponentsPage: ContaCreditoComponentsPage;
  let contaCreditoUpdatePage: ContaCreditoUpdatePage;
  /* let contaCreditoDeleteDialog: ContaCreditoDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load ContaCreditos', async () => {
    await navBarPage.goToEntity('conta-credito');
    contaCreditoComponentsPage = new ContaCreditoComponentsPage();
    await browser.wait(ec.visibilityOf(contaCreditoComponentsPage.title), 5000);
    expect(await contaCreditoComponentsPage.getTitle()).to.eq('mandaApp.contaCredito.home.title');
  });

  it('should load create ContaCredito page', async () => {
    await contaCreditoComponentsPage.clickOnCreateButton();
    contaCreditoUpdatePage = new ContaCreditoUpdatePage();
    expect(await contaCreditoUpdatePage.getPageTitle()).to.eq('mandaApp.contaCredito.home.createOrEditLabel');
    await contaCreditoUpdatePage.cancel();
  });

  /*  it('should create and save ContaCreditos', async () => {
        const nbButtonsBeforeCreate = await contaCreditoComponentsPage.countDeleteButtons();

        await contaCreditoComponentsPage.clickOnCreateButton();
        await promise.all([
            contaCreditoUpdatePage.setValorInput('5'),
            contaCreditoUpdatePage.setDataInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            contaCreditoUpdatePage.contaCreditarSelectLastOption(),
            contaCreditoUpdatePage.lancamentoCreditoSelectLastOption(),
        ]);
        expect(await contaCreditoUpdatePage.getValorInput()).to.eq('5', 'Expected valor value to be equals to 5');
        expect(await contaCreditoUpdatePage.getDataInput()).to.contain('2001-01-01T02:30', 'Expected data value to be equals to 2000-12-31');
        await contaCreditoUpdatePage.save();
        expect(await contaCreditoUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await contaCreditoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /*  it('should delete last ContaCredito', async () => {
        const nbButtonsBeforeDelete = await contaCreditoComponentsPage.countDeleteButtons();
        await contaCreditoComponentsPage.clickOnLastDeleteButton();

        contaCreditoDeleteDialog = new ContaCreditoDeleteDialog();
        expect(await contaCreditoDeleteDialog.getDialogTitle())
            .to.eq('mandaApp.contaCredito.delete.question');
        await contaCreditoDeleteDialog.clickOnConfirmButton();

        expect(await contaCreditoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
