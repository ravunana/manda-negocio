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
import { IEstruturaCalculo, EstruturaCalculo } from 'app/shared/model/estrutura-calculo.model';
import { EstruturaCalculoService } from './estrutura-calculo.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { IProduto } from 'app/shared/model/produto.model';
import { ProdutoService } from 'app/entities/produto/produto.service';

@Component({
  selector: 'rv-estrutura-calculo-update',
  templateUrl: './estrutura-calculo-update.component.html'
})
export class EstruturaCalculoUpdateComponent implements OnInit {
  isSaving: boolean;
  produtoId: 0;

  users: IUser[];

  produtos: IProduto[];

  editForm = this.fb.group({
    id: [],
    custoMateriaPrima: [],
    custoMaoObra: [],
    custoEmbalagem: [],
    custoAquisicao: [],
    comissao: [null, [Validators.min(0), Validators.max(100)]],
    margemLucro: [null, [Validators.min(0), Validators.max(100)]],
    precoVenda: [],
    data: [],
    utilizadorId: [null]
    // produtoId: [null, Validators.required]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected estruturaCalculoService: EstruturaCalculoService,
    protected userService: UserService,
    protected produtoService: ProdutoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ estruturaCalculo }) => {
      this.updateForm(estruturaCalculo);
      this.produtoId = estruturaCalculo.produtoId;
    });
    this.activatedRoute.queryParams.subscribe(parmas => {
      this.produtoId = parmas.produtoId;
    });
    this.userService
      .query()
      .subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.produtoService
      .query()
      .subscribe((res: HttpResponse<IProduto[]>) => (this.produtos = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(estruturaCalculo: IEstruturaCalculo) {
    this.editForm.patchValue({
      id: estruturaCalculo.id,
      custoMateriaPrima: estruturaCalculo.custoMateriaPrima,
      custoMaoObra: estruturaCalculo.custoMaoObra,
      custoEmbalagem: estruturaCalculo.custoEmbalagem,
      custoAquisicao: estruturaCalculo.custoAquisicao,
      comissao: estruturaCalculo.comissao,
      margemLucro: estruturaCalculo.margemLucro,
      precoVenda: estruturaCalculo.precoVenda,
      data: estruturaCalculo.data != null ? estruturaCalculo.data.format(DATE_TIME_FORMAT) : null,
      utilizadorId: estruturaCalculo.utilizadorId,
      produtoId: estruturaCalculo.produtoId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const estruturaCalculo = this.createFromForm();
    if (estruturaCalculo.id !== undefined) {
      this.subscribeToSaveResponse(this.estruturaCalculoService.update(estruturaCalculo));
    } else {
      this.subscribeToSaveResponse(this.estruturaCalculoService.create(estruturaCalculo));
    }
  }

  private createFromForm(): IEstruturaCalculo {
    return {
      ...new EstruturaCalculo(),
      id: this.editForm.get(['id']).value,
      custoMateriaPrima: this.editForm.get(['custoMateriaPrima']).value,
      custoMaoObra: this.editForm.get(['custoMaoObra']).value,
      custoEmbalagem: this.editForm.get(['custoEmbalagem']).value,
      custoAquisicao: this.editForm.get(['custoAquisicao']).value,
      comissao: this.editForm.get(['comissao']).value,
      margemLucro: this.editForm.get(['margemLucro']).value,
      precoVenda: this.editForm.get(['precoVenda']).value,
      data: this.editForm.get(['data']).value != null ? moment(this.editForm.get(['data']).value, DATE_TIME_FORMAT) : undefined,
      utilizadorId: 1,
      // utilizadorId: this.editForm.get(['utilizadorId']).value,
      // produtoId: this.editForm.get(['produtoId']).value
      produtoId: this.produtoId
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEstruturaCalculo>>) {
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

  trackUserById(index: number, item: IUser) {
    return item.id;
  }

  trackProdutoById(index: number, item: IProduto) {
    return item.id;
  }

  searchProdutos($event) {
    this.produtoService
      .query({ 'nome.contains': $event.query })
      .subscribe((res: HttpResponse<IProduto[]>) => (this.produtos = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  onSelectProduto($event) {
    this.editForm.get('produtoId').patchValue($event.id, { emitEvent: false });
  }
}
