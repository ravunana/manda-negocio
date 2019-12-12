import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { ICompostoProduto, CompostoProduto } from 'app/shared/model/composto-produto.model';
import { CompostoProdutoService } from './composto-produto.service';
import { IProduto } from 'app/shared/model/produto.model';
import { ProdutoService } from 'app/entities/produto/produto.service';
import { IUnidadeMedida } from 'app/shared/model/unidade-medida.model';
import { UnidadeMedidaService } from 'app/entities/unidade-medida/unidade-medida.service';

@Component({
  selector: 'rv-composto-produto-update',
  templateUrl: './composto-produto-update.component.html'
})
export class CompostoProdutoUpdateComponent implements OnInit {
  isSaving: boolean;

  produtos: IProduto[];

  unidademedidas: IUnidadeMedida[];

  editForm = this.fb.group({
    id: [],
    quantidade: [null, [Validators.required, Validators.min(1)]],
    valor: [],
    permanente: [],
    produtoId: [null, Validators.required],
    unidadeId: [null, Validators.required],
    compostoId: [null, Validators.required]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected compostoProdutoService: CompostoProdutoService,
    protected produtoService: ProdutoService,
    protected unidadeMedidaService: UnidadeMedidaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ compostoProduto }) => {
      this.updateForm(compostoProduto);
    });
    this.produtoService
      .query()
      .subscribe((res: HttpResponse<IProduto[]>) => (this.produtos = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.unidadeMedidaService
      .query()
      .subscribe(
        (res: HttpResponse<IUnidadeMedida[]>) => (this.unidademedidas = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(compostoProduto: ICompostoProduto) {
    this.editForm.patchValue({
      id: compostoProduto.id,
      quantidade: compostoProduto.quantidade,
      valor: compostoProduto.valor,
      permanente: compostoProduto.permanente,
      produtoId: compostoProduto.produtoId,
      unidadeId: compostoProduto.unidadeId,
      compostoId: compostoProduto.compostoId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const compostoProduto = this.createFromForm();
    if (compostoProduto.id !== undefined) {
      this.subscribeToSaveResponse(this.compostoProdutoService.update(compostoProduto));
    } else {
      this.subscribeToSaveResponse(this.compostoProdutoService.create(compostoProduto));
    }
  }

  private createFromForm(): ICompostoProduto {
    return {
      ...new CompostoProduto(),
      id: this.editForm.get(['id']).value,
      quantidade: this.editForm.get(['quantidade']).value,
      valor: this.editForm.get(['valor']).value,
      permanente: this.editForm.get(['permanente']).value,
      produtoId: this.editForm.get(['produtoId']).value,
      unidadeId: this.editForm.get(['unidadeId']).value,
      compostoId: this.editForm.get(['compostoId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICompostoProduto>>) {
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

  trackProdutoById(index: number, item: IProduto) {
    return item.id;
  }

  trackUnidadeMedidaById(index: number, item: IUnidadeMedida) {
    return item.id;
  }
}
