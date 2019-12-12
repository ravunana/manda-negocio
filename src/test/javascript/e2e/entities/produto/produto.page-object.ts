import { element, by, ElementFinder } from 'protractor';

export class ProdutoComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('rv-produto div table .btn-danger'));
  title = element.all(by.css('rv-produto div h2#page-heading span')).first();

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

export class ProdutoUpdatePage {
  pageTitle = element(by.id('rv-produto-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  tipoInput = element(by.id('field_tipo'));
  eanInput = element(by.id('field_ean'));
  numeroInput = element(by.id('field_numero'));
  nomeInput = element(by.id('field_nome'));
  imagemInput = element(by.id('file_imagem'));
  compostoInput = element(by.id('field_composto'));
  estoqueMinimoInput = element(by.id('field_estoqueMinimo'));
  estoqueMaximoInput = element(by.id('field_estoqueMaximo'));
  estoqueAtualInput = element(by.id('field_estoqueAtual'));
  descricaoInput = element(by.id('field_descricao'));
  mostrarPDVInput = element(by.id('field_mostrarPDV'));
  prazoMedioEntregaInput = element(by.id('field_prazoMedioEntrega'));
  utilizadorSelect = element(by.id('field_utilizador'));
  impostoSelect = element(by.id('field_imposto'));
  fornecedorSelect = element(by.id('field_fornecedor'));
  localArmazenamentoSelect = element(by.id('field_localArmazenamento'));
  familiaSelect = element(by.id('field_familia'));
  empresaSelect = element(by.id('field_empresa'));
  estadoSelect = element(by.id('field_estado'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setTipoInput(tipo) {
    await this.tipoInput.sendKeys(tipo);
  }

  async getTipoInput() {
    return await this.tipoInput.getAttribute('value');
  }

  async setEanInput(ean) {
    await this.eanInput.sendKeys(ean);
  }

  async getEanInput() {
    return await this.eanInput.getAttribute('value');
  }

  async setNumeroInput(numero) {
    await this.numeroInput.sendKeys(numero);
  }

  async getNumeroInput() {
    return await this.numeroInput.getAttribute('value');
  }

  async setNomeInput(nome) {
    await this.nomeInput.sendKeys(nome);
  }

  async getNomeInput() {
    return await this.nomeInput.getAttribute('value');
  }

  async setImagemInput(imagem) {
    await this.imagemInput.sendKeys(imagem);
  }

  async getImagemInput() {
    return await this.imagemInput.getAttribute('value');
  }

  getCompostoInput() {
    return this.compostoInput;
  }
  async setEstoqueMinimoInput(estoqueMinimo) {
    await this.estoqueMinimoInput.sendKeys(estoqueMinimo);
  }

  async getEstoqueMinimoInput() {
    return await this.estoqueMinimoInput.getAttribute('value');
  }

  async setEstoqueMaximoInput(estoqueMaximo) {
    await this.estoqueMaximoInput.sendKeys(estoqueMaximo);
  }

  async getEstoqueMaximoInput() {
    return await this.estoqueMaximoInput.getAttribute('value');
  }

  async setEstoqueAtualInput(estoqueAtual) {
    await this.estoqueAtualInput.sendKeys(estoqueAtual);
  }

  async getEstoqueAtualInput() {
    return await this.estoqueAtualInput.getAttribute('value');
  }

  async setDescricaoInput(descricao) {
    await this.descricaoInput.sendKeys(descricao);
  }

  async getDescricaoInput() {
    return await this.descricaoInput.getAttribute('value');
  }

  getMostrarPDVInput() {
    return this.mostrarPDVInput;
  }
  async setPrazoMedioEntregaInput(prazoMedioEntrega) {
    await this.prazoMedioEntregaInput.sendKeys(prazoMedioEntrega);
  }

  async getPrazoMedioEntregaInput() {
    return await this.prazoMedioEntregaInput.getAttribute('value');
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

  async fornecedorSelectLastOption() {
    await this.fornecedorSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async fornecedorSelectOption(option) {
    await this.fornecedorSelect.sendKeys(option);
  }

  getFornecedorSelect(): ElementFinder {
    return this.fornecedorSelect;
  }

  async getFornecedorSelectedOption() {
    return await this.fornecedorSelect.element(by.css('option:checked')).getText();
  }

  async localArmazenamentoSelectLastOption() {
    await this.localArmazenamentoSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async localArmazenamentoSelectOption(option) {
    await this.localArmazenamentoSelect.sendKeys(option);
  }

  getLocalArmazenamentoSelect(): ElementFinder {
    return this.localArmazenamentoSelect;
  }

  async getLocalArmazenamentoSelectedOption() {
    return await this.localArmazenamentoSelect.element(by.css('option:checked')).getText();
  }

  async familiaSelectLastOption() {
    await this.familiaSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async familiaSelectOption(option) {
    await this.familiaSelect.sendKeys(option);
  }

  getFamiliaSelect(): ElementFinder {
    return this.familiaSelect;
  }

  async getFamiliaSelectedOption() {
    return await this.familiaSelect.element(by.css('option:checked')).getText();
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

  async estadoSelectLastOption() {
    await this.estadoSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async estadoSelectOption(option) {
    await this.estadoSelect.sendKeys(option);
  }

  getEstadoSelect(): ElementFinder {
    return this.estadoSelect;
  }

  async getEstadoSelectedOption() {
    return await this.estadoSelect.element(by.css('option:checked')).getText();
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

export class ProdutoDeleteDialog {
  private dialogTitle = element(by.id('rv-delete-produto-heading'));
  private confirmButton = element(by.id('rv-confirm-delete-produto'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}
