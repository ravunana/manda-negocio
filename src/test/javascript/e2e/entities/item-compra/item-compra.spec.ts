import { browser, ExpectedConditions as ec /* , protractor, promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  ItemCompraComponentsPage,
  /* ItemCompraDeleteDialog,
   */ ItemCompraUpdatePage
} from './item-compra.page-object';

const expect = chai.expect;

describe('ItemCompra e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let itemCompraComponentsPage: ItemCompraComponentsPage;
  let itemCompraUpdatePage: ItemCompraUpdatePage;
  /* let itemCompraDeleteDialog: ItemCompraDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load ItemCompras', async () => {
    await navBarPage.goToEntity('item-compra');
    itemCompraComponentsPage = new ItemCompraComponentsPage();
    await browser.wait(ec.visibilityOf(itemCompraComponentsPage.title), 5000);
    expect(await itemCompraComponentsPage.getTitle()).to.eq('mandaApp.itemCompra.home.title');
  });

  it('should load create ItemCompra page', async () => {
    await itemCompraComponentsPage.clickOnCreateButton();
    itemCompraUpdatePage = new ItemCompraUpdatePage();
    expect(await itemCompraUpdatePage.getPageTitle()).to.eq('mandaApp.itemCompra.home.createOrEditLabel');
    await itemCompraUpdatePage.cancel();
  });

  /*  it('should create and save ItemCompras', async () => {
        const nbButtonsBeforeCreate = await itemCompraComponentsPage.countDeleteButtons();

        await itemCompraComponentsPage.clickOnCreateButton();
        await promise.all([
            itemCompraUpdatePage.setQuantidadeInput('5'),
            itemCompraUpdatePage.setDescontoInput('5'),
            itemCompraUpdatePage.setDataSolicitacaoInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            itemCompraUpdatePage.setDataEntregaInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            itemCompraUpdatePage.setDescricaoInput('descricao'),
            itemCompraUpdatePage.setValorInput('5'),
            itemCompraUpdatePage.solicitanteSelectLastOption(),
            itemCompraUpdatePage.compraSelectLastOption(),
            itemCompraUpdatePage.produtoSelectLastOption(),
            itemCompraUpdatePage.fornecedorSelectLastOption(),
            itemCompraUpdatePage.statusSelectLastOption(),
        ]);
        expect(await itemCompraUpdatePage.getQuantidadeInput()).to.eq('5', 'Expected quantidade value to be equals to 5');
        expect(await itemCompraUpdatePage.getDescontoInput()).to.eq('5', 'Expected desconto value to be equals to 5');
        expect(await itemCompraUpdatePage.getDataSolicitacaoInput()).to.contain('2001-01-01T02:30', 'Expected dataSolicitacao value to be equals to 2000-12-31');
        expect(await itemCompraUpdatePage.getDataEntregaInput()).to.contain('2001-01-01T02:30', 'Expected dataEntrega value to be equals to 2000-12-31');
        expect(await itemCompraUpdatePage.getDescricaoInput()).to.eq('descricao', 'Expected Descricao value to be equals to descricao');
        expect(await itemCompraUpdatePage.getValorInput()).to.eq('5', 'Expected valor value to be equals to 5');
        await itemCompraUpdatePage.save();
        expect(await itemCompraUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await itemCompraComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /*  it('should delete last ItemCompra', async () => {
        const nbButtonsBeforeDelete = await itemCompraComponentsPage.countDeleteButtons();
        await itemCompraComponentsPage.clickOnLastDeleteButton();

        itemCompraDeleteDialog = new ItemCompraDeleteDialog();
        expect(await itemCompraDeleteDialog.getDialogTitle())
            .to.eq('mandaApp.itemCompra.delete.question');
        await itemCompraDeleteDialog.clickOnConfirmButton();

        expect(await itemCompraComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
