import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { MoedaComponentsPage, MoedaDeleteDialog, MoedaUpdatePage } from './moeda.page-object';

const expect = chai.expect;

describe('Moeda e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let moedaComponentsPage: MoedaComponentsPage;
  let moedaUpdatePage: MoedaUpdatePage;
  let moedaDeleteDialog: MoedaDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Moedas', async () => {
    await navBarPage.goToEntity('moeda');
    moedaComponentsPage = new MoedaComponentsPage();
    await browser.wait(ec.visibilityOf(moedaComponentsPage.title), 5000);
    expect(await moedaComponentsPage.getTitle()).to.eq('mandaApp.moeda.home.title');
  });

  it('should load create Moeda page', async () => {
    await moedaComponentsPage.clickOnCreateButton();
    moedaUpdatePage = new MoedaUpdatePage();
    expect(await moedaUpdatePage.getPageTitle()).to.eq('mandaApp.moeda.home.createOrEditLabel');
    await moedaUpdatePage.cancel();
  });

  it('should create and save Moedas', async () => {
    const nbButtonsBeforeCreate = await moedaComponentsPage.countDeleteButtons();

    await moedaComponentsPage.clickOnCreateButton();
    await promise.all([
      moedaUpdatePage.setNomeInput('nome'),
      moedaUpdatePage.setCodigoInput('codigo'),
      moedaUpdatePage.setPaisInput('pais'),
      moedaUpdatePage.setIconInput('icon')
    ]);
    expect(await moedaUpdatePage.getNomeInput()).to.eq('nome', 'Expected Nome value to be equals to nome');
    expect(await moedaUpdatePage.getCodigoInput()).to.eq('codigo', 'Expected Codigo value to be equals to codigo');
    expect(await moedaUpdatePage.getPaisInput()).to.eq('pais', 'Expected Pais value to be equals to pais');
    const selectedNacional = moedaUpdatePage.getNacionalInput();
    if (await selectedNacional.isSelected()) {
      await moedaUpdatePage.getNacionalInput().click();
      expect(await moedaUpdatePage.getNacionalInput().isSelected(), 'Expected nacional not to be selected').to.be.false;
    } else {
      await moedaUpdatePage.getNacionalInput().click();
      expect(await moedaUpdatePage.getNacionalInput().isSelected(), 'Expected nacional to be selected').to.be.true;
    }
    expect(await moedaUpdatePage.getIconInput()).to.eq('icon', 'Expected Icon value to be equals to icon');
    await moedaUpdatePage.save();
    expect(await moedaUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await moedaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Moeda', async () => {
    const nbButtonsBeforeDelete = await moedaComponentsPage.countDeleteButtons();
    await moedaComponentsPage.clickOnLastDeleteButton();

    moedaDeleteDialog = new MoedaDeleteDialog();
    expect(await moedaDeleteDialog.getDialogTitle()).to.eq('mandaApp.moeda.delete.question');
    await moedaDeleteDialog.clickOnConfirmButton();

    expect(await moedaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
