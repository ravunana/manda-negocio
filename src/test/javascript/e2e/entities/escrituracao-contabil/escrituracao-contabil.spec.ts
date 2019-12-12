import { browser, ExpectedConditions as ec /* , protractor, promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  EscrituracaoContabilComponentsPage,
  /* EscrituracaoContabilDeleteDialog,
   */ EscrituracaoContabilUpdatePage
} from './escrituracao-contabil.page-object';

const expect = chai.expect;

describe('EscrituracaoContabil e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let escrituracaoContabilComponentsPage: EscrituracaoContabilComponentsPage;
  let escrituracaoContabilUpdatePage: EscrituracaoContabilUpdatePage;
  /* let escrituracaoContabilDeleteDialog: EscrituracaoContabilDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load EscrituracaoContabils', async () => {
    await navBarPage.goToEntity('escrituracao-contabil');
    escrituracaoContabilComponentsPage = new EscrituracaoContabilComponentsPage();
    await browser.wait(ec.visibilityOf(escrituracaoContabilComponentsPage.title), 5000);
    expect(await escrituracaoContabilComponentsPage.getTitle()).to.eq('mandaApp.escrituracaoContabil.home.title');
  });

  it('should load create EscrituracaoContabil page', async () => {
    await escrituracaoContabilComponentsPage.clickOnCreateButton();
    escrituracaoContabilUpdatePage = new EscrituracaoContabilUpdatePage();
    expect(await escrituracaoContabilUpdatePage.getPageTitle()).to.eq('mandaApp.escrituracaoContabil.home.createOrEditLabel');
    await escrituracaoContabilUpdatePage.cancel();
  });

  /*  it('should create and save EscrituracaoContabils', async () => {
        const nbButtonsBeforeCreate = await escrituracaoContabilComponentsPage.countDeleteButtons();

        await escrituracaoContabilComponentsPage.clickOnCreateButton();
        await promise.all([
            escrituracaoContabilUpdatePage.setNumeroInput('numero'),
            escrituracaoContabilUpdatePage.setHistoricoInput('historico'),
            escrituracaoContabilUpdatePage.setValorInput('5'),
            escrituracaoContabilUpdatePage.setReferenciaInput('referencia'),
            escrituracaoContabilUpdatePage.entidadeReferenciaSelectLastOption(),
            escrituracaoContabilUpdatePage.setTipoInput('tipo'),
            escrituracaoContabilUpdatePage.setDataDocumentoInput('2000-12-31'),
            escrituracaoContabilUpdatePage.setDataInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            escrituracaoContabilUpdatePage.utilizadorSelectLastOption(),
            escrituracaoContabilUpdatePage.empresaSelectLastOption(),
        ]);
        expect(await escrituracaoContabilUpdatePage.getNumeroInput()).to.eq('numero', 'Expected Numero value to be equals to numero');
        expect(await escrituracaoContabilUpdatePage.getHistoricoInput()).to.eq('historico', 'Expected Historico value to be equals to historico');
        expect(await escrituracaoContabilUpdatePage.getValorInput()).to.eq('5', 'Expected valor value to be equals to 5');
        expect(await escrituracaoContabilUpdatePage.getReferenciaInput()).to.eq('referencia', 'Expected Referencia value to be equals to referencia');
        expect(await escrituracaoContabilUpdatePage.getTipoInput()).to.eq('tipo', 'Expected Tipo value to be equals to tipo');
        expect(await escrituracaoContabilUpdatePage.getDataDocumentoInput()).to.eq('2000-12-31', 'Expected dataDocumento value to be equals to 2000-12-31');
        expect(await escrituracaoContabilUpdatePage.getDataInput()).to.contain('2001-01-01T02:30', 'Expected data value to be equals to 2000-12-31');
        await escrituracaoContabilUpdatePage.save();
        expect(await escrituracaoContabilUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await escrituracaoContabilComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /*  it('should delete last EscrituracaoContabil', async () => {
        const nbButtonsBeforeDelete = await escrituracaoContabilComponentsPage.countDeleteButtons();
        await escrituracaoContabilComponentsPage.clickOnLastDeleteButton();

        escrituracaoContabilDeleteDialog = new EscrituracaoContabilDeleteDialog();
        expect(await escrituracaoContabilDeleteDialog.getDialogTitle())
            .to.eq('mandaApp.escrituracaoContabil.delete.question');
        await escrituracaoContabilDeleteDialog.clickOnConfirmButton();

        expect(await escrituracaoContabilComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
