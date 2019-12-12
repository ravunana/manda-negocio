import { element, by, ElementFinder } from 'protractor';

export class CoordenadaBancariaComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('rv-coordenada-bancaria div table .btn-danger'));
  title = element.all(by.css('rv-coordenada-bancaria div h2#page-heading span')).first();

  async clickOnCreateButton() {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton() {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons() {
    return this.deleteButtons.count();
  }

  async getTitle() {
    return this.title.getAttribute('jhiTranslate');
  }
}

export class CoordenadaBancariaUpdatePage {
  pageTitle = element(by.id('rv-coordenada-bancaria-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  descricaoInput = element(by.id('field_descricao'));
  proprietarioInput = element(by.id('field_proprietario'));
  numeroContaInput = element(by.id('field_numeroConta'));
  ibanInput = element(by.id('field_iban'));
  ativoInput = element(by.id('field_ativo'));
  mostrarDocumentoInput = element(by.id('field_mostrarDocumento'));
  mostrarPontoVendaInput = element(by.id('field_mostrarPontoVenda'));
  padraoRecebimentoInput = element(by.id('field_padraoRecebimento'));
  padraoPagamentoInput = element(by.id('field_padraoPagamento'));
  contaSelect = element(by.id('field_conta'));
  empresaSelect = element(by.id('field_empresa'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setDescricaoInput(descricao) {
    await this.descricaoInput.sendKeys(descricao);
  }

  async getDescricaoInput() {
    return await this.descricaoInput.getAttribute('value');
  }

  async setProprietarioInput(proprietario) {
    await this.proprietarioInput.sendKeys(proprietario);
  }

  async getProprietarioInput() {
    return await this.proprietarioInput.getAttribute('value');
  }

  async setNumeroContaInput(numeroConta) {
    await this.numeroContaInput.sendKeys(numeroConta);
  }

  async getNumeroContaInput() {
    return await this.numeroContaInput.getAttribute('value');
  }

  async setIbanInput(iban) {
    await this.ibanInput.sendKeys(iban);
  }

  async getIbanInput() {
    return await this.ibanInput.getAttribute('value');
  }

  getAtivoInput() {
    return this.ativoInput;
  }
  getMostrarDocumentoInput() {
    return this.mostrarDocumentoInput;
  }
  getMostrarPontoVendaInput() {
    return this.mostrarPontoVendaInput;
  }
  getPadraoRecebimentoInput() {
    return this.padraoRecebimentoInput;
  }
  getPadraoPagamentoInput() {
    return this.padraoPagamentoInput;
  }

  async contaSelectLastOption() {
    await this.contaSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async contaSelectOption(option) {
    await this.contaSelect.sendKeys(option);
  }

  getContaSelect(): ElementFinder {
    return this.contaSelect;
  }

  async getContaSelectedOption() {
    return await this.contaSelect.element(by.css('option:checked')).getText();
  }

  async empresaSelectLastOption() {
    await this.empresaSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async empresaSelectOption(option) {
    await this.empresaSelect.sendKeys(option);
  }

  getEmpresaSelect(): ElementFinder {
    return this.empresaSelect;
  }

  async getEmpresaSelectedOption() {
    return await this.empresaSelect.element(by.css('option:checked')).getText();
  }

  async save() {
    await this.saveButton.click();
  }

  async cancel() {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class CoordenadaBancariaDeleteDialog {
  private dialogTitle = element(by.id('rv-delete-coordenadaBancaria-heading'));
  private confirmButton = element(by.id('rv-confirm-delete-coordenadaBancaria'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}
