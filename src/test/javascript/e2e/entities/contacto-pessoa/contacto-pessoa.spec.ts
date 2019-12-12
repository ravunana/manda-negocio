import { browser, ExpectedConditions as ec /* , promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  ContactoPessoaComponentsPage,
  /* ContactoPessoaDeleteDialog,
   */ ContactoPessoaUpdatePage
} from './contacto-pessoa.page-object';

const expect = chai.expect;

describe('ContactoPessoa e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let contactoPessoaComponentsPage: ContactoPessoaComponentsPage;
  let contactoPessoaUpdatePage: ContactoPessoaUpdatePage;
  /* let contactoPessoaDeleteDialog: ContactoPessoaDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load ContactoPessoas', async () => {
    await navBarPage.goToEntity('contacto-pessoa');
    contactoPessoaComponentsPage = new ContactoPessoaComponentsPage();
    await browser.wait(ec.visibilityOf(contactoPessoaComponentsPage.title), 5000);
    expect(await contactoPessoaComponentsPage.getTitle()).to.eq('mandaApp.contactoPessoa.home.title');
  });

  it('should load create ContactoPessoa page', async () => {
    await contactoPessoaComponentsPage.clickOnCreateButton();
    contactoPessoaUpdatePage = new ContactoPessoaUpdatePage();
    expect(await contactoPessoaUpdatePage.getPageTitle()).to.eq('mandaApp.contactoPessoa.home.createOrEditLabel');
    await contactoPessoaUpdatePage.cancel();
  });

  /*  it('should create and save ContactoPessoas', async () => {
        const nbButtonsBeforeCreate = await contactoPessoaComponentsPage.countDeleteButtons();

        await contactoPessoaComponentsPage.clickOnCreateButton();
        await promise.all([
            contactoPessoaUpdatePage.setTipoContactoInput('tipoContacto'),
            contactoPessoaUpdatePage.setDescricaoInput('descricao'),
            contactoPessoaUpdatePage.setContactoInput('contacto'),
            contactoPessoaUpdatePage.pessoaSelectLastOption(),
        ]);
        expect(await contactoPessoaUpdatePage.getTipoContactoInput()).to.eq('tipoContacto', 'Expected TipoContacto value to be equals to tipoContacto');
        expect(await contactoPessoaUpdatePage.getDescricaoInput()).to.eq('descricao', 'Expected Descricao value to be equals to descricao');
        expect(await contactoPessoaUpdatePage.getContactoInput()).to.eq('contacto', 'Expected Contacto value to be equals to contacto');
        await contactoPessoaUpdatePage.save();
        expect(await contactoPessoaUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await contactoPessoaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /*  it('should delete last ContactoPessoa', async () => {
        const nbButtonsBeforeDelete = await contactoPessoaComponentsPage.countDeleteButtons();
        await contactoPessoaComponentsPage.clickOnLastDeleteButton();

        contactoPessoaDeleteDialog = new ContactoPessoaDeleteDialog();
        expect(await contactoPessoaDeleteDialog.getDialogTitle())
            .to.eq('mandaApp.contactoPessoa.delete.question');
        await contactoPessoaDeleteDialog.clickOnConfirmButton();

        expect(await contactoPessoaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
