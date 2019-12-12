import { element, by, ElementFinder } from 'protractor';

export class PessoaComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('rv-pessoa div table .btn-danger'));
  title = element.all(by.css('rv-pessoa div h2#page-heading span')).first();

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

export class PessoaUpdatePage {
  pageTitle = element(by.id('rv-pessoa-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  tipoPessoaInput = element(by.id('field_tipoPessoa'));
  nomeInput = element(by.id('field_nome'));
  nifInput = element(by.id('field_nif'));
  imagemInput = element(by.id('file_imagem'));
  utilizadorSelect = element(by.id('field_utilizador'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setTipoPessoaInput(tipoPessoa) {
    await this.tipoPessoaInput.sendKeys(tipoPessoa);
  }

  async getTipoPessoaInput() {
    return await this.tipoPessoaInput.getAttribute('value');
  }

  async setNomeInput(nome) {
    await this.nomeInput.sendKeys(nome);
  }

  async getNomeInput() {
    return await this.nomeInput.getAttribute('value');
  }

  async setNifInput(nif) {
    await this.nifInput.sendKeys(nif);
  }

  async getNifInput() {
    return await this.nifInput.getAttribute('value');
  }

  async setImagemInput(imagem) {
    await this.imagemInput.sendKeys(imagem);
  }

  async getImagemInput() {
    return await this.imagemInput.getAttribute('value');
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

export class PessoaDeleteDialog {
  private dialogTitle = element(by.id('rv-delete-pessoa-heading'));
  private confirmButton = element(by.id('rv-confirm-delete-pessoa'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}
