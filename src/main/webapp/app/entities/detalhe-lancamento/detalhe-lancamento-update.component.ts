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
import { IDetalheLancamento, DetalheLancamento } from 'app/shared/model/detalhe-lancamento.model';
import { DetalheLancamentoService } from './detalhe-lancamento.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { ILancamentoFinanceiro } from 'app/shared/model/lancamento-financeiro.model';
import { LancamentoFinanceiroService } from 'app/entities/lancamento-financeiro/lancamento-financeiro.service';
import { IMeioLiquidacao } from 'app/shared/model/meio-liquidacao.model';
import { MeioLiquidacaoService } from 'app/entities/meio-liquidacao/meio-liquidacao.service';
import { IMoeda } from 'app/shared/model/moeda.model';
import { MoedaService } from 'app/entities/moeda/moeda.service';
import { ICoordenadaBancaria } from 'app/shared/model/coordenada-bancaria.model';
import { CoordenadaBancariaService } from 'app/entities/coordenada-bancaria/coordenada-bancaria.service';

@Component({
  selector: 'rv-detalhe-lancamento-update',
  templateUrl: './detalhe-lancamento-update.component.html'
})
export class DetalheLancamentoUpdateComponent implements OnInit {
  isSaving: boolean;

  users: IUser[];

  lancamentofinanceiros: ILancamentoFinanceiro[];

  meioliquidacaos: IMeioLiquidacao[];

  moedas: IMoeda[];

  coordenadabancarias: ICoordenadaBancaria[];
  dataVencimentoDp: any;

  editForm = this.fb.group({
    id: [],
    valor: [null, [Validators.required, Validators.min(0)]],
    desconto: [null, [Validators.min(0)]],
    juro: [null, [Validators.min(0), Validators.max(100)]],
    descricao: [],
    data: [null, [Validators.required]],
    retencaoFonte: [],
    dataVencimento: [null, [Validators.required]],
    liquidado: [],
    utilizadorId: [],
    lancamentoFinanceiroId: [null, Validators.required],
    metodoLiquidacaoId: [null, Validators.required],
    moedaId: [null, Validators.required],
    coordenadaId: [null, Validators.required]
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected detalheLancamentoService: DetalheLancamentoService,
    protected userService: UserService,
    protected lancamentoFinanceiroService: LancamentoFinanceiroService,
    protected meioLiquidacaoService: MeioLiquidacaoService,
    protected moedaService: MoedaService,
    protected coordenadaBancariaService: CoordenadaBancariaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ detalheLancamento }) => {
      this.updateForm(detalheLancamento);
    });
    this.userService
      .query()
      .subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.lancamentoFinanceiroService
      .query()
      .subscribe(
        (res: HttpResponse<ILancamentoFinanceiro[]>) => (this.lancamentofinanceiros = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.meioLiquidacaoService
      .query()
      .subscribe(
        (res: HttpResponse<IMeioLiquidacao[]>) => (this.meioliquidacaos = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.moedaService
      .query()
      .subscribe((res: HttpResponse<IMoeda[]>) => (this.moedas = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.coordenadaBancariaService
      .query()
      .subscribe(
        (res: HttpResponse<ICoordenadaBancaria[]>) => (this.coordenadabancarias = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(detalheLancamento: IDetalheLancamento) {
    this.editForm.patchValue({
      id: detalheLancamento.id,
      valor: detalheLancamento.valor,
      desconto: detalheLancamento.desconto,
      juro: detalheLancamento.juro,
      descricao: detalheLancamento.descricao,
      data: detalheLancamento.data != null ? detalheLancamento.data.format(DATE_TIME_FORMAT) : null,
      retencaoFonte: detalheLancamento.retencaoFonte,
      dataVencimento: detalheLancamento.dataVencimento,
      liquidado: detalheLancamento.liquidado,
      utilizadorId: detalheLancamento.utilizadorId,
      lancamentoFinanceiroId: detalheLancamento.lancamentoFinanceiroId,
      metodoLiquidacaoId: detalheLancamento.metodoLiquidacaoId,
      moedaId: detalheLancamento.moedaId,
      coordenadaId: detalheLancamento.coordenadaId
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
    const detalheLancamento = this.createFromForm();
    if (detalheLancamento.id !== undefined) {
      this.subscribeToSaveResponse(this.detalheLancamentoService.update(detalheLancamento));
    } else {
      this.subscribeToSaveResponse(this.detalheLancamentoService.create(detalheLancamento));
    }
  }

  private createFromForm(): IDetalheLancamento {
    return {
      ...new DetalheLancamento(),
      id: this.editForm.get(['id']).value,
      valor: this.editForm.get(['valor']).value,
      desconto: this.editForm.get(['desconto']).value,
      juro: this.editForm.get(['juro']).value,
      descricao: this.editForm.get(['descricao']).value,
      data: this.editForm.get(['data']).value != null ? moment(this.editForm.get(['data']).value, DATE_TIME_FORMAT) : undefined,
      retencaoFonte: this.editForm.get(['retencaoFonte']).value,
      dataVencimento: this.editForm.get(['dataVencimento']).value,
      liquidado: this.editForm.get(['liquidado']).value,
      utilizadorId: this.editForm.get(['utilizadorId']).value,
      lancamentoFinanceiroId: this.editForm.get(['lancamentoFinanceiroId']).value,
      metodoLiquidacaoId: this.editForm.get(['metodoLiquidacaoId']).value,
      moedaId: this.editForm.get(['moedaId']).value,
      coordenadaId: this.editForm.get(['coordenadaId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDetalheLancamento>>) {
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

  trackLancamentoFinanceiroById(index: number, item: ILancamentoFinanceiro) {
    return item.id;
  }

  trackMeioLiquidacaoById(index: number, item: IMeioLiquidacao) {
    return item.id;
  }

  trackMoedaById(index: number, item: IMoeda) {
    return item.id;
  }

  trackCoordenadaBancariaById(index: number, item: ICoordenadaBancaria) {
    return item.id;
  }
}
