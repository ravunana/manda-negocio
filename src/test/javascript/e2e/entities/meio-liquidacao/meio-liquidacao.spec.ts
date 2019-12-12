import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { MeioLiquidacaoComponentsPage, MeioLiquidacaoDeleteDialog, MeioLiquidacaoUpdatePage } from './meio-liquidacao.page-object';

const expect = chai.expect;

describe('MeioLiquidacao e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let meioLiquidacaoComponentsPage: MeioLiquidacaoComponentsPage;
  let meioLiquidacaoUpdatePage: MeioLiquidacaoUpdatePage;
  let meioLiquidacaoDeleteDialog: MeioLiquidacaoDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load MeioLiquidacaos', async () => {
    await navBarPage.goToEntity('meio-liquidacao');
    meioLiquidacaoComponentsPage = new MeioLiquidacaoComponentsPage();
    await browser.wait(ec.visibilityOf(meioLiquidacaoComponentsPage.title), 5000);
    expect(await meioLiquidacaoComponentsPage.getTitle()).to.eq('mandaApp.meioLiquidacao.home.title');
  });

  it('should load create MeioLiquidacao page', async () => {
    await meioLiquidacaoComponentsPage.clickOnCreateButton();
    meioLiquidacaoUpdatePage = new MeioLiquidacaoUpdatePage();
    expect(await meioLiquidacaoUpdatePage.getPageTitle()).to.eq('mandaApp.meioLiquidacao.home.createOrEditLabel');
    await meioLiquidacaoUpdatePage.cancel();
  });

  it('should create and save MeioLiquidacaos', async () => {
    const nbButtonsBeforeCreate = await meioLiquidacaoComponentsPage.countDeleteButtons();

    await meioLiquidacaoComponentsPage.clickOnCreateButton();
    await promise.all([
      meioLiquidacaoUpdatePage.setNomeInput('nome'),
      meioLiquidacaoUpdatePage.setSiglaInput('sigla'),
      meioLiquidacaoUpdatePage.setIconInput('icon')
    ]);
    expect(await meioLiquidacaoUpdatePage.getNomeInput()).to.eq('nome', 'Expected Nome value to be equals to nome');
    expect(await meioLiquidacaoUpdatePage.getSiglaInput()).to.eq('sigla', 'Expected Sigla value to be equals to sigla');
    expect(await meioLiquidacaoUpdatePage.getIconInput()).to.eq('icon', 'Expected Icon value to be equals to icon');
    const selectedMostrarPontoVenda = meioLiquidacaoUpdatePage.getMostrarPontoVendaInput();
    if (await selectedMostrarPontoVenda.isSelected()) {
      await meioLiquidacaoUpdatePage.getMostrarPontoVendaInput().click();
      expect(await meioLiquidacaoUpdatePage.getMostrarPontoVendaInput().isSelected(), 'Expected mostrarPontoVenda not to be selected').to.be
        .false;
    } else {
      await meioLiquidacaoUpdatePage.getMostrarPontoVendaInput().click();
      expect(await meioLiquidacaoUpdatePage.getMostrarPontoVendaInput().isSelected(), 'Expected mostrarPontoVenda to be selected').to.be
        .true;
    }
    await meioLiquidacaoUpdatePage.save();
    expect(await meioLiquidacaoUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await meioLiquidacaoComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last MeioLiquidacao', async () => {
    const nbButtonsBeforeDelete = await meioLiquidacaoComponentsPage.countDeleteButtons();
    await meioLiquidacaoComponentsPage.clickOnLastDeleteButton();

    meioLiquidacaoDeleteDialog = new MeioLiquidacaoDeleteDialog();
    expect(await meioLiquidacaoDeleteDialog.getDialogTitle()).to.eq('mandaApp.meioLiquidacao.delete.question');
    await meioLiquidacaoDeleteDialog.clickOnConfirmButton();

    expect(await meioLiquidacaoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
