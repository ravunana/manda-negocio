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
import { IArquivo, Arquivo } from 'app/shared/model/arquivo.model';
import { ArquivoService } from './arquivo.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

@Component({
  selector: 'rv-arquivo-update',
  templateUrl: './arquivo-update.component.html'
})
export class ArquivoUpdateComponent implements OnInit {
  isSaving: boolean;

  users: IUser[];

  editForm = this.fb.group({
    id: [],
    descricao: [],
    entidade: [],
    anexo: [],
    anexoContentType: [],
    codigoEntidade: [],
    data: [],
    utilizadorId: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected arquivoService: ArquivoService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ arquivo }) => {
      this.updateForm(arquivo);
    });
    this.userService
      .query()
      .subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(arquivo: IArquivo) {
    this.editForm.patchValue({
      id: arquivo.id,
      descricao: arquivo.descricao,
      entidade: arquivo.entidade,
      anexo: arquivo.anexo,
      anexoContentType: arquivo.anexoContentType,
      codigoEntidade: arquivo.codigoEntidade,
      data: arquivo.data != null ? arquivo.data.format(DATE_TIME_FORMAT) : null,
      utilizadorId: arquivo.utilizadorId
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
    const arquivo = this.createFromForm();
    if (arquivo.id !== undefined) {
      this.subscribeToSaveResponse(this.arquivoService.update(arquivo));
    } else {
      this.subscribeToSaveResponse(this.arquivoService.create(arquivo));
    }
  }

  private createFromForm(): IArquivo {
    return {
      ...new Arquivo(),
      id: this.editForm.get(['id']).value,
      descricao: this.editForm.get(['descricao']).value,
      entidade: this.editForm.get(['entidade']).value,
      anexoContentType: this.editForm.get(['anexoContentType']).value,
      anexo: this.editForm.get(['anexo']).value,
      codigoEntidade: this.editForm.get(['codigoEntidade']).value,
      data: this.editForm.get(['data']).value != null ? moment(this.editForm.get(['data']).value, DATE_TIME_FORMAT) : undefined,
      utilizadorId: this.editForm.get(['utilizadorId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IArquivo>>) {
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
}
