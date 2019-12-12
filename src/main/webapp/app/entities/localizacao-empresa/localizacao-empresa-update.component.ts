import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { ILocalizacaoEmpresa, LocalizacaoEmpresa } from 'app/shared/model/localizacao-empresa.model';
import { LocalizacaoEmpresaService } from './localizacao-empresa.service';
import { IEmpresa } from 'app/shared/model/empresa.model';
import { EmpresaService } from 'app/entities/empresa/empresa.service';

@Component({
  selector: 'rv-localizacao-empresa-update',
  templateUrl: './localizacao-empresa-update.component.html'
})
export class LocalizacaoEmpresaUpdateComponent implements OnInit {
  isSaving: boolean;

  empresas: IEmpresa[];

  editForm = this.fb.group({
    id: [],
    pais: [],
    provincia: [],
    municipio: [],
    bairro: [null, [Validators.required]],
    rua: [null, [Validators.required, Validators.maxLength(200)]],
    quarteirao: [null, [Validators.maxLength(10)]],
    numeroPorta: [null, [Validators.maxLength(10)]],
    caixaPostal: [],
    empresaId: [null, Validators.required]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected localizacaoEmpresaService: LocalizacaoEmpresaService,
    protected empresaService: EmpresaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ localizacaoEmpresa }) => {
      this.updateForm(localizacaoEmpresa);
    });
    this.empresaService
      .query()
      .subscribe((res: HttpResponse<IEmpresa[]>) => (this.empresas = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(localizacaoEmpresa: ILocalizacaoEmpresa) {
    this.editForm.patchValue({
      id: localizacaoEmpresa.id,
      pais: localizacaoEmpresa.pais,
      provincia: localizacaoEmpresa.provincia,
      municipio: localizacaoEmpresa.municipio,
      bairro: localizacaoEmpresa.bairro,
      rua: localizacaoEmpresa.rua,
      quarteirao: localizacaoEmpresa.quarteirao,
      numeroPorta: localizacaoEmpresa.numeroPorta,
      caixaPostal: localizacaoEmpresa.caixaPostal,
      empresaId: localizacaoEmpresa.empresaId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const localizacaoEmpresa = this.createFromForm();
    if (localizacaoEmpresa.id !== undefined) {
      this.subscribeToSaveResponse(this.localizacaoEmpresaService.update(localizacaoEmpresa));
    } else {
      this.subscribeToSaveResponse(this.localizacaoEmpresaService.create(localizacaoEmpresa));
    }
  }

  private createFromForm(): ILocalizacaoEmpresa {
    return {
      ...new LocalizacaoEmpresa(),
      id: this.editForm.get(['id']).value,
      pais: this.editForm.get(['pais']).value,
      provincia: this.editForm.get(['provincia']).value,
      municipio: this.editForm.get(['municipio']).value,
      bairro: this.editForm.get(['bairro']).value,
      rua: this.editForm.get(['rua']).value,
      quarteirao: this.editForm.get(['quarteirao']).value,
      numeroPorta: this.editForm.get(['numeroPorta']).value,
      caixaPostal: this.editForm.get(['caixaPostal']).value,
      empresaId: this.editForm.get(['empresaId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILocalizacaoEmpresa>>) {
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
