import { element, by, ElementFinder } from 'protractor';

export class ItemVendaComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('rv-item-venda div table .btn-danger'));
  title = element.all(by.css('rv-item-venda div h2#page-heading span')).first();

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

export class ItemVendaUpdatePage {
  pageTitle = element(by.id('rv-item-venda-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  quantidadeInput = element(by.id('field_quantidade'));
  valorInput = element(by.id('field_valor'));
  descontoInput = element(by.id('field_desconto'));
  dataInput = element(by.id('field_data'));
  vendaSelect = element(by.id('field_venda'));
  produtoSelect = element(by.id('field_produto'));
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

  async setValorInput(valor) {
    await this.valorInput.sendKeys(valor);
  }

  async getValorInput() {
    return await this.valorInput.getAttribute('value');
  }

  async setDescontoInput(desconto) {
    await this.descontoInput.sendKeys(desconto);
  }

  async getDescontoInput() {
    return await this.descontoInput.getAttribute('value');
  }

  async setDataInput(data) {
    await this.dataInput.sendKeys(data);
  }

  async getDataInput() {
    return await this.dataInput.getAttribute('value');
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

export class ItemVendaDeleteDialog {
  private dialogTitle = element(by.id('rv-delete-itemVenda-heading'));
  private confirmButton = element(by.id('rv-confirm-delete-itemVenda'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}
