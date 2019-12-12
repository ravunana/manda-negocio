import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IImposto, Imposto } from 'app/shared/model/imposto.model';
import { ImpostoService } from './imposto.service';
import { IConta } from 'app/shared/model/conta.model';
import { ContaService } from 'app/entities/conta/conta.service';
import { ILancamentoFinanceiro } from 'app/shared/model/lancamento-financeiro.model';
import { LancamentoFinanceiroService } from 'app/entities/lancamento-financeiro/lancamento-financeiro.service';
import { IProduto } from 'app/shared/model/produto.model';
import { ProdutoService } from 'app/entities/produto/produto.service';

@Component({
  selector: 'rv-imposto-update',
  templateUrl: './imposto-update.component.html'
})
export class ImpostoUpdateComponent implements OnInit {
  isSaving: boolean;

  contas: IConta[];

  lancamentofinanceiros: ILancamentoFinanceiro[];

  produtos: IProduto[];
  vigenciaDp: any;

  editForm = this.fb.group({
    id: [],
    tipoImposto: [null, [Validators.required]],
    codigoImposto: [null, [Validators.required]],
    porcentagem: [null, [Validators.required]],
    valor: [null, [Validators.required, Validators.min(0)]],
    descricao: [],
    pais: [],
    vigencia: [],
    contaId: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected impostoService: ImpostoService,
    protected contaService: ContaService,
    protected lancamentoFinanceiroService: LancamentoFinanceiroService,
    protected produtoService: ProdutoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ imposto }) => {
      this.updateForm(imposto);
    });
    this.contaService
      .query()
      .subscribe((res: HttpResponse<IConta[]>) => (this.contas = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.lancamentoFinanceiroService
      .query()
      .subscribe(
        (res: HttpResponse<ILancamentoFinanceiro[]>) => (this.lancamentofinanceiros = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.produtoService
      .query()
      .subscribe((res: HttpResponse<IProduto[]>) => (this.produtos = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(imposto: IImposto) {
    this.editForm.patchValue({
      id: imposto.id,
      tipoImposto: imposto.tipoImposto,
      codigoImposto: imposto.codigoImposto,
      porcentagem: imposto.porcentagem,
      valor: imposto.valor,
      descricao: imposto.descricao,
      pais: imposto.pais,
      vigencia: imposto.vigencia,
      contaId: imposto.contaId
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
    const imposto = this.createFromForm();
    if (imposto.id !== undefined) {
      this.subscribeToSaveResponse(this.impostoService.update(imposto));
    } else {
      this.subscribeToSaveResponse(this.impostoService.create(imposto));
    }
  }

  private createFromForm(): IImposto {
    return {
      ...new Imposto(),
      id: this.editForm.get(['id']).value,
      tipoImposto: this.editForm.get(['tipoImposto']).value,
      codigoImposto: this.editForm.get(['codigoImposto']).value,
      porcentagem: this.editForm.get(['porcentagem']).value,
      valor: this.editForm.get(['valor']).value,
      descricao: this.editForm.get(['descricao']).value,
      pais: this.editForm.get(['pais']).value,
      vigencia: this.editForm.get(['vigencia']).value,
      contaId: this.editForm.get(['contaId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IImposto>>) {
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

  trackLancamentoFinanceiroById(index: number, item: ILancamentoFinanceiro) {
    return item.id;
  }

  trackProdutoById(index: number, item: IProduto) {
    return item.id;
  }

  getSelected(selectedVals: any[], option: any) {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
