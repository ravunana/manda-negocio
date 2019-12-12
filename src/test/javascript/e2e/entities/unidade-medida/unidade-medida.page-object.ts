import { element, by, ElementFinder } from 'protractor';

export class UnidadeMedidaComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('rv-unidade-medida div table .btn-danger'));
  title = element.all(by.css('rv-unidade-medida div h2#page-heading span')).first();

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

export class UnidadeMedidaUpdatePage {
  pageTitle = element(by.id('rv-unidade-medida-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  nomeInput = element(by.id('field_nome'));
  siglaInput = element(by.id('field_sigla'));
  valorInput = element(by.id('field_valor'));
  unidadeConversaoSelect = element(by.id('field_unidadeConversao'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setNomeInput(nome) {
    await this.nomeInput.sendKeys(nome);
  }

  async getNomeInput() {
    return await this.nomeInput.getAttribute('value');
  }

  async setSiglaInput(sigla) {
    await this.siglaInput.sendKeys(sigla);
  }

  async getSiglaInput() {
    return await this.siglaInput.getAttribute('value');
  }

  async setValorInput(valor) {
    await this.valorInput.sendKeys(valor);
  }

  async getValorInput() {
    return await this.valorInput.getAttribute('value');
  }

  async unidadeConversaoSelectLastOption() {
    await this.unidadeConversaoSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async unidadeConversaoSelectOption(option) {
    await this.unidadeConversaoSelect.sendKeys(option);
  }

  getUnidadeConversaoSelect(): ElementFinder {
    return this.unidadeConversaoSelect;
  }

  async getUnidadeConversaoSelectedOption() {
    return await this.unidadeConversaoSelect.element(by.css('option:checked')).getText();
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

export class UnidadeMedidaDeleteDialog {
  private dialogTitle = element(by.id('rv-delete-unidadeMedida-heading'));
  private confirmButton = element(by.id('rv-confirm-delete-unidadeMedida'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}
