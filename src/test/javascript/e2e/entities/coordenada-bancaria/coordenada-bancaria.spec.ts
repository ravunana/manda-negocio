import { browser, ExpectedConditions as ec /* , promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  CoordenadaBancariaComponentsPage,
  /* CoordenadaBancariaDeleteDialog,
   */ CoordenadaBancariaUpdatePage
} from './coordenada-bancaria.page-object';

const expect = chai.expect;

describe('CoordenadaBancaria e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let coordenadaBancariaComponentsPage: CoordenadaBancariaComponentsPage;
  let coordenadaBancariaUpdatePage: CoordenadaBancariaUpdatePage;
  /* let coordenadaBancariaDeleteDialog: CoordenadaBancariaDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load CoordenadaBancarias', async () => {
    await navBarPage.goToEntity('coordenada-bancaria');
    coordenadaBancariaComponentsPage = new CoordenadaBancariaComponentsPage();
    await browser.wait(ec.visibilityOf(coordenadaBancariaComponentsPage.title), 5000);
    expect(await coordenadaBancariaComponentsPage.getTitle()).to.eq('mandaApp.coordenadaBancaria.home.title');
  });

  it('should load create CoordenadaBancaria page', async () => {
    await coordenadaBancariaComponentsPage.clickOnCreateButton();
    coordenadaBancariaUpdatePage = new CoordenadaBancariaUpdatePage();
    expect(await coordenadaBancariaUpdatePage.getPageTitle()).to.eq('mandaApp.coordenadaBancaria.home.createOrEditLabel');
    await coordenadaBancariaUpdatePage.cancel();
  });

  /*  it('should create and save CoordenadaBancarias', async () => {
        const nbButtonsBeforeCreate = await coordenadaBancariaComponentsPage.countDeleteButtons();

        await coordenadaBancariaComponentsPage.clickOnCreateButton();
        await promise.all([
            coordenadaBancariaUpdatePage.setDescricaoInput('descricao'),
            coordenadaBancariaUpdatePage.setProprietarioInput('proprietario'),
            coordenadaBancariaUpdatePage.setNumeroContaInput('numeroConta'),
            coordenadaBancariaUpdatePage.setIbanInput('iban'),
            coordenadaBancariaUpdatePage.contaSelectLastOption(),
            // coordenadaBancariaUpdatePage.empresaSelectLastOption(),
        ]);
        expect(await coordenadaBancariaUpdatePage.getDescricaoInput()).to.eq('descricao', 'Expected Descricao value to be equals to descricao');
        expect(await coordenadaBancariaUpdatePage.getProprietarioInput()).to.eq('proprietario', 'Expected Proprietario value to be equals to proprietario');
        expect(await coordenadaBancariaUpdatePage.getNumeroContaInput()).to.eq('numeroConta', 'Expected NumeroConta value to be equals to numeroConta');
        expect(await coordenadaBancariaUpdatePage.getIbanInput()).to.eq('iban', 'Expected Iban value to be equals to iban');
        const selectedAtivo = coordenadaBancariaUpdatePage.getAtivoInput();
        if (await selectedAtivo.isSelected()) {
            await coordenadaBancariaUpdatePage.getAtivoInput().click();
            expect(await coordenadaBancariaUpdatePage.getAtivoInput().isSelected(), 'Expected ativo not to be selected').to.be.false;
        } else {
            await coordenadaBancariaUpdatePage.getAtivoInput().click();
            expect(await coordenadaBancariaUpdatePage.getAtivoInput().isSelected(), 'Expected ativo to be selected').to.be.true;
        }
        const selectedMostrarDocumento = coordenadaBancariaUpdatePage.getMostrarDocumentoInput();
        if (await selectedMostrarDocumento.isSelected()) {
            await coordenadaBancariaUpdatePage.getMostrarDocumentoInput().click();
            expect(await coordenadaBancariaUpdatePage.getMostrarDocumentoInput().isSelected(), 'Expected mostrarDocumento not to be selected').to.be.false;
        } else {
            await coordenadaBancariaUpdatePage.getMostrarDocumentoInput().click();
            expect(await coordenadaBancariaUpdatePage.getMostrarDocumentoInput().isSelected(), 'Expected mostrarDocumento to be selected').to.be.true;
        }
        const selectedMostrarPontoVenda = coordenadaBancariaUpdatePage.getMostrarPontoVendaInput();
        if (await selectedMostrarPontoVenda.isSelected()) {
            await coordenadaBancariaUpdatePage.getMostrarPontoVendaInput().click();
            expect(await coordenadaBancariaUpdatePage.getMostrarPontoVendaInput().isSelected(), 'Expected mostrarPontoVenda not to be selected').to.be.false;
        } else {
            await coordenadaBancariaUpdatePage.getMostrarPontoVendaInput().click();
            expect(await coordenadaBancariaUpdatePage.getMostrarPontoVendaInput().isSelected(), 'Expected mostrarPontoVenda to be selected').to.be.true;
        }
        const selectedPadraoRecebimento = coordenadaBancariaUpdatePage.getPadraoRecebimentoInput();
        if (await selectedPadraoRecebimento.isSelected()) {
            await coordenadaBancariaUpdatePage.getPadraoRecebimentoInput().click();
            expect(await coordenadaBancariaUpdatePage.getPadraoRecebimentoInput().isSelected(), 'Expected padraoRecebimento not to be selected').to.be.false;
        } else {
            await coordenadaBancariaUpdatePage.getPadraoRecebimentoInput().click();
            expect(await coordenadaBancariaUpdatePage.getPadraoRecebimentoInput().isSelected(), 'Expected padraoRecebimento to be selected').to.be.true;
        }
        const selectedPadraoPagamento = coordenadaBancariaUpdatePage.getPadraoPagamentoInput();
        if (await selectedPadraoPagamento.isSelected()) {
            await coordenadaBancariaUpdatePage.getPadraoPagamentoInput().click();
            expect(await coordenadaBancariaUpdatePage.getPadraoPagamentoInput().isSelected(), 'Expected padraoPagamento not to be selected').to.be.false;
        } else {
            await coordenadaBancariaUpdatePage.getPadraoPagamentoInput().click();
            expect(await coordenadaBancariaUpdatePage.getPadraoPagamentoInput().isSelected(), 'Expected padraoPagamento to be selected').to.be.true;
        }
        await coordenadaBancariaUpdatePage.save();
        expect(await coordenadaBancariaUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await coordenadaBancariaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /*  it('should delete last CoordenadaBancaria', async () => {
        const nbButtonsBeforeDelete = await coordenadaBancariaComponentsPage.countDeleteButtons();
        await coordenadaBancariaComponentsPage.clickOnLastDeleteButton();

        coordenadaBancariaDeleteDialog = new CoordenadaBancariaDeleteDialog();
        expect(await coordenadaBancariaDeleteDialog.getDialogTitle())
            .to.eq('mandaApp.coordenadaBancaria.delete.question');
        await coordenadaBancariaDeleteDialog.clickOnConfirmButton();

        expect(await coordenadaBancariaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
