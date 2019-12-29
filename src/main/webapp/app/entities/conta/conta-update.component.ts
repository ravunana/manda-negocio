import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IConta, Conta } from 'app/shared/model/conta.model';
import { ContaService } from './conta.service';
import { IEmpresa } from 'app/shared/model/empresa.model';
import { EmpresaService } from 'app/entities/empresa/empresa.service';
import { IClasseConta } from 'app/shared/model/classe-conta.model';
import { ClasseContaService } from 'app/entities/classe-conta/classe-conta.service';
import { LookupItemService } from '../lookup-item/lookup-item.service';
import { ILookupItem } from 'app/shared/model/lookup-item.model';

@Component({
  selector: 'rv-conta-update',
  templateUrl: './conta-update.component.html'
})
export class ContaUpdateComponent implements OnInit {
  isSaving: boolean;

  empresas: IEmpresa[];
  existeContaAgregadora: boolean;

  contas: IConta[];
  tiposConta: ILookupItem[];
  niveisConta: ILookupItem[];
  naturezasConta: ILookupItem[];
  gruposConta: ILookupItem[];

  classecontas: IClasseConta[];

  editForm = this.fb.group({
    id: [],
    descricao: [null, [Validators.required]],
    codigo: [null],
    tipo: [],
    grau: [],
    natureza: [],
    grupo: [],
    conteudo: [],
    empresas: [],
    contaAgregadoraId: [],
    classeContaId: [null]
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected contaService: ContaService,
    protected empresaService: EmpresaService,
    protected classeContaService: ClasseContaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
    protected lookupItemService: LookupItemService
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ conta }) => {
      this.updateForm(conta);
    });
    this.empresaService
      .query()
      .subscribe((res: HttpResponse<IEmpresa[]>) => (this.empresas = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.contaService
      .query()
      .subscribe((res: HttpResponse<IConta[]>) => (this.contas = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.classeContaService
      .query()
      .subscribe(
        (res: HttpResponse<IClasseConta[]>) => (this.classecontas = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );

    this.lookupItemService.query({ 'lookupId.equals': '2951' }).subscribe(data => {
      this.tiposConta = data.body;
    });
    this.lookupItemService.query({ 'lookupId.equals': '2952' }).subscribe(data => {
      this.niveisConta = data.body;
    });
    this.lookupItemService.query({ 'lookupId.equals': '2953' }).subscribe(data => {
      this.naturezasConta = data.body;
    });
    this.lookupItemService.query({ 'lookupId.equals': '2954' }).subscribe(data => {
      this.gruposConta = data.body;
    });

    // this.onEditFormChanche();
  }

  updateForm(conta: IConta) {
    this.editForm.patchValue({
      id: conta.id,
      descricao: conta.descricao,
      codigo: conta.codigo,
      tipo: conta.tipo,
      grau: conta.grau,
      natureza: conta.natureza,
      grupo: conta.grupo,
      conteudo: conta.conteudo,
      empresas: conta.empresas,
      contaAgregadoraId: conta.contaAgregadoraId,
      classeContaId: conta.classeContaId
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
    const conta = this.createFromForm();
    if (conta.id !== undefined) {
      this.subscribeToSaveResponse(this.contaService.update(conta));
    } else {
      this.subscribeToSaveResponse(this.contaService.create(conta));
    }
  }

  private createFromForm(): IConta {
    return {
      ...new Conta(),
      id: this.editForm.get(['id']).value,
      descricao: this.editForm.get(['descricao']).value,
      codigo: this.editForm.get(['codigo']).value,
      tipo: this.editForm.get(['tipo']).value,
      grau: this.editForm.get(['grau']).value,
      natureza: this.editForm.get(['natureza']).value,
      grupo: this.editForm.get(['grupo']).value,
      conteudo: this.editForm.get(['conteudo']).value,
      // empresas: this.editForm.get(['empresas']).value,
      empresas: this.empresas,
      contaAgregadoraId: this.editForm.get(['contaAgregadoraId']).value,
      classeContaId: this.editForm.get(['classeContaId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IConta>>) {
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

  trackEmpresaById(index: number, item: IEmpresa) {
    return item.id;
  }

  trackContaById(index: number, item: IConta) {
    return item.id;
  }

  trackClasseContaById(index: number, item: IClasseConta) {
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
    this.editForm.get('contaAgregadoraId').patchValue(conta.id, { emitEvent: false });
  }

  searchConta(conta) {
    this.contaService.query({ 'descricao.contains': conta.query }).subscribe(data => {
      this.contas = data.body;
    });
  }
}
