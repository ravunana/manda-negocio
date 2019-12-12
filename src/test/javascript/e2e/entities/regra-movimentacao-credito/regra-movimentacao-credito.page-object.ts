import { element, by, ElementFinder } from 'protractor';

export class RegraMovimentacaoCreditoComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('rv-regra-movimentacao-credito div table .btn-danger'));
  title = element.all(by.css('rv-regra-movimentacao-credito div h2#page-heading span')).first();

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

export class RegraMovimentacaoCreditoUpdatePage {
  pageTitle = element(by.id('rv-regra-movimentacao-credito-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  descricaoInput = element(by.id('field_descricao'));
  contaSelect = element(by.id('field_conta'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
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

export class RegraMovimentacaoCreditoDeleteDialog {
  private dialogTitle = element(by.id('rv-delete-regraMovimentacaoCredito-heading'));
  private confirmButton = element(by.id('rv-confirm-delete-regraMovimentacaoCredito'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}
