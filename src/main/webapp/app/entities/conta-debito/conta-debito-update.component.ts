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
import { IContaDebito, ContaDebito } from 'app/shared/model/conta-debito.model';
import { ContaDebitoService } from './conta-debito.service';
import { IConta } from 'app/shared/model/conta.model';
import { ContaService } from 'app/entities/conta/conta.service';
import { IEscrituracaoContabil } from 'app/shared/model/escrituracao-contabil.model';
import { EscrituracaoContabilService } from 'app/entities/escrituracao-contabil/escrituracao-contabil.service';

@Component({
  selector: 'rv-conta-debito-update',
  templateUrl: './conta-debito-update.component.html'
})
export class ContaDebitoUpdateComponent implements OnInit {
  isSaving: boolean;

  contas: IConta[];

  escrituracaocontabils: IEscrituracaoContabil[];

  editForm = this.fb.group({
    id: [],
    valor: [null, [Validators.required, Validators.min(0)]],
    data: [],
    contaDebitarId: [null, Validators.required],
    lancamentoDebitoId: [null]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected contaDebitoService: ContaDebitoService,
    protected contaService: ContaService,
    protected escrituracaoContabilService: EscrituracaoContabilService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
    protected escrituracaoContabil: EscrituracaoContabilService
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ contaDebito }) => {
      this.updateForm(contaDebito);
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

  updateForm(contaDebito: IContaDebito) {
    this.editForm.patchValue({
      id: contaDebito.id,
      valor: contaDebito.valor,
      data: contaDebito.data != null ? contaDebito.data.format(DATE_TIME_FORMAT) : null,
      contaDebitarId: contaDebito.contaDebitarId,
      lancamentoDebitoId: contaDebito.lancamentoDebitoId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const contaDebito = this.createFromForm();
    if (contaDebito.id !== undefined) {
      this.subscribeToSaveResponse(this.contaDebitoService.update(contaDebito));
    } else {
      this.subscribeToSaveResponse(this.contaDebitoService.create(contaDebito));
    }
  }

  private createFromForm(): IContaDebito {
    return {
      ...new ContaDebito(),
      id: this.editForm.get(['id']).value,
      valor: this.editForm.get(['valor']).value,
      data: moment(new Date()),
      lancamentoDebitoId: 0,
      // data: this.editForm.get(['data']).value != null ? moment(this.editForm.get(['data']).value, DATE_TIME_FORMAT) : undefined,
      contaDebitarId: this.editForm.get(['contaDebitarId']).value
      // lancamentoDebitoId: this.editForm.get(['lancamentoDebitoId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IContaDebito>>) {
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

  onAddDebito() {
    this.escrituracaoContabil.addDebito(this.createFromForm()).subscribe(data => {
      this.previousState();
    });
  }

  onSelectConta(conta) {
    this.editForm.get('contaDebitarId').patchValue(conta.id, { emitEvent: false });
  }

  searchConta(conta) {
    this.contaService.query({ 'descricao.contains': conta.query }).subscribe(data => {
      this.contas = data.body;
    });
  }
}
