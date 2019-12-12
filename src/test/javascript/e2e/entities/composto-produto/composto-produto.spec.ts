import { browser, ExpectedConditions as ec /* , promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  CompostoProdutoComponentsPage,
  /* CompostoProdutoDeleteDialog,
   */ CompostoProdutoUpdatePage
} from './composto-produto.page-object';

const expect = chai.expect;

describe('CompostoProduto e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let compostoProdutoComponentsPage: CompostoProdutoComponentsPage;
  let compostoProdutoUpdatePage: CompostoProdutoUpdatePage;
  /* let compostoProdutoDeleteDialog: CompostoProdutoDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load CompostoProdutos', async () => {
    await navBarPage.goToEntity('composto-produto');
    compostoProdutoComponentsPage = new CompostoProdutoComponentsPage();
    await browser.wait(ec.visibilityOf(compostoProdutoComponentsPage.title), 5000);
    expect(await compostoProdutoComponentsPage.getTitle()).to.eq('mandaApp.compostoProduto.home.title');
  });

  it('should load create CompostoProduto page', async () => {
    await compostoProdutoComponentsPage.clickOnCreateButton();
    compostoProdutoUpdatePage = new CompostoProdutoUpdatePage();
    expect(await compostoProdutoUpdatePage.getPageTitle()).to.eq('mandaApp.compostoProduto.home.createOrEditLabel');
    await compostoProdutoUpdatePage.cancel();
  });

  /*  it('should create and save CompostoProdutos', async () => {
        const nbButtonsBeforeCreate = await compostoProdutoComponentsPage.countDeleteButtons();

        await compostoProdutoComponentsPage.clickOnCreateButton();
        await promise.all([
            compostoProdutoUpdatePage.setQuantidadeInput('5'),
            compostoProdutoUpdatePage.setValorInput('5'),
            compostoProdutoUpdatePage.produtoSelectLastOption(),
            compostoProdutoUpdatePage.unidadeSelectLastOption(),
            compostoProdutoUpdatePage.compostoSelectLastOption(),
        ]);
        expect(await compostoProdutoUpdatePage.getQuantidadeInput()).to.eq('5', 'Expected quantidade value to be equals to 5');
        expect(await compostoProdutoUpdatePage.getValorInput()).to.eq('5', 'Expected valor value to be equals to 5');
        const selectedPermanente = compostoProdutoUpdatePage.getPermanenteInput();
        if (await selectedPermanente.isSelected()) {
            await compostoProdutoUpdatePage.getPermanenteInput().click();
            expect(await compostoProdutoUpdatePage.getPermanenteInput().isSelected(), 'Expected permanente not to be selected').to.be.false;
        } else {
            await compostoProdutoUpdatePage.getPermanenteInput().click();
            expect(await compostoProdutoUpdatePage.getPermanenteInput().isSelected(), 'Expected permanente to be selected').to.be.true;
        }
        await compostoProdutoUpdatePage.save();
        expect(await compostoProdutoUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await compostoProdutoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /*  it('should delete last CompostoProduto', async () => {
        const nbButtonsBeforeDelete = await compostoProdutoComponentsPage.countDeleteButtons();
        await compostoProdutoComponentsPage.clickOnLastDeleteButton();

        compostoProdutoDeleteDialog = new CompostoProdutoDeleteDialog();
        expect(await compostoProdutoDeleteDialog.getDialogTitle())
            .to.eq('mandaApp.compostoProduto.delete.question');
        await compostoProdutoDeleteDialog.clickOnConfirmButton();

        expect(await compostoProdutoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
