import { browser, ExpectedConditions as ec /* , protractor, promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  DevolucaoVendaComponentsPage,
  /* DevolucaoVendaDeleteDialog,
   */ DevolucaoVendaUpdatePage
} from './devolucao-venda.page-object';

const expect = chai.expect;

describe('DevolucaoVenda e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let devolucaoVendaComponentsPage: DevolucaoVendaComponentsPage;
  let devolucaoVendaUpdatePage: DevolucaoVendaUpdatePage;
  /* let devolucaoVendaDeleteDialog: DevolucaoVendaDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load DevolucaoVendas', async () => {
    await navBarPage.goToEntity('devolucao-venda');
    devolucaoVendaComponentsPage = new DevolucaoVendaComponentsPage();
    await browser.wait(ec.visibilityOf(devolucaoVendaComponentsPage.title), 5000);
    expect(await devolucaoVendaComponentsPage.getTitle()).to.eq('mandaApp.devolucaoVenda.home.title');
  });

  it('should load create DevolucaoVenda page', async () => {
    await devolucaoVendaComponentsPage.clickOnCreateButton();
    devolucaoVendaUpdatePage = new DevolucaoVendaUpdatePage();
    expect(await devolucaoVendaUpdatePage.getPageTitle()).to.eq('mandaApp.devolucaoVenda.home.createOrEditLabel');
    await devolucaoVendaUpdatePage.cancel();
  });

  /*  it('should create and save DevolucaoVendas', async () => {
        const nbButtonsBeforeCreate = await devolucaoVendaComponentsPage.countDeleteButtons();

        await devolucaoVendaComponentsPage.clickOnCreateButton();
        await promise.all([
            devolucaoVendaUpdatePage.setQuantidadeInput('5'),
            devolucaoVendaUpdatePage.setValorInput('5'),
            devolucaoVendaUpdatePage.setDescontoInput('5'),
            devolucaoVendaUpdatePage.setDataInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            devolucaoVendaUpdatePage.setDescricaoInput('descricao'),
            devolucaoVendaUpdatePage.itemSelectLastOption(),
        ]);
        expect(await devolucaoVendaUpdatePage.getQuantidadeInput()).to.eq('5', 'Expected quantidade value to be equals to 5');
        expect(await devolucaoVendaUpdatePage.getValorInput()).to.eq('5', 'Expected valor value to be equals to 5');
        expect(await devolucaoVendaUpdatePage.getDescontoInput()).to.eq('5', 'Expected desconto value to be equals to 5');
        expect(await devolucaoVendaUpdatePage.getDataInput()).to.contain('2001-01-01T02:30', 'Expected data value to be equals to 2000-12-31');
        expect(await devolucaoVendaUpdatePage.getDescricaoInput()).to.eq('descricao', 'Expected Descricao value to be equals to descricao');
        await devolucaoVendaUpdatePage.save();
        expect(await devolucaoVendaUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await devolucaoVendaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /*  it('should delete last DevolucaoVenda', async () => {
        const nbButtonsBeforeDelete = await devolucaoVendaComponentsPage.countDeleteButtons();
        await devolucaoVendaComponentsPage.clickOnLastDeleteButton();

        devolucaoVendaDeleteDialog = new DevolucaoVendaDeleteDialog();
        expect(await devolucaoVendaDeleteDialog.getDialogTitle())
            .to.eq('mandaApp.devolucaoVenda.delete.question');
        await devolucaoVendaDeleteDialog.clickOnConfirmButton();

        expect(await devolucaoVendaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
