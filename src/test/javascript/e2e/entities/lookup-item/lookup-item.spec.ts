import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { LookupItemComponentsPage, LookupItemDeleteDialog, LookupItemUpdatePage } from './lookup-item.page-object';

const expect = chai.expect;

describe('LookupItem e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let lookupItemComponentsPage: LookupItemComponentsPage;
  let lookupItemUpdatePage: LookupItemUpdatePage;
  let lookupItemDeleteDialog: LookupItemDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load LookupItems', async () => {
    await navBarPage.goToEntity('lookup-item');
    lookupItemComponentsPage = new LookupItemComponentsPage();
    await browser.wait(ec.visibilityOf(lookupItemComponentsPage.title), 5000);
    expect(await lookupItemComponentsPage.getTitle()).to.eq('mandaApp.lookupItem.home.title');
  });

  it('should load create LookupItem page', async () => {
    await lookupItemComponentsPage.clickOnCreateButton();
    lookupItemUpdatePage = new LookupItemUpdatePage();
    expect(await lookupItemUpdatePage.getPageTitle()).to.eq('mandaApp.lookupItem.home.createOrEditLabel');
    await lookupItemUpdatePage.cancel();
  });

  it('should create and save LookupItems', async () => {
    const nbButtonsBeforeCreate = await lookupItemComponentsPage.countDeleteButtons();

    await lookupItemComponentsPage.clickOnCreateButton();
    await promise.all([
      lookupItemUpdatePage.setValorInput('valor'),
      lookupItemUpdatePage.setNomeInput('nome'),
      lookupItemUpdatePage.typeSelectLastOption(),
      lookupItemUpdatePage.setOrdemInput('5'),
      lookupItemUpdatePage.lookupSelectLastOption()
    ]);
    expect(await lookupItemUpdatePage.getValorInput()).to.eq('valor', 'Expected Valor value to be equals to valor');
    expect(await lookupItemUpdatePage.getNomeInput()).to.eq('nome', 'Expected Nome value to be equals to nome');
    expect(await lookupItemUpdatePage.getOrdemInput()).to.eq('5', 'Expected ordem value to be equals to 5');
    await lookupItemUpdatePage.save();
    expect(await lookupItemUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await lookupItemComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last LookupItem', async () => {
    const nbButtonsBeforeDelete = await lookupItemComponentsPage.countDeleteButtons();
    await lookupItemComponentsPage.clickOnLastDeleteButton();

    lookupItemDeleteDialog = new LookupItemDeleteDialog();
    expect(await lookupItemDeleteDialog.getDialogTitle()).to.eq('mandaApp.lookupItem.delete.question');
    await lookupItemDeleteDialog.clickOnConfirmButton();

    expect(await lookupItemComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
