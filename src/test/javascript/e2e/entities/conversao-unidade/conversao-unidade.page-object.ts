import { element, by, ElementFinder } from 'protractor';

export class ConversaoUnidadeComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('rv-conversao-unidade div table .btn-danger'));
  title = element.all(by.css('rv-conversao-unidade div h2#page-heading span')).first();

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

export class ConversaoUnidadeUpdatePage {
  pageTitle = element(by.id('rv-conversao-unidade-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  valorEntradaInput = element(by.id('field_valorEntrada'));
  valorSaidaInput = element(by.id('field_valorSaida'));
  entradaSelect = element(by.id('field_entrada'));
  saidaSelect = element(by.id('field_saida'));
  produtoSelect = element(by.id('field_produto'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setValorEntradaInput(valorEntrada) {
    await this.valorEntradaInput.sendKeys(valorEntrada);
  }

  async getValorEntradaInput() {
    return await this.valorEntradaInput.getAttribute('value');
  }

  async setValorSaidaInput(valorSaida) {
    await this.valorSaidaInput.sendKeys(valorSaida);
  }

  async getValorSaidaInput() {
    return await this.valorSaidaInput.getAttribute('value');
  }

  async entradaSelectLastOption() {
    await this.entradaSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async entradaSelectOption(option) {
    await this.entradaSelect.sendKeys(option);
  }

  getEntradaSelect(): ElementFinder {
    return this.entradaSelect;
  }

  async getEntradaSelectedOption() {
    return await this.entradaSelect.element(by.css('option:checked')).getText();
  }

  async saidaSelectLastOption() {
    await this.saidaSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async saidaSelectOption(option) {
    await this.saidaSelect.sendKeys(option);
  }

  getSaidaSelect(): ElementFinder {
    return this.saidaSelect;
  }

  async getSaidaSelectedOption() {
    return await this.saidaSelect.element(by.css('option:checked')).getText();
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

export class ConversaoUnidadeDeleteDialog {
  private dialogTitle = element(by.id('rv-delete-conversaoUnidade-heading'));
  private confirmButton = element(by.id('rv-confirm-delete-conversaoUnidade'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}
