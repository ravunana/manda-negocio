import { element, by, ElementFinder } from 'protractor';

export class LancamentoFinanceiroComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('rv-lancamento-financeiro div table .btn-danger'));
  title = element.all(by.css('rv-lancamento-financeiro div h2#page-heading span')).first();

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

export class LancamentoFinanceiroUpdatePage {
  pageTitle = element(by.id('rv-lancamento-financeiro-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  tipoLancamentoInput = element(by.id('field_tipoLancamento'));
  valorInput = element(by.id('field_valor'));
  externoInput = element(by.id('field_externo'));
  numeroInput = element(by.id('field_numero'));
  descricaoInput = element(by.id('field_descricao'));
  utilizadorSelect = element(by.id('field_utilizador'));
  contaSelect = element(by.id('field_conta'));
  impostoSelect = element(by.id('field_imposto'));
  formaLiquidacaoSelect = element(by.id('field_formaLiquidacao'));
  empresaSelect = element(by.id('field_empresa'));
  tipoReciboSelect = element(by.id('field_tipoRecibo'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setTipoLancamentoInput(tipoLancamento) {
    await this.tipoLancamentoInput.sendKeys(tipoLancamento);
  }

  async getTipoLancamentoInput() {
    return await this.tipoLancamentoInput.getAttribute('value');
  }

  async setValorInput(valor) {
    await this.valorInput.sendKeys(valor);
  }

  async getValorInput() {
    return await this.valorInput.getAttribute('value');
  }

  getExternoInput() {
    return this.externoInput;
  }
  async setNumeroInput(numero) {
    await this.numeroInput.sendKeys(numero);
  }

  async getNumeroInput() {
    return await this.numeroInput.getAttribute('value');
  }

  async setDescricaoInput(descricao) {
    await this.descricaoInput.sendKeys(descricao);
  }

  async getDescricaoInput() {
    return await this.descricaoInput.getAttribute('value');
  }

  async utilizadorSelectLastOption() {
    await this.utilizadorSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async utilizadorSelectOption(option) {
    await this.utilizadorSelect.sendKeys(option);
  }

  getUtilizadorSelect(): ElementFinder {
    return this.utilizadorSelect;
  }

  async getUtilizadorSelectedOption() {
    return await this.utilizadorSelect.element(by.css('option:checked')).getText();
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

  async formaLiquidacaoSelectLastOption() {
    await this.formaLiquidacaoSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async formaLiquidacaoSelectOption(option) {
    await this.formaLiquidacaoSelect.sendKeys(option);
  }

  getFormaLiquidacaoSelect(): ElementFinder {
    return this.formaLiquidacaoSelect;
  }

  async getFormaLiquidacaoSelectedOption() {
    return await this.formaLiquidacaoSelect.element(by.css('option:checked')).getText();
  }

  async empresaSelectLastOption() {
    await this.empresaSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async empresaSelectOption(option) {
    await this.empresaSelect.sendKeys(option);
  }

  getEmpresaSelect(): ElementFinder {
    return this.empresaSelect;
  }

  async getEmpresaSelectedOption() {
    return await this.empresaSelect.element(by.css('option:checked')).getText();
  }

  async tipoReciboSelectLastOption() {
    await this.tipoReciboSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async tipoReciboSelectOption(option) {
    await this.tipoReciboSelect.sendKeys(option);
  }

  getTipoReciboSelect(): ElementFinder {
    return this.tipoReciboSelect;
  }

  async getTipoReciboSelectedOption() {
    return await this.tipoReciboSelect.element(by.css('option:checked')).getText();
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

export class LancamentoFinanceiroDeleteDialog {
  private dialogTitle = element(by.id('rv-delete-lancamentoFinanceiro-heading'));
  private confirmButton = element(by.id('rv-confirm-delete-lancamentoFinanceiro'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}
