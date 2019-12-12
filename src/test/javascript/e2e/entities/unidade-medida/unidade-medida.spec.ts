import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { UnidadeMedidaComponentsPage, UnidadeMedidaDeleteDialog, UnidadeMedidaUpdatePage } from './unidade-medida.page-object';

const expect = chai.expect;

describe('UnidadeMedida e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let unidadeMedidaComponentsPage: UnidadeMedidaComponentsPage;
  let unidadeMedidaUpdatePage: UnidadeMedidaUpdatePage;
  let unidadeMedidaDeleteDialog: UnidadeMedidaDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load UnidadeMedidas', async () => {
    await navBarPage.goToEntity('unidade-medida');
    unidadeMedidaComponentsPage = new UnidadeMedidaComponentsPage();
    await browser.wait(ec.visibilityOf(unidadeMedidaComponentsPage.title), 5000);
    expect(await unidadeMedidaComponentsPage.getTitle()).to.eq('mandaApp.unidadeMedida.home.title');
  });

  it('should load create UnidadeMedida page', async () => {
    await unidadeMedidaComponentsPage.clickOnCreateButton();
    unidadeMedidaUpdatePage = new UnidadeMedidaUpdatePage();
    expect(await unidadeMedidaUpdatePage.getPageTitle()).to.eq('mandaApp.unidadeMedida.home.createOrEditLabel');
    await unidadeMedidaUpdatePage.cancel();
  });

  it('should create and save UnidadeMedidas', async () => {
    const nbButtonsBeforeCreate = await unidadeMedidaComponentsPage.countDeleteButtons();

    await unidadeMedidaComponentsPage.clickOnCreateButton();
    await promise.all([
      unidadeMedidaUpdatePage.setNomeInput('nome'),
      unidadeMedidaUpdatePage.setSiglaInput('sigla'),
      unidadeMedidaUpdatePage.setValorInput('5'),
      unidadeMedidaUpdatePage.unidadeConversaoSelectLastOption()
    ]);
    expect(await unidadeMedidaUpdatePage.getNomeInput()).to.eq('nome', 'Expected Nome value to be equals to nome');
    expect(await unidadeMedidaUpdatePage.getSiglaInput()).to.eq('sigla', 'Expected Sigla value to be equals to sigla');
    expect(await unidadeMedidaUpdatePage.getValorInput()).to.eq('5', 'Expected valor value to be equals to 5');
    await unidadeMedidaUpdatePage.save();
    expect(await unidadeMedidaUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await unidadeMedidaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last UnidadeMedida', async () => {
    const nbButtonsBeforeDelete = await unidadeMedidaComponentsPage.countDeleteButtons();
    await unidadeMedidaComponentsPage.clickOnLastDeleteButton();

    unidadeMedidaDeleteDialog = new UnidadeMedidaDeleteDialog();
    expect(await unidadeMedidaDeleteDialog.getDialogTitle()).to.eq('mandaApp.unidadeMedida.delete.question');
    await unidadeMedidaDeleteDialog.clickOnConfirmButton();

    expect(await unidadeMedidaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
