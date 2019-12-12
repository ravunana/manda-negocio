import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IEscrituracaoContabil, EscrituracaoContabil } from 'app/shared/model/escrituracao-contabil.model';
import { EscrituracaoContabilService } from './escrituracao-contabil.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { IEmpresa } from 'app/shared/model/empresa.model';
import { EmpresaService } from 'app/entities/empresa/empresa.service';

@Component({
  selector: 'rv-escrituracao-contabil-update',
  templateUrl: './escrituracao-contabil-update.component.html'
})
export class EscrituracaoContabilUpdateComponent implements OnInit {
  isSaving: boolean;

  users: IUser[];

  empresas: IEmpresa[];
  dataDocumentoDp: any;

  editForm = this.fb.group({
    id: [],
    numero: [null, [Validators.required]],
    historico: [null, [Validators.required]],
    valor: [null, [Validators.required, Validators.min(0)]],
    referencia: [null, []],
    entidadeReferencia: [],
    tipo: [null, [Validators.required]],
    dataDocumento: [null, [Validators.required]],
    data: [null, [Validators.required]],
    utilizadorId: [],
    empresaId: [null, Validators.required]
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected escrituracaoContabilService: EscrituracaoContabilService,
    protected userService: UserService,
    protected empresaService: EmpresaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ escrituracaoContabil }) => {
      this.updateForm(escrituracaoContabil);
    });
    this.userService
      .query()
      .subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.empresaService
      .query()
      .subscribe((res: HttpResponse<IEmpresa[]>) => (this.empresas = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(escrituracaoContabil: IEscrituracaoContabil) {
    this.editForm.patchValue({
      id: escrituracaoContabil.id,
      numero: escrituracaoContabil.numero,
      historico: escrituracaoContabil.historico,
      valor: escrituracaoContabil.valor,
      referencia: escrituracaoContabil.referencia,
      entidadeReferencia: escrituracaoContabil.entidadeReferencia,
      tipo: escrituracaoContabil.tipo,
      dataDocumento: escrituracaoContabil.dataDocumento,
      data: escrituracaoContabil.data != null ? escrituracaoContabil.data.format(DATE_TIME_FORMAT) : null,
      utilizadorId: escrituracaoContabil.utilizadorId,
      empresaId: escrituracaoContabil.empresaId
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

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const escrituracaoContabil = this.createFromForm();
    if (escrituracaoContabil.id !== undefined) {
      this.subscribeToSaveResponse(this.escrituracaoContabilService.update(escrituracaoContabil));
    } else {
      this.subscribeToSaveResponse(this.escrituracaoContabilService.create(escrituracaoContabil));
    }
  }

  private createFromForm(): IEscrituracaoContabil {
    return {
      ...new EscrituracaoContabil(),
      id: this.editForm.get(['id']).value,
      numero: this.editForm.get(['numero']).value,
      historico: this.editForm.get(['historico']).value,
      valor: this.editForm.get(['valor']).value,
      referencia: this.editForm.get(['referencia']).value,
      entidadeReferencia: this.editForm.get(['entidadeReferencia']).value,
      tipo: this.editForm.get(['tipo']).value,
      dataDocumento: this.editForm.get(['dataDocumento']).value,
      data: this.editForm.get(['data']).value != null ? moment(this.editForm.get(['data']).value, DATE_TIME_FORMAT) : undefined,
      utilizadorId: this.editForm.get(['utilizadorId']).value,
      empresaId: this.editForm.get(['empresaId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEscrituracaoContabil>>) {
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

  trackEmpresaById(index: number, item: IEmpresa) {
    return item.id;
  }
}
