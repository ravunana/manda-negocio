import { element, by, ElementFinder } from 'protractor';

export class LicencaSoftwareComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('rv-licenca-software div table .btn-danger'));
  title = element.all(by.css('rv-licenca-software div h2#page-heading span')).first();

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

export class LicencaSoftwareUpdatePage {
  pageTitle = element(by.id('rv-licenca-software-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  tipoSubscricaoInput = element(by.id('field_tipoSubscricao'));
  inicioInput = element(by.id('field_inicio'));
  fimInput = element(by.id('field_fim'));
  dataInput = element(by.id('field_data'));
  valorInput = element(by.id('field_valor'));
  codigoInput = element(by.id('field_codigo'));
  numeroUsuarioInput = element(by.id('field_numeroUsuario'));
  numeroEmpresaInput = element(by.id('field_numeroEmpresa'));
  softwareSelect = element(by.id('field_software'));
  empresaSelect = element(by.id('field_empresa'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setTipoSubscricaoInput(tipoSubscricao) {
    await this.tipoSubscricaoInput.sendKeys(tipoSubscricao);
  }

  async getTipoSubscricaoInput() {
    return await this.tipoSubscricaoInput.getAttribute('value');
  }

  async setInicioInput(inicio) {
    await this.inicioInput.sendKeys(inicio);
  }

  async getInicioInput() {
    return await this.inicioInput.getAttribute('value');
  }

  async setFimInput(fim) {
    await this.fimInput.sendKeys(fim);
  }

  async getFimInput() {
    return await this.fimInput.getAttribute('value');
  }

  async setDataInput(data) {
    await this.dataInput.sendKeys(data);
  }

  async getDataInput() {
    return await this.dataInput.getAttribute('value');
  }

  async setValorInput(valor) {
    await this.valorInput.sendKeys(valor);
  }

  async getValorInput() {
    return await this.valorInput.getAttribute('value');
  }

  async setCodigoInput(codigo) {
    await this.codigoInput.sendKeys(codigo);
  }

  async getCodigoInput() {
    return await this.codigoInput.getAttribute('value');
  }

  async setNumeroUsuarioInput(numeroUsuario) {
    await this.numeroUsuarioInput.sendKeys(numeroUsuario);
  }

  async getNumeroUsuarioInput() {
    return await this.numeroUsuarioInput.getAttribute('value');
  }

  async setNumeroEmpresaInput(numeroEmpresa) {
    await this.numeroEmpresaInput.sendKeys(numeroEmpresa);
  }

  async getNumeroEmpresaInput() {
    return await this.numeroEmpresaInput.getAttribute('value');
  }

  async softwareSelectLastOption() {
    await this.softwareSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async softwareSelectOption(option) {
    await this.softwareSelect.sendKeys(option);
  }

  getSoftwareSelect(): ElementFinder {
    return this.softwareSelect;
  }

  async getSoftwareSelectedOption() {
    return await this.softwareSelect.element(by.css('option:checked')).getText();
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

export class LicencaSoftwareDeleteDialog {
  private dialogTitle = element(by.id('rv-delete-licencaSoftware-heading'));
  private confirmButton = element(by.id('rv-confirm-delete-licencaSoftware'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}
