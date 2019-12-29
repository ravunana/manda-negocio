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
import { IContaCredito, ContaCredito } from 'app/shared/model/conta-credito.model';
import { ContaCreditoService } from './conta-credito.service';
import { IConta } from 'app/shared/model/conta.model';
import { ContaService } from 'app/entities/conta/conta.service';
import { IEscrituracaoContabil } from 'app/shared/model/escrituracao-contabil.model';
import { EscrituracaoContabilService } from 'app/entities/escrituracao-contabil/escrituracao-contabil.service';

@Component({
  selector: 'rv-conta-credito-update',
  templateUrl: './conta-credito-update.component.html'
})
export class ContaCreditoUpdateComponent implements OnInit {
  isSaving: boolean;

  contas: IConta[];

  escrituracaocontabils: IEscrituracaoContabil[];

  editForm = this.fb.group({
    id: [],
    valor: [null, [Validators.required, Validators.min(0)]],
    data: [],
    contaCreditarId: [null],
    lancamentoCreditoId: [null]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected contaCreditoService: ContaCreditoService,
    protected contaService: ContaService,
    protected escrituracaoContabilService: EscrituracaoContabilService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
    protected escrituracaoContabil: EscrituracaoContabilService
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ contaCredito }) => {
      this.updateForm(contaCredito);
    });
    this.contaService
      .query()
      .subscribe((res: HttpResponse<IConta[]>) => (this.contas = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.escrituracaoContabilService
      .query()
      .subscribe(
        (res: HttpResponse<IEscrituracaoContabil[]>) => (this.escrituracaocontabils = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(contaCredito: IContaCredito) {
    this.editForm.patchValue({
      id: contaCredito.id,
      valor: contaCredito.valor,
      data: contaCredito.data != null ? contaCredito.data.format(DATE_TIME_FORMAT) : null,
      contaCreditarId: contaCredito.contaCreditarId,
      lancamentoCreditoId: contaCredito.lancamentoCreditoId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const contaCredito = this.createFromForm();
    if (contaCredito.id !== undefined) {
      this.subscribeToSaveResponse(this.contaCreditoService.update(contaCredito));
    } else {
      this.subscribeToSaveResponse(this.contaCreditoService.create(contaCredito));
    }
  }

  private createFromForm(): IContaCredito {
    return {
      ...new ContaCredito(),
      id: this.editForm.get(['id']).value,
      valor: this.editForm.get(['valor']).value,
      data: moment(new Date()),
      // data: this.editForm.get(['data']).value != null ? moment(this.editForm.get(['data']).value, DATE_TIME_FORMAT) : undefined,
      contaCreditarId: this.editForm.get(['contaCreditarId']).value
      // lancamentoCreditoId: this.editForm.get(['lancamentoCreditoId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IContaCredito>>) {
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

  trackEscrituracaoContabilById(index: number, item: IEscrituracaoContabil) {
    return item.id;
  }

  onAddCredito() {
    this.escrituracaoContabil.addCredito(this.createFromForm()).subscribe(data => {
      this.previousState();
    });
  }

  onSelectConta(conta) {
    this.editForm.get('contaCreditarId').patchValue(conta.id, { emitEvent: false });
  }

  searchConta(conta) {
    this.contaService.query({ 'descricao.contains': conta.query }).subscribe(data => {
      this.contas = data.body;
    });
  }
}
