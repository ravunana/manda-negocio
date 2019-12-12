import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { IConversaoUnidade, ConversaoUnidade } from 'app/shared/model/conversao-unidade.model';
import { ConversaoUnidadeService } from './conversao-unidade.service';
import { IUnidadeMedida } from 'app/shared/model/unidade-medida.model';
import { UnidadeMedidaService } from 'app/entities/unidade-medida/unidade-medida.service';
import { IProduto } from 'app/shared/model/produto.model';
import { ProdutoService } from 'app/entities/produto/produto.service';

@Component({
  selector: 'rv-conversao-unidade-update',
  templateUrl: './conversao-unidade-update.component.html'
})
export class ConversaoUnidadeUpdateComponent implements OnInit {
  isSaving: boolean;

  unidademedidas: IUnidadeMedida[];

  produtos: IProduto[];

  editForm = this.fb.group({
    id: [],
    valorEntrada: [null, [Validators.required, Validators.min(1)]],
    valorSaida: [null, [Validators.required, Validators.min(1)]],
    entradaId: [null, Validators.required],
    saidaId: [null, Validators.required],
    produtoId: [null, Validators.required]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected conversaoUnidadeService: ConversaoUnidadeService,
    protected unidadeMedidaService: UnidadeMedidaService,
    protected produtoService: ProdutoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ conversaoUnidade }) => {
      this.updateForm(conversaoUnidade);
    });
    this.unidadeMedidaService
      .query()
      .subscribe(
        (res: HttpResponse<IUnidadeMedida[]>) => (this.unidademedidas = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.produtoService
      .query()
      .subscribe((res: HttpResponse<IProduto[]>) => (this.produtos = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(conversaoUnidade: IConversaoUnidade) {
    this.editForm.patchValue({
      id: conversaoUnidade.id,
      valorEntrada: conversaoUnidade.valorEntrada,
      valorSaida: conversaoUnidade.valorSaida,
      entradaId: conversaoUnidade.entradaId,
      saidaId: conversaoUnidade.saidaId,
      produtoId: conversaoUnidade.produtoId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const conversaoUnidade = this.createFromForm();
    if (conversaoUnidade.id !== undefined) {
      this.subscribeToSaveResponse(this.conversaoUnidadeService.update(conversaoUnidade));
    } else {
      this.subscribeToSaveResponse(this.conversaoUnidadeService.create(conversaoUnidade));
    }
  }

  private createFromForm(): IConversaoUnidade {
    return {
      ...new ConversaoUnidade(),
      id: this.editForm.get(['id']).value,
      valorEntrada: this.editForm.get(['valorEntrada']).value,
      valorSaida: this.editForm.get(['valorSaida']).value,
      entradaId: this.editForm.get(['entradaId']).value,
      saidaId: this.editForm.get(['saidaId']).value,
      produtoId: this.editForm.get(['produtoId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IConversaoUnidade>>) {
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

  trackProdutoById(index: number, item: IProduto) {
    return item.id;
  }
}
