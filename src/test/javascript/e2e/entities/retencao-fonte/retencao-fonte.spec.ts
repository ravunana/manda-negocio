import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { RetencaoFonteComponentsPage, RetencaoFonteDeleteDialog, RetencaoFonteUpdatePage } from './retencao-fonte.page-object';

const expect = chai.expect;

describe('RetencaoFonte e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let retencaoFonteComponentsPage: RetencaoFonteComponentsPage;
  let retencaoFonteUpdatePage: RetencaoFonteUpdatePage;
  let retencaoFonteDeleteDialog: RetencaoFonteDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load RetencaoFontes', async () => {
    await navBarPage.goToEntity('retencao-fonte');
    retencaoFonteComponentsPage = new RetencaoFonteComponentsPage();
    await browser.wait(ec.visibilityOf(retencaoFonteComponentsPage.title), 5000);
    expect(await retencaoFonteComponentsPage.getTitle()).to.eq('mandaApp.retencaoFonte.home.title');
  });

  it('should load create RetencaoFonte page', async () => {
    await retencaoFonteComponentsPage.clickOnCreateButton();
    retencaoFonteUpdatePage = new RetencaoFonteUpdatePage();
    expect(await retencaoFonteUpdatePage.getPageTitle()).to.eq('mandaApp.retencaoFonte.home.createOrEditLabel');
    await retencaoFonteUpdatePage.cancel();
  });

  it('should create and save RetencaoFontes', async () => {
    const nbButtonsBeforeCreate = await retencaoFonteComponentsPage.countDeleteButtons();

    await retencaoFonteComponentsPage.clickOnCreateButton();
    await promise.all([
      retencaoFonteUpdatePage.setMotivoRetencaoInput('motivoRetencao'),
      retencaoFonteUpdatePage.setValorInput('valor'),
      retencaoFonteUpdatePage.detalheSelectLastOption(),
      retencaoFonteUpdatePage.impostoSelectLastOption()
    ]);
    expect(await retencaoFonteUpdatePage.getMotivoRetencaoInput()).to.eq(
      'motivoRetencao',
      'Expected MotivoRetencao value to be equals to motivoRetencao'
    );
    expect(await retencaoFonteUpdatePage.getValorInput()).to.eq('valor', 'Expected Valor value to be equals to valor');
    const selectedPorcentagem = retencaoFonteUpdatePage.getPorcentagemInput();
    if (await selectedPorcentagem.isSelected()) {
      await retencaoFonteUpdatePage.getPorcentagemInput().click();
      expect(await retencaoFonteUpdatePage.getPorcentagemInput().isSelected(), 'Expected porcentagem not to be selected').to.be.false;
    } else {
      await retencaoFonteUpdatePage.getPorcentagemInput().click();
      expect(await retencaoFonteUpdatePage.getPorcentagemInput().isSelected(), 'Expected porcentagem to be selected').to.be.true;
    }
    await retencaoFonteUpdatePage.save();
    expect(await retencaoFonteUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await retencaoFonteComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last RetencaoFonte', async () => {
    const nbButtonsBeforeDelete = await retencaoFonteComponentsPage.countDeleteButtons();
    await retencaoFonteComponentsPage.clickOnLastDeleteButton();

    retencaoFonteDeleteDialog = new RetencaoFonteDeleteDialog();
    expect(await retencaoFonteDeleteDialog.getDialogTitle()).to.eq('mandaApp.retencaoFonte.delete.question');
    await retencaoFonteDeleteDialog.clickOnConfirmButton();

    expect(await retencaoFonteComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
