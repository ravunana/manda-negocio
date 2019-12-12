import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { IContactoPessoa, ContactoPessoa } from 'app/shared/model/contacto-pessoa.model';
import { ContactoPessoaService } from './contacto-pessoa.service';
import { IPessoa } from 'app/shared/model/pessoa.model';
import { PessoaService } from 'app/entities/pessoa/pessoa.service';

@Component({
  selector: 'rv-contacto-pessoa-update',
  templateUrl: './contacto-pessoa-update.component.html'
})
export class ContactoPessoaUpdateComponent implements OnInit {
  isSaving: boolean;

  pessoas: IPessoa[];

  editForm = this.fb.group({
    id: [],
    tipoContacto: [null, [Validators.required]],
    descricao: [],
    contacto: [null, [Validators.required]],
    pessoaId: [null, Validators.required]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected contactoPessoaService: ContactoPessoaService,
    protected pessoaService: PessoaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ contactoPessoa }) => {
      this.updateForm(contactoPessoa);
    });
    this.pessoaService
      .query()
      .subscribe((res: HttpResponse<IPessoa[]>) => (this.pessoas = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(contactoPessoa: IContactoPessoa) {
    this.editForm.patchValue({
      id: contactoPessoa.id,
      tipoContacto: contactoPessoa.tipoContacto,
      descricao: contactoPessoa.descricao,
      contacto: contactoPessoa.contacto,
      pessoaId: contactoPessoa.pessoaId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const contactoPessoa = this.createFromForm();
    if (contactoPessoa.id !== undefined) {
      this.subscribeToSaveResponse(this.contactoPessoaService.update(contactoPessoa));
    } else {
      this.subscribeToSaveResponse(this.contactoPessoaService.create(contactoPessoa));
    }
  }

  private createFromForm(): IContactoPessoa {
    return {
      ...new ContactoPessoa(),
      id: this.editForm.get(['id']).value,
      tipoContacto: this.editForm.get(['tipoContacto']).value,
      descricao: this.editForm.get(['descricao']).value,
      contacto: this.editForm.get(['contacto']).value,
      pessoaId: this.editForm.get(['pessoaId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IContactoPessoa>>) {
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

  trackPessoaById(index: number, item: IPessoa) {
    return item.id;
  }
}
