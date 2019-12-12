import { browser, ExpectedConditions as ec /* , protractor, promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  DevolucaoCompraComponentsPage,
  /* DevolucaoCompraDeleteDialog,
   */ DevolucaoCompraUpdatePage
} from './devolucao-compra.page-object';

const expect = chai.expect;

describe('DevolucaoCompra e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let devolucaoCompraComponentsPage: DevolucaoCompraComponentsPage;
  let devolucaoCompraUpdatePage: DevolucaoCompraUpdatePage;
  /* let devolucaoCompraDeleteDialog: DevolucaoCompraDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load DevolucaoCompras', async () => {
    await navBarPage.goToEntity('devolucao-compra');
    devolucaoCompraComponentsPage = new DevolucaoCompraComponentsPage();
    await browser.wait(ec.visibilityOf(devolucaoCompraComponentsPage.title), 5000);
    expect(await devolucaoCompraComponentsPage.getTitle()).to.eq('mandaApp.devolucaoCompra.home.title');
  });

  it('should load create DevolucaoCompra page', async () => {
    await devolucaoCompraComponentsPage.clickOnCreateButton();
    devolucaoCompraUpdatePage = new DevolucaoCompraUpdatePage();
    expect(await devolucaoCompraUpdatePage.getPageTitle()).to.eq('mandaApp.devolucaoCompra.home.createOrEditLabel');
    await devolucaoCompraUpdatePage.cancel();
  });

  /*  it('should create and save DevolucaoCompras', async () => {
        const nbButtonsBeforeCreate = await devolucaoCompraComponentsPage.countDeleteButtons();

        await devolucaoCompraComponentsPage.clickOnCreateButton();
        await promise.all([
            devolucaoCompraUpdatePage.setQuantidadeInput('5'),
            devolucaoCompraUpdatePage.setValorInput('5'),
            devolucaoCompraUpdatePage.setDescontoInput('5'),
            devolucaoCompraUpdatePage.setDataInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            devolucaoCompraUpdatePage.setDescricaoInput('descricao'),
            devolucaoCompraUpdatePage.itemSelectLastOption(),
        ]);
        expect(await devolucaoCompraUpdatePage.getQuantidadeInput()).to.eq('5', 'Expected quantidade value to be equals to 5');
        expect(await devolucaoCompraUpdatePage.getValorInput()).to.eq('5', 'Expected valor value to be equals to 5');
        expect(await devolucaoCompraUpdatePage.getDescontoInput()).to.eq('5', 'Expected desconto value to be equals to 5');
        expect(await devolucaoCompraUpdatePage.getDataInput()).to.contain('2001-01-01T02:30', 'Expected data value to be equals to 2000-12-31');
        expect(await devolucaoCompraUpdatePage.getDescricaoInput()).to.eq('descricao', 'Expected Descricao value to be equals to descricao');
        await devolucaoCompraUpdatePage.save();
        expect(await devolucaoCompraUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await devolucaoCompraComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /*  it('should delete last DevolucaoCompra', async () => {
        const nbButtonsBeforeDelete = await devolucaoCompraComponentsPage.countDeleteButtons();
        await devolucaoCompraComponentsPage.clickOnLastDeleteButton();

        devolucaoCompraDeleteDialog = new DevolucaoCompraDeleteDialog();
        expect(await devolucaoCompraDeleteDialog.getDialogTitle())
            .to.eq('mandaApp.devolucaoCompra.delete.question');
        await devolucaoCompraDeleteDialog.clickOnConfirmButton();

        expect(await devolucaoCompraComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
