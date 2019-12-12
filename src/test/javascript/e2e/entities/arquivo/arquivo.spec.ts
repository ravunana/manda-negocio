import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ArquivoComponentsPage, ArquivoDeleteDialog, ArquivoUpdatePage } from './arquivo.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('Arquivo e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let arquivoComponentsPage: ArquivoComponentsPage;
  let arquivoUpdatePage: ArquivoUpdatePage;
  let arquivoDeleteDialog: ArquivoDeleteDialog;
  const fileNameToUpload = 'logo-jhipster.png';
  const fileToUpload = '../../../../../../src/main/webapp/content/images/' + fileNameToUpload;
  const absolutePath = path.resolve(__dirname, fileToUpload);

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Arquivos', async () => {
    await navBarPage.goToEntity('arquivo');
    arquivoComponentsPage = new ArquivoComponentsPage();
    await browser.wait(ec.visibilityOf(arquivoComponentsPage.title), 5000);
    expect(await arquivoComponentsPage.getTitle()).to.eq('mandaApp.arquivo.home.title');
  });

  it('should load create Arquivo page', async () => {
    await arquivoComponentsPage.clickOnCreateButton();
    arquivoUpdatePage = new ArquivoUpdatePage();
    expect(await arquivoUpdatePage.getPageTitle()).to.eq('mandaApp.arquivo.home.createOrEditLabel');
    await arquivoUpdatePage.cancel();
  });

  it('should create and save Arquivos', async () => {
    const nbButtonsBeforeCreate = await arquivoComponentsPage.countDeleteButtons();

    await arquivoComponentsPage.clickOnCreateButton();
    await promise.all([
      arquivoUpdatePage.setDescricaoInput('descricao'),
      arquivoUpdatePage.entidadeSelectLastOption(),
      arquivoUpdatePage.setAnexoInput(absolutePath),
      arquivoUpdatePage.setCodigoEntidadeInput('codigoEntidade'),
      arquivoUpdatePage.setDataInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      arquivoUpdatePage.utilizadorSelectLastOption()
    ]);
    expect(await arquivoUpdatePage.getDescricaoInput()).to.eq('descricao', 'Expected Descricao value to be equals to descricao');
    expect(await arquivoUpdatePage.getAnexoInput()).to.endsWith(
      fileNameToUpload,
      'Expected Anexo value to be end with ' + fileNameToUpload
    );
    expect(await arquivoUpdatePage.getCodigoEntidadeInput()).to.eq(
      'codigoEntidade',
      'Expected CodigoEntidade value to be equals to codigoEntidade'
    );
    expect(await arquivoUpdatePage.getDataInput()).to.contain('2001-01-01T02:30', 'Expected data value to be equals to 2000-12-31');
    await arquivoUpdatePage.save();
    expect(await arquivoUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await arquivoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Arquivo', async () => {
    const nbButtonsBeforeDelete = await arquivoComponentsPage.countDeleteButtons();
    await arquivoComponentsPage.clickOnLastDeleteButton();

    arquivoDeleteDialog = new ArquivoDeleteDialog();
    expect(await arquivoDeleteDialog.getDialogTitle()).to.eq('mandaApp.arquivo.delete.question');
    await arquivoDeleteDialog.clickOnConfirmButton();

    expect(await arquivoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
