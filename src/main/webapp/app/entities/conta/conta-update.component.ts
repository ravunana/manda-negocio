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
import { isNull, isUndefined } from 'util';

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
  private codigoClasseSelecionada;
  private idClasseContaSelecionada;

  editForm = this.fb.group({
    id: [],
    descricao: [null, [Validators.required]],
    codigo: [null, [Validators.required]],
    tipo: [],
    grau: [],
    natureza: [],
    grupo: [],
    conteudo: [],
    empresas: [],
    contaAgregadoraId: [],
    classeContaId: [null, Validators.required]
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

    this.onEditFormChanche();
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

  onEditFormChanche() {
    let codigoProximaConta;
    this.editForm.valueChanges.subscribe((value: IConta) => {
      if (isNull(value.contaAgregadoraId) || isUndefined(value.contaAgregadoraId)) {
        console.log('conta agregador não existe', this.existeContaAgregadora);
        this.existeContaAgregadora = false;

        let classeContaId = value.classeContaId;

        this.classeContaService.query().subscribe(data => {
          this.codigoClasseSelecionada = data.body.filter(c => c.id === classeContaId).shift().codigo;
          this.idClasseContaSelecionada = data.body.filter(c => c.id === classeContaId).shift().id;
          console.log('Classe da conta ' + classeContaId + ' Codigo da conta ' + this.codigoClasseSelecionada);

          this.editForm.patchValue(value, { emitEvent: false });
          let grupo;
          let natureza;

          switch (this.codigoClasseSelecionada) {
            case '1':
              console.log(' grupo Activo nao corrente');
              grupo = this.gruposConta.filter(n => n.id === 3015).shift().nome;
              natureza = this.naturezasConta.filter(n => n.id === 3012).shift().nome;
              this.editForm.get('grupo').patchValue(grupo, { emitEvent: false });
              this.editForm.get('natureza').patchValue(natureza, { emitEvent: false });
              console.log('Devedora');
              break;
            case '2':
              console.log('Activo corrente');
              console.log('Devedora');
              break;
            case '3':
              console.log('indefinido');
              console.log('indefinido');
              break;
            case '4':
              console.log('Activo corrente');
              console.log('Devedora');
              break;
            case '5':
              console.log('indefinido');
              console.log('indefinido');
              break;
            case '6':
              console.log('Activo nao corrente');
              console.log('credora');
              break;
            case '7':
              console.log('Passivo nao corrente');
              console.log('Devedora');
              break;
            case '8':
              console.log('indefinido');
              console.log('indefinido');
              break;
          }

          console.log('Codigo da proxima conta ' + codigoProximaConta);
          let grau = this.niveisConta.filter(n => n.nome === '2').shift().nome;
          this.editForm.get('grau').patchValue(grau, { emitEvent: false });
          codigoProximaConta = this.codigoClasseSelecionada + 1;
          this.editForm.get('codigo').patchValue(codigoProximaConta, { emitEvent: false });

          console.log('ID conta selecionada ' + this.idClasseContaSelecionada);

          this.contaService.query({ 'classeContaId.equals': this.idClasseContaSelecionada }).subscribe(data => {
            let ultimoCodigoContaClasseSelecionada = data.body.pop().codigo;
            console.log('Ultimo codigo da conta classe selecionada ' + ultimoCodigoContaClasseSelecionada);
            if (!isUndefined(ultimoCodigoContaClasseSelecionada)) {
              codigoProximaConta = Number(ultimoCodigoContaClasseSelecionada) + 1;
              console.log(Number(ultimoCodigoContaClasseSelecionada) + 1);
              this.editForm.get('codigo').patchValue(codigoProximaConta, { emitEvent: false });
            } else {
              // nao existe ainda nehuma subclass ou conta no grau 2
            }
          });
        });
      } else {
        let proximoCondigoConta;

        console.log('Existe conta agregadora', this.existeContaAgregadora);
        this.existeContaAgregadora = true;
        let contaAgregadoraId = this.editForm.get('contaAgregadoraId').value;
        const conta = this.contas.filter(c => c.id === contaAgregadoraId).shift();

        // const ultimaContagrau = this.contas.filter( c => c.contaAgregadoraId === contaAgregadoraId ).shift();
        // console.log( 'Ultima conta do grau ' + ultimaContagrau );

        this.contaService.query({ 'contaAgregadoraId.equals': contaAgregadoraId }).subscribe(data => {
          const grauAtual = Number(conta.grau);
          const proximograu = Number(conta.grau) + 1;

          const ultimaConta = data.body.pop();

          let grau = this.niveisConta.filter(n => n.nome === proximograu.toString()).shift().nome;
          this.editForm.get('grau').patchValue(grau, { emitEvent: false });

          if (data.body.length === 0) {
            proximoCondigoConta = conta.codigo + '.' + 1;
            console.log(proximoCondigoConta);
          } else {
            console.log(data.body);
            const ultimo = data.body.pop().codigo.substr(3);
            console.log(ultimo);

            if (proximograu === 3) {
              proximoCondigoConta = conta.codigo + '.' + (Number(ultimo) + 1);
            } else if (proximograu === 4) {
              proximoCondigoConta = ultimaConta.codigo + '.' + (Number(ultimo) + 1);
            }
          }

          this.editForm.get('classeContaId').patchValue(conta.classeContaId, { emitEvent: false });
          this.editForm.get('codigo').patchValue(proximoCondigoConta, { emitEvent: false });
        });
      }
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

  // private getCodigoClasseById( id: number ) {
  //   this.classeContaService.query().subscribe( data => {
  //     this.codigoClasseSelecionada = data.body.filter( c => c.id === id ).shift().codigo;
  //     console.log( this.codigoClasseSelecionada );
  //   })
  //   console.log( this.codigoClasseSelecionada );
  //   return this.codigoClasseSelecionada;
  // }
}
