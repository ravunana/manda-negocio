import { element, by, ElementFinder } from 'protractor';

export class FluxoDocumentoComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('rv-fluxo-documento div table .btn-danger'));
  title = element.all(by.css('rv-fluxo-documento div h2#page-heading span')).first();

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

export class FluxoDocumentoUpdatePage {
  pageTitle = element(by.id('rv-fluxo-documento-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  nomeInput = element(by.id('field_nome'));
  aumentaEstoqueInput = element(by.id('field_aumentaEstoque'));
  aumentaValorCaixaInput = element(by.id('field_aumentaValorCaixa'));
  corInput = element(by.id('field_cor'));
  padraoInput = element(by.id('field_padrao'));
  descricaoInput = element(by.id('field_descricao'));
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

  getAumentaEstoqueInput() {
    return this.aumentaEstoqueInput;
  }
  getAumentaValorCaixaInput() {
    return this.aumentaValorCaixaInput;
  }
  async setCorInput(cor) {
    await this.corInput.sendKeys(cor);
  }

  async getCorInput() {
    return await this.corInput.getAttribute('value');
  }

  getPadraoInput() {
    return this.padraoInput;
  }
  async setDescricaoInput(descricao) {
    await this.descricaoInput.sendKeys(descricao);
  }

  async getDescricaoInput() {
    return await this.descricaoInput.getAttribute('value');
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

export class FluxoDocumentoDeleteDialog {
  private dialogTitle = element(by.id('rv-delete-fluxoDocumento-heading'));
  private confirmButton = element(by.id('rv-confirm-delete-fluxoDocumento'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}
