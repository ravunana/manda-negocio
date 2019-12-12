import { element, by, ElementFinder } from 'protractor';

export class ArquivoComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('rv-arquivo div table .btn-danger'));
  title = element.all(by.css('rv-arquivo div h2#page-heading span')).first();

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

export class ArquivoUpdatePage {
  pageTitle = element(by.id('rv-arquivo-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  descricaoInput = element(by.id('field_descricao'));
  entidadeSelect = element(by.id('field_entidade'));
  anexoInput = element(by.id('file_anexo'));
  codigoEntidadeInput = element(by.id('field_codigoEntidade'));
  dataInput = element(by.id('field_data'));
  utilizadorSelect = element(by.id('field_utilizador'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
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

  async setAnexoInput(anexo) {
    await this.anexoInput.sendKeys(anexo);
  }

  async getAnexoInput() {
    return await this.anexoInput.getAttribute('value');
  }

  async setCodigoEntidadeInput(codigoEntidade) {
    await this.codigoEntidadeInput.sendKeys(codigoEntidade);
  }

  async getCodigoEntidadeInput() {
    return await this.codigoEntidadeInput.getAttribute('value');
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

export class ArquivoDeleteDialog {
  private dialogTitle = element(by.id('rv-delete-arquivo-heading'));
  private confirmButton = element(by.id('rv-confirm-delete-arquivo'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}
