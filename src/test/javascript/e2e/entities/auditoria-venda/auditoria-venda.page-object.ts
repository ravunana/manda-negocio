import { element, by, ElementFinder } from 'protractor';

export class AuditoriaVendaComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('rv-auditoria-venda div table .btn-danger'));
  title = element.all(by.css('rv-auditoria-venda div h2#page-heading span')).first();

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

export class AuditoriaVendaUpdatePage {
  pageTitle = element(by.id('rv-auditoria-venda-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  estadoInput = element(by.id('field_estado'));
  dataInput = element(by.id('field_data'));
  motivoAlteracaoDocumentoInput = element(by.id('field_motivoAlteracaoDocumento'));
  origemDocumentoInput = element(by.id('field_origemDocumento'));
  hashInput = element(by.id('field_hash'));
  hashControlInput = element(by.id('field_hashControl'));
  vendaSelect = element(by.id('field_venda'));
  utilizadorSelect = element(by.id('field_utilizador'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setEstadoInput(estado) {
    await this.estadoInput.sendKeys(estado);
  }

  async getEstadoInput() {
    return await this.estadoInput.getAttribute('value');
  }

  async setDataInput(data) {
    await this.dataInput.sendKeys(data);
  }

  async getDataInput() {
    return await this.dataInput.getAttribute('value');
  }

  async setMotivoAlteracaoDocumentoInput(motivoAlteracaoDocumento) {
    await this.motivoAlteracaoDocumentoInput.sendKeys(motivoAlteracaoDocumento);
  }

  async getMotivoAlteracaoDocumentoInput() {
    return await this.motivoAlteracaoDocumentoInput.getAttribute('value');
  }

  async setOrigemDocumentoInput(origemDocumento) {
    await this.origemDocumentoInput.sendKeys(origemDocumento);
  }

  async getOrigemDocumentoInput() {
    return await this.origemDocumentoInput.getAttribute('value');
  }

  async setHashInput(hash) {
    await this.hashInput.sendKeys(hash);
  }

  async getHashInput() {
    return await this.hashInput.getAttribute('value');
  }

  async setHashControlInput(hashControl) {
    await this.hashControlInput.sendKeys(hashControl);
  }

  async getHashControlInput() {
    return await this.hashControlInput.getAttribute('value');
  }

  async vendaSelectLastOption() {
    await this.vendaSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async vendaSelectOption(option) {
    await this.vendaSelect.sendKeys(option);
  }

  getVendaSelect(): ElementFinder {
    return this.vendaSelect;
  }

  async getVendaSelectedOption() {
    return await this.vendaSelect.element(by.css('option:checked')).getText();
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

export class AuditoriaVendaDeleteDialog {
  private dialogTitle = element(by.id('rv-delete-auditoriaVenda-heading'));
  private confirmButton = element(by.id('rv-confirm-delete-auditoriaVenda'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}
