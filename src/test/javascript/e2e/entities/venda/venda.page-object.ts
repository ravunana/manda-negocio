import { element, by, ElementFinder } from 'protractor';

export class VendaComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('rv-venda div table .btn-danger'));
  title = element.all(by.css('rv-venda div h2#page-heading span')).first();

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

export class VendaUpdatePage {
  pageTitle = element(by.id('rv-venda-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  numeroInput = element(by.id('field_numero'));
  dataInput = element(by.id('field_data'));
  observacaoGeralInput = element(by.id('field_observacaoGeral'));
  observacaoInternaInput = element(by.id('field_observacaoInterna'));
  vendedorSelect = element(by.id('field_vendedor'));
  clienteSelect = element(by.id('field_cliente'));
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

  async vendedorSelectLastOption() {
    await this.vendedorSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async vendedorSelectOption(option) {
    await this.vendedorSelect.sendKeys(option);
  }

  getVendedorSelect(): ElementFinder {
    return this.vendedorSelect;
  }

  async getVendedorSelectedOption() {
    return await this.vendedorSelect.element(by.css('option:checked')).getText();
  }

  async clienteSelectLastOption() {
    await this.clienteSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async clienteSelectOption(option) {
    await this.clienteSelect.sendKeys(option);
  }

  getClienteSelect(): ElementFinder {
    return this.clienteSelect;
  }

  async getClienteSelectedOption() {
    return await this.clienteSelect.element(by.css('option:checked')).getText();
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

export class VendaDeleteDialog {
  private dialogTitle = element(by.id('rv-delete-venda-heading'));
  private confirmButton = element(by.id('rv-confirm-delete-venda'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}
