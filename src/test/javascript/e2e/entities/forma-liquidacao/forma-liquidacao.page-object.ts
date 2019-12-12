import { element, by, ElementFinder } from 'protractor';

export class FormaLiquidacaoComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('rv-forma-liquidacao div table .btn-danger'));
  title = element.all(by.css('rv-forma-liquidacao div h2#page-heading span')).first();

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

export class FormaLiquidacaoUpdatePage {
  pageTitle = element(by.id('rv-forma-liquidacao-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  nomeInput = element(by.id('field_nome'));
  juroInput = element(by.id('field_juro'));
  prazoLiquidacaoInput = element(by.id('field_prazoLiquidacao'));
  quantidadeInput = element(by.id('field_quantidade'));
  iconInput = element(by.id('field_icon'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setNomeInput(nome) {
    await this.nomeInput.sendKeys(nome);
  }

  async getNomeInput() {
    return await this.nomeInput.getAttribute('value');
  }

  async setJuroInput(juro) {
    await this.juroInput.sendKeys(juro);
  }

  async getJuroInput() {
    return await this.juroInput.getAttribute('value');
  }

  async setPrazoLiquidacaoInput(prazoLiquidacao) {
    await this.prazoLiquidacaoInput.sendKeys(prazoLiquidacao);
  }

  async getPrazoLiquidacaoInput() {
    return await this.prazoLiquidacaoInput.getAttribute('value');
  }

  async setQuantidadeInput(quantidade) {
    await this.quantidadeInput.sendKeys(quantidade);
  }

  async getQuantidadeInput() {
    return await this.quantidadeInput.getAttribute('value');
  }

  async setIconInput(icon) {
    await this.iconInput.sendKeys(icon);
  }

  async getIconInput() {
    return await this.iconInput.getAttribute('value');
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

export class FormaLiquidacaoDeleteDialog {
  private dialogTitle = element(by.id('rv-delete-formaLiquidacao-heading'));
  private confirmButton = element(by.id('rv-confirm-delete-formaLiquidacao'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}
