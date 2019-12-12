import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { FamiliaProdutoComponentsPage, FamiliaProdutoDeleteDialog, FamiliaProdutoUpdatePage } from './familia-produto.page-object';

const expect = chai.expect;

describe('FamiliaProduto e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let familiaProdutoComponentsPage: FamiliaProdutoComponentsPage;
  let familiaProdutoUpdatePage: FamiliaProdutoUpdatePage;
  let familiaProdutoDeleteDialog: FamiliaProdutoDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load FamiliaProdutos', async () => {
    await navBarPage.goToEntity('familia-produto');
    familiaProdutoComponentsPage = new FamiliaProdutoComponentsPage();
    await browser.wait(ec.visibilityOf(familiaProdutoComponentsPage.title), 5000);
    expect(await familiaProdutoComponentsPage.getTitle()).to.eq('mandaApp.familiaProduto.home.title');
  });

  it('should load create FamiliaProduto page', async () => {
    await familiaProdutoComponentsPage.clickOnCreateButton();
    familiaProdutoUpdatePage = new FamiliaProdutoUpdatePage();
    expect(await familiaProdutoUpdatePage.getPageTitle()).to.eq('mandaApp.familiaProduto.home.createOrEditLabel');
    await familiaProdutoUpdatePage.cancel();
  });

  it('should create and save FamiliaProdutos', async () => {
    const nbButtonsBeforeCreate = await familiaProdutoComponentsPage.countDeleteButtons();

    await familiaProdutoComponentsPage.clickOnCreateButton();
    await promise.all([
      familiaProdutoUpdatePage.setNomeInput('nome'),
      familiaProdutoUpdatePage.setDescricaoInput('descricao'),
      familiaProdutoUpdatePage.contaSelectLastOption(),
      familiaProdutoUpdatePage.hierarquiaSelectLastOption()
    ]);
    expect(await familiaProdutoUpdatePage.getNomeInput()).to.eq('nome', 'Expected Nome value to be equals to nome');
    expect(await familiaProdutoUpdatePage.getDescricaoInput()).to.eq('descricao', 'Expected Descricao value to be equals to descricao');
    await familiaProdutoUpdatePage.save();
    expect(await familiaProdutoUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await familiaProdutoComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last FamiliaProduto', async () => {
    const nbButtonsBeforeDelete = await familiaProdutoComponentsPage.countDeleteButtons();
    await familiaProdutoComponentsPage.clickOnLastDeleteButton();

    familiaProdutoDeleteDialog = new FamiliaProdutoDeleteDialog();
    expect(await familiaProdutoDeleteDialog.getDialogTitle()).to.eq('mandaApp.familiaProduto.delete.question');
    await familiaProdutoDeleteDialog.clickOnConfirmButton();

    expect(await familiaProdutoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
