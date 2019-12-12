import { element, by, ElementFinder } from 'protractor';

export class DetalheLancamentoComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('rv-detalhe-lancamento div table .btn-danger'));
  title = element.all(by.css('rv-detalhe-lancamento div h2#page-heading span')).first();

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

export class DetalheLancamentoUpdatePage {
  pageTitle = element(by.id('rv-detalhe-lancamento-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  valorInput = element(by.id('field_valor'));
  descontoInput = element(by.id('field_desconto'));
  juroInput = element(by.id('field_juro'));
  descricaoInput = element(by.id('field_descricao'));
  dataInput = element(by.id('field_data'));
  retencaoFonteInput = element(by.id('field_retencaoFonte'));
  dataVencimentoInput = element(by.id('field_dataVencimento'));
  liquidadoInput = element(by.id('field_liquidado'));
  utilizadorSelect = element(by.id('field_utilizador'));
  lancamentoFinanceiroSelect = element(by.id('field_lancamentoFinanceiro'));
  metodoLiquidacaoSelect = element(by.id('field_metodoLiquidacao'));
  moedaSelect = element(by.id('field_moeda'));
  coordenadaSelect = element(by.id('field_coordenada'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
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

  async setJuroInput(juro) {
    await this.juroInput.sendKeys(juro);
  }

  async getJuroInput() {
    return await this.juroInput.getAttribute('value');
  }

  async setDescricaoInput(descricao) {
    await this.descricaoInput.sendKeys(descricao);
  }

  async getDescricaoInput() {
    return await this.descricaoInput.getAttribute('value');
  }

  async setDataInput(data) {
    await this.dataInput.sendKeys(data);
  }

  async getDataInput() {
    return await this.dataInput.getAttribute('value');
  }

  getRetencaoFonteInput() {
    return this.retencaoFonteInput;
  }
  async setDataVencimentoInput(dataVencimento) {
    await this.dataVencimentoInput.sendKeys(dataVencimento);
  }

  async getDataVencimentoInput() {
    return await this.dataVencimentoInput.getAttribute('value');
  }

  getLiquidadoInput() {
    return this.liquidadoInput;
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

  async lancamentoFinanceiroSelectLastOption() {
    await this.lancamentoFinanceiroSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async lancamentoFinanceiroSelectOption(option) {
    await this.lancamentoFinanceiroSelect.sendKeys(option);
  }

  getLancamentoFinanceiroSelect(): ElementFinder {
    return this.lancamentoFinanceiroSelect;
  }

  async getLancamentoFinanceiroSelectedOption() {
    return await this.lancamentoFinanceiroSelect.element(by.css('option:checked')).getText();
  }

  async metodoLiquidacaoSelectLastOption() {
    await this.metodoLiquidacaoSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async metodoLiquidacaoSelectOption(option) {
    await this.metodoLiquidacaoSelect.sendKeys(option);
  }

  getMetodoLiquidacaoSelect(): ElementFinder {
    return this.metodoLiquidacaoSelect;
  }

  async getMetodoLiquidacaoSelectedOption() {
    return await this.metodoLiquidacaoSelect.element(by.css('option:checked')).getText();
  }

  async moedaSelectLastOption() {
    await this.moedaSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async moedaSelectOption(option) {
    await this.moedaSelect.sendKeys(option);
  }

  getMoedaSelect(): ElementFinder {
    return this.moedaSelect;
  }

  async getMoedaSelectedOption() {
    return await this.moedaSelect.element(by.css('option:checked')).getText();
  }

  async coordenadaSelectLastOption() {
    await this.coordenadaSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async coordenadaSelectOption(option) {
    await this.coordenadaSelect.sendKeys(option);
  }

  getCoordenadaSelect(): ElementFinder {
    return this.coordenadaSelect;
  }

  async getCoordenadaSelectedOption() {
    return await this.coordenadaSelect.element(by.css('option:checked')).getText();
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

export class DetalheLancamentoDeleteDialog {
  private dialogTitle = element(by.id('rv-delete-detalheLancamento-heading'));
  private confirmButton = element(by.id('rv-confirm-delete-detalheLancamento'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}
