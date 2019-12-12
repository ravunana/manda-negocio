import { browser, ExpectedConditions as ec /* , promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  VariacaoProdutoComponentsPage,
  /* VariacaoProdutoDeleteDialog,
   */ VariacaoProdutoUpdatePage
} from './variacao-produto.page-object';

const expect = chai.expect;

describe('VariacaoProduto e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let variacaoProdutoComponentsPage: VariacaoProdutoComponentsPage;
  let variacaoProdutoUpdatePage: VariacaoProdutoUpdatePage;
  /* let variacaoProdutoDeleteDialog: VariacaoProdutoDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load VariacaoProdutos', async () => {
    await navBarPage.goToEntity('variacao-produto');
    variacaoProdutoComponentsPage = new VariacaoProdutoComponentsPage();
    await browser.wait(ec.visibilityOf(variacaoProdutoComponentsPage.title), 5000);
    expect(await variacaoProdutoComponentsPage.getTitle()).to.eq('mandaApp.variacaoProduto.home.title');
  });

  it('should load create VariacaoProduto page', async () => {
    await variacaoProdutoComponentsPage.clickOnCreateButton();
    variacaoProdutoUpdatePage = new VariacaoProdutoUpdatePage();
    expect(await variacaoProdutoUpdatePage.getPageTitle()).to.eq('mandaApp.variacaoProduto.home.createOrEditLabel');
    await variacaoProdutoUpdatePage.cancel();
  });

  /*  it('should create and save VariacaoProdutos', async () => {
        const nbButtonsBeforeCreate = await variacaoProdutoComponentsPage.countDeleteButtons();

        await variacaoProdutoComponentsPage.clickOnCreateButton();
        await promise.all([
            variacaoProdutoUpdatePage.setGeneroInput('genero'),
            variacaoProdutoUpdatePage.setCorInput('cor'),
            variacaoProdutoUpdatePage.setModeloInput('modelo'),
            variacaoProdutoUpdatePage.setMarcaInput('marca'),
            variacaoProdutoUpdatePage.setTamanhoInput('tamanho'),
            variacaoProdutoUpdatePage.produtoSelectLastOption(),
        ]);
        expect(await variacaoProdutoUpdatePage.getGeneroInput()).to.eq('genero', 'Expected Genero value to be equals to genero');
        expect(await variacaoProdutoUpdatePage.getCorInput()).to.eq('cor', 'Expected Cor value to be equals to cor');
        expect(await variacaoProdutoUpdatePage.getModeloInput()).to.eq('modelo', 'Expected Modelo value to be equals to modelo');
        expect(await variacaoProdutoUpdatePage.getMarcaInput()).to.eq('marca', 'Expected Marca value to be equals to marca');
        expect(await variacaoProdutoUpdatePage.getTamanhoInput()).to.eq('tamanho', 'Expected Tamanho value to be equals to tamanho');
        await variacaoProdutoUpdatePage.save();
        expect(await variacaoProdutoUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await variacaoProdutoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /*  it('should delete last VariacaoProduto', async () => {
        const nbButtonsBeforeDelete = await variacaoProdutoComponentsPage.countDeleteButtons();
        await variacaoProdutoComponentsPage.clickOnLastDeleteButton();

        variacaoProdutoDeleteDialog = new VariacaoProdutoDeleteDialog();
        expect(await variacaoProdutoDeleteDialog.getDialogTitle())
            .to.eq('mandaApp.variacaoProduto.delete.question');
        await variacaoProdutoDeleteDialog.clickOnConfirmButton();

        expect(await variacaoProdutoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
