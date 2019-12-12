import { browser, ExpectedConditions as ec /* , promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  DetalheAduaneiroComponentsPage,
  /* DetalheAduaneiroDeleteDialog,
   */ DetalheAduaneiroUpdatePage
} from './detalhe-aduaneiro.page-object';

const expect = chai.expect;

describe('DetalheAduaneiro e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let detalheAduaneiroComponentsPage: DetalheAduaneiroComponentsPage;
  let detalheAduaneiroUpdatePage: DetalheAduaneiroUpdatePage;
  /* let detalheAduaneiroDeleteDialog: DetalheAduaneiroDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load DetalheAduaneiros', async () => {
    await navBarPage.goToEntity('detalhe-aduaneiro');
    detalheAduaneiroComponentsPage = new DetalheAduaneiroComponentsPage();
    await browser.wait(ec.visibilityOf(detalheAduaneiroComponentsPage.title), 5000);
    expect(await detalheAduaneiroComponentsPage.getTitle()).to.eq('mandaApp.detalheAduaneiro.home.title');
  });

  it('should load create DetalheAduaneiro page', async () => {
    await detalheAduaneiroComponentsPage.clickOnCreateButton();
    detalheAduaneiroUpdatePage = new DetalheAduaneiroUpdatePage();
    expect(await detalheAduaneiroUpdatePage.getPageTitle()).to.eq('mandaApp.detalheAduaneiro.home.createOrEditLabel');
    await detalheAduaneiroUpdatePage.cancel();
  });

  /*  it('should create and save DetalheAduaneiros', async () => {
        const nbButtonsBeforeCreate = await detalheAduaneiroComponentsPage.countDeleteButtons();

        await detalheAduaneiroComponentsPage.clickOnCreateButton();
        await promise.all([
            detalheAduaneiroUpdatePage.setNumeroONUInput('numeroONU'),
            detalheAduaneiroUpdatePage.setLarguraInput('5'),
            detalheAduaneiroUpdatePage.setAlturaInput('5'),
            detalheAduaneiroUpdatePage.setPesoLiquidoInput('5'),
            detalheAduaneiroUpdatePage.setPesoBrutoInput('5'),
            detalheAduaneiroUpdatePage.setDataFabricoInput('2000-12-31'),
            detalheAduaneiroUpdatePage.setDataExpiracaoInput('2000-12-31'),
            detalheAduaneiroUpdatePage.produtoSelectLastOption(),
        ]);
        expect(await detalheAduaneiroUpdatePage.getNumeroONUInput()).to.eq('numeroONU', 'Expected NumeroONU value to be equals to numeroONU');
        expect(await detalheAduaneiroUpdatePage.getLarguraInput()).to.eq('5', 'Expected largura value to be equals to 5');
        expect(await detalheAduaneiroUpdatePage.getAlturaInput()).to.eq('5', 'Expected altura value to be equals to 5');
        expect(await detalheAduaneiroUpdatePage.getPesoLiquidoInput()).to.eq('5', 'Expected pesoLiquido value to be equals to 5');
        expect(await detalheAduaneiroUpdatePage.getPesoBrutoInput()).to.eq('5', 'Expected pesoBruto value to be equals to 5');
        expect(await detalheAduaneiroUpdatePage.getDataFabricoInput()).to.eq('2000-12-31', 'Expected dataFabrico value to be equals to 2000-12-31');
        expect(await detalheAduaneiroUpdatePage.getDataExpiracaoInput()).to.eq('2000-12-31', 'Expected dataExpiracao value to be equals to 2000-12-31');
        await detalheAduaneiroUpdatePage.save();
        expect(await detalheAduaneiroUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await detalheAduaneiroComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /*  it('should delete last DetalheAduaneiro', async () => {
        const nbButtonsBeforeDelete = await detalheAduaneiroComponentsPage.countDeleteButtons();
        await detalheAduaneiroComponentsPage.clickOnLastDeleteButton();

        detalheAduaneiroDeleteDialog = new DetalheAduaneiroDeleteDialog();
        expect(await detalheAduaneiroDeleteDialog.getDialogTitle())
            .to.eq('mandaApp.detalheAduaneiro.delete.question');
        await detalheAduaneiroDeleteDialog.clickOnConfirmButton();

        expect(await detalheAduaneiroComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
