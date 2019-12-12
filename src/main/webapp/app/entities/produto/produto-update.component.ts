import { Component, OnInit, ElementRef } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IProduto, Produto } from 'app/shared/model/produto.model';
import { ProdutoService } from './produto.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { IImposto } from 'app/shared/model/imposto.model';
import { ImpostoService } from 'app/entities/imposto/imposto.service';
import { IFornecedor } from 'app/shared/model/fornecedor.model';
import { FornecedorService } from 'app/entities/fornecedor/fornecedor.service';
import { ILocalArmazenamento } from 'app/shared/model/local-armazenamento.model';
import { LocalArmazenamentoService } from 'app/entities/local-armazenamento/local-armazenamento.service';
import { IFamiliaProduto } from 'app/shared/model/familia-produto.model';
import { FamiliaProdutoService } from 'app/entities/familia-produto/familia-produto.service';
import { IEmpresa } from 'app/shared/model/empresa.model';
import { EmpresaService } from 'app/entities/empresa/empresa.service';
import { IFluxoDocumento } from 'app/shared/model/fluxo-documento.model';
import { FluxoDocumentoService } from 'app/entities/fluxo-documento/fluxo-documento.service';

@Component({
  selector: 'rv-produto-update',
  templateUrl: './produto-update.component.html'
})
export class ProdutoUpdateComponent implements OnInit {
  isSaving: boolean;

  users: IUser[];

  impostos: IImposto[];

  fornecedors: IFornecedor[];

  localarmazenamentos: ILocalArmazenamento[];

  familiaprodutos: IFamiliaProduto[];

  empresas: IEmpresa[];

  fluxodocumentos: IFluxoDocumento[];

  editForm = this.fb.group({
    id: [],
    tipo: [],
    ean: [null, []],
    numero: [null, [Validators.required]],
    nome: [null, [Validators.required]],
    imagem: [],
    imagemContentType: [],
    composto: [null, [Validators.required]],
    estoqueMinimo: [null, [Validators.min(0)]],
    estoqueMaximo: [null, [Validators.min(0)]],
    estoqueAtual: [null, [Validators.required, Validators.min(0)]],
    descricao: [],
    mostrarPDV: [],
    prazoMedioEntrega: [],
    utilizadorId: [null, Validators.required],
    impostos: [],
    fornecedors: [],
    localArmazenamentoId: [],
    familiaId: [null, Validators.required],
    empresaId: [],
    estadoId: [null, Validators.required]
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected produtoService: ProdutoService,
    protected userService: UserService,
    protected impostoService: ImpostoService,
    protected fornecedorService: FornecedorService,
    protected localArmazenamentoService: LocalArmazenamentoService,
    protected familiaProdutoService: FamiliaProdutoService,
    protected empresaService: EmpresaService,
    protected fluxoDocumentoService: FluxoDocumentoService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ produto }) => {
      this.updateForm(produto);
    });
    this.userService
      .query()
      .subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.impostoService
      .query()
      .subscribe((res: HttpResponse<IImposto[]>) => (this.impostos = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.fornecedorService
      .query()
      .subscribe(
        (res: HttpResponse<IFornecedor[]>) => (this.fornecedors = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.localArmazenamentoService
      .query()
      .subscribe(
        (res: HttpResponse<ILocalArmazenamento[]>) => (this.localarmazenamentos = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.familiaProdutoService
      .query()
      .subscribe(
        (res: HttpResponse<IFamiliaProduto[]>) => (this.familiaprodutos = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.empresaService
      .query()
      .subscribe((res: HttpResponse<IEmpresa[]>) => (this.empresas = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.fluxoDocumentoService
      .query()
      .subscribe(
        (res: HttpResponse<IFluxoDocumento[]>) => (this.fluxodocumentos = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(produto: IProduto) {
    this.editForm.patchValue({
      id: produto.id,
      tipo: produto.tipo,
      ean: produto.ean,
      numero: produto.numero,
      nome: produto.nome,
      imagem: produto.imagem,
      imagemContentType: produto.imagemContentType,
      composto: produto.composto,
      estoqueMinimo: produto.estoqueMinimo,
      estoqueMaximo: produto.estoqueMaximo,
      estoqueAtual: produto.estoqueAtual,
      descricao: produto.descricao,
      mostrarPDV: produto.mostrarPDV,
      prazoMedioEntrega: produto.prazoMedioEntrega,
      utilizadorId: produto.utilizadorId,
      impostos: produto.impostos,
      fornecedors: produto.fornecedors,
      localArmazenamentoId: produto.localArmazenamentoId,
      familiaId: produto.familiaId,
      empresaId: produto.empresaId,
      estadoId: produto.estadoId
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
    const produto = this.createFromForm();
    if (produto.id !== undefined) {
      this.subscribeToSaveResponse(this.produtoService.update(produto));
    } else {
      this.subscribeToSaveResponse(this.produtoService.create(produto));
    }
  }

  private createFromForm(): IProduto {
    return {
      ...new Produto(),
      id: this.editForm.get(['id']).value,
      tipo: this.editForm.get(['tipo']).value,
      ean: this.editForm.get(['ean']).value,
      numero: this.editForm.get(['numero']).value,
      nome: this.editForm.get(['nome']).value,
      imagemContentType: this.editForm.get(['imagemContentType']).value,
      imagem: this.editForm.get(['imagem']).value,
      composto: this.editForm.get(['composto']).value,
      estoqueMinimo: this.editForm.get(['estoqueMinimo']).value,
      estoqueMaximo: this.editForm.get(['estoqueMaximo']).value,
      estoqueAtual: this.editForm.get(['estoqueAtual']).value,
      descricao: this.editForm.get(['descricao']).value,
      mostrarPDV: this.editForm.get(['mostrarPDV']).value,
      prazoMedioEntrega: this.editForm.get(['prazoMedioEntrega']).value,
      utilizadorId: this.editForm.get(['utilizadorId']).value,
      impostos: this.editForm.get(['impostos']).value,
      fornecedors: this.editForm.get(['fornecedors']).value,
      localArmazenamentoId: this.editForm.get(['localArmazenamentoId']).value,
      familiaId: this.editForm.get(['familiaId']).value,
      empresaId: this.editForm.get(['empresaId']).value,
      estadoId: this.editForm.get(['estadoId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProduto>>) {
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

  trackFornecedorById(index: number, item: IFornecedor) {
    return item.id;
  }

  trackLocalArmazenamentoById(index: number, item: ILocalArmazenamento) {
    return item.id;
  }

  trackFamiliaProdutoById(index: number, item: IFamiliaProduto) {
    return item.id;
  }

  trackEmpresaById(index: number, item: IEmpresa) {
    return item.id;
  }

  trackFluxoDocumentoById(index: number, item: IFluxoDocumento) {
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
