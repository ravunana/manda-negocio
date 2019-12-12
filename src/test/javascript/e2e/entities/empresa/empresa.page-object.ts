import { element, by, ElementFinder } from 'protractor';

export class EmpresaComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('rv-empresa div table .btn-danger'));
  title = element.all(by.css('rv-empresa div h2#page-heading span')).first();

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

export class EmpresaUpdatePage {
  pageTitle = element(by.id('rv-empresa-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  tipoSociedadeInput = element(by.id('field_tipoSociedade'));
  nomeInput = element(by.id('field_nome'));
  logotipoInput = element(by.id('file_logotipo'));
  capitalSocialInput = element(by.id('field_capitalSocial'));
  fundacaoInput = element(by.id('field_fundacao'));
  nifInput = element(by.id('field_nif'));
  numeroRegistroComercialInput = element(by.id('field_numeroRegistroComercial'));
  despesaFixaInput = element(by.id('field_despesaFixa'));
  despesaVariavelInput = element(by.id('field_despesaVariavel'));
  aberturaExercioInput = element(by.id('field_aberturaExercio'));
  designacaoComercialInput = element(by.id('field_designacaoComercial'));
  sedeInput = element(by.id('field_sede'));
  utilizadorSelect = element(by.id('field_utilizador'));
  contaSelect = element(by.id('field_conta'));
  hierarquiaSelect = element(by.id('field_hierarquia'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setTipoSociedadeInput(tipoSociedade) {
    await this.tipoSociedadeInput.sendKeys(tipoSociedade);
  }

  async getTipoSociedadeInput() {
    return await this.tipoSociedadeInput.getAttribute('value');
  }

  async setNomeInput(nome) {
    await this.nomeInput.sendKeys(nome);
  }

  async getNomeInput() {
    return await this.nomeInput.getAttribute('value');
  }

  async setLogotipoInput(logotipo) {
    await this.logotipoInput.sendKeys(logotipo);
  }

  async getLogotipoInput() {
    return await this.logotipoInput.getAttribute('value');
  }

  async setCapitalSocialInput(capitalSocial) {
    await this.capitalSocialInput.sendKeys(capitalSocial);
  }

  async getCapitalSocialInput() {
    return await this.capitalSocialInput.getAttribute('value');
  }

  async setFundacaoInput(fundacao) {
    await this.fundacaoInput.sendKeys(fundacao);
  }

  async getFundacaoInput() {
    return await this.fundacaoInput.getAttribute('value');
  }

  async setNifInput(nif) {
    await this.nifInput.sendKeys(nif);
  }

  async getNifInput() {
    return await this.nifInput.getAttribute('value');
  }

  async setNumeroRegistroComercialInput(numeroRegistroComercial) {
    await this.numeroRegistroComercialInput.sendKeys(numeroRegistroComercial);
  }

  async getNumeroRegistroComercialInput() {
    return await this.numeroRegistroComercialInput.getAttribute('value');
  }

  async setDespesaFixaInput(despesaFixa) {
    await this.despesaFixaInput.sendKeys(despesaFixa);
  }

  async getDespesaFixaInput() {
    return await this.despesaFixaInput.getAttribute('value');
  }

  async setDespesaVariavelInput(despesaVariavel) {
    await this.despesaVariavelInput.sendKeys(despesaVariavel);
  }

  async getDespesaVariavelInput() {
    return await this.despesaVariavelInput.getAttribute('value');
  }

  async setAberturaExercioInput(aberturaExercio) {
    await this.aberturaExercioInput.sendKeys(aberturaExercio);
  }

  async getAberturaExercioInput() {
    return await this.aberturaExercioInput.getAttribute('value');
  }

  async setDesignacaoComercialInput(designacaoComercial) {
    await this.designacaoComercialInput.sendKeys(designacaoComercial);
  }

  async getDesignacaoComercialInput() {
    return await this.designacaoComercialInput.getAttribute('value');
  }

  getSedeInput() {
    return this.sedeInput;
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

export class EmpresaDeleteDialog {
  private dialogTitle = element(by.id('rv-delete-empresa-heading'));
  private confirmButton = element(by.id('rv-confirm-delete-empresa'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}
