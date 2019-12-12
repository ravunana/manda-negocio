import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  RegraMovimentacaoDebitoComponentsPage,
  RegraMovimentacaoDebitoDeleteDialog,
  RegraMovimentacaoDebitoUpdatePage
} from './regra-movimentacao-debito.page-object';

const expect = chai.expect;

describe('RegraMovimentacaoDebito e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let regraMovimentacaoDebitoComponentsPage: RegraMovimentacaoDebitoComponentsPage;
  let regraMovimentacaoDebitoUpdatePage: RegraMovimentacaoDebitoUpdatePage;
  let regraMovimentacaoDebitoDeleteDialog: RegraMovimentacaoDebitoDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load RegraMovimentacaoDebitos', async () => {
    await navBarPage.goToEntity('regra-movimentacao-debito');
    regraMovimentacaoDebitoComponentsPage = new RegraMovimentacaoDebitoComponentsPage();
    await browser.wait(ec.visibilityOf(regraMovimentacaoDebitoComponentsPage.title), 5000);
    expect(await regraMovimentacaoDebitoComponentsPage.getTitle()).to.eq('mandaApp.regraMovimentacaoDebito.home.title');
  });

  it('should load create RegraMovimentacaoDebito page', async () => {
    await regraMovimentacaoDebitoComponentsPage.clickOnCreateButton();
    regraMovimentacaoDebitoUpdatePage = new RegraMovimentacaoDebitoUpdatePage();
    expect(await regraMovimentacaoDebitoUpdatePage.getPageTitle()).to.eq('mandaApp.regraMovimentacaoDebito.home.createOrEditLabel');
    await regraMovimentacaoDebitoUpdatePage.cancel();
  });

  it('should create and save RegraMovimentacaoDebitos', async () => {
    const nbButtonsBeforeCreate = await regraMovimentacaoDebitoComponentsPage.countDeleteButtons();

    await regraMovimentacaoDebitoComponentsPage.clickOnCreateButton();
    await promise.all([
      regraMovimentacaoDebitoUpdatePage.setDescricaoInput('descricao'),
      regraMovimentacaoDebitoUpdatePage.contaSelectLastOption()
    ]);
    expect(await regraMovimentacaoDebitoUpdatePage.getDescricaoInput()).to.eq(
      'descricao',
      'Expected Descricao value to be equals to descricao'
    );
    await regraMovimentacaoDebitoUpdatePage.save();
    expect(await regraMovimentacaoDebitoUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await regraMovimentacaoDebitoComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last RegraMovimentacaoDebito', async () => {
    const nbButtonsBeforeDelete = await regraMovimentacaoDebitoComponentsPage.countDeleteButtons();
    await regraMovimentacaoDebitoComponentsPage.clickOnLastDeleteButton();

    regraMovimentacaoDebitoDeleteDialog = new RegraMovimentacaoDebitoDeleteDialog();
    expect(await regraMovimentacaoDebitoDeleteDialog.getDialogTitle()).to.eq('mandaApp.regraMovimentacaoDebito.delete.question');
    await regraMovimentacaoDebitoDeleteDialog.clickOnConfirmButton();

    expect(await regraMovimentacaoDebitoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
