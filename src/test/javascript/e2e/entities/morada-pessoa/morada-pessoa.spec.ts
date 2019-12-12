import { browser, ExpectedConditions as ec /* , promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  MoradaPessoaComponentsPage,
  /* MoradaPessoaDeleteDialog,
   */ MoradaPessoaUpdatePage
} from './morada-pessoa.page-object';

const expect = chai.expect;

describe('MoradaPessoa e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let moradaPessoaComponentsPage: MoradaPessoaComponentsPage;
  let moradaPessoaUpdatePage: MoradaPessoaUpdatePage;
  /* let moradaPessoaDeleteDialog: MoradaPessoaDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load MoradaPessoas', async () => {
    await navBarPage.goToEntity('morada-pessoa');
    moradaPessoaComponentsPage = new MoradaPessoaComponentsPage();
    await browser.wait(ec.visibilityOf(moradaPessoaComponentsPage.title), 5000);
    expect(await moradaPessoaComponentsPage.getTitle()).to.eq('mandaApp.moradaPessoa.home.title');
  });

  it('should load create MoradaPessoa page', async () => {
    await moradaPessoaComponentsPage.clickOnCreateButton();
    moradaPessoaUpdatePage = new MoradaPessoaUpdatePage();
    expect(await moradaPessoaUpdatePage.getPageTitle()).to.eq('mandaApp.moradaPessoa.home.createOrEditLabel');
    await moradaPessoaUpdatePage.cancel();
  });

  /*  it('should create and save MoradaPessoas', async () => {
        const nbButtonsBeforeCreate = await moradaPessoaComponentsPage.countDeleteButtons();

        await moradaPessoaComponentsPage.clickOnCreateButton();
        await promise.all([
            moradaPessoaUpdatePage.setPaisInput('pais'),
            moradaPessoaUpdatePage.setProvinciaInput('provincia'),
            moradaPessoaUpdatePage.setMunicipioInput('municipio'),
            moradaPessoaUpdatePage.setBairroInput('bairro'),
            moradaPessoaUpdatePage.setRuaInput('rua'),
            moradaPessoaUpdatePage.setQuarteiraoInput('quarteirao'),
            moradaPessoaUpdatePage.setNumeroPortaInput('numeroPorta'),
            moradaPessoaUpdatePage.pessoaSelectLastOption(),
        ]);
        expect(await moradaPessoaUpdatePage.getPaisInput()).to.eq('pais', 'Expected Pais value to be equals to pais');
        expect(await moradaPessoaUpdatePage.getProvinciaInput()).to.eq('provincia', 'Expected Provincia value to be equals to provincia');
        expect(await moradaPessoaUpdatePage.getMunicipioInput()).to.eq('municipio', 'Expected Municipio value to be equals to municipio');
        expect(await moradaPessoaUpdatePage.getBairroInput()).to.eq('bairro', 'Expected Bairro value to be equals to bairro');
        expect(await moradaPessoaUpdatePage.getRuaInput()).to.eq('rua', 'Expected Rua value to be equals to rua');
        expect(await moradaPessoaUpdatePage.getQuarteiraoInput()).to.eq('quarteirao', 'Expected Quarteirao value to be equals to quarteirao');
        expect(await moradaPessoaUpdatePage.getNumeroPortaInput()).to.eq('numeroPorta', 'Expected NumeroPorta value to be equals to numeroPorta');
        await moradaPessoaUpdatePage.save();
        expect(await moradaPessoaUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await moradaPessoaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /*  it('should delete last MoradaPessoa', async () => {
        const nbButtonsBeforeDelete = await moradaPessoaComponentsPage.countDeleteButtons();
        await moradaPessoaComponentsPage.clickOnLastDeleteButton();

        moradaPessoaDeleteDialog = new MoradaPessoaDeleteDialog();
        expect(await moradaPessoaDeleteDialog.getDialogTitle())
            .to.eq('mandaApp.moradaPessoa.delete.question');
        await moradaPessoaDeleteDialog.clickOnConfirmButton();

        expect(await moradaPessoaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
