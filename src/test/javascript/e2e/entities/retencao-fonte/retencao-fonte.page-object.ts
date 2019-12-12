import { element, by, ElementFinder } from 'protractor';

export class RetencaoFonteComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('rv-retencao-fonte div table .btn-danger'));
  title = element.all(by.css('rv-retencao-fonte div h2#page-heading span')).first();

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

export class RetencaoFonteUpdatePage {
  pageTitle = element(by.id('rv-retencao-fonte-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  motivoRetencaoInput = element(by.id('field_motivoRetencao'));
  valorInput = element(by.id('field_valor'));
  porcentagemInput = element(by.id('field_porcentagem'));
  detalheSelect = element(by.id('field_detalhe'));
  impostoSelect = element(by.id('field_imposto'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setMotivoRetencaoInput(motivoRetencao) {
    await this.motivoRetencaoInput.sendKeys(motivoRetencao);
  }

  async getMotivoRetencaoInput() {
    return await this.motivoRetencaoInput.getAttribute('value');
  }

  async setValorInput(valor) {
    await this.valorInput.sendKeys(valor);
  }

  async getValorInput() {
    return await this.valorInput.getAttribute('value');
  }

  getPorcentagemInput() {
    return this.porcentagemInput;
  }

  async detalheSelectLastOption() {
    await this.detalheSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async detalheSelectOption(option) {
    await this.detalheSelect.sendKeys(option);
  }

  getDetalheSelect(): ElementFinder {
    return this.detalheSelect;
  }

  async getDetalheSelectedOption() {
    return await this.detalheSelect.element(by.css('option:checked')).getText();
  }

  async impostoSelectLastOption() {
    await this.impostoSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async impostoSelectOption(option) {
    await this.impostoSelect.sendKeys(option);
  }

  getImpostoSelect(): ElementFinder {
    return this.impostoSelect;
  }

  async getImpostoSelectedOption() {
    return await this.impostoSelect.element(by.css('option:checked')).getText();
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

export class RetencaoFonteDeleteDialog {
  private dialogTitle = element(by.id('rv-delete-retencaoFonte-heading'));
  private confirmButton = element(by.id('rv-confirm-delete-retencaoFonte'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}
