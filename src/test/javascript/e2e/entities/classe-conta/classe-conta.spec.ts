import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ClasseContaComponentsPage, ClasseContaDeleteDialog, ClasseContaUpdatePage } from './classe-conta.page-object';

const expect = chai.expect;

describe('ClasseConta e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let classeContaComponentsPage: ClasseContaComponentsPage;
  let classeContaUpdatePage: ClasseContaUpdatePage;
  let classeContaDeleteDialog: ClasseContaDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load ClasseContas', async () => {
    await navBarPage.goToEntity('classe-conta');
    classeContaComponentsPage = new ClasseContaComponentsPage();
    await browser.wait(ec.visibilityOf(classeContaComponentsPage.title), 5000);
    expect(await classeContaComponentsPage.getTitle()).to.eq('mandaApp.classeConta.home.title');
  });

  it('should load create ClasseConta page', async () => {
    await classeContaComponentsPage.clickOnCreateButton();
    classeContaUpdatePage = new ClasseContaUpdatePage();
    expect(await classeContaUpdatePage.getPageTitle()).to.eq('mandaApp.classeConta.home.createOrEditLabel');
    await classeContaUpdatePage.cancel();
  });

  it('should create and save ClasseContas', async () => {
    const nbButtonsBeforeCreate = await classeContaComponentsPage.countDeleteButtons();

    await classeContaComponentsPage.clickOnCreateButton();
    await promise.all([classeContaUpdatePage.setDescricaoInput('descricao'), classeContaUpdatePage.setCodigoInput('codigo')]);
    expect(await classeContaUpdatePage.getDescricaoInput()).to.eq('descricao', 'Expected Descricao value to be equals to descricao');
    expect(await classeContaUpdatePage.getCodigoInput()).to.eq('codigo', 'Expected Codigo value to be equals to codigo');
    await classeContaUpdatePage.save();
    expect(await classeContaUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await classeContaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last ClasseConta', async () => {
    const nbButtonsBeforeDelete = await classeContaComponentsPage.countDeleteButtons();
    await classeContaComponentsPage.clickOnLastDeleteButton();

    classeContaDeleteDialog = new ClasseContaDeleteDialog();
    expect(await classeContaDeleteDialog.getDialogTitle()).to.eq('mandaApp.classeConta.delete.question');
    await classeContaDeleteDialog.clickOnConfirmButton();

    expect(await classeContaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
