import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IDocumentoComercial, DocumentoComercial } from 'app/shared/model/documento-comercial.model';
import { DocumentoComercialService } from './documento-comercial.service';

@Component({
  selector: 'rv-documento-comercial-update',
  templateUrl: './documento-comercial-update.component.html'
})
export class DocumentoComercialUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required]],
    serie: [null, [Validators.required]],
    padrao: [null, [Validators.required]],
    movimentaEstoque: [null, [Validators.required]],
    movimentaCaixa: [null, [Validators.required]],
    movimentaContabilidade: [null, [Validators.required]],
    cor: [],
    descricao: [],
    mostraPontoVenda: [],
    entidade: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected documentoComercialService: DocumentoComercialService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ documentoComercial }) => {
      this.updateForm(documentoComercial);
    });
  }

  updateForm(documentoComercial: IDocumentoComercial) {
    this.editForm.patchValue({
      id: documentoComercial.id,
      nome: documentoComercial.nome,
      serie: documentoComercial.serie,
      padrao: documentoComercial.padrao,
      movimentaEstoque: documentoComercial.movimentaEstoque,
      movimentaCaixa: documentoComercial.movimentaCaixa,
      movimentaContabilidade: documentoComercial.movimentaContabilidade,
      cor: documentoComercial.cor,
      descricao: documentoComercial.descricao,
      mostraPontoVenda: documentoComercial.mostraPontoVenda,
      entidade: documentoComercial.entidade
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
    const documentoComercial = this.createFromForm();
    if (documentoComercial.id !== undefined) {
      this.subscribeToSaveResponse(this.documentoComercialService.update(documentoComercial));
    } else {
      this.subscribeToSaveResponse(this.documentoComercialService.create(documentoComercial));
    }
  }

  private createFromForm(): IDocumentoComercial {
    return {
      ...new DocumentoComercial(),
      id: this.editForm.get(['id']).value,
      nome: this.editForm.get(['nome']).value,
      serie: this.editForm.get(['serie']).value,
      padrao: this.editForm.get(['padrao']).value,
      movimentaEstoque: this.editForm.get(['movimentaEstoque']).value,
      movimentaCaixa: this.editForm.get(['movimentaCaixa']).value,
      movimentaContabilidade: this.editForm.get(['movimentaContabilidade']).value,
      cor: this.editForm.get(['cor']).value,
      descricao: this.editForm.get(['descricao']).value,
      mostraPontoVenda: this.editForm.get(['mostraPontoVenda']).value,
      entidade: this.editForm.get(['entidade']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDocumentoComercial>>) {
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
