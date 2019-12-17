import { element, by, ElementFinder } from 'protractor';

export class ItemCompraComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('rv-item-compra div table .btn-danger'));
  title = element.all(by.css('rv-item-compra div h2#page-heading span')).first();

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

export class ItemCompraUpdatePage {
  pageTitle = element(by.id('rv-item-compra-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  quantidadeInput = element(by.id('field_quantidade'));
  descontoInput = element(by.id('field_desconto'));
  dataSolicitacaoInput = element(by.id('field_dataSolicitacao'));
  dataEntregaInput = element(by.id('field_dataEntrega'));
  descricaoInput = element(by.id('field_descricao'));
  valorInput = element(by.id('field_valor'));
  solicitanteInput = element(by.id('field_solicitante'));
  compraSelect = element(by.id('field_compra'));
  produtoSelect = element(by.id('field_produto'));
  fornecedorSelect = element(by.id('field_fornecedor'));
  statusSelect = element(by.id('field_status'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setQuantidadeInput(quantidade) {
    await this.quantidadeInput.sendKeys(quantidade);
  }

  async getQuantidadeInput() {
    return await this.quantidadeInput.getAttribute('value');
  }

  async setDescontoInput(desconto) {
    await this.descontoInput.sendKeys(desconto);
  }

  async getDescontoInput() {
    return await this.descontoInput.getAttribute('value');
  }

  async setDataSolicitacaoInput(dataSolicitacao) {
    await this.dataSolicitacaoInput.sendKeys(dataSolicitacao);
  }

  async getDataSolicitacaoInput() {
    return await this.dataSolicitacaoInput.getAttribute('value');
  }

  async setDataEntregaInput(dataEntrega) {
    await this.dataEntregaInput.sendKeys(dataEntrega);
  }

  async getDataEntregaInput() {
    return await this.dataEntregaInput.getAttribute('value');
  }

  async setDescricaoInput(descricao) {
    await this.descricaoInput.sendKeys(descricao);
  }

  async getDescricaoInput() {
    return await this.descricaoInput.getAttribute('value');
  }

  async setValorInput(valor) {
    await this.valorInput.sendKeys(valor);
  }

  async getValorInput() {
    return await this.valorInput.getAttribute('value');
  }

  async setSolicitanteInput(solicitante) {
    await this.solicitanteInput.sendKeys(solicitante);
  }

  async getSolicitanteInput() {
    return await this.solicitanteInput.getAttribute('value');
  }

  async compraSelectLastOption() {
    await this.compraSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async compraSelectOption(option) {
    await this.compraSelect.sendKeys(option);
  }

  getCompraSelect(): ElementFinder {
    return this.compraSelect;
  }

  async getCompraSelectedOption() {
    return await this.compraSelect.element(by.css('option:checked')).getText();
  }

  async produtoSelectLastOption() {
    await this.produtoSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async produtoSelectOption(option) {
    await this.produtoSelect.sendKeys(option);
  }

  getProdutoSelect(): ElementFinder {
    return this.produtoSelect;
  }

  async getProdutoSelectedOption() {
    return await this.produtoSelect.element(by.css('option:checked')).getText();
  }

  async fornecedorSelectLastOption() {
    await this.fornecedorSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async fornecedorSelectOption(option) {
    await this.fornecedorSelect.sendKeys(option);
  }

  getFornecedorSelect(): ElementFinder {
    return this.fornecedorSelect;
  }

  async getFornecedorSelectedOption() {
    return await this.fornecedorSelect.element(by.css('option:checked')).getText();
  }

  async statusSelectLastOption() {
    await this.statusSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async statusSelectOption(option) {
    await this.statusSelect.sendKeys(option);
  }

  getStatusSelect(): ElementFinder {
    return this.statusSelect;
  }

  async getStatusSelectedOption() {
    return await this.statusSelect.element(by.css('option:checked')).getText();
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

export class ItemCompraDeleteDialog {
  private dialogTitle = element(by.id('rv-delete-itemCompra-heading'));
  private confirmButton = element(by.id('rv-confirm-delete-itemCompra'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}
