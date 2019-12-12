import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { FormaLiquidacaoComponentsPage, FormaLiquidacaoDeleteDialog, FormaLiquidacaoUpdatePage } from './forma-liquidacao.page-object';

const expect = chai.expect;

describe('FormaLiquidacao e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let formaLiquidacaoComponentsPage: FormaLiquidacaoComponentsPage;
  let formaLiquidacaoUpdatePage: FormaLiquidacaoUpdatePage;
  let formaLiquidacaoDeleteDialog: FormaLiquidacaoDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load FormaLiquidacaos', async () => {
    await navBarPage.goToEntity('forma-liquidacao');
    formaLiquidacaoComponentsPage = new FormaLiquidacaoComponentsPage();
    await browser.wait(ec.visibilityOf(formaLiquidacaoComponentsPage.title), 5000);
    expect(await formaLiquidacaoComponentsPage.getTitle()).to.eq('mandaApp.formaLiquidacao.home.title');
  });

  it('should load create FormaLiquidacao page', async () => {
    await formaLiquidacaoComponentsPage.clickOnCreateButton();
    formaLiquidacaoUpdatePage = new FormaLiquidacaoUpdatePage();
    expect(await formaLiquidacaoUpdatePage.getPageTitle()).to.eq('mandaApp.formaLiquidacao.home.createOrEditLabel');
    await formaLiquidacaoUpdatePage.cancel();
  });

  it('should create and save FormaLiquidacaos', async () => {
    const nbButtonsBeforeCreate = await formaLiquidacaoComponentsPage.countDeleteButtons();

    await formaLiquidacaoComponentsPage.clickOnCreateButton();
    await promise.all([
      formaLiquidacaoUpdatePage.setNomeInput('nome'),
      formaLiquidacaoUpdatePage.setJuroInput('5'),
      formaLiquidacaoUpdatePage.setPrazoLiquidacaoInput('5'),
      formaLiquidacaoUpdatePage.setQuantidadeInput('5'),
      formaLiquidacaoUpdatePage.setIconInput('icon')
    ]);
    expect(await formaLiquidacaoUpdatePage.getNomeInput()).to.eq('nome', 'Expected Nome value to be equals to nome');
    expect(await formaLiquidacaoUpdatePage.getJuroInput()).to.eq('5', 'Expected juro value to be equals to 5');
    expect(await formaLiquidacaoUpdatePage.getPrazoLiquidacaoInput()).to.eq('5', 'Expected prazoLiquidacao value to be equals to 5');
    expect(await formaLiquidacaoUpdatePage.getQuantidadeInput()).to.eq('5', 'Expected quantidade value to be equals to 5');
    expect(await formaLiquidacaoUpdatePage.getIconInput()).to.eq('icon', 'Expected Icon value to be equals to icon');
    await formaLiquidacaoUpdatePage.save();
    expect(await formaLiquidacaoUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await formaLiquidacaoComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last FormaLiquidacao', async () => {
    const nbButtonsBeforeDelete = await formaLiquidacaoComponentsPage.countDeleteButtons();
    await formaLiquidacaoComponentsPage.clickOnLastDeleteButton();

    formaLiquidacaoDeleteDialog = new FormaLiquidacaoDeleteDialog();
    expect(await formaLiquidacaoDeleteDialog.getDialogTitle()).to.eq('mandaApp.formaLiquidacao.delete.question');
    await formaLiquidacaoDeleteDialog.clickOnConfirmButton();

    expect(await formaLiquidacaoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
