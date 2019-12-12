import { browser, ExpectedConditions as ec /* , promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  RelacionamentoPessoaComponentsPage,
  /* RelacionamentoPessoaDeleteDialog,
   */ RelacionamentoPessoaUpdatePage
} from './relacionamento-pessoa.page-object';

const expect = chai.expect;

describe('RelacionamentoPessoa e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let relacionamentoPessoaComponentsPage: RelacionamentoPessoaComponentsPage;
  let relacionamentoPessoaUpdatePage: RelacionamentoPessoaUpdatePage;
  /* let relacionamentoPessoaDeleteDialog: RelacionamentoPessoaDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load RelacionamentoPessoas', async () => {
    await navBarPage.goToEntity('relacionamento-pessoa');
    relacionamentoPessoaComponentsPage = new RelacionamentoPessoaComponentsPage();
    await browser.wait(ec.visibilityOf(relacionamentoPessoaComponentsPage.title), 5000);
    expect(await relacionamentoPessoaComponentsPage.getTitle()).to.eq('mandaApp.relacionamentoPessoa.home.title');
  });

  it('should load create RelacionamentoPessoa page', async () => {
    await relacionamentoPessoaComponentsPage.clickOnCreateButton();
    relacionamentoPessoaUpdatePage = new RelacionamentoPessoaUpdatePage();
    expect(await relacionamentoPessoaUpdatePage.getPageTitle()).to.eq('mandaApp.relacionamentoPessoa.home.createOrEditLabel');
    await relacionamentoPessoaUpdatePage.cancel();
  });

  /*  it('should create and save RelacionamentoPessoas', async () => {
        const nbButtonsBeforeCreate = await relacionamentoPessoaComponentsPage.countDeleteButtons();

        await relacionamentoPessoaComponentsPage.clickOnCreateButton();
        await promise.all([
            relacionamentoPessoaUpdatePage.setGrauParentescoInput('grauParentesco'),
            relacionamentoPessoaUpdatePage.deSelectLastOption(),
            relacionamentoPessoaUpdatePage.paraSelectLastOption(),
        ]);
        expect(await relacionamentoPessoaUpdatePage.getGrauParentescoInput()).to.eq('grauParentesco', 'Expected GrauParentesco value to be equals to grauParentesco');
        await relacionamentoPessoaUpdatePage.save();
        expect(await relacionamentoPessoaUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await relacionamentoPessoaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /*  it('should delete last RelacionamentoPessoa', async () => {
        const nbButtonsBeforeDelete = await relacionamentoPessoaComponentsPage.countDeleteButtons();
        await relacionamentoPessoaComponentsPage.clickOnLastDeleteButton();

        relacionamentoPessoaDeleteDialog = new RelacionamentoPessoaDeleteDialog();
        expect(await relacionamentoPessoaDeleteDialog.getDialogTitle())
            .to.eq('mandaApp.relacionamentoPessoa.delete.question');
        await relacionamentoPessoaDeleteDialog.clickOnConfirmButton();

        expect(await relacionamentoPessoaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
