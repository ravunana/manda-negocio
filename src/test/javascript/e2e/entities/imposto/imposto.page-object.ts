import { element, by, ElementFinder } from 'protractor';

export class ImpostoComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('rv-imposto div table .btn-danger'));
  title = element.all(by.css('rv-imposto div h2#page-heading span')).first();

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

export class ImpostoUpdatePage {
  pageTitle = element(by.id('rv-imposto-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  tipoImpostoInput = element(by.id('field_tipoImposto'));
  codigoImpostoInput = element(by.id('field_codigoImposto'));
  porcentagemInput = element(by.id('field_porcentagem'));
  valorInput = element(by.id('field_valor'));
  descricaoInput = element(by.id('field_descricao'));
  paisInput = element(by.id('field_pais'));
  vigenciaInput = element(by.id('field_vigencia'));
  contaSelect = element(by.id('field_conta'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setTipoImpostoInput(tipoImposto) {
    await this.tipoImpostoInput.sendKeys(tipoImposto);
  }

  async getTipoImpostoInput() {
    return await this.tipoImpostoInput.getAttribute('value');
  }

  async setCodigoImpostoInput(codigoImposto) {
    await this.codigoImpostoInput.sendKeys(codigoImposto);
  }

  async getCodigoImpostoInput() {
    return await this.codigoImpostoInput.getAttribute('value');
  }

  getPorcentagemInput() {
    return this.porcentagemInput;
  }
  async setValorInput(valor) {
    await this.valorInput.sendKeys(valor);
  }

  async getValorInput() {
    return await this.valorInput.getAttribute('value');
  }

  async setDescricaoInput(descricao) {
    await this.descricaoInput.sendKeys(descricao);
  }

  async getDescricaoInput() {
    return await this.descricaoInput.getAttribute('value');
  }

  async setPaisInput(pais) {
    await this.paisInput.sendKeys(pais);
  }

  async getPaisInput() {
    return await this.paisInput.getAttribute('value');
  }

  async setVigenciaInput(vigencia) {
    await this.vigenciaInput.sendKeys(vigencia);
  }

  async getVigenciaInput() {
    return await this.vigenciaInput.getAttribute('value');
  }

  async contaSelectLastOption() {
    await this.contaSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async contaSelectOption(option) {
    await this.contaSelect.sendKeys(option);
  }

  getContaSelect(): ElementFinder {
    return this.contaSelect;
  }

  async getContaSelectedOption() {
    return await this.contaSelect.element(by.css('option:checked')).getText();
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

export class ImpostoDeleteDialog {
  private dialogTitle = element(by.id('rv-delete-imposto-heading'));
  private confirmButton = element(by.id('rv-confirm-delete-imposto'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}
