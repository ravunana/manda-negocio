import { element, by, ElementFinder } from 'protractor';

export class FornecedorComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('rv-fornecedor div table .btn-danger'));
  title = element.all(by.css('rv-fornecedor div h2#page-heading span')).first();

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

export class FornecedorUpdatePage {
  pageTitle = element(by.id('rv-fornecedor-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  certificadoISO9001Input = element(by.id('field_certificadoISO9001'));
  garantiaDefeitoFabricaInput = element(by.id('field_garantiaDefeitoFabrica'));
  transporteInput = element(by.id('field_transporte'));
  qualidadeInput = element(by.id('field_qualidade'));
  pagamentoPrazoInput = element(by.id('field_pagamentoPrazo'));
  metodosPagamentoInput = element(by.id('field_metodosPagamento'));
  classificacaoInput = element(by.id('field_classificacao'));
  descricaoInput = element(by.id('field_descricao'));
  ativoInput = element(by.id('field_ativo'));
  numeroInput = element(by.id('field_numero'));
  pessoaSelect = element(by.id('field_pessoa'));
  contaSelect = element(by.id('field_conta'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  getCertificadoISO9001Input() {
    return this.certificadoISO9001Input;
  }
  getGarantiaDefeitoFabricaInput() {
    return this.garantiaDefeitoFabricaInput;
  }
  getTransporteInput() {
    return this.transporteInput;
  }
  async setQualidadeInput(qualidade) {
    await this.qualidadeInput.sendKeys(qualidade);
  }

  async getQualidadeInput() {
    return await this.qualidadeInput.getAttribute('value');
  }

  getPagamentoPrazoInput() {
    return this.pagamentoPrazoInput;
  }
  async setMetodosPagamentoInput(metodosPagamento) {
    await this.metodosPagamentoInput.sendKeys(metodosPagamento);
  }

  async getMetodosPagamentoInput() {
    return await this.metodosPagamentoInput.getAttribute('value');
  }

  async setClassificacaoInput(classificacao) {
    await this.classificacaoInput.sendKeys(classificacao);
  }

  async getClassificacaoInput() {
    return await this.classificacaoInput.getAttribute('value');
  }

  async setDescricaoInput(descricao) {
    await this.descricaoInput.sendKeys(descricao);
  }

  async getDescricaoInput() {
    return await this.descricaoInput.getAttribute('value');
  }

  getAtivoInput() {
    return this.ativoInput;
  }
  async setNumeroInput(numero) {
    await this.numeroInput.sendKeys(numero);
  }

  async getNumeroInput() {
    return await this.numeroInput.getAttribute('value');
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

export class FornecedorDeleteDialog {
  private dialogTitle = element(by.id('rv-delete-fornecedor-heading'));
  private confirmButton = element(by.id('rv-confirm-delete-fornecedor'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}
