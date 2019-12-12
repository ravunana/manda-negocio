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
import { IDevolucaoVenda, DevolucaoVenda } from 'app/shared/model/devolucao-venda.model';
import { DevolucaoVendaService } from './devolucao-venda.service';
import { IItemVenda } from 'app/shared/model/item-venda.model';
import { ItemVendaService } from 'app/entities/item-venda/item-venda.service';

@Component({
  selector: 'rv-devolucao-venda-update',
  templateUrl: './devolucao-venda-update.component.html'
})
export class DevolucaoVendaUpdateComponent implements OnInit {
  isSaving: boolean;

  items: IItemVenda[];

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
    protected devolucaoVendaService: DevolucaoVendaService,
    protected itemVendaService: ItemVendaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ devolucaoVenda }) => {
      this.updateForm(devolucaoVenda);
    });
    this.itemVendaService.query({ 'devolucaoVendaId.specified': 'false' }).subscribe(
      (res: HttpResponse<IItemVenda[]>) => {
        if (!this.editForm.get('itemId').value) {
          this.items = res.body;
        } else {
          this.itemVendaService
            .find(this.editForm.get('itemId').value)
            .subscribe(
              (subRes: HttpResponse<IItemVenda>) => (this.items = [subRes.body].concat(res.body)),
              (subRes: HttpErrorResponse) => this.onError(subRes.message)
            );
        }
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  updateForm(devolucaoVenda: IDevolucaoVenda) {
    this.editForm.patchValue({
      id: devolucaoVenda.id,
      quantidade: devolucaoVenda.quantidade,
      valor: devolucaoVenda.valor,
      desconto: devolucaoVenda.desconto,
      data: devolucaoVenda.data != null ? devolucaoVenda.data.format(DATE_TIME_FORMAT) : null,
      descricao: devolucaoVenda.descricao,
      itemId: devolucaoVenda.itemId
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
    const devolucaoVenda = this.createFromForm();
    if (devolucaoVenda.id !== undefined) {
      this.subscribeToSaveResponse(this.devolucaoVendaService.update(devolucaoVenda));
    } else {
      this.subscribeToSaveResponse(this.devolucaoVendaService.create(devolucaoVenda));
    }
  }

  private createFromForm(): IDevolucaoVenda {
    return {
      ...new DevolucaoVenda(),
      id: this.editForm.get(['id']).value,
      quantidade: this.editForm.get(['quantidade']).value,
      valor: this.editForm.get(['valor']).value,
      desconto: this.editForm.get(['desconto']).value,
      data: this.editForm.get(['data']).value != null ? moment(this.editForm.get(['data']).value, DATE_TIME_FORMAT) : undefined,
      descricao: this.editForm.get(['descricao']).value,
      itemId: this.editForm.get(['itemId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDevolucaoVenda>>) {
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

  trackItemVendaById(index: number, item: IItemVenda) {
    return item.id;
  }
}
