import { element, by, ElementFinder } from 'protractor';

export class CompraComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('rv-compra div table .btn-danger'));
  title = element.all(by.css('rv-compra div h2#page-heading span')).first();

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

export class CompraUpdatePage {
  pageTitle = element(by.id('rv-compra-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  numeroInput = element(by.id('field_numero'));
  dataInput = element(by.id('field_data'));
  observacaoGeralInput = element(by.id('field_observacaoGeral'));
  observacaoInternaInput = element(by.id('field_observacaoInterna'));
  utilizadorSelect = element(by.id('field_utilizador'));
  tipoDocumentoSelect = element(by.id('field_tipoDocumento'));
  empresaSelect = element(by.id('field_empresa'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setNumeroInput(numero) {
    await this.numeroInput.sendKeys(numero);
  }

  async getNumeroInput() {
    return await this.numeroInput.getAttribute('value');
  }

  async setDataInput(data) {
    await this.dataInput.sendKeys(data);
  }

  async getDataInput() {
    return await this.dataInput.getAttribute('value');
  }

  async setObservacaoGeralInput(observacaoGeral) {
    await this.observacaoGeralInput.sendKeys(observacaoGeral);
  }

  async getObservacaoGeralInput() {
    return await this.observacaoGeralInput.getAttribute('value');
  }

  async setObservacaoInternaInput(observacaoInterna) {
    await this.observacaoInternaInput.sendKeys(observacaoInterna);
  }

  async getObservacaoInternaInput() {
    return await this.observacaoInternaInput.getAttribute('value');
  }

  async utilizadorSelectLastOption() {
    await this.utilizadorSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async utilizadorSelectOption(option) {
    await this.utilizadorSelect.sendKeys(option);
  }

  getUtilizadorSelect(): ElementFinder {
    return this.utilizadorSelect;
  }

  async getUtilizadorSelectedOption() {
    return await this.utilizadorSelect.element(by.css('option:checked')).getText();
  }

  async tipoDocumentoSelectLastOption() {
    await this.tipoDocumentoSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async tipoDocumentoSelectOption(option) {
    await this.tipoDocumentoSelect.sendKeys(option);
  }

  getTipoDocumentoSelect(): ElementFinder {
    return this.tipoDocumentoSelect;
  }

  async getTipoDocumentoSelectedOption() {
    return await this.tipoDocumentoSelect.element(by.css('option:checked')).getText();
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

export class CompraDeleteDialog {
  private dialogTitle = element(by.id('rv-delete-compra-heading'));
  private confirmButton = element(by.id('rv-confirm-delete-compra'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}
