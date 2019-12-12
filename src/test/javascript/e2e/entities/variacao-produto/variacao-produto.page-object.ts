import { element, by, ElementFinder } from 'protractor';

export class VariacaoProdutoComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('rv-variacao-produto div table .btn-danger'));
  title = element.all(by.css('rv-variacao-produto div h2#page-heading span')).first();

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

export class VariacaoProdutoUpdatePage {
  pageTitle = element(by.id('rv-variacao-produto-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  generoInput = element(by.id('field_genero'));
  corInput = element(by.id('field_cor'));
  modeloInput = element(by.id('field_modelo'));
  marcaInput = element(by.id('field_marca'));
  tamanhoInput = element(by.id('field_tamanho'));
  produtoSelect = element(by.id('field_produto'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setGeneroInput(genero) {
    await this.generoInput.sendKeys(genero);
  }

  async getGeneroInput() {
    return await this.generoInput.getAttribute('value');
  }

  async setCorInput(cor) {
    await this.corInput.sendKeys(cor);
  }

  async getCorInput() {
    return await this.corInput.getAttribute('value');
  }

  async setModeloInput(modelo) {
    await this.modeloInput.sendKeys(modelo);
  }

  async getModeloInput() {
    return await this.modeloInput.getAttribute('value');
  }

  async setMarcaInput(marca) {
    await this.marcaInput.sendKeys(marca);
  }

  async getMarcaInput() {
    return await this.marcaInput.getAttribute('value');
  }

  async setTamanhoInput(tamanho) {
    await this.tamanhoInput.sendKeys(tamanho);
  }

  async getTamanhoInput() {
    return await this.tamanhoInput.getAttribute('value');
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

export class VariacaoProdutoDeleteDialog {
  private dialogTitle = element(by.id('rv-delete-variacaoProduto-heading'));
  private confirmButton = element(by.id('rv-confirm-delete-variacaoProduto'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}
