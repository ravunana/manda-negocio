import { IDetalheLancamento } from './../../shared/model/detalhe-lancamento.model';
import { ILookupItem } from './../../shared/model/lookup-item.model';
import { LookupItemService } from './../lookup-item/lookup-item.service';
import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { ILancamentoFinanceiro, LancamentoFinanceiro } from 'app/shared/model/lancamento-financeiro.model';
import { LancamentoFinanceiroService } from './lancamento-financeiro.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { IImposto } from 'app/shared/model/imposto.model';
import { ImpostoService } from 'app/entities/imposto/imposto.service';
import { IFormaLiquidacao } from 'app/shared/model/forma-liquidacao.model';
import { FormaLiquidacaoService } from 'app/entities/forma-liquidacao/forma-liquidacao.service';
import { IEmpresa } from 'app/shared/model/empresa.model';
import { EmpresaService } from 'app/entities/empresa/empresa.service';
import { IDocumentoComercial } from 'app/shared/model/documento-comercial.model';
import { DocumentoComercialService } from 'app/entities/documento-comercial/documento-comercial.service';
import { IConta } from 'app/shared/model/conta.model';
import { ContaService } from 'app/entities/conta/conta.service';
import { EntidadeSistema } from 'app/shared/model/enumerations/entidade-sistema.model';

@Component({
  selector: 'rv-lancamento-financeiro-update',
  templateUrl: './lancamento-financeiro-update.component.html'
})
export class LancamentoFinanceiroUpdateComponent implements OnInit {
  isSaving: boolean;

  users: IUser[];

  impostos: IImposto[];

  formaliquidacaos: IFormaLiquidacao[];

  empresas: IEmpresa[];

  documentocomercials: IDocumentoComercial[];
  tiposLancamento: ILookupItem[];
  detalhesLancamento: IDetalheLancamento[] = [];
  TOTAL_ENTREGUE = 0;

  contas: IConta[];

  editForm = this.fb.group({
    id: [],
    tipoLancamento: [null, [Validators.required]],
    valor: [null, [Validators.required, Validators.min(0)]],
    externo: [],
    numero: [null],
    descricao: [null, [Validators.required]],
    entidadeDocumento: [null],
    numeroDocumento: [null, [Validators.required]],
    utilizadorId: [],
    impostos: [null, Validators.required],
    formaLiquidacaoId: [null, Validators.required],
    empresaId: [],
    tipoReciboId: [null, Validators.required],
    contaId: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected lancamentoFinanceiroService: LancamentoFinanceiroService,
    protected userService: UserService,
    protected impostoService: ImpostoService,
    protected formaLiquidacaoService: FormaLiquidacaoService,
    protected empresaService: EmpresaService,
    protected documentoComercialService: DocumentoComercialService,
    protected contaService: ContaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
    protected lookupItemService: LookupItemService
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ lancamentoFinanceiro }) => {
      this.updateForm(lancamentoFinanceiro);
    });
    this.userService
      .query()
      .subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.impostoService
      .query()
      .subscribe((res: HttpResponse<IImposto[]>) => (this.impostos = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.formaLiquidacaoService
      .query()
      .subscribe(
        (res: HttpResponse<IFormaLiquidacao[]>) => (this.formaliquidacaos = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.empresaService
      .query()
      .subscribe((res: HttpResponse<IEmpresa[]>) => (this.empresas = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.documentoComercialService
      .query()
      .subscribe(
        (res: HttpResponse<IDocumentoComercial[]>) => (this.documentocomercials = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.contaService
      .query()
      .subscribe((res: HttpResponse<IConta[]>) => (this.contas = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.lookupItemService.query({ 'lookupId.equals': '1404' }).subscribe(data => {
      this.tiposLancamento = data.body;
    });

    this.getPagamentos();
  }

  updateForm(lancamentoFinanceiro: ILancamentoFinanceiro) {
    this.editForm.patchValue({
      id: lancamentoFinanceiro.id,
      tipoLancamento: lancamentoFinanceiro.tipoLancamento,
      valor: lancamentoFinanceiro.valor,
      externo: lancamentoFinanceiro.externo,
      numero: lancamentoFinanceiro.numero,
      descricao: lancamentoFinanceiro.descricao,
      entidadeDocumento: lancamentoFinanceiro.entidadeDocumento,
      numeroDocumento: lancamentoFinanceiro.numeroDocumento,
      utilizadorId: lancamentoFinanceiro.utilizadorId,
      impostos: lancamentoFinanceiro.impostos,
      formaLiquidacaoId: lancamentoFinanceiro.formaLiquidacaoId,
      empresaId: lancamentoFinanceiro.empresaId,
      tipoReciboId: lancamentoFinanceiro.tipoReciboId,
      contaId: lancamentoFinanceiro.contaId
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
    const lancamentoFinanceiro = this.createFromForm();
    if (lancamentoFinanceiro.id !== undefined) {
      this.subscribeToSaveResponse(this.lancamentoFinanceiroService.update(lancamentoFinanceiro));
    } else {
      this.subscribeToSaveResponse(this.lancamentoFinanceiroService.create(lancamentoFinanceiro));
    }
  }

  private createFromForm(): ILancamentoFinanceiro {
    return {
      ...new LancamentoFinanceiro(),
      id: this.editForm.get(['id']).value,
      tipoLancamento: this.editForm.get(['tipoLancamento']).value,
      valor: this.editForm.get(['valor']).value,
      externo: this.editForm.get(['externo']).value,
      numero: '00000',
      descricao: this.editForm.get(['descricao']).value,
      entidadeDocumento: EntidadeSistema.ARQUIVO,
      numeroDocumento: this.editForm.get(['numeroDocumento']).value,
      utilizadorId: 0,
      impostos: this.editForm.get(['impostos']).value,
      formaLiquidacaoId: this.editForm.get(['formaLiquidacaoId']).value,
      empresaId: this.editForm.get(['empresaId']).value,
      tipoReciboId: this.editForm.get(['tipoReciboId']).value,
      contaId: this.editForm.get(['contaId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILancamentoFinanceiro>>) {
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

  trackImpostoById(index: number, item: IImposto) {
    return item.id;
  }

  trackFormaLiquidacaoById(index: number, item: IFormaLiquidacao) {
    return item.id;
  }

  trackEmpresaById(index: number, item: IEmpresa) {
    return item.id;
  }

  trackDocumentoComercialById(index: number, item: IDocumentoComercial) {
    return item.id;
  }

  trackContaById(index: number, item: IConta) {
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
    this.lancamentoFinanceiroService.getDetalhes().subscribe(data => {
      this.detalhesLancamento = data;
      this.TOTAL_ENTREGUE = this.detalhesLancamento
        .filter(d => d.liquidado === true)
        .map(i => i.valor)
        .reduce(function(total, subTotal) {
          let valor = total + subTotal;
          return valor;
        });
    });
  }

  onDeleteValor(index) {
    this.lancamentoFinanceiroService.deleteDetalhe(index).subscribe(valorEliminado => {
      alert(valorEliminado.valor + ' foi removido');
      this.getPagamentos();
    });
  }

  onSelectConta(conta) {
    this.editForm.get('contaId').patchValue(conta.id, { emitEvent: false });
  }

  searchConta(conta) {
    this.contaService.query({ 'descricao.contains': conta.query }).subscribe(data => {
      this.contas = data.body;
    });
  }
}
