import { element, by, ElementFinder } from 'protractor';

export class LookupItemComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('rv-lookup-item div table .btn-danger'));
  title = element.all(by.css('rv-lookup-item div h2#page-heading span')).first();

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

export class LookupItemUpdatePage {
  pageTitle = element(by.id('rv-lookup-item-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  valorInput = element(by.id('field_valor'));
  nomeInput = element(by.id('field_nome'));
  typeSelect = element(by.id('field_type'));
  ordemInput = element(by.id('field_ordem'));
  lookupSelect = element(by.id('field_lookup'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setValorInput(valor) {
    await this.valorInput.sendKeys(valor);
  }

  async getValorInput() {
    return await this.valorInput.getAttribute('value');
  }

  async setNomeInput(nome) {
    await this.nomeInput.sendKeys(nome);
  }

  async getNomeInput() {
    return await this.nomeInput.getAttribute('value');
  }

  async setTypeSelect(type) {
    await this.typeSelect.sendKeys(type);
  }

  async getTypeSelect() {
    return await this.typeSelect.element(by.css('option:checked')).getText();
  }

  async typeSelectLastOption() {
    await this.typeSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async setOrdemInput(ordem) {
    await this.ordemInput.sendKeys(ordem);
  }

  async getOrdemInput() {
    return await this.ordemInput.getAttribute('value');
  }

  async lookupSelectLastOption() {
    await this.lookupSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async lookupSelectOption(option) {
    await this.lookupSelect.sendKeys(option);
  }

  getLookupSelect(): ElementFinder {
    return this.lookupSelect;
  }

  async getLookupSelectedOption() {
    return await this.lookupSelect.element(by.css('option:checked')).getText();
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

export class LookupItemDeleteDialog {
  private dialogTitle = element(by.id('rv-delete-lookupItem-heading'));
  private confirmButton = element(by.id('rv-confirm-delete-lookupItem'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}
