import { browser, ExpectedConditions as ec /* , promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  ContaComponentsPage,
  /* ContaDeleteDialog,
   */ ContaUpdatePage
} from './conta.page-object';

const expect = chai.expect;

describe('Conta e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let contaComponentsPage: ContaComponentsPage;
  let contaUpdatePage: ContaUpdatePage;
  /* let contaDeleteDialog: ContaDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Contas', async () => {
    await navBarPage.goToEntity('conta');
    contaComponentsPage = new ContaComponentsPage();
    await browser.wait(ec.visibilityOf(contaComponentsPage.title), 5000);
    expect(await contaComponentsPage.getTitle()).to.eq('mandaApp.conta.home.title');
  });

  it('should load create Conta page', async () => {
    await contaComponentsPage.clickOnCreateButton();
    contaUpdatePage = new ContaUpdatePage();
    expect(await contaUpdatePage.getPageTitle()).to.eq('mandaApp.conta.home.createOrEditLabel');
    await contaUpdatePage.cancel();
  });

  /*  it('should create and save Contas', async () => {
        const nbButtonsBeforeCreate = await contaComponentsPage.countDeleteButtons();

        await contaComponentsPage.clickOnCreateButton();
        await promise.all([
            contaUpdatePage.setDescricaoInput('descricao'),
            contaUpdatePage.setCodigoInput('codigo'),
            contaUpdatePage.setTipoInput('tipo'),
            contaUpdatePage.setGrauInput('5'),
            contaUpdatePage.setNaturezaInput('natureza'),
            contaUpdatePage.setGrupoInput('grupo'),
            contaUpdatePage.setConteudoInput('conteudo'),
            // contaUpdatePage.empresaSelectLastOption(),
            contaUpdatePage.contaAgregadoraSelectLastOption(),
            contaUpdatePage.classeContaSelectLastOption(),
            contaUpdatePage.lancamentoFinanceiroSelectLastOption(),
        ]);
        expect(await contaUpdatePage.getDescricaoInput()).to.eq('descricao', 'Expected Descricao value to be equals to descricao');
        expect(await contaUpdatePage.getCodigoInput()).to.eq('codigo', 'Expected Codigo value to be equals to codigo');
        expect(await contaUpdatePage.getTipoInput()).to.eq('tipo', 'Expected Tipo value to be equals to tipo');
        expect(await contaUpdatePage.getGrauInput()).to.eq('5', 'Expected grau value to be equals to 5');
        expect(await contaUpdatePage.getNaturezaInput()).to.eq('natureza', 'Expected Natureza value to be equals to natureza');
        expect(await contaUpdatePage.getGrupoInput()).to.eq('grupo', 'Expected Grupo value to be equals to grupo');
        expect(await contaUpdatePage.getConteudoInput()).to.eq('conteudo', 'Expected Conteudo value to be equals to conteudo');
        await contaUpdatePage.save();
        expect(await contaUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await contaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /*  it('should delete last Conta', async () => {
        const nbButtonsBeforeDelete = await contaComponentsPage.countDeleteButtons();
        await contaComponentsPage.clickOnLastDeleteButton();

        contaDeleteDialog = new ContaDeleteDialog();
        expect(await contaDeleteDialog.getDialogTitle())
            .to.eq('mandaApp.conta.delete.question');
        await contaDeleteDialog.clickOnConfirmButton();

        expect(await contaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
