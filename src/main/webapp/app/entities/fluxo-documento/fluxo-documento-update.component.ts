import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IFluxoDocumento, FluxoDocumento } from 'app/shared/model/fluxo-documento.model';
import { FluxoDocumentoService } from './fluxo-documento.service';

@Component({
  selector: 'rv-fluxo-documento-update',
  templateUrl: './fluxo-documento-update.component.html'
})
export class FluxoDocumentoUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required]],
    aumentaEstoque: [],
    aumentaValorCaixa: [],
    cor: [],
    padrao: [null, [Validators.required]],
    descricao: [],
    entidade: [null, [Validators.required]]
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected fluxoDocumentoService: FluxoDocumentoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ fluxoDocumento }) => {
      this.updateForm(fluxoDocumento);
    });
  }

  updateForm(fluxoDocumento: IFluxoDocumento) {
    this.editForm.patchValue({
      id: fluxoDocumento.id,
      nome: fluxoDocumento.nome,
      aumentaEstoque: fluxoDocumento.aumentaEstoque,
      aumentaValorCaixa: fluxoDocumento.aumentaValorCaixa,
      cor: fluxoDocumento.cor,
      padrao: fluxoDocumento.padrao,
      descricao: fluxoDocumento.descricao,
      entidade: fluxoDocumento.entidade
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
    const fluxoDocumento = this.createFromForm();
    if (fluxoDocumento.id !== undefined) {
      this.subscribeToSaveResponse(this.fluxoDocumentoService.update(fluxoDocumento));
    } else {
      this.subscribeToSaveResponse(this.fluxoDocumentoService.create(fluxoDocumento));
    }
  }

  private createFromForm(): IFluxoDocumento {
    return {
      ...new FluxoDocumento(),
      id: this.editForm.get(['id']).value,
      nome: this.editForm.get(['nome']).value,
      aumentaEstoque: this.editForm.get(['aumentaEstoque']).value,
      aumentaValorCaixa: this.editForm.get(['aumentaValorCaixa']).value,
      cor: this.editForm.get(['cor']).value,
      padrao: this.editForm.get(['padrao']).value,
      descricao: this.editForm.get(['descricao']).value,
      entidade: this.editForm.get(['entidade']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFluxoDocumento>>) {
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
}
