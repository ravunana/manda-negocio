import { Component, OnInit, ElementRef } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IEmpresa, Empresa } from 'app/shared/model/empresa.model';
import { EmpresaService } from './empresa.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { IConta } from 'app/shared/model/conta.model';
import { ContaService } from 'app/entities/conta/conta.service';
import { ICoordenadaBancaria } from 'app/shared/model/coordenada-bancaria.model';
import { CoordenadaBancariaService } from 'app/entities/coordenada-bancaria/coordenada-bancaria.service';

@Component({
  selector: 'rv-empresa-update',
  templateUrl: './empresa-update.component.html'
})
export class EmpresaUpdateComponent implements OnInit {
  isSaving: boolean;

  users: IUser[];

  contas: IConta[];

  empresas: IEmpresa[];

  coordenadabancarias: ICoordenadaBancaria[];
  fundacaoDp: any;
  aberturaExercioDp: any;

  editForm = this.fb.group({
    id: [],
    tipoSociedade: [],
    nome: [null, [Validators.required]],
    logotipo: [],
    logotipoContentType: [],
    capitalSocial: [null, [Validators.min(0)]],
    fundacao: [],
    nif: [null, [Validators.required, Validators.maxLength(20)]],
    numeroRegistroComercial: [null, [Validators.required]],
    despesaFixa: [null, [Validators.required, Validators.min(0), Validators.max(100)]],
    despesaVariavel: [null, [Validators.required, Validators.min(0), Validators.max(100)]],
    aberturaExercio: [],
    designacaoComercial: [],
    sede: [],
    utilizadorId: [],
    contaId: [],
    hierarquiaId: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected empresaService: EmpresaService,
    protected userService: UserService,
    protected contaService: ContaService,
    protected coordenadaBancariaService: CoordenadaBancariaService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ empresa }) => {
      this.updateForm(empresa);
    });
    this.userService
      .query()
      .subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.contaService
      .query()
      .subscribe((res: HttpResponse<IConta[]>) => (this.contas = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.empresaService
      .query()
      .subscribe((res: HttpResponse<IEmpresa[]>) => (this.empresas = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.coordenadaBancariaService
      .query()
      .subscribe(
        (res: HttpResponse<ICoordenadaBancaria[]>) => (this.coordenadabancarias = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(empresa: IEmpresa) {
    this.editForm.patchValue({
      id: empresa.id,
      tipoSociedade: empresa.tipoSociedade,
      nome: empresa.nome,
      logotipo: empresa.logotipo,
      logotipoContentType: empresa.logotipoContentType,
      capitalSocial: empresa.capitalSocial,
      fundacao: empresa.fundacao,
      nif: empresa.nif,
      numeroRegistroComercial: empresa.numeroRegistroComercial,
      despesaFixa: empresa.despesaFixa,
      despesaVariavel: empresa.despesaVariavel,
      aberturaExercio: empresa.aberturaExercio,
      designacaoComercial: empresa.designacaoComercial,
      sede: empresa.sede,
      utilizadorId: empresa.utilizadorId,
      contaId: empresa.contaId,
      hierarquiaId: empresa.hierarquiaId
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

  clearInputImage(field: string, fieldContentType: string, idInput: string) {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null
    });
    if (this.elementRef && idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const empresa = this.createFromForm();
    if (empresa.id !== undefined) {
      this.subscribeToSaveResponse(this.empresaService.update(empresa));
    } else {
      this.subscribeToSaveResponse(this.empresaService.create(empresa));
    }
  }

  private createFromForm(): IEmpresa {
    return {
      ...new Empresa(),
      id: this.editForm.get(['id']).value,
      tipoSociedade: this.editForm.get(['tipoSociedade']).value,
      nome: this.editForm.get(['nome']).value,
      logotipoContentType: this.editForm.get(['logotipoContentType']).value,
      logotipo: this.editForm.get(['logotipo']).value,
      capitalSocial: this.editForm.get(['capitalSocial']).value,
      fundacao: this.editForm.get(['fundacao']).value,
      nif: this.editForm.get(['nif']).value,
      numeroRegistroComercial: this.editForm.get(['numeroRegistroComercial']).value,
      despesaFixa: this.editForm.get(['despesaFixa']).value,
      despesaVariavel: this.editForm.get(['despesaVariavel']).value,
      aberturaExercio: this.editForm.get(['aberturaExercio']).value,
      designacaoComercial: this.editForm.get(['designacaoComercial']).value,
      sede: this.editForm.get(['sede']).value,
      utilizadorId: this.editForm.get(['utilizadorId']).value,
      contaId: this.editForm.get(['contaId']).value,
      hierarquiaId: this.editForm.get(['hierarquiaId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEmpresa>>) {
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

  trackContaById(index: number, item: IConta) {
    return item.id;
  }

  trackEmpresaById(index: number, item: IEmpresa) {
    return item.id;
  }

  trackCoordenadaBancariaById(index: number, item: ICoordenadaBancaria) {
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
}
