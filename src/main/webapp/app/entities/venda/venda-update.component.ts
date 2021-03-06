import { LancamentoFinanceiroService } from './../lancamento-financeiro/lancamento-financeiro.service';
import { ItemVendaService } from 'app/entities/item-venda/item-venda.service';
import { ProdutoService } from './../produto/produto.service';
import { PessoaService } from './../pessoa/pessoa.service';
import { IPessoa } from './../../shared/model/pessoa.model';
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
import { IVenda, Venda } from 'app/shared/model/venda.model';
import { VendaService } from './venda.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { ICliente } from 'app/shared/model/cliente.model';
import { ClienteService } from 'app/entities/cliente/cliente.service';
import { IDocumentoComercial } from 'app/shared/model/documento-comercial.model';
import { DocumentoComercialService } from 'app/entities/documento-comercial/documento-comercial.service';
import { IEmpresa } from 'app/shared/model/empresa.model';
import { EmpresaService } from 'app/entities/empresa/empresa.service';
import { FormaLiquidacaoService } from '../forma-liquidacao/forma-liquidacao.service';
import { IFormaLiquidacao } from 'app/shared/model/forma-liquidacao.model';
import { IImposto } from 'app/shared/model/imposto.model';
import { ImpostoService } from '../imposto/imposto.service';
import { IDetalheLancamento } from 'app/shared/model/detalhe-lancamento.model';
import { IItemCompra } from 'app/shared/model/item-compra.model';

@Component({
  selector: 'rv-venda-update',
  templateUrl: './venda-update.component.html'
})
export class VendaUpdateComponent implements OnInit {
  isSaving: boolean;

  users: IUser[];

  clientes: ICliente[];
  pagamentos: IDetalheLancamento[] = [];
  items: IItemCompra[] = [];
  pessoas: IPessoa[];

  SUB_TOTAL = 0;
  TOTAL_DESCONTO = 0;
  TOTAL_PAGAR = 0;
  TOTAL_ENTREGUE = 0;
  TROCO = 0;

  documentocomercials: IDocumentoComercial[];
  formaliquidacaos: IFormaLiquidacao[];
  impostos: IImposto[];

  empresas: IEmpresa[];
  clienteId = 0;

  editForm = this.fb.group({
    id: [],
    numero: [],
    data: [],
    observacaoGeral: [],
    observacaoInterna: [],
    vendedorId: [],
    clienteId: [null],
    tipoDocumentoId: [null, Validators.required],
    empresaId: [],
    impostos: [null, Validators.required],
    formaLiquidacaoId: [null, Validators.required]
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected vendaService: VendaService,
    protected userService: UserService,
    protected clienteService: ClienteService,
    protected documentoComercialService: DocumentoComercialService,
    protected empresaService: EmpresaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
    protected formaLiquidacaoService: FormaLiquidacaoService,
    protected impostoService: ImpostoService,
    protected pessoaService: PessoaService,
    public produtoService: ProdutoService,
    protected itemVendaService: ItemVendaService,
    protected lancamentoFinancerioService: LancamentoFinanceiroService
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ venda }) => {
      this.updateForm(venda);
      this.clienteId = venda.clienteId;
    });
    this.userService
      .query()
      .subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.clienteService
      .query()
      .subscribe((res: HttpResponse<ICliente[]>) => (this.clientes = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.documentoComercialService
      .query()
      .subscribe(
        (res: HttpResponse<IDocumentoComercial[]>) => (this.documentocomercials = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.empresaService
      .query()
      .subscribe((res: HttpResponse<IEmpresa[]>) => (this.empresas = res.body), (res: HttpErrorResponse) => this.onError(res.message));

    this.impostoService
      .query()
      .subscribe((res: HttpResponse<IImposto[]>) => (this.impostos = res.body), (res: HttpErrorResponse) => this.onError(res.message));

    this.formaLiquidacaoService
      .query()
      .subscribe(
        (res: HttpResponse<IFormaLiquidacao[]>) => (this.formaliquidacaos = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );

    this.getItems();
    this.getPagamentos();
  }

  updateForm(venda: IVenda) {
    this.editForm.patchValue({
      id: venda.id,
      numero: venda.numero,
      data: venda.data != null ? venda.data.format(DATE_TIME_FORMAT) : null,
      observacaoGeral: venda.observacaoGeral,
      observacaoInterna: venda.observacaoInterna,
      vendedorId: venda.vendedorId,
      clienteId: venda.clienteId,
      tipoDocumentoId: venda.tipoDocumentoId,
      empresaId: venda.empresaId
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
    const venda = this.createFromForm();
    if (venda.id !== undefined) {
      this.subscribeToSaveResponse(this.vendaService.update(venda));
    } else {
      this.subscribeToSaveResponse(this.vendaService.create(venda));
    }
  }

  private createFromForm(): IVenda {
    return {
      ...new Venda(),
      id: this.editForm.get(['id']).value,
      numero: this.editForm.get(['numero']).value,
      data: this.editForm.get(['data']).value != null ? moment(this.editForm.get(['data']).value, DATE_TIME_FORMAT) : undefined,
      observacaoGeral: this.editForm.get(['observacaoGeral']).value,
      observacaoInterna: this.editForm.get(['observacaoInterna']).value,
      vendedorId: this.editForm.get(['vendedorId']).value,
      clienteId: this.clienteId,
      tipoDocumentoId: this.editForm.get(['tipoDocumentoId']).value,
      empresaId: this.editForm.get(['empresaId']).value,
      impostos: this.editForm.get(['impostos']).value,
      formaLiquidacaoId: this.editForm.get(['formaLiquidacaoId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVenda>>) {
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

  trackClienteById(index: number, item: ICliente) {
    return item.id;
  }

  trackDocumentoComercialById(index: number, item: IDocumentoComercial) {
    return item.id;
  }

  trackEmpresaById(index: number, item: IEmpresa) {
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

  getPagamentos() {
    this.lancamentoFinancerioService.getDetalhes().subscribe(data => {
      this.pagamentos = data;
      this.TOTAL_ENTREGUE = this.pagamentos
        .map(i => i.valor)
        .reduce(function(total, subTotal) {
          return total + subTotal;
        });
    });
  }

  getItems() {
    this.itemVendaService.getItems().subscribe(itemsResult => {
      this.items = itemsResult;

      // Calcular subTotal sem desconto da factura
      this.SUB_TOTAL = this.items
        .map(i => i.quantidade * i.valor)
        .reduce(function(total, subTotal) {
          return total + subTotal;
        });
      // calcular valor em numerario do desconto da factura
      const valorDesconto = this.items
        .map(i => this.produtoService.calcularSubTotalItem(i.quantidade, i.desconto, i.valor))
        .reduce(function(total, desconto) {
          return total + desconto;
        });

      this.TOTAL_DESCONTO = this.SUB_TOTAL - valorDesconto;
      this.TOTAL_PAGAR = this.SUB_TOTAL - this.TOTAL_DESCONTO;
    });
  }

  onDeleteItem(index) {
    this.itemVendaService.deleteItem(index).subscribe(itemEliminado => {
      alert(itemEliminado.produtoNome + ' foi removido da lista');
      this.getItems();
    });
  }

  onDeleteValor(index) {
    this.lancamentoFinancerioService.deleteDetalhe(index).subscribe(valorEliminado => {
      alert(valorEliminado.valor + ' foi removido');
      this.getPagamentos();
    });
  }

  onSelectPessoa(pessoa) {
    this.clienteService.query({ 'pessoaId.equals': pessoa.id }).subscribe(clienteResult => {
      this.clienteId = clienteResult.body.shift().id;
      // this.editForm.get('fornecedorId').patchValue(fornecedorId, { emitEvent: false });
    });
  }

  searchPessoa(pessoa) {
    this.pessoaService.query({ 'nome.contains': pessoa.query }).subscribe(data => {
      this.pessoas = data.body;
    });
  }
}
