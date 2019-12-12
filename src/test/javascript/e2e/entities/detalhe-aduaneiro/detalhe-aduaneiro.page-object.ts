import { element, by, ElementFinder } from 'protractor';

export class DetalheAduaneiroComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('rv-detalhe-aduaneiro div table .btn-danger'));
  title = element.all(by.css('rv-detalhe-aduaneiro div h2#page-heading span')).first();

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

export class DetalheAduaneiroUpdatePage {
  pageTitle = element(by.id('rv-detalhe-aduaneiro-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  numeroONUInput = element(by.id('field_numeroONU'));
  larguraInput = element(by.id('field_largura'));
  alturaInput = element(by.id('field_altura'));
  pesoLiquidoInput = element(by.id('field_pesoLiquido'));
  pesoBrutoInput = element(by.id('field_pesoBruto'));
  dataFabricoInput = element(by.id('field_dataFabrico'));
  dataExpiracaoInput = element(by.id('field_dataExpiracao'));
  produtoSelect = element(by.id('field_produto'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setNumeroONUInput(numeroONU) {
    await this.numeroONUInput.sendKeys(numeroONU);
  }

  async getNumeroONUInput() {
    return await this.numeroONUInput.getAttribute('value');
  }

  async setLarguraInput(largura) {
    await this.larguraInput.sendKeys(largura);
  }

  async getLarguraInput() {
    return await this.larguraInput.getAttribute('value');
  }

  async setAlturaInput(altura) {
    await this.alturaInput.sendKeys(altura);
  }

  async getAlturaInput() {
    return await this.alturaInput.getAttribute('value');
  }

  async setPesoLiquidoInput(pesoLiquido) {
    await this.pesoLiquidoInput.sendKeys(pesoLiquido);
  }

  async getPesoLiquidoInput() {
    return await this.pesoLiquidoInput.getAttribute('value');
  }

  async setPesoBrutoInput(pesoBruto) {
    await this.pesoBrutoInput.sendKeys(pesoBruto);
  }

  async getPesoBrutoInput() {
    return await this.pesoBrutoInput.getAttribute('value');
  }

  async setDataFabricoInput(dataFabrico) {
    await this.dataFabricoInput.sendKeys(dataFabrico);
  }

  async getDataFabricoInput() {
    return await this.dataFabricoInput.getAttribute('value');
  }

  async setDataExpiracaoInput(dataExpiracao) {
    await this.dataExpiracaoInput.sendKeys(dataExpiracao);
  }

  async getDataExpiracaoInput() {
    return await this.dataExpiracaoInput.getAttribute('value');
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

export class DetalheAduaneiroDeleteDialog {
  private dialogTitle = element(by.id('rv-delete-detalheAduaneiro-heading'));
  private confirmButton = element(by.id('rv-confirm-delete-detalheAduaneiro'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}
