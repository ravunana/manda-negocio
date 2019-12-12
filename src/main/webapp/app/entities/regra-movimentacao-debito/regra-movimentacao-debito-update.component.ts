import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IRegraMovimentacaoDebito, RegraMovimentacaoDebito } from 'app/shared/model/regra-movimentacao-debito.model';
import { RegraMovimentacaoDebitoService } from './regra-movimentacao-debito.service';
import { IConta } from 'app/shared/model/conta.model';
import { ContaService } from 'app/entities/conta/conta.service';

@Component({
  selector: 'rv-regra-movimentacao-debito-update',
  templateUrl: './regra-movimentacao-debito-update.component.html'
})
export class RegraMovimentacaoDebitoUpdateComponent implements OnInit {
  isSaving: boolean;

  contas: IConta[];

  editForm = this.fb.group({
    id: [],
    descricao: [],
    contaId: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected regraMovimentacaoDebitoService: RegraMovimentacaoDebitoService,
    protected contaService: ContaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ regraMovimentacaoDebito }) => {
      this.updateForm(regraMovimentacaoDebito);
    });
    this.contaService.query({ filter: 'regramovimentacaodebito-is-null' }).subscribe(
      (res: HttpResponse<IConta[]>) => {
        if (!this.editForm.get('contaId').value) {
          this.contas = res.body;
        } else {
          this.contaService
            .find(this.editForm.get('contaId').value)
            .subscribe(
              (subRes: HttpResponse<IConta>) => (this.contas = [subRes.body].concat(res.body)),
              (subRes: HttpErrorResponse) => this.onError(subRes.message)
            );
        }
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  updateForm(regraMovimentacaoDebito: IRegraMovimentacaoDebito) {
    this.editForm.patchValue({
      id: regraMovimentacaoDebito.id,
      descricao: regraMovimentacaoDebito.descricao,
      contaId: regraMovimentacaoDebito.contaId
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
    const regraMovimentacaoDebito = this.createFromForm();
    if (regraMovimentacaoDebito.id !== undefined) {
      this.subscribeToSaveResponse(this.regraMovimentacaoDebitoService.update(regraMovimentacaoDebito));
    } else {
      this.subscribeToSaveResponse(this.regraMovimentacaoDebitoService.create(regraMovimentacaoDebito));
    }
  }

  private createFromForm(): IRegraMovimentacaoDebito {
    return {
      ...new RegraMovimentacaoDebito(),
      id: this.editForm.get(['id']).value,
      descricao: this.editForm.get(['descricao']).value,
      contaId: this.editForm.get(['contaId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRegraMovimentacaoDebito>>) {
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

  trackContaById(index: number, item: IConta) {
    return item.id;
  }
}
