import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { IEstoqueConfig, EstoqueConfig } from 'app/shared/model/estoque-config.model';
import { EstoqueConfigService } from './estoque-config.service';
import { IProduto } from 'app/shared/model/produto.model';
import { ProdutoService } from 'app/entities/produto/produto.service';
import { IConta } from 'app/shared/model/conta.model';
import { ContaService } from 'app/entities/conta/conta.service';

@Component({
  selector: 'rv-estoque-config-update',
  templateUrl: './estoque-config-update.component.html'
})
export class EstoqueConfigUpdateComponent implements OnInit {
  isSaving: boolean;

  produtos: IProduto[];

  contas: IConta[];

  editForm = this.fb.group({
    id: [],
    produtoId: [null, Validators.required],
    contaVendaId: [],
    contaCompraId: [],
    contaImobilizadoId: [],
    devolucaoCompraId: [],
    devolucaoVendaId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected estoqueConfigService: EstoqueConfigService,
    protected produtoService: ProdutoService,
    protected contaService: ContaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ estoqueConfig }) => {
      this.updateForm(estoqueConfig);
    });
    this.produtoService.query({ filter: 'estoqueconfig-is-null' }).subscribe(
      (res: HttpResponse<IProduto[]>) => {
        if (!this.editForm.get('produtoId').value) {
          this.produtos = res.body;
        } else {
          this.produtoService
            .find(this.editForm.get('produtoId').value)
            .subscribe(
              (subRes: HttpResponse<IProduto>) => (this.produtos = [subRes.body].concat(res.body)),
              (subRes: HttpErrorResponse) => this.onError(subRes.message)
            );
        }
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
    this.contaService
      .query()
      .subscribe((res: HttpResponse<IConta[]>) => (this.contas = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(estoqueConfig: IEstoqueConfig) {
    this.editForm.patchValue({
      id: estoqueConfig.id,
      produtoId: estoqueConfig.produtoId,
      contaVendaId: estoqueConfig.contaVendaId,
      contaCompraId: estoqueConfig.contaCompraId,
      contaImobilizadoId: estoqueConfig.contaImobilizadoId,
      devolucaoCompraId: estoqueConfig.devolucaoCompraId,
      devolucaoVendaId: estoqueConfig.devolucaoVendaId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const estoqueConfig = this.createFromForm();
    if (estoqueConfig.id !== undefined) {
      this.subscribeToSaveResponse(this.estoqueConfigService.update(estoqueConfig));
    } else {
      this.subscribeToSaveResponse(this.estoqueConfigService.create(estoqueConfig));
    }
  }

  private createFromForm(): IEstoqueConfig {
    return {
      ...new EstoqueConfig(),
      id: this.editForm.get(['id']).value,
      produtoId: this.editForm.get(['produtoId']).value,
      contaVendaId: this.editForm.get(['contaVendaId']).value,
      contaCompraId: this.editForm.get(['contaCompraId']).value,
      contaImobilizadoId: this.editForm.get(['contaImobilizadoId']).value,
      devolucaoCompraId: this.editForm.get(['devolucaoCompraId']).value,
      devolucaoVendaId: this.editForm.get(['devolucaoVendaId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEstoqueConfig>>) {
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

  trackContaById(index: number, item: IConta) {
    return item.id;
  }
}
