import { element, by, ElementFinder } from 'protractor';

export class RelacionamentoPessoaComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('rv-relacionamento-pessoa div table .btn-danger'));
  title = element.all(by.css('rv-relacionamento-pessoa div h2#page-heading span')).first();

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

export class RelacionamentoPessoaUpdatePage {
  pageTitle = element(by.id('rv-relacionamento-pessoa-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  grauParentescoInput = element(by.id('field_grauParentesco'));
  deSelect = element(by.id('field_de'));
  paraSelect = element(by.id('field_para'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setGrauParentescoInput(grauParentesco) {
    await this.grauParentescoInput.sendKeys(grauParentesco);
  }

  async getGrauParentescoInput() {
    return await this.grauParentescoInput.getAttribute('value');
  }

  async deSelectLastOption() {
    await this.deSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async deSelectOption(option) {
    await this.deSelect.sendKeys(option);
  }

  getDeSelect(): ElementFinder {
    return this.deSelect;
  }

  async getDeSelectedOption() {
    return await this.deSelect.element(by.css('option:checked')).getText();
  }

  async paraSelectLastOption() {
    await this.paraSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async paraSelectOption(option) {
    await this.paraSelect.sendKeys(option);
  }

  getParaSelect(): ElementFinder {
    return this.paraSelect;
  }

  async getParaSelectedOption() {
    return await this.paraSelect.element(by.css('option:checked')).getText();
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

export class RelacionamentoPessoaDeleteDialog {
  private dialogTitle = element(by.id('rv-delete-relacionamentoPessoa-heading'));
  private confirmButton = element(by.id('rv-confirm-delete-relacionamentoPessoa'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}
