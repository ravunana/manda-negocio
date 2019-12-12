import { element, by, ElementFinder } from 'protractor';

export class LocalizacaoEmpresaComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('rv-localizacao-empresa div table .btn-danger'));
  title = element.all(by.css('rv-localizacao-empresa div h2#page-heading span')).first();

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

export class LocalizacaoEmpresaUpdatePage {
  pageTitle = element(by.id('rv-localizacao-empresa-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  paisInput = element(by.id('field_pais'));
  provinciaInput = element(by.id('field_provincia'));
  municipioInput = element(by.id('field_municipio'));
  bairroInput = element(by.id('field_bairro'));
  ruaInput = element(by.id('field_rua'));
  quarteiraoInput = element(by.id('field_quarteirao'));
  numeroPortaInput = element(by.id('field_numeroPorta'));
  caixaPostalInput = element(by.id('field_caixaPostal'));
  empresaSelect = element(by.id('field_empresa'));

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

  async setCaixaPostalInput(caixaPostal) {
    await this.caixaPostalInput.sendKeys(caixaPostal);
  }

  async getCaixaPostalInput() {
    return await this.caixaPostalInput.getAttribute('value');
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

export class LocalizacaoEmpresaDeleteDialog {
  private dialogTitle = element(by.id('rv-delete-localizacaoEmpresa-heading'));
  private confirmButton = element(by.id('rv-confirm-delete-localizacaoEmpresa'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}
