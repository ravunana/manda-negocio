import { element, by, ElementFinder } from 'protractor';

export class MoradaPessoaComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('rv-morada-pessoa div table .btn-danger'));
  title = element.all(by.css('rv-morada-pessoa div h2#page-heading span')).first();

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

export class MoradaPessoaUpdatePage {
  pageTitle = element(by.id('rv-morada-pessoa-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  paisInput = element(by.id('field_pais'));
  provinciaInput = element(by.id('field_provincia'));
  municipioInput = element(by.id('field_municipio'));
  bairroInput = element(by.id('field_bairro'));
  ruaInput = element(by.id('field_rua'));
  quarteiraoInput = element(by.id('field_quarteirao'));
  numeroPortaInput = element(by.id('field_numeroPorta'));
  pessoaSelect = element(by.id('field_pessoa'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setPaisInput(pais) {
    await this.paisInput.sendKeys(pais);
  }

  async getPaisInput() {
    return await this.paisInput.getAttribute('value');
  }

  async setProvinciaInput(provincia) {
    await this.provinciaInput.sendKeys(provincia);
  }

  async getProvinciaInput() {
    return await this.provinciaInput.getAttribute('value');
  }

  async setMunicipioInput(municipio) {
    await this.municipioInput.sendKeys(municipio);
  }

  async getMunicipioInput() {
    return await this.municipioInput.getAttribute('value');
  }

  async setBairroInput(bairro) {
    await this.bairroInput.sendKeys(bairro);
  }

  async getBairroInput() {
    return await this.bairroInput.getAttribute('value');
  }

  async setRuaInput(rua) {
    await this.ruaInput.sendKeys(rua);
  }

  async getRuaInput() {
    return await this.ruaInput.getAttribute('value');
  }

  async setQuarteiraoInput(quarteirao) {
    await this.quarteiraoInput.sendKeys(quarteirao);
  }

  async getQuarteiraoInput() {
    return await this.quarteiraoInput.getAttribute('value');
  }

  async setNumeroPortaInput(numeroPorta) {
    await this.numeroPortaInput.sendKeys(numeroPorta);
  }

  async getNumeroPortaInput() {
    return await this.numeroPortaInput.getAttribute('value');
  }

  async pessoaSelectLastOption() {
    await this.pessoaSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async pessoaSelectOption(option) {
    await this.pessoaSelect.sendKeys(option);
  }

  getPessoaSelect(): ElementFinder {
    return this.pessoaSelect;
  }

  async getPessoaSelectedOption() {
    return await this.pessoaSelect.element(by.css('option:checked')).getText();
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

export class MoradaPessoaDeleteDialog {
  private dialogTitle = element(by.id('rv-delete-moradaPessoa-heading'));
  private confirmButton = element(by.id('rv-confirm-delete-moradaPessoa'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}
