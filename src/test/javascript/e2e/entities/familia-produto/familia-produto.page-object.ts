import { element, by, ElementFinder } from 'protractor';

export class FamiliaProdutoComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('rv-familia-produto div table .btn-danger'));
  title = element.all(by.css('rv-familia-produto div h2#page-heading span')).first();

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

export class FamiliaProdutoUpdatePage {
  pageTitle = element(by.id('rv-familia-produto-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  nomeInput = element(by.id('field_nome'));
  descricaoInput = element(by.id('field_descricao'));
  contaSelect = element(by.id('field_conta'));
  hierarquiaSelect = element(by.id('field_hierarquia'));

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

export class FamiliaProdutoDeleteDialog {
  private dialogTitle = element(by.id('rv-delete-familiaProduto-heading'));
  private confirmButton = element(by.id('rv-confirm-delete-familiaProduto'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}
