import { browser, ExpectedConditions as ec /* , promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  LancamentoFinanceiroComponentsPage,
  /* LancamentoFinanceiroDeleteDialog,
   */ LancamentoFinanceiroUpdatePage
} from './lancamento-financeiro.page-object';

const expect = chai.expect;

describe('LancamentoFinanceiro e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let lancamentoFinanceiroComponentsPage: LancamentoFinanceiroComponentsPage;
  let lancamentoFinanceiroUpdatePage: LancamentoFinanceiroUpdatePage;
  /* let lancamentoFinanceiroDeleteDialog: LancamentoFinanceiroDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load LancamentoFinanceiros', async () => {
    await navBarPage.goToEntity('lancamento-financeiro');
    lancamentoFinanceiroComponentsPage = new LancamentoFinanceiroComponentsPage();
    await browser.wait(ec.visibilityOf(lancamentoFinanceiroComponentsPage.title), 5000);
    expect(await lancamentoFinanceiroComponentsPage.getTitle()).to.eq('mandaApp.lancamentoFinanceiro.home.title');
  });

  it('should load create LancamentoFinanceiro page', async () => {
    await lancamentoFinanceiroComponentsPage.clickOnCreateButton();
    lancamentoFinanceiroUpdatePage = new LancamentoFinanceiroUpdatePage();
    expect(await lancamentoFinanceiroUpdatePage.getPageTitle()).to.eq('mandaApp.lancamentoFinanceiro.home.createOrEditLabel');
    await lancamentoFinanceiroUpdatePage.cancel();
  });

  /*  it('should create and save LancamentoFinanceiros', async () => {
        const nbButtonsBeforeCreate = await lancamentoFinanceiroComponentsPage.countDeleteButtons();

        await lancamentoFinanceiroComponentsPage.clickOnCreateButton();
        await promise.all([
            lancamentoFinanceiroUpdatePage.setTipoLancamentoInput('tipoLancamento'),
            lancamentoFinanceiroUpdatePage.setValorInput('5'),
            lancamentoFinanceiroUpdatePage.setNumeroInput('numero'),
            lancamentoFinanceiroUpdatePage.setDescricaoInput('descricao'),
            lancamentoFinanceiroUpdatePage.utilizadorSelectLastOption(),
            lancamentoFinanceiroUpdatePage.contaSelectLastOption(),
            // lancamentoFinanceiroUpdatePage.impostoSelectLastOption(),
            lancamentoFinanceiroUpdatePage.formaLiquidacaoSelectLastOption(),
            lancamentoFinanceiroUpdatePage.empresaSelectLastOption(),
            lancamentoFinanceiroUpdatePage.tipoReciboSelectLastOption(),
        ]);
        expect(await lancamentoFinanceiroUpdatePage.getTipoLancamentoInput()).to.eq('tipoLancamento', 'Expected TipoLancamento value to be equals to tipoLancamento');
        expect(await lancamentoFinanceiroUpdatePage.getValorInput()).to.eq('5', 'Expected valor value to be equals to 5');
        const selectedExterno = lancamentoFinanceiroUpdatePage.getExternoInput();
        if (await selectedExterno.isSelected()) {
            await lancamentoFinanceiroUpdatePage.getExternoInput().click();
            expect(await lancamentoFinanceiroUpdatePage.getExternoInput().isSelected(), 'Expected externo not to be selected').to.be.false;
        } else {
            await lancamentoFinanceiroUpdatePage.getExternoInput().click();
            expect(await lancamentoFinanceiroUpdatePage.getExternoInput().isSelected(), 'Expected externo to be selected').to.be.true;
        }
        expect(await lancamentoFinanceiroUpdatePage.getNumeroInput()).to.eq('numero', 'Expected Numero value to be equals to numero');
        expect(await lancamentoFinanceiroUpdatePage.getDescricaoInput()).to.eq('descricao', 'Expected Descricao value to be equals to descricao');
        await lancamentoFinanceiroUpdatePage.save();
        expect(await lancamentoFinanceiroUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await lancamentoFinanceiroComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /*  it('should delete last LancamentoFinanceiro', async () => {
        const nbButtonsBeforeDelete = await lancamentoFinanceiroComponentsPage.countDeleteButtons();
        await lancamentoFinanceiroComponentsPage.clickOnLastDeleteButton();

        lancamentoFinanceiroDeleteDialog = new LancamentoFinanceiroDeleteDialog();
        expect(await lancamentoFinanceiroDeleteDialog.getDialogTitle())
            .to.eq('mandaApp.lancamentoFinanceiro.delete.question');
        await lancamentoFinanceiroDeleteDialog.clickOnConfirmButton();

        expect(await lancamentoFinanceiroComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
