import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ImpostoComponentsPage, ImpostoDeleteDialog, ImpostoUpdatePage } from './imposto.page-object';

const expect = chai.expect;

describe('Imposto e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let impostoComponentsPage: ImpostoComponentsPage;
  let impostoUpdatePage: ImpostoUpdatePage;
  let impostoDeleteDialog: ImpostoDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Impostos', async () => {
    await navBarPage.goToEntity('imposto');
    impostoComponentsPage = new ImpostoComponentsPage();
    await browser.wait(ec.visibilityOf(impostoComponentsPage.title), 5000);
    expect(await impostoComponentsPage.getTitle()).to.eq('mandaApp.imposto.home.title');
  });

  it('should load create Imposto page', async () => {
    await impostoComponentsPage.clickOnCreateButton();
    impostoUpdatePage = new ImpostoUpdatePage();
    expect(await impostoUpdatePage.getPageTitle()).to.eq('mandaApp.imposto.home.createOrEditLabel');
    await impostoUpdatePage.cancel();
  });

  it('should create and save Impostos', async () => {
    const nbButtonsBeforeCreate = await impostoComponentsPage.countDeleteButtons();

    await impostoComponentsPage.clickOnCreateButton();
    await promise.all([
      impostoUpdatePage.setTipoImpostoInput('tipoImposto'),
      impostoUpdatePage.setCodigoImpostoInput('codigoImposto'),
      impostoUpdatePage.setValorInput('5'),
      impostoUpdatePage.setDescricaoInput('descricao'),
      impostoUpdatePage.setPaisInput('pais'),
      impostoUpdatePage.setVigenciaInput('2000-12-31'),
      impostoUpdatePage.contaSelectLastOption()
    ]);
    expect(await impostoUpdatePage.getTipoImpostoInput()).to.eq('tipoImposto', 'Expected TipoImposto value to be equals to tipoImposto');
    expect(await impostoUpdatePage.getCodigoImpostoInput()).to.eq(
      'codigoImposto',
      'Expected CodigoImposto value to be equals to codigoImposto'
    );
    const selectedPorcentagem = impostoUpdatePage.getPorcentagemInput();
    if (await selectedPorcentagem.isSelected()) {
      await impostoUpdatePage.getPorcentagemInput().click();
      expect(await impostoUpdatePage.getPorcentagemInput().isSelected(), 'Expected porcentagem not to be selected').to.be.false;
    } else {
      await impostoUpdatePage.getPorcentagemInput().click();
      expect(await impostoUpdatePage.getPorcentagemInput().isSelected(), 'Expected porcentagem to be selected').to.be.true;
    }
    expect(await impostoUpdatePage.getValorInput()).to.eq('5', 'Expected valor value to be equals to 5');
    expect(await impostoUpdatePage.getDescricaoInput()).to.eq('descricao', 'Expected Descricao value to be equals to descricao');
    expect(await impostoUpdatePage.getPaisInput()).to.eq('pais', 'Expected Pais value to be equals to pais');
    expect(await impostoUpdatePage.getVigenciaInput()).to.eq('2000-12-31', 'Expected vigencia value to be equals to 2000-12-31');
    await impostoUpdatePage.save();
    expect(await impostoUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await impostoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Imposto', async () => {
    const nbButtonsBeforeDelete = await impostoComponentsPage.countDeleteButtons();
    await impostoComponentsPage.clickOnLastDeleteButton();

    impostoDeleteDialog = new ImpostoDeleteDialog();
    expect(await impostoDeleteDialog.getDialogTitle()).to.eq('mandaApp.imposto.delete.question');
    await impostoDeleteDialog.clickOnConfirmButton();

    expect(await impostoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
