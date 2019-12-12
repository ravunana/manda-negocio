import { element, by, ElementFinder } from 'protractor';

export class DocumentoComercialComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('rv-documento-comercial div table .btn-danger'));
  title = element.all(by.css('rv-documento-comercial div h2#page-heading span')).first();

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

export class DocumentoComercialUpdatePage {
  pageTitle = element(by.id('rv-documento-comercial-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  nomeInput = element(by.id('field_nome'));
  serieInput = element(by.id('field_serie'));
  padraoInput = element(by.id('field_padrao'));
  movimentaEstoqueInput = element(by.id('field_movimentaEstoque'));
  movimentaCaixaInput = element(by.id('field_movimentaCaixa'));
  movimentaContabilidadeInput = element(by.id('field_movimentaContabilidade'));
  corInput = element(by.id('field_cor'));
  descricaoInput = element(by.id('field_descricao'));
  mostraPontoVendaInput = element(by.id('field_mostraPontoVenda'));
  entidadeSelect = element(by.id('field_entidade'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setNomeInput(nome) {
    await this.nomeInput.sendKeys(nome);
  }

  async getNomeInput() {
    return await this.nomeInput.getAttribute('value');
  }

  async setSerieInput(serie) {
    await this.serieInput.sendKeys(serie);
  }

  async getSerieInput() {
    return await this.serieInput.getAttribute('value');
  }

  getPadraoInput() {
    return this.padraoInput;
  }
  getMovimentaEstoqueInput() {
    return this.movimentaEstoqueInput;
  }
  getMovimentaCaixaInput() {
    return this.movimentaCaixaInput;
  }
  getMovimentaContabilidadeInput() {
    return this.movimentaContabilidadeInput;
  }
  async setCorInput(cor) {
    await this.corInput.sendKeys(cor);
  }

  async getCorInput() {
    return await this.corInput.getAttribute('value');
  }

  async setDescricaoInput(descricao) {
    await this.descricaoInput.sendKeys(descricao);
  }

  async getDescricaoInput() {
    return await this.descricaoInput.getAttribute('value');
  }

  getMostraPontoVendaInput() {
    return this.mostraPontoVendaInput;
  }
  async setEntidadeSelect(entidade) {
    await this.entidadeSelect.sendKeys(entidade);
  }

  async getEntidadeSelect() {
    return await this.entidadeSelect.element(by.css('option:checked')).getText();
  }

  async entidadeSelectLastOption() {
    await this.entidadeSelect
      .all(by.tagName('option'))
      .last()
      .click();
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

export class DocumentoComercialDeleteDialog {
  private dialogTitle = element(by.id('rv-delete-documentoComercial-heading'));
  private confirmButton = element(by.id('rv-confirm-delete-documentoComercial'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}
