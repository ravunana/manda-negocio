import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { EmpresaComponentsPage, EmpresaDeleteDialog, EmpresaUpdatePage } from './empresa.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('Empresa e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let empresaComponentsPage: EmpresaComponentsPage;
  let empresaUpdatePage: EmpresaUpdatePage;
  let empresaDeleteDialog: EmpresaDeleteDialog;
  const fileNameToUpload = 'logo-jhipster.png';
  const fileToUpload = '../../../../../../src/main/webapp/content/images/' + fileNameToUpload;
  const absolutePath = path.resolve(__dirname, fileToUpload);

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Empresas', async () => {
    await navBarPage.goToEntity('empresa');
    empresaComponentsPage = new EmpresaComponentsPage();
    await browser.wait(ec.visibilityOf(empresaComponentsPage.title), 5000);
    expect(await empresaComponentsPage.getTitle()).to.eq('mandaApp.empresa.home.title');
  });

  it('should load create Empresa page', async () => {
    await empresaComponentsPage.clickOnCreateButton();
    empresaUpdatePage = new EmpresaUpdatePage();
    expect(await empresaUpdatePage.getPageTitle()).to.eq('mandaApp.empresa.home.createOrEditLabel');
    await empresaUpdatePage.cancel();
  });

  it('should create and save Empresas', async () => {
    const nbButtonsBeforeCreate = await empresaComponentsPage.countDeleteButtons();

    await empresaComponentsPage.clickOnCreateButton();
    await promise.all([
      empresaUpdatePage.setTipoSociedadeInput('tipoSociedade'),
      empresaUpdatePage.setNomeInput('nome'),
      empresaUpdatePage.setLogotipoInput(absolutePath),
      empresaUpdatePage.setCapitalSocialInput('5'),
      empresaUpdatePage.setFundacaoInput('2000-12-31'),
      empresaUpdatePage.setNifInput('nif'),
      empresaUpdatePage.setNumeroRegistroComercialInput('numeroRegistroComercial'),
      empresaUpdatePage.setDespesaFixaInput('5'),
      empresaUpdatePage.setDespesaVariavelInput('5'),
      empresaUpdatePage.setAberturaExercioInput('2000-12-31'),
      empresaUpdatePage.setDesignacaoComercialInput('designacaoComercial'),
      empresaUpdatePage.utilizadorSelectLastOption(),
      empresaUpdatePage.contaSelectLastOption(),
      empresaUpdatePage.hierarquiaSelectLastOption()
    ]);
    expect(await empresaUpdatePage.getTipoSociedadeInput()).to.eq(
      'tipoSociedade',
      'Expected TipoSociedade value to be equals to tipoSociedade'
    );
    expect(await empresaUpdatePage.getNomeInput()).to.eq('nome', 'Expected Nome value to be equals to nome');
    expect(await empresaUpdatePage.getLogotipoInput()).to.endsWith(
      fileNameToUpload,
      'Expected Logotipo value to be end with ' + fileNameToUpload
    );
    expect(await empresaUpdatePage.getCapitalSocialInput()).to.eq('5', 'Expected capitalSocial value to be equals to 5');
    expect(await empresaUpdatePage.getFundacaoInput()).to.eq('2000-12-31', 'Expected fundacao value to be equals to 2000-12-31');
    expect(await empresaUpdatePage.getNifInput()).to.eq('nif', 'Expected Nif value to be equals to nif');
    expect(await empresaUpdatePage.getNumeroRegistroComercialInput()).to.eq(
      'numeroRegistroComercial',
      'Expected NumeroRegistroComercial value to be equals to numeroRegistroComercial'
    );
    expect(await empresaUpdatePage.getDespesaFixaInput()).to.eq('5', 'Expected despesaFixa value to be equals to 5');
    expect(await empresaUpdatePage.getDespesaVariavelInput()).to.eq('5', 'Expected despesaVariavel value to be equals to 5');
    expect(await empresaUpdatePage.getAberturaExercioInput()).to.eq(
      '2000-12-31',
      'Expected aberturaExercio value to be equals to 2000-12-31'
    );
    expect(await empresaUpdatePage.getDesignacaoComercialInput()).to.eq(
      'designacaoComercial',
      'Expected DesignacaoComercial value to be equals to designacaoComercial'
    );
    const selectedSede = empresaUpdatePage.getSedeInput();
    if (await selectedSede.isSelected()) {
      await empresaUpdatePage.getSedeInput().click();
      expect(await empresaUpdatePage.getSedeInput().isSelected(), 'Expected sede not to be selected').to.be.false;
    } else {
      await empresaUpdatePage.getSedeInput().click();
      expect(await empresaUpdatePage.getSedeInput().isSelected(), 'Expected sede to be selected').to.be.true;
    }
    await empresaUpdatePage.save();
    expect(await empresaUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await empresaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Empresa', async () => {
    const nbButtonsBeforeDelete = await empresaComponentsPage.countDeleteButtons();
    await empresaComponentsPage.clickOnLastDeleteButton();

    empresaDeleteDialog = new EmpresaDeleteDialog();
    expect(await empresaDeleteDialog.getDialogTitle()).to.eq('mandaApp.empresa.delete.question');
    await empresaDeleteDialog.clickOnConfirmButton();

    expect(await empresaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
