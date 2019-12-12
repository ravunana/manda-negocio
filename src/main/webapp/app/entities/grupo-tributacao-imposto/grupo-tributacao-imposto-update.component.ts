import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { IGrupoTributacaoImposto, GrupoTributacaoImposto } from 'app/shared/model/grupo-tributacao-imposto.model';
import { GrupoTributacaoImpostoService } from './grupo-tributacao-imposto.service';
import { IImposto } from 'app/shared/model/imposto.model';
import { ImpostoService } from 'app/entities/imposto/imposto.service';
import { IUnidadeMedida } from 'app/shared/model/unidade-medida.model';
import { UnidadeMedidaService } from 'app/entities/unidade-medida/unidade-medida.service';

@Component({
  selector: 'rv-grupo-tributacao-imposto-update',
  templateUrl: './grupo-tributacao-imposto-update.component.html'
})
export class GrupoTributacaoImpostoUpdateComponent implements OnInit {
  isSaving: boolean;

  impostos: IImposto[];

  unidademedidas: IUnidadeMedida[];

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required]],
    valor: [null, [Validators.required, Validators.min(0)]],
    de: [],
    ate: [],
    limiteLiquidacao: [null, [Validators.min(1), Validators.max(31)]],
    impostoId: [null, Validators.required],
    unidadeLimiteId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected grupoTributacaoImpostoService: GrupoTributacaoImpostoService,
    protected impostoService: ImpostoService,
    protected unidadeMedidaService: UnidadeMedidaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ grupoTributacaoImposto }) => {
      this.updateForm(grupoTributacaoImposto);
    });
    this.impostoService
      .query()
      .subscribe((res: HttpResponse<IImposto[]>) => (this.impostos = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.unidadeMedidaService
      .query()
      .subscribe(
        (res: HttpResponse<IUnidadeMedida[]>) => (this.unidademedidas = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(grupoTributacaoImposto: IGrupoTributacaoImposto) {
    this.editForm.patchValue({
      id: grupoTributacaoImposto.id,
      nome: grupoTributacaoImposto.nome,
      valor: grupoTributacaoImposto.valor,
      de: grupoTributacaoImposto.de,
      ate: grupoTributacaoImposto.ate,
      limiteLiquidacao: grupoTributacaoImposto.limiteLiquidacao,
      impostoId: grupoTributacaoImposto.impostoId,
      unidadeLimiteId: grupoTributacaoImposto.unidadeLimiteId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const grupoTributacaoImposto = this.createFromForm();
    if (grupoTributacaoImposto.id !== undefined) {
      this.subscribeToSaveResponse(this.grupoTributacaoImpostoService.update(grupoTributacaoImposto));
    } else {
      this.subscribeToSaveResponse(this.grupoTributacaoImpostoService.create(grupoTributacaoImposto));
    }
  }

  private createFromForm(): IGrupoTributacaoImposto {
    return {
      ...new GrupoTributacaoImposto(),
      id: this.editForm.get(['id']).value,
      nome: this.editForm.get(['nome']).value,
      valor: this.editForm.get(['valor']).value,
      de: this.editForm.get(['de']).value,
      ate: this.editForm.get(['ate']).value,
      limiteLiquidacao: this.editForm.get(['limiteLiquidacao']).value,
      impostoId: this.editForm.get(['impostoId']).value,
      unidadeLimiteId: this.editForm.get(['unidadeLimiteId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGrupoTributacaoImposto>>) {
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

  trackImpostoById(index: number, item: IImposto) {
    return item.id;
  }

  trackUnidadeMedidaById(index: number, item: IUnidadeMedida) {
    return item.id;
  }
}
