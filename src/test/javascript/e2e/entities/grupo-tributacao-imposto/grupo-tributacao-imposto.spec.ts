import { browser, ExpectedConditions as ec /* , promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  GrupoTributacaoImpostoComponentsPage,
  /* GrupoTributacaoImpostoDeleteDialog,
   */ GrupoTributacaoImpostoUpdatePage
} from './grupo-tributacao-imposto.page-object';

const expect = chai.expect;

describe('GrupoTributacaoImposto e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let grupoTributacaoImpostoComponentsPage: GrupoTributacaoImpostoComponentsPage;
  let grupoTributacaoImpostoUpdatePage: GrupoTributacaoImpostoUpdatePage;
  /* let grupoTributacaoImpostoDeleteDialog: GrupoTributacaoImpostoDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load GrupoTributacaoImpostos', async () => {
    await navBarPage.goToEntity('grupo-tributacao-imposto');
    grupoTributacaoImpostoComponentsPage = new GrupoTributacaoImpostoComponentsPage();
    await browser.wait(ec.visibilityOf(grupoTributacaoImpostoComponentsPage.title), 5000);
    expect(await grupoTributacaoImpostoComponentsPage.getTitle()).to.eq('mandaApp.grupoTributacaoImposto.home.title');
  });

  it('should load create GrupoTributacaoImposto page', async () => {
    await grupoTributacaoImpostoComponentsPage.clickOnCreateButton();
    grupoTributacaoImpostoUpdatePage = new GrupoTributacaoImpostoUpdatePage();
    expect(await grupoTributacaoImpostoUpdatePage.getPageTitle()).to.eq('mandaApp.grupoTributacaoImposto.home.createOrEditLabel');
    await grupoTributacaoImpostoUpdatePage.cancel();
  });

  /*  it('should create and save GrupoTributacaoImpostos', async () => {
        const nbButtonsBeforeCreate = await grupoTributacaoImpostoComponentsPage.countDeleteButtons();

        await grupoTributacaoImpostoComponentsPage.clickOnCreateButton();
        await promise.all([
            grupoTributacaoImpostoUpdatePage.setNomeInput('nome'),
            grupoTributacaoImpostoUpdatePage.setValorInput('5'),
            grupoTributacaoImpostoUpdatePage.setDeInput('5'),
            grupoTributacaoImpostoUpdatePage.setAteInput('5'),
            grupoTributacaoImpostoUpdatePage.setLimiteLiquidacaoInput('5'),
            grupoTributacaoImpostoUpdatePage.impostoSelectLastOption(),
            grupoTributacaoImpostoUpdatePage.unidadeLimiteSelectLastOption(),
        ]);
        expect(await grupoTributacaoImpostoUpdatePage.getNomeInput()).to.eq('nome', 'Expected Nome value to be equals to nome');
        expect(await grupoTributacaoImpostoUpdatePage.getValorInput()).to.eq('5', 'Expected valor value to be equals to 5');
        expect(await grupoTributacaoImpostoUpdatePage.getDeInput()).to.eq('5', 'Expected de value to be equals to 5');
        expect(await grupoTributacaoImpostoUpdatePage.getAteInput()).to.eq('5', 'Expected ate value to be equals to 5');
        expect(await grupoTributacaoImpostoUpdatePage.getLimiteLiquidacaoInput()).to.eq('5', 'Expected limiteLiquidacao value to be equals to 5');
        await grupoTributacaoImpostoUpdatePage.save();
        expect(await grupoTributacaoImpostoUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await grupoTributacaoImpostoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /*  it('should delete last GrupoTributacaoImposto', async () => {
        const nbButtonsBeforeDelete = await grupoTributacaoImpostoComponentsPage.countDeleteButtons();
        await grupoTributacaoImpostoComponentsPage.clickOnLastDeleteButton();

        grupoTributacaoImpostoDeleteDialog = new GrupoTributacaoImpostoDeleteDialog();
        expect(await grupoTributacaoImpostoDeleteDialog.getDialogTitle())
            .to.eq('mandaApp.grupoTributacaoImposto.delete.question');
        await grupoTributacaoImpostoDeleteDialog.clickOnConfirmButton();

        expect(await grupoTributacaoImpostoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
