import { element, by, ElementFinder } from 'protractor';

export class MeioLiquidacaoComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('rv-meio-liquidacao div table .btn-danger'));
  title = element.all(by.css('rv-meio-liquidacao div h2#page-heading span')).first();

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

export class MeioLiquidacaoUpdatePage {
  pageTitle = element(by.id('rv-meio-liquidacao-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  nomeInput = element(by.id('field_nome'));
  siglaInput = element(by.id('field_sigla'));
  iconInput = element(by.id('field_icon'));
  mostrarPontoVendaInput = element(by.id('field_mostrarPontoVenda'));

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

  async setIconInput(icon) {
    await this.iconInput.sendKeys(icon);
  }

  async getIconInput() {
    return await this.iconInput.getAttribute('value');
  }

  getMostrarPontoVendaInput() {
    return this.mostrarPontoVendaInput;
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

export class MeioLiquidacaoDeleteDialog {
  private dialogTitle = element(by.id('rv-delete-meioLiquidacao-heading'));
  private confirmButton = element(by.id('rv-confirm-delete-meioLiquidacao'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}
