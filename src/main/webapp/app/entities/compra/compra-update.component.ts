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
import { ICompra, Compra } from 'app/shared/model/compra.model';
import { CompraService } from './compra.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { IDocumentoComercial } from 'app/shared/model/documento-comercial.model';
import { DocumentoComercialService } from 'app/entities/documento-comercial/documento-comercial.service';
import { IEmpresa } from 'app/shared/model/empresa.model';
import { EmpresaService } from 'app/entities/empresa/empresa.service';
import { IItemCompra } from 'app/shared/model/item-compra.model';
import { ItemCompraService } from '../item-compra/item-compra.service';
import { ProdutoService } from '../produto/produto.service';
import { MoedaService } from '../moeda/moeda.service';

@Component({
  selector: 'rv-compra-update',
  templateUrl: './compra-update.component.html'
})
export class CompraUpdateComponent implements OnInit {
  isSaving: boolean;

  users: IUser[];

  SUB_TOTAL = 0;
  TOTAL_DESCONTO = 0;
  TOTAL_PAGAR = 0;
  TOTAL_ENTREGUE = 0;
  TROCO = 0;

  items: IItemCompra[];

  documentocomercials: IDocumentoComercial[];

  empresas: IEmpresa[];
  moedaNacional = 'Moeda';

  editForm = this.fb.group({
    id: [],
    numero: [null],
    data: [],
    observacaoGeral: [],
    observacaoInterna: [],
    utilizadorId: [],
    tipoDocumentoId: [null, Validators.required],
    empresaId: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected compraService: CompraService,
    protected userService: UserService,
    protected documentoComercialService: DocumentoComercialService,
    protected empresaService: EmpresaService,
    protected activatedRoute: ActivatedRoute,
    protected itemCompraSrvice: ItemCompraService,
    public produtoService: ProdutoService,
    private fb: FormBuilder,
    protected moedaService: MoedaService
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.moedaService.query().subscribe(moedaResult => {
      this.moedaNacional = moedaResult.body.filter(m => m.nacional === true).shift().codigo;
    });

    this.activatedRoute.data.subscribe(({ compra }) => {
      this.updateForm(compra);
    });
    this.userService
      .query()
      .subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.documentoComercialService
      .query()
      .subscribe(
        (res: HttpResponse<IDocumentoComercial[]>) => (this.documentocomercials = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );

    this.getItems();

    this.empresaService
      .query()
      .subscribe((res: HttpResponse<IEmpresa[]>) => (this.empresas = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(compra: ICompra) {
    this.editForm.patchValue({
      id: compra.id,
      numero: compra.numero,
      data: compra.data != null ? compra.data.format(DATE_TIME_FORMAT) : null,
      observacaoGeral: compra.observacaoGeral,
      observacaoInterna: compra.observacaoInterna,
      utilizadorId: compra.utilizadorId,
      tipoDocumentoId: compra.tipoDocumentoId,
      empresaId: compra.empresaId
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
    const compra = this.createFromForm();
    if (compra.id !== undefined) {
      this.subscribeToSaveResponse(this.compraService.update(compra));
    } else {
      this.subscribeToSaveResponse(this.compraService.create(compra));
    }
  }

  private createFromForm(): ICompra {
    return {
      ...new Compra(),
      id: this.editForm.get(['id']).value,
      numero: '0',
      // numero: this.editForm.get(['numero']).value,
      data: moment(),
      // data: this.editForm.get(['data']).value != null ? moment(this.editForm.get(['data']).value, DATE_TIME_FORMAT) : undefined,
      observacaoGeral: this.editForm.get(['observacaoGeral']).value,
      observacaoInterna: this.editForm.get(['observacaoInterna']).value,
      // utilizadorId: this.editForm.get(['utilizadorId']).value,
      utilizadorId: 1,
      tipoDocumentoId: this.editForm.get(['tipoDocumentoId']).value,
      empresaId: this.editForm.get(['empresaId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICompra>>) {
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

  trackDocumentoComercialById(index: number, item: IDocumentoComercial) {
    return item.id;
  }

  trackEmpresaById(index: number, item: IEmpresa) {
    return item.id;
  }

  getItems() {
    this.itemCompraSrvice.getItems().subscribe(itemsResult => {
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
    this.itemCompraSrvice.deleteItem(index).subscribe(itemEliminado => {
      alert(itemEliminado.produtoNome + ' foi removido da lista');
      this.getItems();
    });
  }
}
