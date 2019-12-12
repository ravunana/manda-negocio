import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IDetalheAduaneiro, DetalheAduaneiro } from 'app/shared/model/detalhe-aduaneiro.model';
import { DetalheAduaneiroService } from './detalhe-aduaneiro.service';
import { IProduto } from 'app/shared/model/produto.model';
import { ProdutoService } from 'app/entities/produto/produto.service';

@Component({
  selector: 'rv-detalhe-aduaneiro-update',
  templateUrl: './detalhe-aduaneiro-update.component.html'
})
export class DetalheAduaneiroUpdateComponent implements OnInit {
  isSaving: boolean;

  produtos: IProduto[];
  dataFabricoDp: any;
  dataExpiracaoDp: any;

  editForm = this.fb.group({
    id: [],
    numeroONU: [null, []],
    largura: [],
    altura: [],
    pesoLiquido: [],
    pesoBruto: [],
    dataFabrico: [],
    dataExpiracao: [],
    produtoId: [null, Validators.required]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected detalheAduaneiroService: DetalheAduaneiroService,
    protected produtoService: ProdutoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ detalheAduaneiro }) => {
      this.updateForm(detalheAduaneiro);
    });
    this.produtoService.query({ filter: 'detalheaduaneiro-is-null' }).subscribe(
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

  updateForm(detalheAduaneiro: IDetalheAduaneiro) {
    this.editForm.patchValue({
      id: detalheAduaneiro.id,
      numeroONU: detalheAduaneiro.numeroONU,
      largura: detalheAduaneiro.largura,
      altura: detalheAduaneiro.altura,
      pesoLiquido: detalheAduaneiro.pesoLiquido,
      pesoBruto: detalheAduaneiro.pesoBruto,
      dataFabrico: detalheAduaneiro.dataFabrico,
      dataExpiracao: detalheAduaneiro.dataExpiracao,
      produtoId: detalheAduaneiro.produtoId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const detalheAduaneiro = this.createFromForm();
    if (detalheAduaneiro.id !== undefined) {
      this.subscribeToSaveResponse(this.detalheAduaneiroService.update(detalheAduaneiro));
    } else {
      this.subscribeToSaveResponse(this.detalheAduaneiroService.create(detalheAduaneiro));
    }
  }

  private createFromForm(): IDetalheAduaneiro {
    return {
      ...new DetalheAduaneiro(),
      id: this.editForm.get(['id']).value,
      numeroONU: this.editForm.get(['numeroONU']).value,
      largura: this.editForm.get(['largura']).value,
      altura: this.editForm.get(['altura']).value,
      pesoLiquido: this.editForm.get(['pesoLiquido']).value,
      pesoBruto: this.editForm.get(['pesoBruto']).value,
      dataFabrico: this.editForm.get(['dataFabrico']).value,
      dataExpiracao: this.editForm.get(['dataExpiracao']).value,
      produtoId: this.editForm.get(['produtoId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDetalheAduaneiro>>) {
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
