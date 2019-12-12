import { element, by, ElementFinder } from 'protractor';

export class LocalArmazenamentoComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('rv-local-armazenamento div table .btn-danger'));
  title = element.all(by.css('rv-local-armazenamento div h2#page-heading span')).first();

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

export class LocalArmazenamentoUpdatePage {
  pageTitle = element(by.id('rv-local-armazenamento-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  nomeInput = element(by.id('field_nome'));
  descricaoInput = element(by.id('field_descricao'));
  hierarquiaSelect = element(by.id('field_hierarquia'));
  empresaSelect = element(by.id('field_empresa'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setNomeInput(nome) {
    await this.nomeInput.sendKeys(nome);
  }

  async getNomeInput() {
    return await this.nomeInput.getAttribute('value');
  }

  async setDescricaoInput(descricao) {
    await this.descricaoInput.sendKeys(descricao);
  }

  async getDescricaoInput() {
    return await this.descricaoInput.getAttribute('value');
  }

  async hierarquiaSelectLastOption() {
    await this.hierarquiaSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async hierarquiaSelectOption(option) {
    await this.hierarquiaSelect.sendKeys(option);
  }

  getHierarquiaSelect(): ElementFinder {
    return this.hierarquiaSelect;
  }

  async getHierarquiaSelectedOption() {
    return await this.hierarquiaSelect.element(by.css('option:checked')).getText();
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

export class LocalArmazenamentoDeleteDialog {
  private dialogTitle = element(by.id('rv-delete-localArmazenamento-heading'));
  private confirmButton = element(by.id('rv-confirm-delete-localArmazenamento'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}
