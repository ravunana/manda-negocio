import { browser, ExpectedConditions as ec /* , promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  ProdutoComponentsPage,
  /* ProdutoDeleteDialog,
   */ ProdutoUpdatePage
} from './produto.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('Produto e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let produtoComponentsPage: ProdutoComponentsPage;
  let produtoUpdatePage: ProdutoUpdatePage;
  /* let produtoDeleteDialog: ProdutoDeleteDialog; */
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

  it('should load Produtos', async () => {
    await navBarPage.goToEntity('produto');
    produtoComponentsPage = new ProdutoComponentsPage();
    await browser.wait(ec.visibilityOf(produtoComponentsPage.title), 5000);
    expect(await produtoComponentsPage.getTitle()).to.eq('mandaApp.produto.home.title');
  });

  it('should load create Produto page', async () => {
    await produtoComponentsPage.clickOnCreateButton();
    produtoUpdatePage = new ProdutoUpdatePage();
    expect(await produtoUpdatePage.getPageTitle()).to.eq('mandaApp.produto.home.createOrEditLabel');
    await produtoUpdatePage.cancel();
  });

  /*  it('should create and save Produtos', async () => {
        const nbButtonsBeforeCreate = await produtoComponentsPage.countDeleteButtons();

        await produtoComponentsPage.clickOnCreateButton();
        await promise.all([
            produtoUpdatePage.setTipoInput('tipo'),
            produtoUpdatePage.setEanInput('ean'),
            produtoUpdatePage.setNumeroInput('numero'),
            produtoUpdatePage.setNomeInput('nome'),
            produtoUpdatePage.setImagemInput(absolutePath),
            produtoUpdatePage.setEstoqueMinimoInput('5'),
            produtoUpdatePage.setEstoqueMaximoInput('5'),
            produtoUpdatePage.setEstoqueAtualInput('5'),
            produtoUpdatePage.setDescricaoInput('descricao'),
            produtoUpdatePage.setPrazoMedioEntregaInput('prazoMedioEntrega'),
            produtoUpdatePage.utilizadorSelectLastOption(),
            // produtoUpdatePage.impostoSelectLastOption(),
            // produtoUpdatePage.fornecedorSelectLastOption(),
            produtoUpdatePage.localArmazenamentoSelectLastOption(),
            produtoUpdatePage.familiaSelectLastOption(),
            produtoUpdatePage.empresaSelectLastOption(),
            produtoUpdatePage.estadoSelectLastOption(),
        ]);
        expect(await produtoUpdatePage.getTipoInput()).to.eq('tipo', 'Expected Tipo value to be equals to tipo');
        expect(await produtoUpdatePage.getEanInput()).to.eq('ean', 'Expected Ean value to be equals to ean');
        expect(await produtoUpdatePage.getNumeroInput()).to.eq('numero', 'Expected Numero value to be equals to numero');
        expect(await produtoUpdatePage.getNomeInput()).to.eq('nome', 'Expected Nome value to be equals to nome');
        expect(await produtoUpdatePage.getImagemInput()).to.endsWith(fileNameToUpload, 'Expected Imagem value to be end with ' + fileNameToUpload);
        const selectedComposto = produtoUpdatePage.getCompostoInput();
        if (await selectedComposto.isSelected()) {
            await produtoUpdatePage.getCompostoInput().click();
            expect(await produtoUpdatePage.getCompostoInput().isSelected(), 'Expected composto not to be selected').to.be.false;
        } else {
            await produtoUpdatePage.getCompostoInput().click();
            expect(await produtoUpdatePage.getCompostoInput().isSelected(), 'Expected composto to be selected').to.be.true;
        }
        expect(await produtoUpdatePage.getEstoqueMinimoInput()).to.eq('5', 'Expected estoqueMinimo value to be equals to 5');
        expect(await produtoUpdatePage.getEstoqueMaximoInput()).to.eq('5', 'Expected estoqueMaximo value to be equals to 5');
        expect(await produtoUpdatePage.getEstoqueAtualInput()).to.eq('5', 'Expected estoqueAtual value to be equals to 5');
        expect(await produtoUpdatePage.getDescricaoInput()).to.eq('descricao', 'Expected Descricao value to be equals to descricao');
        const selectedMostrarPDV = produtoUpdatePage.getMostrarPDVInput();
        if (await selectedMostrarPDV.isSelected()) {
            await produtoUpdatePage.getMostrarPDVInput().click();
            expect(await produtoUpdatePage.getMostrarPDVInput().isSelected(), 'Expected mostrarPDV not to be selected').to.be.false;
        } else {
            await produtoUpdatePage.getMostrarPDVInput().click();
            expect(await produtoUpdatePage.getMostrarPDVInput().isSelected(), 'Expected mostrarPDV to be selected').to.be.true;
        }
        expect(await produtoUpdatePage.getPrazoMedioEntregaInput()).to.eq('prazoMedioEntrega', 'Expected PrazoMedioEntrega value to be equals to prazoMedioEntrega');
        await produtoUpdatePage.save();
        expect(await produtoUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await produtoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /*  it('should delete last Produto', async () => {
        const nbButtonsBeforeDelete = await produtoComponentsPage.countDeleteButtons();
        await produtoComponentsPage.clickOnLastDeleteButton();

        produtoDeleteDialog = new ProdutoDeleteDialog();
        expect(await produtoDeleteDialog.getDialogTitle())
            .to.eq('mandaApp.produto.delete.question');
        await produtoDeleteDialog.clickOnConfirmButton();

        expect(await produtoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
