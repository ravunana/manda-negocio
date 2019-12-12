import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { IVariacaoProduto, VariacaoProduto } from 'app/shared/model/variacao-produto.model';
import { VariacaoProdutoService } from './variacao-produto.service';
import { IProduto } from 'app/shared/model/produto.model';
import { ProdutoService } from 'app/entities/produto/produto.service';

@Component({
  selector: 'rv-variacao-produto-update',
  templateUrl: './variacao-produto-update.component.html'
})
export class VariacaoProdutoUpdateComponent implements OnInit {
  isSaving: boolean;

  produtos: IProduto[];

  editForm = this.fb.group({
    id: [],
    genero: [],
    cor: [],
    modelo: [],
    marca: [],
    tamanho: [],
    produtoId: [null, Validators.required]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected variacaoProdutoService: VariacaoProdutoService,
    protected produtoService: ProdutoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ variacaoProduto }) => {
      this.updateForm(variacaoProduto);
    });
    this.produtoService.query({ filter: 'variacaoproduto-is-null' }).subscribe(
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
  }

  updateForm(variacaoProduto: IVariacaoProduto) {
    this.editForm.patchValue({
      id: variacaoProduto.id,
      genero: variacaoProduto.genero,
      cor: variacaoProduto.cor,
      modelo: variacaoProduto.modelo,
      marca: variacaoProduto.marca,
      tamanho: variacaoProduto.tamanho,
      produtoId: variacaoProduto.produtoId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const variacaoProduto = this.createFromForm();
    if (variacaoProduto.id !== undefined) {
      this.subscribeToSaveResponse(this.variacaoProdutoService.update(variacaoProduto));
    } else {
      this.subscribeToSaveResponse(this.variacaoProdutoService.create(variacaoProduto));
    }
  }

  private createFromForm(): IVariacaoProduto {
    return {
      ...new VariacaoProduto(),
      id: this.editForm.get(['id']).value,
      genero: this.editForm.get(['genero']).value,
      cor: this.editForm.get(['cor']).value,
      modelo: this.editForm.get(['modelo']).value,
      marca: this.editForm.get(['marca']).value,
      tamanho: this.editForm.get(['tamanho']).value,
      produtoId: this.editForm.get(['produtoId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVariacaoProduto>>) {
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
}
