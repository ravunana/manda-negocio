import { browser, ExpectedConditions as ec /* , promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  ContactoEmpresaComponentsPage,
  /* ContactoEmpresaDeleteDialog,
   */ ContactoEmpresaUpdatePage
} from './contacto-empresa.page-object';

const expect = chai.expect;

describe('ContactoEmpresa e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let contactoEmpresaComponentsPage: ContactoEmpresaComponentsPage;
  let contactoEmpresaUpdatePage: ContactoEmpresaUpdatePage;
  /* let contactoEmpresaDeleteDialog: ContactoEmpresaDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load ContactoEmpresas', async () => {
    await navBarPage.goToEntity('contacto-empresa');
    contactoEmpresaComponentsPage = new ContactoEmpresaComponentsPage();
    await browser.wait(ec.visibilityOf(contactoEmpresaComponentsPage.title), 5000);
    expect(await contactoEmpresaComponentsPage.getTitle()).to.eq('mandaApp.contactoEmpresa.home.title');
  });

  it('should load create ContactoEmpresa page', async () => {
    await contactoEmpresaComponentsPage.clickOnCreateButton();
    contactoEmpresaUpdatePage = new ContactoEmpresaUpdatePage();
    expect(await contactoEmpresaUpdatePage.getPageTitle()).to.eq('mandaApp.contactoEmpresa.home.createOrEditLabel');
    await contactoEmpresaUpdatePage.cancel();
  });

  /*  it('should create and save ContactoEmpresas', async () => {
        const nbButtonsBeforeCreate = await contactoEmpresaComponentsPage.countDeleteButtons();

        await contactoEmpresaComponentsPage.clickOnCreateButton();
        await promise.all([
            contactoEmpresaUpdatePage.setTipoContactoInput('tipoContacto'),
            contactoEmpresaUpdatePage.setDescricaoInput('descricao'),
            contactoEmpresaUpdatePage.setContactoInput('contacto'),
            contactoEmpresaUpdatePage.empresaSelectLastOption(),
        ]);
        expect(await contactoEmpresaUpdatePage.getTipoContactoInput()).to.eq('tipoContacto', 'Expected TipoContacto value to be equals to tipoContacto');
        expect(await contactoEmpresaUpdatePage.getDescricaoInput()).to.eq('descricao', 'Expected Descricao value to be equals to descricao');
        expect(await contactoEmpresaUpdatePage.getContactoInput()).to.eq('contacto', 'Expected Contacto value to be equals to contacto');
        const selectedPadrao = contactoEmpresaUpdatePage.getPadraoInput();
        if (await selectedPadrao.isSelected()) {
            await contactoEmpresaUpdatePage.getPadraoInput().click();
            expect(await contactoEmpresaUpdatePage.getPadraoInput().isSelected(), 'Expected padrao not to be selected').to.be.false;
        } else {
            await contactoEmpresaUpdatePage.getPadraoInput().click();
            expect(await contactoEmpresaUpdatePage.getPadraoInput().isSelected(), 'Expected padrao to be selected').to.be.true;
        }
        await contactoEmpresaUpdatePage.save();
        expect(await contactoEmpresaUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await contactoEmpresaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /*  it('should delete last ContactoEmpresa', async () => {
        const nbButtonsBeforeDelete = await contactoEmpresaComponentsPage.countDeleteButtons();
        await contactoEmpresaComponentsPage.clickOnLastDeleteButton();

        contactoEmpresaDeleteDialog = new ContactoEmpresaDeleteDialog();
        expect(await contactoEmpresaDeleteDialog.getDialogTitle())
            .to.eq('mandaApp.contactoEmpresa.delete.question');
        await contactoEmpresaDeleteDialog.clickOnConfirmButton();

        expect(await contactoEmpresaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
