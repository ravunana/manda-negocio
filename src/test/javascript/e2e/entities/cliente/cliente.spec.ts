import { browser, ExpectedConditions as ec /* , promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  ClienteComponentsPage,
  /* ClienteDeleteDialog,
   */ ClienteUpdatePage
} from './cliente.page-object';

const expect = chai.expect;

describe('Cliente e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let clienteComponentsPage: ClienteComponentsPage;
  let clienteUpdatePage: ClienteUpdatePage;
  /* let clienteDeleteDialog: ClienteDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Clientes', async () => {
    await navBarPage.goToEntity('cliente');
    clienteComponentsPage = new ClienteComponentsPage();
    await browser.wait(ec.visibilityOf(clienteComponentsPage.title), 5000);
    expect(await clienteComponentsPage.getTitle()).to.eq('mandaApp.cliente.home.title');
  });

  it('should load create Cliente page', async () => {
    await clienteComponentsPage.clickOnCreateButton();
    clienteUpdatePage = new ClienteUpdatePage();
    expect(await clienteUpdatePage.getPageTitle()).to.eq('mandaApp.cliente.home.createOrEditLabel');
    await clienteUpdatePage.cancel();
  });

  /*  it('should create and save Clientes', async () => {
        const nbButtonsBeforeCreate = await clienteComponentsPage.countDeleteButtons();

        await clienteComponentsPage.clickOnCreateButton();
        await promise.all([
            clienteUpdatePage.setPerfilProfissionalInput('perfilProfissional'),
            clienteUpdatePage.setSatisfacaoInput('5'),
            clienteUpdatePage.setFrequenciaInput('5'),
            clienteUpdatePage.setCanalUsadoInput('canalUsado'),
            clienteUpdatePage.setNumeroInput('numero'),
            clienteUpdatePage.pessoaSelectLastOption(),
            clienteUpdatePage.contaSelectLastOption(),
        ]);
        const selectedAtivo = clienteUpdatePage.getAtivoInput();
        if (await selectedAtivo.isSelected()) {
            await clienteUpdatePage.getAtivoInput().click();
            expect(await clienteUpdatePage.getAtivoInput().isSelected(), 'Expected ativo not to be selected').to.be.false;
        } else {
            await clienteUpdatePage.getAtivoInput().click();
            expect(await clienteUpdatePage.getAtivoInput().isSelected(), 'Expected ativo to be selected').to.be.true;
        }
        expect(await clienteUpdatePage.getPerfilProfissionalInput()).to.eq('perfilProfissional', 'Expected PerfilProfissional value to be equals to perfilProfissional');
        expect(await clienteUpdatePage.getSatisfacaoInput()).to.eq('5', 'Expected satisfacao value to be equals to 5');
        expect(await clienteUpdatePage.getFrequenciaInput()).to.eq('5', 'Expected frequencia value to be equals to 5');
        expect(await clienteUpdatePage.getCanalUsadoInput()).to.eq('canalUsado', 'Expected CanalUsado value to be equals to canalUsado');
        expect(await clienteUpdatePage.getNumeroInput()).to.eq('numero', 'Expected Numero value to be equals to numero');
        const selectedAutofacturacao = clienteUpdatePage.getAutofacturacaoInput();
        if (await selectedAutofacturacao.isSelected()) {
            await clienteUpdatePage.getAutofacturacaoInput().click();
            expect(await clienteUpdatePage.getAutofacturacaoInput().isSelected(), 'Expected autofacturacao not to be selected').to.be.false;
        } else {
            await clienteUpdatePage.getAutofacturacaoInput().click();
            expect(await clienteUpdatePage.getAutofacturacaoInput().isSelected(), 'Expected autofacturacao to be selected').to.be.true;
        }
        await clienteUpdatePage.save();
        expect(await clienteUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await clienteComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /*  it('should delete last Cliente', async () => {
        const nbButtonsBeforeDelete = await clienteComponentsPage.countDeleteButtons();
        await clienteComponentsPage.clickOnLastDeleteButton();

        clienteDeleteDialog = new ClienteDeleteDialog();
        expect(await clienteDeleteDialog.getDialogTitle())
            .to.eq('mandaApp.cliente.delete.question');
        await clienteDeleteDialog.clickOnConfirmButton();

        expect(await clienteComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
