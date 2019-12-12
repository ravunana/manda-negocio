import { element, by, ElementFinder } from 'protractor';

export class ContaCreditoComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('rv-conta-credito div table .btn-danger'));
  title = element.all(by.css('rv-conta-credito div h2#page-heading span')).first();

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

export class ContaCreditoUpdatePage {
  pageTitle = element(by.id('rv-conta-credito-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  valorInput = element(by.id('field_valor'));
  dataInput = element(by.id('field_data'));
  contaCreditarSelect = element(by.id('field_contaCreditar'));
  lancamentoCreditoSelect = element(by.id('field_lancamentoCredito'));

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

  async contaCreditarSelectLastOption() {
    await this.contaCreditarSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async contaCreditarSelectOption(option) {
    await this.contaCreditarSelect.sendKeys(option);
  }

  getContaCreditarSelect(): ElementFinder {
    return this.contaCreditarSelect;
  }

  async getContaCreditarSelectedOption() {
    return await this.contaCreditarSelect.element(by.css('option:checked')).getText();
  }

  async lancamentoCreditoSelectLastOption() {
    await this.lancamentoCreditoSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async lancamentoCreditoSelectOption(option) {
    await this.lancamentoCreditoSelect.sendKeys(option);
  }

  getLancamentoCreditoSelect(): ElementFinder {
    return this.lancamentoCreditoSelect;
  }

  async getLancamentoCreditoSelectedOption() {
    return await this.lancamentoCreditoSelect.element(by.css('option:checked')).getText();
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

export class ContaCreditoDeleteDialog {
  private dialogTitle = element(by.id('rv-delete-contaCredito-heading'));
  private confirmButton = element(by.id('rv-confirm-delete-contaCredito'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}
