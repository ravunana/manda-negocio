import { element, by, ElementFinder } from 'protractor';

export class EscrituracaoContabilComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('rv-escrituracao-contabil div table .btn-danger'));
  title = element.all(by.css('rv-escrituracao-contabil div h2#page-heading span')).first();

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

export class EscrituracaoContabilUpdatePage {
  pageTitle = element(by.id('rv-escrituracao-contabil-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  numeroInput = element(by.id('field_numero'));
  historicoInput = element(by.id('field_historico'));
  valorInput = element(by.id('field_valor'));
  referenciaInput = element(by.id('field_referencia'));
  entidadeReferenciaSelect = element(by.id('field_entidadeReferencia'));
  tipoInput = element(by.id('field_tipo'));
  dataDocumentoInput = element(by.id('field_dataDocumento'));
  dataInput = element(by.id('field_data'));
  utilizadorSelect = element(by.id('field_utilizador'));
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

  async setHistoricoInput(historico) {
    await this.historicoInput.sendKeys(historico);
  }

  async getHistoricoInput() {
    return await this.historicoInput.getAttribute('value');
  }

  async setValorInput(valor) {
    await this.valorInput.sendKeys(valor);
  }

  async getValorInput() {
    return await this.valorInput.getAttribute('value');
  }

  async setReferenciaInput(referencia) {
    await this.referenciaInput.sendKeys(referencia);
  }

  async getReferenciaInput() {
    return await this.referenciaInput.getAttribute('value');
  }

  async setEntidadeReferenciaSelect(entidadeReferencia) {
    await this.entidadeReferenciaSelect.sendKeys(entidadeReferencia);
  }

  async getEntidadeReferenciaSelect() {
    return await this.entidadeReferenciaSelect.element(by.css('option:checked')).getText();
  }

  async entidadeReferenciaSelectLastOption() {
    await this.entidadeReferenciaSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async setTipoInput(tipo) {
    await this.tipoInput.sendKeys(tipo);
  }

  async getTipoInput() {
    return await this.tipoInput.getAttribute('value');
  }

  async setDataDocumentoInput(dataDocumento) {
    await this.dataDocumentoInput.sendKeys(dataDocumento);
  }

  async getDataDocumentoInput() {
    return await this.dataDocumentoInput.getAttribute('value');
  }

  async setDataInput(data) {
    await this.dataInput.sendKeys(data);
  }

  async getDataInput() {
    return await this.dataInput.getAttribute('value');
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

export class EscrituracaoContabilDeleteDialog {
  private dialogTitle = element(by.id('rv-delete-escrituracaoContabil-heading'));
  private confirmButton = element(by.id('rv-confirm-delete-escrituracaoContabil'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}
