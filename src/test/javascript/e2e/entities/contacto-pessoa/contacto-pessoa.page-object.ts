import { element, by, ElementFinder } from 'protractor';

export class ContactoPessoaComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('rv-contacto-pessoa div table .btn-danger'));
  title = element.all(by.css('rv-contacto-pessoa div h2#page-heading span')).first();

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

export class ContactoPessoaUpdatePage {
  pageTitle = element(by.id('rv-contacto-pessoa-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  tipoContactoInput = element(by.id('field_tipoContacto'));
  descricaoInput = element(by.id('field_descricao'));
  contactoInput = element(by.id('field_contacto'));
  pessoaSelect = element(by.id('field_pessoa'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setTipoContactoInput(tipoContacto) {
    await this.tipoContactoInput.sendKeys(tipoContacto);
  }

  async getTipoContactoInput() {
    return await this.tipoContactoInput.getAttribute('value');
  }

  async setDescricaoInput(descricao) {
    await this.descricaoInput.sendKeys(descricao);
  }

  async getDescricaoInput() {
    return await this.descricaoInput.getAttribute('value');
  }

  async setContactoInput(contacto) {
    await this.contactoInput.sendKeys(contacto);
  }

  async getContactoInput() {
    return await this.contactoInput.getAttribute('value');
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

export class ContactoPessoaDeleteDialog {
  private dialogTitle = element(by.id('rv-delete-contactoPessoa-heading'));
  private confirmButton = element(by.id('rv-confirm-delete-contactoPessoa'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}
