import { browser, ExpectedConditions as ec /* , protractor, promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  DetalheLancamentoComponentsPage,
  /* DetalheLancamentoDeleteDialog,
   */ DetalheLancamentoUpdatePage
} from './detalhe-lancamento.page-object';

const expect = chai.expect;

describe('DetalheLancamento e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let detalheLancamentoComponentsPage: DetalheLancamentoComponentsPage;
  let detalheLancamentoUpdatePage: DetalheLancamentoUpdatePage;
  /* let detalheLancamentoDeleteDialog: DetalheLancamentoDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load DetalheLancamentos', async () => {
    await navBarPage.goToEntity('detalhe-lancamento');
    detalheLancamentoComponentsPage = new DetalheLancamentoComponentsPage();
    await browser.wait(ec.visibilityOf(detalheLancamentoComponentsPage.title), 5000);
    expect(await detalheLancamentoComponentsPage.getTitle()).to.eq('mandaApp.detalheLancamento.home.title');
  });

  it('should load create DetalheLancamento page', async () => {
    await detalheLancamentoComponentsPage.clickOnCreateButton();
    detalheLancamentoUpdatePage = new DetalheLancamentoUpdatePage();
    expect(await detalheLancamentoUpdatePage.getPageTitle()).to.eq('mandaApp.detalheLancamento.home.createOrEditLabel');
    await detalheLancamentoUpdatePage.cancel();
  });

  /*  it('should create and save DetalheLancamentos', async () => {
        const nbButtonsBeforeCreate = await detalheLancamentoComponentsPage.countDeleteButtons();

        await detalheLancamentoComponentsPage.clickOnCreateButton();
        await promise.all([
            detalheLancamentoUpdatePage.setValorInput('5'),
            detalheLancamentoUpdatePage.setDescontoInput('5'),
            detalheLancamentoUpdatePage.setJuroInput('5'),
            detalheLancamentoUpdatePage.setDescricaoInput('descricao'),
            detalheLancamentoUpdatePage.setDataInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            detalheLancamentoUpdatePage.setDataVencimentoInput('2000-12-31'),
            detalheLancamentoUpdatePage.utilizadorSelectLastOption(),
            detalheLancamentoUpdatePage.lancamentoFinanceiroSelectLastOption(),
            detalheLancamentoUpdatePage.metodoLiquidacaoSelectLastOption(),
            detalheLancamentoUpdatePage.moedaSelectLastOption(),
            detalheLancamentoUpdatePage.coordenadaSelectLastOption(),
        ]);
        expect(await detalheLancamentoUpdatePage.getValorInput()).to.eq('5', 'Expected valor value to be equals to 5');
        expect(await detalheLancamentoUpdatePage.getDescontoInput()).to.eq('5', 'Expected desconto value to be equals to 5');
        expect(await detalheLancamentoUpdatePage.getJuroInput()).to.eq('5', 'Expected juro value to be equals to 5');
        expect(await detalheLancamentoUpdatePage.getDescricaoInput()).to.eq('descricao', 'Expected Descricao value to be equals to descricao');
        expect(await detalheLancamentoUpdatePage.getDataInput()).to.contain('2001-01-01T02:30', 'Expected data value to be equals to 2000-12-31');
        const selectedRetencaoFonte = detalheLancamentoUpdatePage.getRetencaoFonteInput();
        if (await selectedRetencaoFonte.isSelected()) {
            await detalheLancamentoUpdatePage.getRetencaoFonteInput().click();
            expect(await detalheLancamentoUpdatePage.getRetencaoFonteInput().isSelected(), 'Expected retencaoFonte not to be selected').to.be.false;
        } else {
            await detalheLancamentoUpdatePage.getRetencaoFonteInput().click();
            expect(await detalheLancamentoUpdatePage.getRetencaoFonteInput().isSelected(), 'Expected retencaoFonte to be selected').to.be.true;
        }
        expect(await detalheLancamentoUpdatePage.getDataVencimentoInput()).to.eq('2000-12-31', 'Expected dataVencimento value to be equals to 2000-12-31');
        const selectedLiquidado = detalheLancamentoUpdatePage.getLiquidadoInput();
        if (await selectedLiquidado.isSelected()) {
            await detalheLancamentoUpdatePage.getLiquidadoInput().click();
            expect(await detalheLancamentoUpdatePage.getLiquidadoInput().isSelected(), 'Expected liquidado not to be selected').to.be.false;
        } else {
            await detalheLancamentoUpdatePage.getLiquidadoInput().click();
            expect(await detalheLancamentoUpdatePage.getLiquidadoInput().isSelected(), 'Expected liquidado to be selected').to.be.true;
        }
        await detalheLancamentoUpdatePage.save();
        expect(await detalheLancamentoUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await detalheLancamentoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /*  it('should delete last DetalheLancamento', async () => {
        const nbButtonsBeforeDelete = await detalheLancamentoComponentsPage.countDeleteButtons();
        await detalheLancamentoComponentsPage.clickOnLastDeleteButton();

        detalheLancamentoDeleteDialog = new DetalheLancamentoDeleteDialog();
        expect(await detalheLancamentoDeleteDialog.getDialogTitle())
            .to.eq('mandaApp.detalheLancamento.delete.question');
        await detalheLancamentoDeleteDialog.clickOnConfirmButton();

        expect(await detalheLancamentoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
