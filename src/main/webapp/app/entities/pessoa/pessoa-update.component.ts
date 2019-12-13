import { Component, OnInit, ElementRef } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IPessoa, Pessoa } from 'app/shared/model/pessoa.model';
import { PessoaService } from './pessoa.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { LookupItem } from 'app/shared/model/lookup-item.model';
import { IContactoPessoa } from 'app/shared/model/contacto-pessoa.model';
import { IMoradaPessoa } from 'app/shared/model/morada-pessoa.model';
import { LookupItemService } from '../lookup-item/lookup-item.service';
import { MoradaPessoaService } from '../morada-pessoa/morada-pessoa.service';
import { ContactoPessoaService } from '../contacto-pessoa/contacto-pessoa.service';

@Component({
  selector: 'rv-pessoa-update',
  templateUrl: './pessoa-update.component.html'
})
export class PessoaUpdateComponent implements OnInit {
  isSaving: boolean;
  tiposPessoa: LookupItem[];
  contactos: IContactoPessoa[];
  moradas: IMoradaPessoa[];

  users: IUser[];

  editForm = this.fb.group({
    id: [],
    tipoPessoa: [null, [Validators.required]],
    nome: [null, [Validators.required]],
    nif: [null, []],
    imagem: [],
    imagemContentType: [],
    utilizadorId: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected pessoaService: PessoaService,
    protected userService: UserService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    protected lookupItemService: LookupItemService,
    protected moradaService: MoradaPessoaService,
    protected contactoService: ContactoPessoaService,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ pessoa }) => {
      this.updateForm(pessoa);
    });
    this.lookupItemService.query({ 'lookupId.equals': '11801' }).subscribe(data => {
      this.tiposPessoa = data.body;
    });
    this.getContactos();
    this.getMoradas();
    this.userService
      .query()
      .subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(pessoa: IPessoa) {
    this.editForm.patchValue({
      id: pessoa.id,
      tipoPessoa: pessoa.tipoPessoa,
      nome: pessoa.nome,
      nif: pessoa.nif,
      imagem: pessoa.imagem,
      imagemContentType: pessoa.imagemContentType,
      utilizadorId: pessoa.utilizadorId
    });
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

  setFileData(event, field: string, isImage) {
    return new Promise((resolve, reject) => {
      if (event && event.target && event.target.files && event.target.files[0]) {
        const file: File = event.target.files[0];
        if (isImage && !file.type.startsWith('image/')) {
          reject(`File was expected to be an image but was found to be ${file.type}`);
        } else {
          const filedContentType: string = field + 'ContentType';
          this.dataUtils.toBase64(file, base64Data => {
            this.editForm.patchValue({
              [field]: base64Data,
              [filedContentType]: file.type
            });
          });
        }
      } else {
        reject(`Base64 data was not set as file could not be extracted from passed parameter: ${event}`);
      }
    }).then(
      // eslint-disable-next-line no-console
      () => console.log('blob added'), // success
      this.onError
    );
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string) {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null
    });
    if (this.elementRef && idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const pessoa = this.createFromForm();
    if (pessoa.id !== undefined) {
      this.subscribeToSaveResponse(this.pessoaService.update(pessoa));
    } else {
      this.subscribeToSaveResponse(this.pessoaService.create(pessoa));
    }
  }

  private createFromForm(): IPessoa {
    return {
      ...new Pessoa(),
      id: this.editForm.get(['id']).value,
      tipoPessoa: this.editForm.get(['tipoPessoa']).value,
      nome: this.editForm.get(['nome']).value,
      nif: this.editForm.get(['nif']).value,
      imagemContentType: this.editForm.get(['imagemContentType']).value,
      imagem: this.editForm.get(['imagem']).value,
      utilizadorId: 0
      // utilizadorId: this.editForm.get(['utilizadorId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPessoa>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackUserById(index: number, item: IUser) {
    return item.id;
  }

  getContactos() {
    this.contactoService.getContactos().subscribe(contactoResult => {
      this.contactos = contactoResult;
    });
  }

  getMoradas() {
    this.moradaService.getMoradas().subscribe(moradaResult => {
      this.moradas = moradaResult;
    });
  }

  onDeleteContacto(index) {
    this.contactoService.deleteContacto(index).subscribe(() => {
      this.getContactos();
    });
  }

  onDeleteMorada(index) {
    this.moradaService.deleteMorada(index).subscribe(() => {
      this.getMoradas();
    });
  }
}
