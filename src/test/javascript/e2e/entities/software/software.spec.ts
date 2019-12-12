import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { SoftwareComponentsPage, SoftwareDeleteDialog, SoftwareUpdatePage } from './software.page-object';

const expect = chai.expect;

describe('Software e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let softwareComponentsPage: SoftwareComponentsPage;
  let softwareUpdatePage: SoftwareUpdatePage;
  let softwareDeleteDialog: SoftwareDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Softwares', async () => {
    await navBarPage.goToEntity('software');
    softwareComponentsPage = new SoftwareComponentsPage();
    await browser.wait(ec.visibilityOf(softwareComponentsPage.title), 5000);
    expect(await softwareComponentsPage.getTitle()).to.eq('mandaApp.software.home.title');
  });

  it('should load create Software page', async () => {
    await softwareComponentsPage.clickOnCreateButton();
    softwareUpdatePage = new SoftwareUpdatePage();
    expect(await softwareUpdatePage.getPageTitle()).to.eq('mandaApp.software.home.createOrEditLabel');
    await softwareUpdatePage.cancel();
  });

  it('should create and save Softwares', async () => {
    const nbButtonsBeforeCreate = await softwareComponentsPage.countDeleteButtons();

    await softwareComponentsPage.clickOnCreateButton();
    await promise.all([
      softwareUpdatePage.setEmpresaInput('empresa'),
      softwareUpdatePage.setTipoSistemaInput('tipoSistema'),
      softwareUpdatePage.setNifInput('nif'),
      softwareUpdatePage.setNumeroValidacaoAGTInput('5'),
      softwareUpdatePage.setNomeInput('nome'),
      softwareUpdatePage.setVersaoInput('versao'),
      softwareUpdatePage.setHashCodeInput('hashCode'),
      softwareUpdatePage.setHashControlInput('hashControl')
    ]);
    expect(await softwareUpdatePage.getEmpresaInput()).to.eq('empresa', 'Expected Empresa value to be equals to empresa');
    expect(await softwareUpdatePage.getTipoSistemaInput()).to.eq('tipoSistema', 'Expected TipoSistema value to be equals to tipoSistema');
    expect(await softwareUpdatePage.getNifInput()).to.eq('nif', 'Expected Nif value to be equals to nif');
    expect(await softwareUpdatePage.getNumeroValidacaoAGTInput()).to.eq('5', 'Expected numeroValidacaoAGT value to be equals to 5');
    expect(await softwareUpdatePage.getNomeInput()).to.eq('nome', 'Expected Nome value to be equals to nome');
    expect(await softwareUpdatePage.getVersaoInput()).to.eq('versao', 'Expected Versao value to be equals to versao');
    expect(await softwareUpdatePage.getHashCodeInput()).to.eq('hashCode', 'Expected HashCode value to be equals to hashCode');
    expect(await softwareUpdatePage.getHashControlInput()).to.eq('hashControl', 'Expected HashControl value to be equals to hashControl');
    await softwareUpdatePage.save();
    expect(await softwareUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await softwareComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Software', async () => {
    const nbButtonsBeforeDelete = await softwareComponentsPage.countDeleteButtons();
    await softwareComponentsPage.clickOnLastDeleteButton();

    softwareDeleteDialog = new SoftwareDeleteDialog();
    expect(await softwareDeleteDialog.getDialogTitle()).to.eq('mandaApp.software.delete.question');
    await softwareDeleteDialog.clickOnConfirmButton();

    expect(await softwareComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
