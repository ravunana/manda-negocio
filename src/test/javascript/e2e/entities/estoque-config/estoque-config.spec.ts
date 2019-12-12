import { browser, ExpectedConditions as ec /* , promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  EstoqueConfigComponentsPage,
  /* EstoqueConfigDeleteDialog,
   */ EstoqueConfigUpdatePage
} from './estoque-config.page-object';

const expect = chai.expect;

describe('EstoqueConfig e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let estoqueConfigComponentsPage: EstoqueConfigComponentsPage;
  let estoqueConfigUpdatePage: EstoqueConfigUpdatePage;
  /* let estoqueConfigDeleteDialog: EstoqueConfigDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load EstoqueConfigs', async () => {
    await navBarPage.goToEntity('estoque-config');
    estoqueConfigComponentsPage = new EstoqueConfigComponentsPage();
    await browser.wait(ec.visibilityOf(estoqueConfigComponentsPage.title), 5000);
    expect(await estoqueConfigComponentsPage.getTitle()).to.eq('mandaApp.estoqueConfig.home.title');
  });

  it('should load create EstoqueConfig page', async () => {
    await estoqueConfigComponentsPage.clickOnCreateButton();
    estoqueConfigUpdatePage = new EstoqueConfigUpdatePage();
    expect(await estoqueConfigUpdatePage.getPageTitle()).to.eq('mandaApp.estoqueConfig.home.createOrEditLabel');
    await estoqueConfigUpdatePage.cancel();
  });

  /*  it('should create and save EstoqueConfigs', async () => {
        const nbButtonsBeforeCreate = await estoqueConfigComponentsPage.countDeleteButtons();

        await estoqueConfigComponentsPage.clickOnCreateButton();
        await promise.all([
            estoqueConfigUpdatePage.produtoSelectLastOption(),
            estoqueConfigUpdatePage.contaVendaSelectLastOption(),
            estoqueConfigUpdatePage.contaCompraSelectLastOption(),
            estoqueConfigUpdatePage.contaImobilizadoSelectLastOption(),
            estoqueConfigUpdatePage.devolucaoCompraSelectLastOption(),
            estoqueConfigUpdatePage.devolucaoVendaSelectLastOption(),
        ]);
        await estoqueConfigUpdatePage.save();
        expect(await estoqueConfigUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await estoqueConfigComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /*  it('should delete last EstoqueConfig', async () => {
        const nbButtonsBeforeDelete = await estoqueConfigComponentsPage.countDeleteButtons();
        await estoqueConfigComponentsPage.clickOnLastDeleteButton();

        estoqueConfigDeleteDialog = new EstoqueConfigDeleteDialog();
        expect(await estoqueConfigDeleteDialog.getDialogTitle())
            .to.eq('mandaApp.estoqueConfig.delete.question');
        await estoqueConfigDeleteDialog.clickOnConfirmButton();

        expect(await estoqueConfigComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
