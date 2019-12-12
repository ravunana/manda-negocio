import { element, by, ElementFinder } from 'protractor';

export class ContaComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('rv-conta div table .btn-danger'));
  title = element.all(by.css('rv-conta div h2#page-heading span')).first();

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

export class ContaUpdatePage {
  pageTitle = element(by.id('rv-conta-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  descricaoInput = element(by.id('field_descricao'));
  codigoInput = element(by.id('field_codigo'));
  tipoInput = element(by.id('field_tipo'));
  grauInput = element(by.id('field_grau'));
  naturezaInput = element(by.id('field_natureza'));
  grupoInput = element(by.id('field_grupo'));
  conteudoInput = element(by.id('field_conteudo'));
  empresaSelect = element(by.id('field_empresa'));
  contaAgregadoraSelect = element(by.id('field_contaAgregadora'));
  classeContaSelect = element(by.id('field_classeConta'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setDescricaoInput(descricao) {
    await this.descricaoInput.sendKeys(descricao);
  }

  async getDescricaoInput() {
    return await this.descricaoInput.getAttribute('value');
  }

  async setCodigoInput(codigo) {
    await this.codigoInput.sendKeys(codigo);
  }

  async getCodigoInput() {
    return await this.codigoInput.getAttribute('value');
  }

  async setTipoInput(tipo) {
    await this.tipoInput.sendKeys(tipo);
  }

  async getTipoInput() {
    return await this.tipoInput.getAttribute('value');
  }

  async setGrauInput(grau) {
    await this.grauInput.sendKeys(grau);
  }

  async getGrauInput() {
    return await this.grauInput.getAttribute('value');
  }

  async setNaturezaInput(natureza) {
    await this.naturezaInput.sendKeys(natureza);
  }

  async getNaturezaInput() {
    return await this.naturezaInput.getAttribute('value');
  }

  async setGrupoInput(grupo) {
    await this.grupoInput.sendKeys(grupo);
  }

  async getGrupoInput() {
    return await this.grupoInput.getAttribute('value');
  }

  async setConteudoInput(conteudo) {
    await this.conteudoInput.sendKeys(conteudo);
  }

  async getConteudoInput() {
    return await this.conteudoInput.getAttribute('value');
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

  async contaAgregadoraSelectLastOption() {
    await this.contaAgregadoraSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async contaAgregadoraSelectOption(option) {
    await this.contaAgregadoraSelect.sendKeys(option);
  }

  getContaAgregadoraSelect(): ElementFinder {
    return this.contaAgregadoraSelect;
  }

  async getContaAgregadoraSelectedOption() {
    return await this.contaAgregadoraSelect.element(by.css('option:checked')).getText();
  }

  async classeContaSelectLastOption() {
    await this.classeContaSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async classeContaSelectOption(option) {
    await this.classeContaSelect.sendKeys(option);
  }

  getClasseContaSelect(): ElementFinder {
    return this.classeContaSelect;
  }

  async getClasseContaSelectedOption() {
    return await this.classeContaSelect.element(by.css('option:checked')).getText();
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

export class ContaDeleteDialog {
  private dialogTitle = element(by.id('rv-delete-conta-heading'));
  private confirmButton = element(by.id('rv-confirm-delete-conta'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}
