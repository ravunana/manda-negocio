import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { IContactoEmpresa, ContactoEmpresa } from 'app/shared/model/contacto-empresa.model';
import { ContactoEmpresaService } from './contacto-empresa.service';
import { IEmpresa } from 'app/shared/model/empresa.model';
import { EmpresaService } from 'app/entities/empresa/empresa.service';

@Component({
  selector: 'rv-contacto-empresa-update',
  templateUrl: './contacto-empresa-update.component.html'
})
export class ContactoEmpresaUpdateComponent implements OnInit {
  isSaving: boolean;

  empresas: IEmpresa[];

  editForm = this.fb.group({
    id: [],
    tipoContacto: [null, [Validators.required]],
    descricao: [],
    contacto: [null, [Validators.required]],
    padrao: [],
    empresaId: [null, Validators.required]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected contactoEmpresaService: ContactoEmpresaService,
    protected empresaService: EmpresaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ contactoEmpresa }) => {
      this.updateForm(contactoEmpresa);
    });
    this.empresaService
      .query()
      .subscribe((res: HttpResponse<IEmpresa[]>) => (this.empresas = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(contactoEmpresa: IContactoEmpresa) {
    this.editForm.patchValue({
      id: contactoEmpresa.id,
      tipoContacto: contactoEmpresa.tipoContacto,
      descricao: contactoEmpresa.descricao,
      contacto: contactoEmpresa.contacto,
      padrao: contactoEmpresa.padrao,
      empresaId: contactoEmpresa.empresaId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const contactoEmpresa = this.createFromForm();
    if (contactoEmpresa.id !== undefined) {
      this.subscribeToSaveResponse(this.contactoEmpresaService.update(contactoEmpresa));
    } else {
      this.subscribeToSaveResponse(this.contactoEmpresaService.create(contactoEmpresa));
    }
  }

  private createFromForm(): IContactoEmpresa {
    return {
      ...new ContactoEmpresa(),
      id: this.editForm.get(['id']).value,
      tipoContacto: this.editForm.get(['tipoContacto']).value,
      descricao: this.editForm.get(['descricao']).value,
      contacto: this.editForm.get(['contacto']).value,
      padrao: this.editForm.get(['padrao']).value,
      empresaId: this.editForm.get(['empresaId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IContactoEmpresa>>) {
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

  trackEmpresaById(index: number, item: IEmpresa) {
    return item.id;
  }
}
