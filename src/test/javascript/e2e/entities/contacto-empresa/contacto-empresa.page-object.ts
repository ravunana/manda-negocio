import { element, by, ElementFinder } from 'protractor';

export class ContactoEmpresaComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('rv-contacto-empresa div table .btn-danger'));
  title = element.all(by.css('rv-contacto-empresa div h2#page-heading span')).first();

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

export class ContactoEmpresaUpdatePage {
  pageTitle = element(by.id('rv-contacto-empresa-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  tipoContactoInput = element(by.id('field_tipoContacto'));
  descricaoInput = element(by.id('field_descricao'));
  contactoInput = element(by.id('field_contacto'));
  padraoInput = element(by.id('field_padrao'));
  empresaSelect = element(by.id('field_empresa'));

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

  getPadraoInput() {
    return this.padraoInput;
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

export class ContactoEmpresaDeleteDialog {
  private dialogTitle = element(by.id('rv-delete-contactoEmpresa-heading'));
  private confirmButton = element(by.id('rv-confirm-delete-contactoEmpresa'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}
