import { element, by, ElementFinder } from 'protractor';

export class DevolucaoVendaComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('rv-devolucao-venda div table .btn-danger'));
  title = element.all(by.css('rv-devolucao-venda div h2#page-heading span')).first();

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

export class DevolucaoVendaUpdatePage {
  pageTitle = element(by.id('rv-devolucao-venda-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  quantidadeInput = element(by.id('field_quantidade'));
  valorInput = element(by.id('field_valor'));
  descontoInput = element(by.id('field_desconto'));
  dataInput = element(by.id('field_data'));
  descricaoInput = element(by.id('field_descricao'));
  itemSelect = element(by.id('field_item'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setQuantidadeInput(quantidade) {
    await this.quantidadeInput.sendKeys(quantidade);
  }

  async getQuantidadeInput() {
    return await this.quantidadeInput.getAttribute('value');
  }

  async setValorInput(valor) {
    await this.valorInput.sendKeys(valor);
  }

  async getValorInput() {
    return await this.valorInput.getAttribute('value');
  }

  async setDescontoInput(desconto) {
    await this.descontoInput.sendKeys(desconto);
  }

  async getDescontoInput() {
    return await this.descontoInput.getAttribute('value');
  }

  async setDataInput(data) {
    await this.dataInput.sendKeys(data);
  }

  async getDataInput() {
    return await this.dataInput.getAttribute('value');
  }

  async setDescricaoInput(descricao) {
    await this.descricaoInput.sendKeys(descricao);
  }

  async getDescricaoInput() {
    return await this.descricaoInput.getAttribute('value');
  }

  async itemSelectLastOption() {
    await this.itemSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async itemSelectOption(option) {
    await this.itemSelect.sendKeys(option);
  }

  getItemSelect(): ElementFinder {
    return this.itemSelect;
  }

  async getItemSelectedOption() {
    return await this.itemSelect.element(by.css('option:checked')).getText();
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

export class DevolucaoVendaDeleteDialog {
  private dialogTitle = element(by.id('rv-delete-devolucaoVenda-heading'));
  private confirmButton = element(by.id('rv-confirm-delete-devolucaoVenda'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}
