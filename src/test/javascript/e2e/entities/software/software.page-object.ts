import { element, by, ElementFinder } from 'protractor';

export class SoftwareComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('rv-software div table .btn-danger'));
  title = element.all(by.css('rv-software div h2#page-heading span')).first();

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

export class SoftwareUpdatePage {
  pageTitle = element(by.id('rv-software-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  empresaInput = element(by.id('field_empresa'));
  tipoSistemaInput = element(by.id('field_tipoSistema'));
  nifInput = element(by.id('field_nif'));
  numeroValidacaoAGTInput = element(by.id('field_numeroValidacaoAGT'));
  nomeInput = element(by.id('field_nome'));
  versaoInput = element(by.id('field_versao'));
  hashCodeInput = element(by.id('field_hashCode'));
  hashControlInput = element(by.id('field_hashControl'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setEmpresaInput(empresa) {
    await this.empresaInput.sendKeys(empresa);
  }

  async getEmpresaInput() {
    return await this.empresaInput.getAttribute('value');
  }

  async setTipoSistemaInput(tipoSistema) {
    await this.tipoSistemaInput.sendKeys(tipoSistema);
  }

  async getTipoSistemaInput() {
    return await this.tipoSistemaInput.getAttribute('value');
  }

  async setNifInput(nif) {
    await this.nifInput.sendKeys(nif);
  }

  async getNifInput() {
    return await this.nifInput.getAttribute('value');
  }

  async setNumeroValidacaoAGTInput(numeroValidacaoAGT) {
    await this.numeroValidacaoAGTInput.sendKeys(numeroValidacaoAGT);
  }

  async getNumeroValidacaoAGTInput() {
    return await this.numeroValidacaoAGTInput.getAttribute('value');
  }

  async setNomeInput(nome) {
    await this.nomeInput.sendKeys(nome);
  }

  async getNomeInput() {
    return await this.nomeInput.getAttribute('value');
  }

  async setVersaoInput(versao) {
    await this.versaoInput.sendKeys(versao);
  }

  async getVersaoInput() {
    return await this.versaoInput.getAttribute('value');
  }

  async setHashCodeInput(hashCode) {
    await this.hashCodeInput.sendKeys(hashCode);
  }

  async getHashCodeInput() {
    return await this.hashCodeInput.getAttribute('value');
  }

  async setHashControlInput(hashControl) {
    await this.hashControlInput.sendKeys(hashControl);
  }

  async getHashControlInput() {
    return await this.hashControlInput.getAttribute('value');
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

export class SoftwareDeleteDialog {
  private dialogTitle = element(by.id('rv-delete-software-heading'));
  private confirmButton = element(by.id('rv-confirm-delete-software'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}
