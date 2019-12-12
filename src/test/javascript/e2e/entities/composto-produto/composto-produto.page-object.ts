import { element, by, ElementFinder } from 'protractor';

export class CompostoProdutoComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('rv-composto-produto div table .btn-danger'));
  title = element.all(by.css('rv-composto-produto div h2#page-heading span')).first();

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

export class CompostoProdutoUpdatePage {
  pageTitle = element(by.id('rv-composto-produto-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  quantidadeInput = element(by.id('field_quantidade'));
  valorInput = element(by.id('field_valor'));
  permanenteInput = element(by.id('field_permanente'));
  produtoSelect = element(by.id('field_produto'));
  unidadeSelect = element(by.id('field_unidade'));
  compostoSelect = element(by.id('field_composto'));

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

  getPermanenteInput() {
    return this.permanenteInput;
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

  async unidadeSelectLastOption() {
    await this.unidadeSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async unidadeSelectOption(option) {
    await this.unidadeSelect.sendKeys(option);
  }

  getUnidadeSelect(): ElementFinder {
    return this.unidadeSelect;
  }

  async getUnidadeSelectedOption() {
    return await this.unidadeSelect.element(by.css('option:checked')).getText();
  }

  async compostoSelectLastOption() {
    await this.compostoSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async compostoSelectOption(option) {
    await this.compostoSelect.sendKeys(option);
  }

  getCompostoSelect(): ElementFinder {
    return this.compostoSelect;
  }

  async getCompostoSelectedOption() {
    return await this.compostoSelect.element(by.css('option:checked')).getText();
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

export class CompostoProdutoDeleteDialog {
  private dialogTitle = element(by.id('rv-delete-compostoProduto-heading'));
  private confirmButton = element(by.id('rv-confirm-delete-compostoProduto'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}
