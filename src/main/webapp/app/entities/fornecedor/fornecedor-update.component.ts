import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IFornecedor, Fornecedor } from 'app/shared/model/fornecedor.model';
import { FornecedorService } from './fornecedor.service';
import { IPessoa } from 'app/shared/model/pessoa.model';
import { PessoaService } from 'app/entities/pessoa/pessoa.service';
import { IConta } from 'app/shared/model/conta.model';
import { ContaService } from 'app/entities/conta/conta.service';
import { IProduto } from 'app/shared/model/produto.model';
import { ProdutoService } from 'app/entities/produto/produto.service';

@Component({
  selector: 'rv-fornecedor-update',
  templateUrl: './fornecedor-update.component.html'
})
export class FornecedorUpdateComponent implements OnInit {
  isSaving: boolean;

  pessoas: IPessoa[];

  contas: IConta[];

  produtos: IProduto[];

  editForm = this.fb.group({
    id: [],
    certificadoISO9001: [],
    garantiaDefeitoFabrica: [],
    transporte: [],
    qualidade: [null, [Validators.min(0), Validators.max(10)]],
    pagamentoPrazo: [],
    metodosPagamento: [],
    classificacao: [null, [Validators.required, Validators.min(0), Validators.max(10)]],
    descricao: [],
    ativo: [],
    numero: [null],
    pessoaId: [null],
    contaId: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected fornecedorService: FornecedorService,
    protected pessoaService: PessoaService,
    protected contaService: ContaService,
    protected produtoService: ProdutoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ fornecedor }) => {
      this.updateForm(fornecedor);
    });
    this.pessoaService.query({ 'fornecedorId.specified': 'false' }).subscribe(
      (res: HttpResponse<IPessoa[]>) => {
        if (!this.editForm.get('pessoaId').value) {
          this.pessoas = res.body;
        } else {
          this.pessoaService
            .find(this.editForm.get('pessoaId').value)
            .subscribe(
              (subRes: HttpResponse<IPessoa>) => (this.pessoas = [subRes.body].concat(res.body)),
              (subRes: HttpErrorResponse) => this.onError(subRes.message)
            );
        }
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
    this.contaService
      .query()
      .subscribe((res: HttpResponse<IConta[]>) => (this.contas = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.produtoService
      .query()
      .subscribe((res: HttpResponse<IProduto[]>) => (this.produtos = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(fornecedor: IFornecedor) {
    this.editForm.patchValue({
      id: fornecedor.id,
      certificadoISO9001: fornecedor.certificadoISO9001,
      garantiaDefeitoFabrica: fornecedor.garantiaDefeitoFabrica,
      transporte: fornecedor.transporte,
      qualidade: fornecedor.qualidade,
      pagamentoPrazo: fornecedor.pagamentoPrazo,
      metodosPagamento: fornecedor.metodosPagamento,
      classificacao: fornecedor.classificacao,
      descricao: fornecedor.descricao,
      ativo: fornecedor.ativo,
      numero: fornecedor.numero,
      pessoaId: fornecedor.pessoaId,
      contaId: fornecedor.contaId
    });
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

  setFileData(event, field: string, isImage) {
    return new Promise((resolve, reject) => {
      if (event && event.target && event.target.files && event.target.files[0]) {
        const file: File = event.target.files[0];
        if (isImage && !file.type.startsWith('image/')) {
          reject(`File was expected to be an image but was found to be ${file.type}`);
        } else {
          const filedContentType: string = field + 'ContentType';
          this.dataUtils.toBase64(file, base64Data => {
            this.editForm.patchValue({
              [field]: base64Data,
              [filedContentType]: file.type
            });
          });
        }
      } else {
        reject(`Base64 data was not set as file could not be extracted from passed parameter: ${event}`);
      }
    }).then(
      // eslint-disable-next-line no-console
      () => console.log('blob added'), // success
      this.onError
    );
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const fornecedor = this.createFromForm();
    if (fornecedor.id !== undefined) {
      this.subscribeToSaveResponse(this.fornecedorService.update(fornecedor));
    } else {
      this.subscribeToSaveResponse(this.fornecedorService.create(fornecedor));
    }
  }

  private createFromForm(): IFornecedor {
    return {
      ...new Fornecedor(),
      id: this.editForm.get(['id']).value,
      certificadoISO9001: this.editForm.get(['certificadoISO9001']).value,
      garantiaDefeitoFabrica: this.editForm.get(['garantiaDefeitoFabrica']).value,
      transporte: this.editForm.get(['transporte']).value,
      qualidade: this.editForm.get(['qualidade']).value,
      pagamentoPrazo: this.editForm.get(['pagamentoPrazo']).value,
      metodosPagamento: this.editForm.get(['metodosPagamento']).value,
      classificacao: this.editForm.get(['classificacao']).value,
      descricao: this.editForm.get(['descricao']).value,
      ativo: this.editForm.get(['ativo']).value,
      numero: '0',
      // numero: this.editForm.get(['numero']).value,
      pessoaId: this.editForm.get(['pessoaId']).value,
      contaId: this.editForm.get(['contaId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFornecedor>>) {
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

  trackPessoaById(index: number, item: IPessoa) {
    return item.id;
  }

  trackContaById(index: number, item: IConta) {
    return item.id;
  }

  trackProdutoById(index: number, item: IProduto) {
    return item.id;
  }

  getSelected(selectedVals: any[], option: any) {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }

  onSelectConta(conta) {
    this.editForm.get('contaId').patchValue(conta.id, { emitEvent: false });
  }

  searchConta(conta) {
    this.contaService.query({ 'descricao.contains': conta.query }).subscribe(data => {
      this.contas = data.body;
    });
  }

  onSelectPessoa(pessoa) {
    this.editForm.get('pessoaId').patchValue(pessoa.id, { emitEvent: false });
  }

  searchPessoa(pessoa) {
    this.pessoaService.query({ 'nome.contains': pessoa.query }).subscribe(data => {
      this.pessoas = data.body;
    });
  }
}
