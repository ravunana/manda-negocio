import { element, by, ElementFinder } from 'protractor';

export class MoedaComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('rv-moeda div table .btn-danger'));
  title = element.all(by.css('rv-moeda div h2#page-heading span')).first();

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

export class MoedaUpdatePage {
  pageTitle = element(by.id('rv-moeda-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  nomeInput = element(by.id('field_nome'));
  codigoInput = element(by.id('field_codigo'));
  paisInput = element(by.id('field_pais'));
  nacionalInput = element(by.id('field_nacional'));
  iconInput = element(by.id('field_icon'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setNomeInput(nome) {
    await this.nomeInput.sendKeys(nome);
  }

  async getNomeInput() {
    return await this.nomeInput.getAttribute('value');
  }

  async setCodigoInput(codigo) {
    await this.codigoInput.sendKeys(codigo);
  }

  async getCodigoInput() {
    return await this.codigoInput.getAttribute('value');
  }

  async setPaisInput(pais) {
    await this.paisInput.sendKeys(pais);
  }

  async getPaisInput() {
    return await this.paisInput.getAttribute('value');
  }

  getNacionalInput() {
    return this.nacionalInput;
  }
  async setIconInput(icon) {
    await this.iconInput.sendKeys(icon);
  }

  async getIconInput() {
    return await this.iconInput.getAttribute('value');
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

export class MoedaDeleteDialog {
  private dialogTitle = element(by.id('rv-delete-moeda-heading'));
  private confirmButton = element(by.id('rv-confirm-delete-moeda'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}
