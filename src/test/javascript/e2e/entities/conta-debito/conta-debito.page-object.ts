import { element, by, ElementFinder } from 'protractor';

export class ContaDebitoComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('rv-conta-debito div table .btn-danger'));
  title = element.all(by.css('rv-conta-debito div h2#page-heading span')).first();

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

export class ContaDebitoUpdatePage {
  pageTitle = element(by.id('rv-conta-debito-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  valorInput = element(by.id('field_valor'));
  dataInput = element(by.id('field_data'));
  contaDebitarSelect = element(by.id('field_contaDebitar'));
  lancamentoDebitoSelect = element(by.id('field_lancamentoDebito'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setValorInput(valor) {
    await this.valorInput.sendKeys(valor);
  }

  async getValorInput() {
    return await this.valorInput.getAttribute('value');
  }

  async setDataInput(data) {
    await this.dataInput.sendKeys(data);
  }

  async getDataInput() {
    return await this.dataInput.getAttribute('value');
  }

  async contaDebitarSelectLastOption() {
    await this.contaDebitarSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async contaDebitarSelectOption(option) {
    await this.contaDebitarSelect.sendKeys(option);
  }

  getContaDebitarSelect(): ElementFinder {
    return this.contaDebitarSelect;
  }

  async getContaDebitarSelectedOption() {
    return await this.contaDebitarSelect.element(by.css('option:checked')).getText();
  }

  async lancamentoDebitoSelectLastOption() {
    await this.lancamentoDebitoSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async lancamentoDebitoSelectOption(option) {
    await this.lancamentoDebitoSelect.sendKeys(option);
  }

  getLancamentoDebitoSelect(): ElementFinder {
    return this.lancamentoDebitoSelect;
  }

  async getLancamentoDebitoSelectedOption() {
    return await this.lancamentoDebitoSelect.element(by.css('option:checked')).getText();
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

export class ContaDebitoDeleteDialog {
  private dialogTitle = element(by.id('rv-delete-contaDebito-heading'));
  private confirmButton = element(by.id('rv-confirm-delete-contaDebito'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}
