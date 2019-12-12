import { element, by, ElementFinder } from 'protractor';

export class SerieDocumentoComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('rv-serie-documento div table .btn-danger'));
  title = element.all(by.css('rv-serie-documento div h2#page-heading span')).first();

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

export class SerieDocumentoUpdatePage {
  pageTitle = element(by.id('rv-serie-documento-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  serieInput = element(by.id('field_serie'));
  codigoLancamentoFinanceiroInput = element(by.id('field_codigoLancamentoFinanceiro'));
  codigoEscrituracaoContabilInput = element(by.id('field_codigoEscrituracaoContabil'));
  codigoVendaInput = element(by.id('field_codigoVenda'));
  codigoCompraInput = element(by.id('field_codigoCompra'));
  codigoClienteInput = element(by.id('field_codigoCliente'));
  codigoFornecedorInput = element(by.id('field_codigoFornecedor'));
  codigoDevolucaoVendaInput = element(by.id('field_codigoDevolucaoVenda'));
  codigoDevolucaoCompraInput = element(by.id('field_codigoDevolucaoCompra'));
  codigoProdutoInput = element(by.id('field_codigoProduto'));
  dataInput = element(by.id('field_data'));
  empresaSelect = element(by.id('field_empresa'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setSerieInput(serie) {
    await this.serieInput.sendKeys(serie);
  }

  async getSerieInput() {
    return await this.serieInput.getAttribute('value');
  }

  async setCodigoLancamentoFinanceiroInput(codigoLancamentoFinanceiro) {
    await this.codigoLancamentoFinanceiroInput.sendKeys(codigoLancamentoFinanceiro);
  }

  async getCodigoLancamentoFinanceiroInput() {
    return await this.codigoLancamentoFinanceiroInput.getAttribute('value');
  }

  async setCodigoEscrituracaoContabilInput(codigoEscrituracaoContabil) {
    await this.codigoEscrituracaoContabilInput.sendKeys(codigoEscrituracaoContabil);
  }

  async getCodigoEscrituracaoContabilInput() {
    return await this.codigoEscrituracaoContabilInput.getAttribute('value');
  }

  async setCodigoVendaInput(codigoVenda) {
    await this.codigoVendaInput.sendKeys(codigoVenda);
  }

  async getCodigoVendaInput() {
    return await this.codigoVendaInput.getAttribute('value');
  }

  async setCodigoCompraInput(codigoCompra) {
    await this.codigoCompraInput.sendKeys(codigoCompra);
  }

  async getCodigoCompraInput() {
    return await this.codigoCompraInput.getAttribute('value');
  }

  async setCodigoClienteInput(codigoCliente) {
    await this.codigoClienteInput.sendKeys(codigoCliente);
  }

  async getCodigoClienteInput() {
    return await this.codigoClienteInput.getAttribute('value');
  }

  async setCodigoFornecedorInput(codigoFornecedor) {
    await this.codigoFornecedorInput.sendKeys(codigoFornecedor);
  }

  async getCodigoFornecedorInput() {
    return await this.codigoFornecedorInput.getAttribute('value');
  }

  async setCodigoDevolucaoVendaInput(codigoDevolucaoVenda) {
    await this.codigoDevolucaoVendaInput.sendKeys(codigoDevolucaoVenda);
  }

  async getCodigoDevolucaoVendaInput() {
    return await this.codigoDevolucaoVendaInput.getAttribute('value');
  }

  async setCodigoDevolucaoCompraInput(codigoDevolucaoCompra) {
    await this.codigoDevolucaoCompraInput.sendKeys(codigoDevolucaoCompra);
  }

  async getCodigoDevolucaoCompraInput() {
    return await this.codigoDevolucaoCompraInput.getAttribute('value');
  }

  async setCodigoProdutoInput(codigoProduto) {
    await this.codigoProdutoInput.sendKeys(codigoProduto);
  }

  async getCodigoProdutoInput() {
    return await this.codigoProdutoInput.getAttribute('value');
  }

  async setDataInput(data) {
    await this.dataInput.sendKeys(data);
  }

  async getDataInput() {
    return await this.dataInput.getAttribute('value');
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

export class SerieDocumentoDeleteDialog {
  private dialogTitle = element(by.id('rv-delete-serieDocumento-heading'));
  private confirmButton = element(by.id('rv-confirm-delete-serieDocumento'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}
