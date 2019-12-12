import { element, by, ElementFinder } from 'protractor';

export class EstoqueConfigComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('rv-estoque-config div table .btn-danger'));
  title = element.all(by.css('rv-estoque-config div h2#page-heading span')).first();

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

export class EstoqueConfigUpdatePage {
  pageTitle = element(by.id('rv-estoque-config-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  produtoSelect = element(by.id('field_produto'));
  contaVendaSelect = element(by.id('field_contaVenda'));
  contaCompraSelect = element(by.id('field_contaCompra'));
  contaImobilizadoSelect = element(by.id('field_contaImobilizado'));
  devolucaoCompraSelect = element(by.id('field_devolucaoCompra'));
  devolucaoVendaSelect = element(by.id('field_devolucaoVenda'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
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

  async contaVendaSelectLastOption() {
    await this.contaVendaSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async contaVendaSelectOption(option) {
    await this.contaVendaSelect.sendKeys(option);
  }

  getContaVendaSelect(): ElementFinder {
    return this.contaVendaSelect;
  }

  async getContaVendaSelectedOption() {
    return await this.contaVendaSelect.element(by.css('option:checked')).getText();
  }

  async contaCompraSelectLastOption() {
    await this.contaCompraSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async contaCompraSelectOption(option) {
    await this.contaCompraSelect.sendKeys(option);
  }

  getContaCompraSelect(): ElementFinder {
    return this.contaCompraSelect;
  }

  async getContaCompraSelectedOption() {
    return await this.contaCompraSelect.element(by.css('option:checked')).getText();
  }

  async contaImobilizadoSelectLastOption() {
    await this.contaImobilizadoSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async contaImobilizadoSelectOption(option) {
    await this.contaImobilizadoSelect.sendKeys(option);
  }

  getContaImobilizadoSelect(): ElementFinder {
    return this.contaImobilizadoSelect;
  }

  async getContaImobilizadoSelectedOption() {
    return await this.contaImobilizadoSelect.element(by.css('option:checked')).getText();
  }

  async devolucaoCompraSelectLastOption() {
    await this.devolucaoCompraSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async devolucaoCompraSelectOption(option) {
    await this.devolucaoCompraSelect.sendKeys(option);
  }

  getDevolucaoCompraSelect(): ElementFinder {
    return this.devolucaoCompraSelect;
  }

  async getDevolucaoCompraSelectedOption() {
    return await this.devolucaoCompraSelect.element(by.css('option:checked')).getText();
  }

  async devolucaoVendaSelectLastOption() {
    await this.devolucaoVendaSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async devolucaoVendaSelectOption(option) {
    await this.devolucaoVendaSelect.sendKeys(option);
  }

  getDevolucaoVendaSelect(): ElementFinder {
    return this.devolucaoVendaSelect;
  }

  async getDevolucaoVendaSelectedOption() {
    return await this.devolucaoVendaSelect.element(by.css('option:checked')).getText();
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

export class EstoqueConfigDeleteDialog {
  private dialogTitle = element(by.id('rv-delete-estoqueConfig-heading'));
  private confirmButton = element(by.id('rv-confirm-delete-estoqueConfig'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}
