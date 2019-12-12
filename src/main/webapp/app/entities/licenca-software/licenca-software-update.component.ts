import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { ILicencaSoftware, LicencaSoftware } from 'app/shared/model/licenca-software.model';
import { LicencaSoftwareService } from './licenca-software.service';
import { ISoftware } from 'app/shared/model/software.model';
import { SoftwareService } from 'app/entities/software/software.service';
import { IEmpresa } from 'app/shared/model/empresa.model';
import { EmpresaService } from 'app/entities/empresa/empresa.service';

@Component({
  selector: 'rv-licenca-software-update',
  templateUrl: './licenca-software-update.component.html'
})
export class LicencaSoftwareUpdateComponent implements OnInit {
  isSaving: boolean;

  softwares: ISoftware[];

  empresas: IEmpresa[];

  editForm = this.fb.group({
    id: [],
    tipoSubscricao: [null, [Validators.required]],
    inicio: [null, [Validators.required]],
    fim: [null, [Validators.required]],
    data: [],
    valor: [null, [Validators.required, Validators.min(0)]],
    codigo: [null, [Validators.required]],
    numeroUsuario: [null, [Validators.min(1)]],
    numeroEmpresa: [null, [Validators.min(1)]],
    softwareId: [],
    empresaId: [null, Validators.required]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected licencaSoftwareService: LicencaSoftwareService,
    protected softwareService: SoftwareService,
    protected empresaService: EmpresaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ licencaSoftware }) => {
      this.updateForm(licencaSoftware);
    });
    this.softwareService
      .query()
      .subscribe((res: HttpResponse<ISoftware[]>) => (this.softwares = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.empresaService
      .query()
      .subscribe((res: HttpResponse<IEmpresa[]>) => (this.empresas = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(licencaSoftware: ILicencaSoftware) {
    this.editForm.patchValue({
      id: licencaSoftware.id,
      tipoSubscricao: licencaSoftware.tipoSubscricao,
      inicio: licencaSoftware.inicio != null ? licencaSoftware.inicio.format(DATE_TIME_FORMAT) : null,
      fim: licencaSoftware.fim != null ? licencaSoftware.fim.format(DATE_TIME_FORMAT) : null,
      data: licencaSoftware.data != null ? licencaSoftware.data.format(DATE_TIME_FORMAT) : null,
      valor: licencaSoftware.valor,
      codigo: licencaSoftware.codigo,
      numeroUsuario: licencaSoftware.numeroUsuario,
      numeroEmpresa: licencaSoftware.numeroEmpresa,
      softwareId: licencaSoftware.softwareId,
      empresaId: licencaSoftware.empresaId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const licencaSoftware = this.createFromForm();
    if (licencaSoftware.id !== undefined) {
      this.subscribeToSaveResponse(this.licencaSoftwareService.update(licencaSoftware));
    } else {
      this.subscribeToSaveResponse(this.licencaSoftwareService.create(licencaSoftware));
    }
  }

  private createFromForm(): ILicencaSoftware {
    return {
      ...new LicencaSoftware(),
      id: this.editForm.get(['id']).value,
      tipoSubscricao: this.editForm.get(['tipoSubscricao']).value,
      inicio: this.editForm.get(['inicio']).value != null ? moment(this.editForm.get(['inicio']).value, DATE_TIME_FORMAT) : undefined,
      fim: this.editForm.get(['fim']).value != null ? moment(this.editForm.get(['fim']).value, DATE_TIME_FORMAT) : undefined,
      data: this.editForm.get(['data']).value != null ? moment(this.editForm.get(['data']).value, DATE_TIME_FORMAT) : undefined,
      valor: this.editForm.get(['valor']).value,
      codigo: this.editForm.get(['codigo']).value,
      numeroUsuario: this.editForm.get(['numeroUsuario']).value,
      numeroEmpresa: this.editForm.get(['numeroEmpresa']).value,
      softwareId: this.editForm.get(['softwareId']).value,
      empresaId: this.editForm.get(['empresaId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILicencaSoftware>>) {
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

  trackSoftwareById(index: number, item: ISoftware) {
    return item.id;
  }

  trackEmpresaById(index: number, item: IEmpresa) {
    return item.id;
  }
}
