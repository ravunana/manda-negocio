import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { IRetencaoFonte, RetencaoFonte } from 'app/shared/model/retencao-fonte.model';
import { RetencaoFonteService } from './retencao-fonte.service';
import { IDetalheLancamento } from 'app/shared/model/detalhe-lancamento.model';
import { DetalheLancamentoService } from 'app/entities/detalhe-lancamento/detalhe-lancamento.service';
import { IImposto } from 'app/shared/model/imposto.model';
import { ImpostoService } from 'app/entities/imposto/imposto.service';

@Component({
  selector: 'rv-retencao-fonte-update',
  templateUrl: './retencao-fonte-update.component.html'
})
export class RetencaoFonteUpdateComponent implements OnInit {
  isSaving: boolean;

  detalhelancamentos: IDetalheLancamento[];

  impostos: IImposto[];

  editForm = this.fb.group({
    id: [],
    motivoRetencao: [],
    valor: [],
    porcentagem: [],
    detalheId: [],
    impostoId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected retencaoFonteService: RetencaoFonteService,
    protected detalheLancamentoService: DetalheLancamentoService,
    protected impostoService: ImpostoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ retencaoFonte }) => {
      this.updateForm(retencaoFonte);
    });
    this.detalheLancamentoService
      .query()
      .subscribe(
        (res: HttpResponse<IDetalheLancamento[]>) => (this.detalhelancamentos = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.impostoService
      .query()
      .subscribe((res: HttpResponse<IImposto[]>) => (this.impostos = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(retencaoFonte: IRetencaoFonte) {
    this.editForm.patchValue({
      id: retencaoFonte.id,
      motivoRetencao: retencaoFonte.motivoRetencao,
      valor: retencaoFonte.valor,
      porcentagem: retencaoFonte.porcentagem,
      detalheId: retencaoFonte.detalheId,
      impostoId: retencaoFonte.impostoId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const retencaoFonte = this.createFromForm();
    if (retencaoFonte.id !== undefined) {
      this.subscribeToSaveResponse(this.retencaoFonteService.update(retencaoFonte));
    } else {
      this.subscribeToSaveResponse(this.retencaoFonteService.create(retencaoFonte));
    }
  }

  private createFromForm(): IRetencaoFonte {
    return {
      ...new RetencaoFonte(),
      id: this.editForm.get(['id']).value,
      motivoRetencao: this.editForm.get(['motivoRetencao']).value,
      valor: this.editForm.get(['valor']).value,
      porcentagem: this.editForm.get(['porcentagem']).value,
      detalheId: this.editForm.get(['detalheId']).value,
      impostoId: this.editForm.get(['impostoId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRetencaoFonte>>) {
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

  trackDetalheLancamentoById(index: number, item: IDetalheLancamento) {
    return item.id;
  }

  trackImpostoById(index: number, item: IImposto) {
    return item.id;
  }
}
