import { element, by, ElementFinder } from 'protractor';

export class ClienteComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('rv-cliente div table .btn-danger'));
  title = element.all(by.css('rv-cliente div h2#page-heading span')).first();

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

export class ClienteUpdatePage {
  pageTitle = element(by.id('rv-cliente-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  ativoInput = element(by.id('field_ativo'));
  perfilProfissionalInput = element(by.id('field_perfilProfissional'));
  satisfacaoInput = element(by.id('field_satisfacao'));
  frequenciaInput = element(by.id('field_frequencia'));
  canalUsadoInput = element(by.id('field_canalUsado'));
  numeroInput = element(by.id('field_numero'));
  autofacturacaoInput = element(by.id('field_autofacturacao'));
  pessoaSelect = element(by.id('field_pessoa'));
  contaSelect = element(by.id('field_conta'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  getAtivoInput() {
    return this.ativoInput;
  }
  async setPerfilProfissionalInput(perfilProfissional) {
    await this.perfilProfissionalInput.sendKeys(perfilProfissional);
  }

  async getPerfilProfissionalInput() {
    return await this.perfilProfissionalInput.getAttribute('value');
  }

  async setSatisfacaoInput(satisfacao) {
    await this.satisfacaoInput.sendKeys(satisfacao);
  }

  async getSatisfacaoInput() {
    return await this.satisfacaoInput.getAttribute('value');
  }

  async setFrequenciaInput(frequencia) {
    await this.frequenciaInput.sendKeys(frequencia);
  }

  async getFrequenciaInput() {
    return await this.frequenciaInput.getAttribute('value');
  }

  async setCanalUsadoInput(canalUsado) {
    await this.canalUsadoInput.sendKeys(canalUsado);
  }

  async getCanalUsadoInput() {
    return await this.canalUsadoInput.getAttribute('value');
  }

  async setNumeroInput(numero) {
    await this.numeroInput.sendKeys(numero);
  }

  async getNumeroInput() {
    return await this.numeroInput.getAttribute('value');
  }

  getAutofacturacaoInput() {
    return this.autofacturacaoInput;
  }

  async pessoaSelectLastOption() {
    await this.pessoaSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async pessoaSelectOption(option) {
    await this.pessoaSelect.sendKeys(option);
  }

  getPessoaSelect(): ElementFinder {
    return this.pessoaSelect;
  }

  async getPessoaSelectedOption() {
    return await this.pessoaSelect.element(by.css('option:checked')).getText();
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

export class ClienteDeleteDialog {
  private dialogTitle = element(by.id('rv-delete-cliente-heading'));
  private confirmButton = element(by.id('rv-confirm-delete-cliente'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}
