import { browser, ExpectedConditions as ec /* , promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  ConversaoUnidadeComponentsPage,
  /* ConversaoUnidadeDeleteDialog,
   */ ConversaoUnidadeUpdatePage
} from './conversao-unidade.page-object';

const expect = chai.expect;

describe('ConversaoUnidade e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let conversaoUnidadeComponentsPage: ConversaoUnidadeComponentsPage;
  let conversaoUnidadeUpdatePage: ConversaoUnidadeUpdatePage;
  /* let conversaoUnidadeDeleteDialog: ConversaoUnidadeDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load ConversaoUnidades', async () => {
    await navBarPage.goToEntity('conversao-unidade');
    conversaoUnidadeComponentsPage = new ConversaoUnidadeComponentsPage();
    await browser.wait(ec.visibilityOf(conversaoUnidadeComponentsPage.title), 5000);
    expect(await conversaoUnidadeComponentsPage.getTitle()).to.eq('mandaApp.conversaoUnidade.home.title');
  });

  it('should load create ConversaoUnidade page', async () => {
    await conversaoUnidadeComponentsPage.clickOnCreateButton();
    conversaoUnidadeUpdatePage = new ConversaoUnidadeUpdatePage();
    expect(await conversaoUnidadeUpdatePage.getPageTitle()).to.eq('mandaApp.conversaoUnidade.home.createOrEditLabel');
    await conversaoUnidadeUpdatePage.cancel();
  });

  /*  it('should create and save ConversaoUnidades', async () => {
        const nbButtonsBeforeCreate = await conversaoUnidadeComponentsPage.countDeleteButtons();

        await conversaoUnidadeComponentsPage.clickOnCreateButton();
        await promise.all([
            conversaoUnidadeUpdatePage.setValorEntradaInput('5'),
            conversaoUnidadeUpdatePage.setValorSaidaInput('5'),
            conversaoUnidadeUpdatePage.entradaSelectLastOption(),
            conversaoUnidadeUpdatePage.saidaSelectLastOption(),
            conversaoUnidadeUpdatePage.produtoSelectLastOption(),
        ]);
        expect(await conversaoUnidadeUpdatePage.getValorEntradaInput()).to.eq('5', 'Expected valorEntrada value to be equals to 5');
        expect(await conversaoUnidadeUpdatePage.getValorSaidaInput()).to.eq('5', 'Expected valorSaida value to be equals to 5');
        await conversaoUnidadeUpdatePage.save();
        expect(await conversaoUnidadeUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await conversaoUnidadeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /*  it('should delete last ConversaoUnidade', async () => {
        const nbButtonsBeforeDelete = await conversaoUnidadeComponentsPage.countDeleteButtons();
        await conversaoUnidadeComponentsPage.clickOnLastDeleteButton();

        conversaoUnidadeDeleteDialog = new ConversaoUnidadeDeleteDialog();
        expect(await conversaoUnidadeDeleteDialog.getDialogTitle())
            .to.eq('mandaApp.conversaoUnidade.delete.question');
        await conversaoUnidadeDeleteDialog.clickOnConfirmButton();

        expect(await conversaoUnidadeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
