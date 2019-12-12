import { browser, ExpectedConditions as ec /* , protractor, promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  ItemVendaComponentsPage,
  /* ItemVendaDeleteDialog,
   */ ItemVendaUpdatePage
} from './item-venda.page-object';

const expect = chai.expect;

describe('ItemVenda e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let itemVendaComponentsPage: ItemVendaComponentsPage;
  let itemVendaUpdatePage: ItemVendaUpdatePage;
  /* let itemVendaDeleteDialog: ItemVendaDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load ItemVendas', async () => {
    await navBarPage.goToEntity('item-venda');
    itemVendaComponentsPage = new ItemVendaComponentsPage();
    await browser.wait(ec.visibilityOf(itemVendaComponentsPage.title), 5000);
    expect(await itemVendaComponentsPage.getTitle()).to.eq('mandaApp.itemVenda.home.title');
  });

  it('should load create ItemVenda page', async () => {
    await itemVendaComponentsPage.clickOnCreateButton();
    itemVendaUpdatePage = new ItemVendaUpdatePage();
    expect(await itemVendaUpdatePage.getPageTitle()).to.eq('mandaApp.itemVenda.home.createOrEditLabel');
    await itemVendaUpdatePage.cancel();
  });

  /*  it('should create and save ItemVendas', async () => {
        const nbButtonsBeforeCreate = await itemVendaComponentsPage.countDeleteButtons();

        await itemVendaComponentsPage.clickOnCreateButton();
        await promise.all([
            itemVendaUpdatePage.setQuantidadeInput('5'),
            itemVendaUpdatePage.setValorInput('5'),
            itemVendaUpdatePage.setDescontoInput('5'),
            itemVendaUpdatePage.setDataInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            itemVendaUpdatePage.vendaSelectLastOption(),
            itemVendaUpdatePage.produtoSelectLastOption(),
            itemVendaUpdatePage.statusSelectLastOption(),
        ]);
        expect(await itemVendaUpdatePage.getQuantidadeInput()).to.eq('5', 'Expected quantidade value to be equals to 5');
        expect(await itemVendaUpdatePage.getValorInput()).to.eq('5', 'Expected valor value to be equals to 5');
        expect(await itemVendaUpdatePage.getDescontoInput()).to.eq('5', 'Expected desconto value to be equals to 5');
        expect(await itemVendaUpdatePage.getDataInput()).to.contain('2001-01-01T02:30', 'Expected data value to be equals to 2000-12-31');
        await itemVendaUpdatePage.save();
        expect(await itemVendaUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await itemVendaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /*  it('should delete last ItemVenda', async () => {
        const nbButtonsBeforeDelete = await itemVendaComponentsPage.countDeleteButtons();
        await itemVendaComponentsPage.clickOnLastDeleteButton();

        itemVendaDeleteDialog = new ItemVendaDeleteDialog();
        expect(await itemVendaDeleteDialog.getDialogTitle())
            .to.eq('mandaApp.itemVenda.delete.question');
        await itemVendaDeleteDialog.clickOnConfirmButton();

        expect(await itemVendaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
