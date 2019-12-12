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
import { IItemVenda, ItemVenda } from 'app/shared/model/item-venda.model';
import { ItemVendaService } from './item-venda.service';
import { IVenda } from 'app/shared/model/venda.model';
import { VendaService } from 'app/entities/venda/venda.service';
import { IProduto } from 'app/shared/model/produto.model';
import { ProdutoService } from 'app/entities/produto/produto.service';
import { IFluxoDocumento } from 'app/shared/model/fluxo-documento.model';
import { FluxoDocumentoService } from 'app/entities/fluxo-documento/fluxo-documento.service';

@Component({
  selector: 'rv-item-venda-update',
  templateUrl: './item-venda-update.component.html'
})
export class ItemVendaUpdateComponent implements OnInit {
  isSaving: boolean;

  vendas: IVenda[];

  produtos: IProduto[];

  fluxodocumentos: IFluxoDocumento[];

  editForm = this.fb.group({
    id: [],
    quantidade: [null, [Validators.min(1)]],
    valor: [null, [Validators.min(0)]],
    desconto: [null, [Validators.min(0), Validators.max(100)]],
    data: [],
    vendaId: [null, Validators.required],
    produtoId: [],
    statusId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected itemVendaService: ItemVendaService,
    protected vendaService: VendaService,
    protected produtoService: ProdutoService,
    protected fluxoDocumentoService: FluxoDocumentoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ itemVenda }) => {
      this.updateForm(itemVenda);
    });
    this.vendaService
      .query()
      .subscribe((res: HttpResponse<IVenda[]>) => (this.vendas = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.produtoService
      .query()
      .subscribe((res: HttpResponse<IProduto[]>) => (this.produtos = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.fluxoDocumentoService
      .query()
      .subscribe(
        (res: HttpResponse<IFluxoDocumento[]>) => (this.fluxodocumentos = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(itemVenda: IItemVenda) {
    this.editForm.patchValue({
      id: itemVenda.id,
      quantidade: itemVenda.quantidade,
      valor: itemVenda.valor,
      desconto: itemVenda.desconto,
      data: itemVenda.data != null ? itemVenda.data.format(DATE_TIME_FORMAT) : null,
      vendaId: itemVenda.vendaId,
      produtoId: itemVenda.produtoId,
      statusId: itemVenda.statusId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const itemVenda = this.createFromForm();
    if (itemVenda.id !== undefined) {
      this.subscribeToSaveResponse(this.itemVendaService.update(itemVenda));
    } else {
      this.subscribeToSaveResponse(this.itemVendaService.create(itemVenda));
    }
  }

  private createFromForm(): IItemVenda {
    return {
      ...new ItemVenda(),
      id: this.editForm.get(['id']).value,
      quantidade: this.editForm.get(['quantidade']).value,
      valor: this.editForm.get(['valor']).value,
      desconto: this.editForm.get(['desconto']).value,
      data: this.editForm.get(['data']).value != null ? moment(this.editForm.get(['data']).value, DATE_TIME_FORMAT) : undefined,
      vendaId: this.editForm.get(['vendaId']).value,
      produtoId: this.editForm.get(['produtoId']).value,
      statusId: this.editForm.get(['statusId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IItemVenda>>) {
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

  trackVendaById(index: number, item: IVenda) {
    return item.id;
  }

  trackProdutoById(index: number, item: IProduto) {
    return item.id;
  }

  trackFluxoDocumentoById(index: number, item: IFluxoDocumento) {
    return item.id;
  }
}
