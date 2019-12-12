import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  LocalArmazenamentoComponentsPage,
  LocalArmazenamentoDeleteDialog,
  LocalArmazenamentoUpdatePage
} from './local-armazenamento.page-object';

const expect = chai.expect;

describe('LocalArmazenamento e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let localArmazenamentoComponentsPage: LocalArmazenamentoComponentsPage;
  let localArmazenamentoUpdatePage: LocalArmazenamentoUpdatePage;
  let localArmazenamentoDeleteDialog: LocalArmazenamentoDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load LocalArmazenamentos', async () => {
    await navBarPage.goToEntity('local-armazenamento');
    localArmazenamentoComponentsPage = new LocalArmazenamentoComponentsPage();
    await browser.wait(ec.visibilityOf(localArmazenamentoComponentsPage.title), 5000);
    expect(await localArmazenamentoComponentsPage.getTitle()).to.eq('mandaApp.localArmazenamento.home.title');
  });

  it('should load create LocalArmazenamento page', async () => {
    await localArmazenamentoComponentsPage.clickOnCreateButton();
    localArmazenamentoUpdatePage = new LocalArmazenamentoUpdatePage();
    expect(await localArmazenamentoUpdatePage.getPageTitle()).to.eq('mandaApp.localArmazenamento.home.createOrEditLabel');
    await localArmazenamentoUpdatePage.cancel();
  });

  it('should create and save LocalArmazenamentos', async () => {
    const nbButtonsBeforeCreate = await localArmazenamentoComponentsPage.countDeleteButtons();

    await localArmazenamentoComponentsPage.clickOnCreateButton();
    await promise.all([
      localArmazenamentoUpdatePage.setNomeInput('nome'),
      localArmazenamentoUpdatePage.setDescricaoInput('descricao'),
      localArmazenamentoUpdatePage.hierarquiaSelectLastOption(),
      localArmazenamentoUpdatePage.empresaSelectLastOption()
    ]);
    expect(await localArmazenamentoUpdatePage.getNomeInput()).to.eq('nome', 'Expected Nome value to be equals to nome');
    expect(await localArmazenamentoUpdatePage.getDescricaoInput()).to.eq('descricao', 'Expected Descricao value to be equals to descricao');
    await localArmazenamentoUpdatePage.save();
    expect(await localArmazenamentoUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await localArmazenamentoComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last LocalArmazenamento', async () => {
    const nbButtonsBeforeDelete = await localArmazenamentoComponentsPage.countDeleteButtons();
    await localArmazenamentoComponentsPage.clickOnLastDeleteButton();

    localArmazenamentoDeleteDialog = new LocalArmazenamentoDeleteDialog();
    expect(await localArmazenamentoDeleteDialog.getDialogTitle()).to.eq('mandaApp.localArmazenamento.delete.question');
    await localArmazenamentoDeleteDialog.clickOnConfirmButton();

    expect(await localArmazenamentoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
