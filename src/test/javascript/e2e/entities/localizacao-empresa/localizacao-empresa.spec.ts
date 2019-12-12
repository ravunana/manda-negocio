import { browser, ExpectedConditions as ec /* , promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  LocalizacaoEmpresaComponentsPage,
  /* LocalizacaoEmpresaDeleteDialog,
   */ LocalizacaoEmpresaUpdatePage
} from './localizacao-empresa.page-object';

const expect = chai.expect;

describe('LocalizacaoEmpresa e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let localizacaoEmpresaComponentsPage: LocalizacaoEmpresaComponentsPage;
  let localizacaoEmpresaUpdatePage: LocalizacaoEmpresaUpdatePage;
  /* let localizacaoEmpresaDeleteDialog: LocalizacaoEmpresaDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load LocalizacaoEmpresas', async () => {
    await navBarPage.goToEntity('localizacao-empresa');
    localizacaoEmpresaComponentsPage = new LocalizacaoEmpresaComponentsPage();
    await browser.wait(ec.visibilityOf(localizacaoEmpresaComponentsPage.title), 5000);
    expect(await localizacaoEmpresaComponentsPage.getTitle()).to.eq('mandaApp.localizacaoEmpresa.home.title');
  });

  it('should load create LocalizacaoEmpresa page', async () => {
    await localizacaoEmpresaComponentsPage.clickOnCreateButton();
    localizacaoEmpresaUpdatePage = new LocalizacaoEmpresaUpdatePage();
    expect(await localizacaoEmpresaUpdatePage.getPageTitle()).to.eq('mandaApp.localizacaoEmpresa.home.createOrEditLabel');
    await localizacaoEmpresaUpdatePage.cancel();
  });

  /*  it('should create and save LocalizacaoEmpresas', async () => {
        const nbButtonsBeforeCreate = await localizacaoEmpresaComponentsPage.countDeleteButtons();

        await localizacaoEmpresaComponentsPage.clickOnCreateButton();
        await promise.all([
            localizacaoEmpresaUpdatePage.setPaisInput('pais'),
            localizacaoEmpresaUpdatePage.setProvinciaInput('provincia'),
            localizacaoEmpresaUpdatePage.setMunicipioInput('municipio'),
            localizacaoEmpresaUpdatePage.setBairroInput('bairro'),
            localizacaoEmpresaUpdatePage.setRuaInput('rua'),
            localizacaoEmpresaUpdatePage.setQuarteiraoInput('quarteirao'),
            localizacaoEmpresaUpdatePage.setNumeroPortaInput('numeroPorta'),
            localizacaoEmpresaUpdatePage.setCaixaPostalInput('caixaPostal'),
            localizacaoEmpresaUpdatePage.empresaSelectLastOption(),
        ]);
        expect(await localizacaoEmpresaUpdatePage.getPaisInput()).to.eq('pais', 'Expected Pais value to be equals to pais');
        expect(await localizacaoEmpresaUpdatePage.getProvinciaInput()).to.eq('provincia', 'Expected Provincia value to be equals to provincia');
        expect(await localizacaoEmpresaUpdatePage.getMunicipioInput()).to.eq('municipio', 'Expected Municipio value to be equals to municipio');
        expect(await localizacaoEmpresaUpdatePage.getBairroInput()).to.eq('bairro', 'Expected Bairro value to be equals to bairro');
        expect(await localizacaoEmpresaUpdatePage.getRuaInput()).to.eq('rua', 'Expected Rua value to be equals to rua');
        expect(await localizacaoEmpresaUpdatePage.getQuarteiraoInput()).to.eq('quarteirao', 'Expected Quarteirao value to be equals to quarteirao');
        expect(await localizacaoEmpresaUpdatePage.getNumeroPortaInput()).to.eq('numeroPorta', 'Expected NumeroPorta value to be equals to numeroPorta');
        expect(await localizacaoEmpresaUpdatePage.getCaixaPostalInput()).to.eq('caixaPostal', 'Expected CaixaPostal value to be equals to caixaPostal');
        await localizacaoEmpresaUpdatePage.save();
        expect(await localizacaoEmpresaUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await localizacaoEmpresaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /*  it('should delete last LocalizacaoEmpresa', async () => {
        const nbButtonsBeforeDelete = await localizacaoEmpresaComponentsPage.countDeleteButtons();
        await localizacaoEmpresaComponentsPage.clickOnLastDeleteButton();

        localizacaoEmpresaDeleteDialog = new LocalizacaoEmpresaDeleteDialog();
        expect(await localizacaoEmpresaDeleteDialog.getDialogTitle())
            .to.eq('mandaApp.localizacaoEmpresa.delete.question');
        await localizacaoEmpresaDeleteDialog.clickOnConfirmButton();

        expect(await localizacaoEmpresaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
