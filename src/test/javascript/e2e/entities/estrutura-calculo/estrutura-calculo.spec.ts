import { browser, ExpectedConditions as ec /* , protractor, promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  EstruturaCalculoComponentsPage,
  /* EstruturaCalculoDeleteDialog,
   */ EstruturaCalculoUpdatePage
} from './estrutura-calculo.page-object';

const expect = chai.expect;

describe('EstruturaCalculo e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let estruturaCalculoComponentsPage: EstruturaCalculoComponentsPage;
  let estruturaCalculoUpdatePage: EstruturaCalculoUpdatePage;
  /* let estruturaCalculoDeleteDialog: EstruturaCalculoDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load EstruturaCalculos', async () => {
    await navBarPage.goToEntity('estrutura-calculo');
    estruturaCalculoComponentsPage = new EstruturaCalculoComponentsPage();
    await browser.wait(ec.visibilityOf(estruturaCalculoComponentsPage.title), 5000);
    expect(await estruturaCalculoComponentsPage.getTitle()).to.eq('mandaApp.estruturaCalculo.home.title');
  });

  it('should load create EstruturaCalculo page', async () => {
    await estruturaCalculoComponentsPage.clickOnCreateButton();
    estruturaCalculoUpdatePage = new EstruturaCalculoUpdatePage();
    expect(await estruturaCalculoUpdatePage.getPageTitle()).to.eq('mandaApp.estruturaCalculo.home.createOrEditLabel');
    await estruturaCalculoUpdatePage.cancel();
  });

  /*  it('should create and save EstruturaCalculos', async () => {
        const nbButtonsBeforeCreate = await estruturaCalculoComponentsPage.countDeleteButtons();

        await estruturaCalculoComponentsPage.clickOnCreateButton();
        await promise.all([
            estruturaCalculoUpdatePage.setCustoMateriaPrimaInput('5'),
            estruturaCalculoUpdatePage.setCustoMaoObraInput('5'),
            estruturaCalculoUpdatePage.setCustoEmbalagemInput('5'),
            estruturaCalculoUpdatePage.setCustoAquisicaoInput('5'),
            estruturaCalculoUpdatePage.setComissaoInput('5'),
            estruturaCalculoUpdatePage.setMargemLucroInput('5'),
            estruturaCalculoUpdatePage.setPrecoVendaInput('5'),
            estruturaCalculoUpdatePage.setDataInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            estruturaCalculoUpdatePage.utilizadorSelectLastOption(),
            estruturaCalculoUpdatePage.produtoSelectLastOption(),
        ]);
        expect(await estruturaCalculoUpdatePage.getCustoMateriaPrimaInput()).to.eq('5', 'Expected custoMateriaPrima value to be equals to 5');
        expect(await estruturaCalculoUpdatePage.getCustoMaoObraInput()).to.eq('5', 'Expected custoMaoObra value to be equals to 5');
        expect(await estruturaCalculoUpdatePage.getCustoEmbalagemInput()).to.eq('5', 'Expected custoEmbalagem value to be equals to 5');
        expect(await estruturaCalculoUpdatePage.getCustoAquisicaoInput()).to.eq('5', 'Expected custoAquisicao value to be equals to 5');
        expect(await estruturaCalculoUpdatePage.getComissaoInput()).to.eq('5', 'Expected comissao value to be equals to 5');
        expect(await estruturaCalculoUpdatePage.getMargemLucroInput()).to.eq('5', 'Expected margemLucro value to be equals to 5');
        expect(await estruturaCalculoUpdatePage.getPrecoVendaInput()).to.eq('5', 'Expected precoVenda value to be equals to 5');
        expect(await estruturaCalculoUpdatePage.getDataInput()).to.contain('2001-01-01T02:30', 'Expected data value to be equals to 2000-12-31');
        await estruturaCalculoUpdatePage.save();
        expect(await estruturaCalculoUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await estruturaCalculoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /*  it('should delete last EstruturaCalculo', async () => {
        const nbButtonsBeforeDelete = await estruturaCalculoComponentsPage.countDeleteButtons();
        await estruturaCalculoComponentsPage.clickOnLastDeleteButton();

        estruturaCalculoDeleteDialog = new EstruturaCalculoDeleteDialog();
        expect(await estruturaCalculoDeleteDialog.getDialogTitle())
            .to.eq('mandaApp.estruturaCalculo.delete.question');
        await estruturaCalculoDeleteDialog.clickOnConfirmButton();

        expect(await estruturaCalculoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
