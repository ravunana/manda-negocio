import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IItemCompra, ItemCompra } from 'app/shared/model/item-compra.model';
import { ItemCompraService } from './item-compra.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { ICompra } from 'app/shared/model/compra.model';
import { CompraService } from 'app/entities/compra/compra.service';
import { IProduto } from 'app/shared/model/produto.model';
import { ProdutoService } from 'app/entities/produto/produto.service';
import { IFornecedor } from 'app/shared/model/fornecedor.model';
import { FornecedorService } from 'app/entities/fornecedor/fornecedor.service';
import { IFluxoDocumento } from 'app/shared/model/fluxo-documento.model';
import { FluxoDocumentoService } from 'app/entities/fluxo-documento/fluxo-documento.service';
import { EstruturaCalculoService } from '../estrutura-calculo/estrutura-calculo.service';
import { MoedaService } from '../moeda/moeda.service';
import { PessoaService } from '../pessoa/pessoa.service';
import { IPessoa } from 'app/shared/model/pessoa.model';
import { isUndefined } from 'util';

@Component({
  selector: 'rv-item-compra-update',
  templateUrl: './item-compra-update.component.html'
})
export class ItemCompraUpdateComponent implements OnInit {
  isSaving: boolean;
  compraId = 0;
  fornecedorId = 0;
  opcao;

  users: IUser[];
  pessoas: IPessoa[];

  compras: ICompra[];

  produtos: IProduto[];

  fornecedors: IFornecedor[];

  fluxodocumentos: IFluxoDocumento[];
  moedaNacional: string;

  editForm = this.fb.group({
    id: [],
    quantidade: [1, [Validators.min(1)]],
    desconto: [null, [Validators.min(0), Validators.max(100)]],
    dataSolicitacao: [],
    dataEntrega: [],
    descricao: [],
    valor: [],
    solicitante: [],
    compraId: [0],
    produtoId: [null, Validators.required],
    fornecedorId: [],
    statusId: [null, Validators.required],
    subTotal: [0]
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected itemCompraService: ItemCompraService,
    protected userService: UserService,
    protected compraService: CompraService,
    protected produtoService: ProdutoService,
    protected fornecedorService: FornecedorService,
    protected fluxoDocumentoService: FluxoDocumentoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
    protected estruturaCalculoService: EstruturaCalculoService,
    protected moedaService: MoedaService,
    protected pessoaService: PessoaService
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ itemCompra }) => {
      this.updateForm(itemCompra);
      this.compraId = itemCompra.compraId;
      this.fornecedorId = itemCompra.fornecedorId;
      this.calcularSubTotal(itemCompra.valor);
    });
    this.userService
      .query()
      .subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.compraService
      .query()
      .subscribe((res: HttpResponse<ICompra[]>) => (this.compras = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.produtoService
      .query()
      .subscribe((res: HttpResponse<IProduto[]>) => (this.produtos = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    // this.fornecedorService
    //   .query()
    //   .subscribe(
    //     (res: HttpResponse<IFornecedor[]>) => (this.fornecedors = res.body),
    //     (res: HttpErrorResponse) => this.onError(res.message)
    //   );
    this.fluxoDocumentoService
      .query()
      .subscribe(
        (res: HttpResponse<IFluxoDocumento[]>) => (this.fluxodocumentos = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );

    this.onFormChanche();
    this.activatedRoute.queryParams.subscribe(parmas => {
      this.opcao = parmas.op;
    });

    if (this.opcao === 'new') {
      this.initForm();
    }

    this.moedaService.query().subscribe(moedaResult => {
      this.moedaNacional = moedaResult.body.filter(m => m.nacional === true).shift().codigo;
    });
  }

  updateForm(itemCompra: IItemCompra) {
    this.editForm.patchValue({
      id: itemCompra.id,
      quantidade: itemCompra.quantidade,
      desconto: itemCompra.desconto,
      dataSolicitacao: itemCompra.dataSolicitacao != null ? itemCompra.dataSolicitacao.format(DATE_TIME_FORMAT) : null,
      dataEntrega: itemCompra.dataEntrega != null ? itemCompra.dataEntrega.format(DATE_TIME_FORMAT) : null,
      descricao: itemCompra.descricao,
      valor: itemCompra.valor,
      solicitante: itemCompra.solicitante,
      compraId: itemCompra.compraId,
      produtoId: itemCompra.produtoId,
      fornecedorId: itemCompra.fornecedorId,
      statusId: itemCompra.statusId
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
    const itemCompra = this.createFromForm();
    if (itemCompra.id !== undefined) {
      this.subscribeToSaveResponse(this.itemCompraService.update(itemCompra));
    } else {
      this.subscribeToSaveResponse(this.itemCompraService.create(itemCompra));
    }
  }

  private createFromForm(): IItemCompra {
    return {
      ...new ItemCompra(),
      id: this.editForm.get(['id']).value,
      quantidade: this.editForm.get(['quantidade']).value,
      desconto: this.editForm.get(['desconto']).value,
      dataSolicitacao:
        this.editForm.get(['dataSolicitacao']).value != null
          ? moment(this.editForm.get(['dataSolicitacao']).value, DATE_TIME_FORMAT)
          : undefined,
      dataEntrega:
        this.editForm.get(['dataEntrega']).value != null ? moment(this.editForm.get(['dataEntrega']).value, DATE_TIME_FORMAT) : undefined,
      descricao: this.editForm.get(['descricao']).value,
      valor: this.editForm.get(['valor']).value,
      solicitante: this.editForm.get(['solicitante']).value,
      compraId: this.compraId,
      produtoId: this.editForm.get(['produtoId']).value,
      fornecedorId: this.fornecedorId,
      statusId: this.editForm.get(['statusId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IItemCompra>>) {
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

  trackCompraById(index: number, item: ICompra) {
    return item.id;
  }

  trackProdutoById(index: number, item: IProduto) {
    return item.id;
  }

  trackFornecedorById(index: number, item: IFornecedor) {
    return item.id;
  }

  trackFluxoDocumentoById(index: number, item: IFluxoDocumento) {
    return item.id;
  }

  searchProdutos($event) {
    this.produtoService
      .query({ 'nome.contains': $event.query })
      .subscribe((res: HttpResponse<IProduto[]>) => (this.produtos = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  onSelectProduto($event) {
    this.editForm.get('produtoId').patchValue($event.id, { emitEvent: false });
    this.estruturaCalculoService.getPrecoAtualizado(this.editForm.get('produtoId').value).subscribe(precoResult => {
      this.calcularSubTotal(precoResult);
    });
  }

  initForm() {
    this.editForm.patchValue({
      quantidade: 1,
      dataEntrega: moment(new Date()),
      dataSolicitacao: moment(new Date()),
      desconto: 0,
      valor: 0,
      subTotal: 0
    });
  }

  calcularSubTotal(preco?: number) {
    this.editForm.get('valor').patchValue(preco, { emitEvent: false });

    const qtdeForm = this.editForm.get(['quantidade']).value;
    const valorForm = this.editForm.get(['valor']).value;
    const descontoForm = this.editForm.get(['desconto']).value;

    const totalUnitario = this.produtoService.calcularSubTotalItem(qtdeForm, descontoForm, valorForm);
    this.editForm.get('subTotal').patchValue(totalUnitario, { emitEvent: false });
  }

  onFormChanche() {
    this.editForm.valueChanges.subscribe((value: IItemCompra) => {
      this.editForm.patchValue(value, { emitEvent: false });
      this.calcularSubTotal(value.valor);
    });
  }

  onAddItem() {
    this.itemCompraService.addItem(this.createFromForm()).subscribe(data => {
      alert(data.produtoNome + ' incluido na lista de compra' + data.fornecedorId);
    });
  }

  onSelectPessoa(pessoa) {
    this.fornecedorService.query({ 'pessoaId.equals': pessoa.id }).subscribe(fornecedorResult => {
      this.fornecedorId = fornecedorResult.body.shift().id;
      alert(this.fornecedorId);
      // this.editForm.get('fornecedorId').patchValue(fornecedorId, { emitEvent: false });
    });
  }

  searchPessoa(pessoa) {
    this.pessoaService.query({ 'nome.contains': pessoa.query }).subscribe(data => {
      this.pessoas = data.body;
    });
  }
}
