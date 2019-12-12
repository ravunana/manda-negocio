import { browser, ExpectedConditions as ec /* , protractor, promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  LicencaSoftwareComponentsPage,
  /* LicencaSoftwareDeleteDialog,
   */ LicencaSoftwareUpdatePage
} from './licenca-software.page-object';

const expect = chai.expect;

describe('LicencaSoftware e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let licencaSoftwareComponentsPage: LicencaSoftwareComponentsPage;
  let licencaSoftwareUpdatePage: LicencaSoftwareUpdatePage;
  /* let licencaSoftwareDeleteDialog: LicencaSoftwareDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load LicencaSoftwares', async () => {
    await navBarPage.goToEntity('licenca-software');
    licencaSoftwareComponentsPage = new LicencaSoftwareComponentsPage();
    await browser.wait(ec.visibilityOf(licencaSoftwareComponentsPage.title), 5000);
    expect(await licencaSoftwareComponentsPage.getTitle()).to.eq('mandaApp.licencaSoftware.home.title');
  });

  it('should load create LicencaSoftware page', async () => {
    await licencaSoftwareComponentsPage.clickOnCreateButton();
    licencaSoftwareUpdatePage = new LicencaSoftwareUpdatePage();
    expect(await licencaSoftwareUpdatePage.getPageTitle()).to.eq('mandaApp.licencaSoftware.home.createOrEditLabel');
    await licencaSoftwareUpdatePage.cancel();
  });

  /*  it('should create and save LicencaSoftwares', async () => {
        const nbButtonsBeforeCreate = await licencaSoftwareComponentsPage.countDeleteButtons();

        await licencaSoftwareComponentsPage.clickOnCreateButton();
        await promise.all([
            licencaSoftwareUpdatePage.setTipoSubscricaoInput('tipoSubscricao'),
            licencaSoftwareUpdatePage.setInicioInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            licencaSoftwareUpdatePage.setFimInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            licencaSoftwareUpdatePage.setDataInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            licencaSoftwareUpdatePage.setValorInput('5'),
            licencaSoftwareUpdatePage.setCodigoInput('codigo'),
            licencaSoftwareUpdatePage.setNumeroUsuarioInput('5'),
            licencaSoftwareUpdatePage.setNumeroEmpresaInput('5'),
            licencaSoftwareUpdatePage.softwareSelectLastOption(),
            licencaSoftwareUpdatePage.empresaSelectLastOption(),
        ]);
        expect(await licencaSoftwareUpdatePage.getTipoSubscricaoInput()).to.eq('tipoSubscricao', 'Expected TipoSubscricao value to be equals to tipoSubscricao');
        expect(await licencaSoftwareUpdatePage.getInicioInput()).to.contain('2001-01-01T02:30', 'Expected inicio value to be equals to 2000-12-31');
        expect(await licencaSoftwareUpdatePage.getFimInput()).to.contain('2001-01-01T02:30', 'Expected fim value to be equals to 2000-12-31');
        expect(await licencaSoftwareUpdatePage.getDataInput()).to.contain('2001-01-01T02:30', 'Expected data value to be equals to 2000-12-31');
        expect(await licencaSoftwareUpdatePage.getValorInput()).to.eq('5', 'Expected valor value to be equals to 5');
        expect(await licencaSoftwareUpdatePage.getCodigoInput()).to.eq('codigo', 'Expected Codigo value to be equals to codigo');
        expect(await licencaSoftwareUpdatePage.getNumeroUsuarioInput()).to.eq('5', 'Expected numeroUsuario value to be equals to 5');
        expect(await licencaSoftwareUpdatePage.getNumeroEmpresaInput()).to.eq('5', 'Expected numeroEmpresa value to be equals to 5');
        await licencaSoftwareUpdatePage.save();
        expect(await licencaSoftwareUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await licencaSoftwareComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /*  it('should delete last LicencaSoftware', async () => {
        const nbButtonsBeforeDelete = await licencaSoftwareComponentsPage.countDeleteButtons();
        await licencaSoftwareComponentsPage.clickOnLastDeleteButton();

        licencaSoftwareDeleteDialog = new LicencaSoftwareDeleteDialog();
        expect(await licencaSoftwareDeleteDialog.getDialogTitle())
            .to.eq('mandaApp.licencaSoftware.delete.question');
        await licencaSoftwareDeleteDialog.clickOnConfirmButton();

        expect(await licencaSoftwareComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
