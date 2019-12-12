import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { ILocalArmazenamento, LocalArmazenamento } from 'app/shared/model/local-armazenamento.model';
import { LocalArmazenamentoService } from './local-armazenamento.service';
import { IEmpresa } from 'app/shared/model/empresa.model';
import { EmpresaService } from 'app/entities/empresa/empresa.service';

@Component({
  selector: 'rv-local-armazenamento-update',
  templateUrl: './local-armazenamento-update.component.html'
})
export class LocalArmazenamentoUpdateComponent implements OnInit {
  isSaving: boolean;

  localarmazenamentos: ILocalArmazenamento[];

  empresas: IEmpresa[];

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required]],
    descricao: [],
    hierarquiaId: [],
    empresaId: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected localArmazenamentoService: LocalArmazenamentoService,
    protected empresaService: EmpresaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ localArmazenamento }) => {
      this.updateForm(localArmazenamento);
    });
    this.localArmazenamentoService
      .query()
      .subscribe(
        (res: HttpResponse<ILocalArmazenamento[]>) => (this.localarmazenamentos = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.empresaService
      .query()
      .subscribe((res: HttpResponse<IEmpresa[]>) => (this.empresas = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(localArmazenamento: ILocalArmazenamento) {
    this.editForm.patchValue({
      id: localArmazenamento.id,
      nome: localArmazenamento.nome,
      descricao: localArmazenamento.descricao,
      hierarquiaId: localArmazenamento.hierarquiaId,
      empresaId: localArmazenamento.empresaId
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
    const localArmazenamento = this.createFromForm();
    if (localArmazenamento.id !== undefined) {
      this.subscribeToSaveResponse(this.localArmazenamentoService.update(localArmazenamento));
    } else {
      this.subscribeToSaveResponse(this.localArmazenamentoService.create(localArmazenamento));
    }
  }

  private createFromForm(): ILocalArmazenamento {
    return {
      ...new LocalArmazenamento(),
      id: this.editForm.get(['id']).value,
      nome: this.editForm.get(['nome']).value,
      descricao: this.editForm.get(['descricao']).value,
      hierarquiaId: this.editForm.get(['hierarquiaId']).value,
      empresaId: this.editForm.get(['empresaId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILocalArmazenamento>>) {
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

  trackLocalArmazenamentoById(index: number, item: ILocalArmazenamento) {
    return item.id;
  }

  trackEmpresaById(index: number, item: IEmpresa) {
    return item.id;
  }
}
