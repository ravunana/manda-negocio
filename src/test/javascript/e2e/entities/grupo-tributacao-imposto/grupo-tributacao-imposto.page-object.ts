import { element, by, ElementFinder } from 'protractor';

export class GrupoTributacaoImpostoComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('rv-grupo-tributacao-imposto div table .btn-danger'));
  title = element.all(by.css('rv-grupo-tributacao-imposto div h2#page-heading span')).first();

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

export class GrupoTributacaoImpostoUpdatePage {
  pageTitle = element(by.id('rv-grupo-tributacao-imposto-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  nomeInput = element(by.id('field_nome'));
  valorInput = element(by.id('field_valor'));
  deInput = element(by.id('field_de'));
  ateInput = element(by.id('field_ate'));
  limiteLiquidacaoInput = element(by.id('field_limiteLiquidacao'));
  impostoSelect = element(by.id('field_imposto'));
  unidadeLimiteSelect = element(by.id('field_unidadeLimite'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setNomeInput(nome) {
    await this.nomeInput.sendKeys(nome);
  }

  async getNomeInput() {
    return await this.nomeInput.getAttribute('value');
  }

  async setValorInput(valor) {
    await this.valorInput.sendKeys(valor);
  }

  async getValorInput() {
    return await this.valorInput.getAttribute('value');
  }

  async setDeInput(de) {
    await this.deInput.sendKeys(de);
  }

  async getDeInput() {
    return await this.deInput.getAttribute('value');
  }

  async setAteInput(ate) {
    await this.ateInput.sendKeys(ate);
  }

  async getAteInput() {
    return await this.ateInput.getAttribute('value');
  }

  async setLimiteLiquidacaoInput(limiteLiquidacao) {
    await this.limiteLiquidacaoInput.sendKeys(limiteLiquidacao);
  }

  async getLimiteLiquidacaoInput() {
    return await this.limiteLiquidacaoInput.getAttribute('value');
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

  async unidadeLimiteSelectLastOption() {
    await this.unidadeLimiteSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async unidadeLimiteSelectOption(option) {
    await this.unidadeLimiteSelect.sendKeys(option);
  }

  getUnidadeLimiteSelect(): ElementFinder {
    return this.unidadeLimiteSelect;
  }

  async getUnidadeLimiteSelectedOption() {
    return await this.unidadeLimiteSelect.element(by.css('option:checked')).getText();
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

export class GrupoTributacaoImpostoDeleteDialog {
  private dialogTitle = element(by.id('rv-delete-grupoTributacaoImposto-heading'));
  private confirmButton = element(by.id('rv-confirm-delete-grupoTributacaoImposto'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}
