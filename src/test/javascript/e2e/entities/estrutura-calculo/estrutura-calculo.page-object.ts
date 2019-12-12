import { element, by, ElementFinder } from 'protractor';

export class EstruturaCalculoComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('rv-estrutura-calculo div table .btn-danger'));
  title = element.all(by.css('rv-estrutura-calculo div h2#page-heading span')).first();

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

export class EstruturaCalculoUpdatePage {
  pageTitle = element(by.id('rv-estrutura-calculo-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  custoMateriaPrimaInput = element(by.id('field_custoMateriaPrima'));
  custoMaoObraInput = element(by.id('field_custoMaoObra'));
  custoEmbalagemInput = element(by.id('field_custoEmbalagem'));
  custoAquisicaoInput = element(by.id('field_custoAquisicao'));
  comissaoInput = element(by.id('field_comissao'));
  margemLucroInput = element(by.id('field_margemLucro'));
  precoVendaInput = element(by.id('field_precoVenda'));
  dataInput = element(by.id('field_data'));
  utilizadorSelect = element(by.id('field_utilizador'));
  produtoSelect = element(by.id('field_produto'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setCustoMateriaPrimaInput(custoMateriaPrima) {
    await this.custoMateriaPrimaInput.sendKeys(custoMateriaPrima);
  }

  async getCustoMateriaPrimaInput() {
    return await this.custoMateriaPrimaInput.getAttribute('value');
  }

  async setCustoMaoObraInput(custoMaoObra) {
    await this.custoMaoObraInput.sendKeys(custoMaoObra);
  }

  async getCustoMaoObraInput() {
    return await this.custoMaoObraInput.getAttribute('value');
  }

  async setCustoEmbalagemInput(custoEmbalagem) {
    await this.custoEmbalagemInput.sendKeys(custoEmbalagem);
  }

  async getCustoEmbalagemInput() {
    return await this.custoEmbalagemInput.getAttribute('value');
  }

  async setCustoAquisicaoInput(custoAquisicao) {
    await this.custoAquisicaoInput.sendKeys(custoAquisicao);
  }

  async getCustoAquisicaoInput() {
    return await this.custoAquisicaoInput.getAttribute('value');
  }

  async setComissaoInput(comissao) {
    await this.comissaoInput.sendKeys(comissao);
  }

  async getComissaoInput() {
    return await this.comissaoInput.getAttribute('value');
  }

  async setMargemLucroInput(margemLucro) {
    await this.margemLucroInput.sendKeys(margemLucro);
  }

  async getMargemLucroInput() {
    return await this.margemLucroInput.getAttribute('value');
  }

  async setPrecoVendaInput(precoVenda) {
    await this.precoVendaInput.sendKeys(precoVenda);
  }

  async getPrecoVendaInput() {
    return await this.precoVendaInput.getAttribute('value');
  }

  async setDataInput(data) {
    await this.dataInput.sendKeys(data);
  }

  async getDataInput() {
    return await this.dataInput.getAttribute('value');
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

export class EstruturaCalculoDeleteDialog {
  private dialogTitle = element(by.id('rv-delete-estruturaCalculo-heading'));
  private confirmButton = element(by.id('rv-confirm-delete-estruturaCalculo'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}
