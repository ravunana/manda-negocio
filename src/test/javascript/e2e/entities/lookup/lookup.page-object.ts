import { element, by, ElementFinder } from 'protractor';

export class LookupComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('rv-lookup div table .btn-danger'));
  title = element.all(by.css('rv-lookup div h2#page-heading span')).first();

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

export class LookupUpdatePage {
  pageTitle = element(by.id('rv-lookup-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  nomeInput = element(by.id('field_nome'));
  entidadeSelect = element(by.id('field_entidade'));
  modificavelInput = element(by.id('field_modificavel'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setNomeInput(nome) {
    await this.nomeInput.sendKeys(nome);
  }

  async getNomeInput() {
    return await this.nomeInput.getAttribute('value');
  }

  async setEntidadeSelect(entidade) {
    await this.entidadeSelect.sendKeys(entidade);
  }

  async getEntidadeSelect() {
    return await this.entidadeSelect.element(by.css('option:checked')).getText();
  }

  async entidadeSelectLastOption() {
    await this.entidadeSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  getModificavelInput() {
    return this.modificavelInput;
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

export class LookupDeleteDialog {
  private dialogTitle = element(by.id('rv-delete-lookup-heading'));
  private confirmButton = element(by.id('rv-confirm-delete-lookup'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}
