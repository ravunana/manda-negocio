import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  RegraMovimentacaoCreditoComponentsPage,
  RegraMovimentacaoCreditoDeleteDialog,
  RegraMovimentacaoCreditoUpdatePage
} from './regra-movimentacao-credito.page-object';

const expect = chai.expect;

describe('RegraMovimentacaoCredito e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let regraMovimentacaoCreditoComponentsPage: RegraMovimentacaoCreditoComponentsPage;
  let regraMovimentacaoCreditoUpdatePage: RegraMovimentacaoCreditoUpdatePage;
  let regraMovimentacaoCreditoDeleteDialog: RegraMovimentacaoCreditoDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load RegraMovimentacaoCreditos', async () => {
    await navBarPage.goToEntity('regra-movimentacao-credito');
    regraMovimentacaoCreditoComponentsPage = new RegraMovimentacaoCreditoComponentsPage();
    await browser.wait(ec.visibilityOf(regraMovimentacaoCreditoComponentsPage.title), 5000);
    expect(await regraMovimentacaoCreditoComponentsPage.getTitle()).to.eq('mandaApp.regraMovimentacaoCredito.home.title');
  });

  it('should load create RegraMovimentacaoCredito page', async () => {
    await regraMovimentacaoCreditoComponentsPage.clickOnCreateButton();
    regraMovimentacaoCreditoUpdatePage = new RegraMovimentacaoCreditoUpdatePage();
    expect(await regraMovimentacaoCreditoUpdatePage.getPageTitle()).to.eq('mandaApp.regraMovimentacaoCredito.home.createOrEditLabel');
    await regraMovimentacaoCreditoUpdatePage.cancel();
  });

  it('should create and save RegraMovimentacaoCreditos', async () => {
    const nbButtonsBeforeCreate = await regraMovimentacaoCreditoComponentsPage.countDeleteButtons();

    await regraMovimentacaoCreditoComponentsPage.clickOnCreateButton();
    await promise.all([
      regraMovimentacaoCreditoUpdatePage.setDescricaoInput('descricao'),
      regraMovimentacaoCreditoUpdatePage.contaSelectLastOption()
    ]);
    expect(await regraMovimentacaoCreditoUpdatePage.getDescricaoInput()).to.eq(
      'descricao',
      'Expected Descricao value to be equals to descricao'
    );
    await regraMovimentacaoCreditoUpdatePage.save();
    expect(await regraMovimentacaoCreditoUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await regraMovimentacaoCreditoComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last RegraMovimentacaoCredito', async () => {
    const nbButtonsBeforeDelete = await regraMovimentacaoCreditoComponentsPage.countDeleteButtons();
    await regraMovimentacaoCreditoComponentsPage.clickOnLastDeleteButton();

    regraMovimentacaoCreditoDeleteDialog = new RegraMovimentacaoCreditoDeleteDialog();
    expect(await regraMovimentacaoCreditoDeleteDialog.getDialogTitle()).to.eq('mandaApp.regraMovimentacaoCredito.delete.question');
    await regraMovimentacaoCreditoDeleteDialog.clickOnConfirmButton();

    expect(await regraMovimentacaoCreditoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
