import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { IUnidadeMedida, UnidadeMedida } from 'app/shared/model/unidade-medida.model';
import { UnidadeMedidaService } from './unidade-medida.service';

@Component({
  selector: 'rv-unidade-medida-update',
  templateUrl: './unidade-medida-update.component.html'
})
export class UnidadeMedidaUpdateComponent implements OnInit {
  isSaving: boolean;

  unidademedidas: IUnidadeMedida[];

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required]],
    sigla: [null, [Validators.required]],
    valor: [null, [Validators.required, Validators.min(0)]],
    unidadeConversaoId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected unidadeMedidaService: UnidadeMedidaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ unidadeMedida }) => {
      this.updateForm(unidadeMedida);
    });
    this.unidadeMedidaService
      .query()
      .subscribe(
        (res: HttpResponse<IUnidadeMedida[]>) => (this.unidademedidas = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(unidadeMedida: IUnidadeMedida) {
    this.editForm.patchValue({
      id: unidadeMedida.id,
      nome: unidadeMedida.nome,
      sigla: unidadeMedida.sigla,
      valor: unidadeMedida.valor,
      unidadeConversaoId: unidadeMedida.unidadeConversaoId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const unidadeMedida = this.createFromForm();
    if (unidadeMedida.id !== undefined) {
      this.subscribeToSaveResponse(this.unidadeMedidaService.update(unidadeMedida));
    } else {
      this.subscribeToSaveResponse(this.unidadeMedidaService.create(unidadeMedida));
    }
  }

  private createFromForm(): IUnidadeMedida {
    return {
      ...new UnidadeMedida(),
      id: this.editForm.get(['id']).value,
      nome: this.editForm.get(['nome']).value,
      sigla: this.editForm.get(['sigla']).value,
      valor: this.editForm.get(['valor']).value,
      unidadeConversaoId: this.editForm.get(['unidadeConversaoId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUnidadeMedida>>) {
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

  trackUnidadeMedidaById(index: number, item: IUnidadeMedida) {
    return item.id;
  }
}
