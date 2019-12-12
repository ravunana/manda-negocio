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
import { IDevolucaoCompra, DevolucaoCompra } from 'app/shared/model/devolucao-compra.model';
import { DevolucaoCompraService } from './devolucao-compra.service';
import { IItemCompra } from 'app/shared/model/item-compra.model';
import { ItemCompraService } from 'app/entities/item-compra/item-compra.service';

@Component({
  selector: 'rv-devolucao-compra-update',
  templateUrl: './devolucao-compra-update.component.html'
})
export class DevolucaoCompraUpdateComponent implements OnInit {
  isSaving: boolean;

  items: IItemCompra[];

  editForm = this.fb.group({
    id: [],
    quantidade: [null, [Validators.min(1)]],
    valor: [null, [Validators.min(0)]],
    desconto: [null, [Validators.min(0)]],
    data: [],
    descricao: [],
    itemId: [null, Validators.required]
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected devolucaoCompraService: DevolucaoCompraService,
    protected itemCompraService: ItemCompraService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ devolucaoCompra }) => {
      this.updateForm(devolucaoCompra);
    });
    this.itemCompraService.query({ 'devolucaoCompraId.specified': 'false' }).subscribe(
      (res: HttpResponse<IItemCompra[]>) => {
        if (!this.editForm.get('itemId').value) {
          this.items = res.body;
        } else {
          this.itemCompraService
            .find(this.editForm.get('itemId').value)
            .subscribe(
              (subRes: HttpResponse<IItemCompra>) => (this.items = [subRes.body].concat(res.body)),
              (subRes: HttpErrorResponse) => this.onError(subRes.message)
            );
        }
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  updateForm(devolucaoCompra: IDevolucaoCompra) {
    this.editForm.patchValue({
      id: devolucaoCompra.id,
      quantidade: devolucaoCompra.quantidade,
      valor: devolucaoCompra.valor,
      desconto: devolucaoCompra.desconto,
      data: devolucaoCompra.data != null ? devolucaoCompra.data.format(DATE_TIME_FORMAT) : null,
      descricao: devolucaoCompra.descricao,
      itemId: devolucaoCompra.itemId
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
    const devolucaoCompra = this.createFromForm();
    if (devolucaoCompra.id !== undefined) {
      this.subscribeToSaveResponse(this.devolucaoCompraService.update(devolucaoCompra));
    } else {
      this.subscribeToSaveResponse(this.devolucaoCompraService.create(devolucaoCompra));
    }
  }

  private createFromForm(): IDevolucaoCompra {
    return {
      ...new DevolucaoCompra(),
      id: this.editForm.get(['id']).value,
      quantidade: this.editForm.get(['quantidade']).value,
      valor: this.editForm.get(['valor']).value,
      desconto: this.editForm.get(['desconto']).value,
      data: this.editForm.get(['data']).value != null ? moment(this.editForm.get(['data']).value, DATE_TIME_FORMAT) : undefined,
      descricao: this.editForm.get(['descricao']).value,
      itemId: this.editForm.get(['itemId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDevolucaoCompra>>) {
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

  trackItemCompraById(index: number, item: IItemCompra) {
    return item.id;
  }
}
