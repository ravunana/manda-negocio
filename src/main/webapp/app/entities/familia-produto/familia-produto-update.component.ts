import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IFamiliaProduto, FamiliaProduto } from 'app/shared/model/familia-produto.model';
import { FamiliaProdutoService } from './familia-produto.service';
import { IConta } from 'app/shared/model/conta.model';
import { ContaService } from 'app/entities/conta/conta.service';

@Component({
  selector: 'rv-familia-produto-update',
  templateUrl: './familia-produto-update.component.html'
})
export class FamiliaProdutoUpdateComponent implements OnInit {
  isSaving: boolean;

  contas: IConta[];

  familiaprodutos: IFamiliaProduto[];

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required]],
    descricao: [],
    contaId: [],
    hierarquiaId: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected familiaProdutoService: FamiliaProdutoService,
    protected contaService: ContaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ familiaProduto }) => {
      this.updateForm(familiaProduto);
    });
    this.contaService
      .query()
      .subscribe((res: HttpResponse<IConta[]>) => (this.contas = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.familiaProdutoService
      .query()
      .subscribe(
        (res: HttpResponse<IFamiliaProduto[]>) => (this.familiaprodutos = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(familiaProduto: IFamiliaProduto) {
    this.editForm.patchValue({
      id: familiaProduto.id,
      nome: familiaProduto.nome,
      descricao: familiaProduto.descricao,
      contaId: familiaProduto.contaId,
      hierarquiaId: familiaProduto.hierarquiaId
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
    const familiaProduto = this.createFromForm();
    if (familiaProduto.id !== undefined) {
      this.subscribeToSaveResponse(this.familiaProdutoService.update(familiaProduto));
    } else {
      this.subscribeToSaveResponse(this.familiaProdutoService.create(familiaProduto));
    }
  }

  private createFromForm(): IFamiliaProduto {
    return {
      ...new FamiliaProduto(),
      id: this.editForm.get(['id']).value,
      nome: this.editForm.get(['nome']).value,
      descricao: this.editForm.get(['descricao']).value,
      contaId: this.editForm.get(['contaId']).value,
      hierarquiaId: this.editForm.get(['hierarquiaId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFamiliaProduto>>) {
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

  trackContaById(index: number, item: IConta) {
    return item.id;
  }

  trackFamiliaProdutoById(index: number, item: IFamiliaProduto) {
    return item.id;
  }
}
