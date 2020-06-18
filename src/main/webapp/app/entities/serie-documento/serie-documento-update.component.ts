import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { ISerieDocumento, SerieDocumento } from 'app/shared/model/serie-documento.model';
import { SerieDocumentoService } from './serie-documento.service';
import { IEmpresa } from 'app/shared/model/empresa.model';
import { EmpresaService } from 'app/entities/empresa/empresa.service';

@Component({
  selector: 'rv-serie-documento-update',
  templateUrl: './serie-documento-update.component.html'
})
export class SerieDocumentoUpdateComponent implements OnInit {
  isSaving: boolean;

  empresas: IEmpresa[];
  dataDp: any;

  editForm = this.fb.group({
    id: [],
    serie: [],
    codigoLancamentoFinanceiro: [null, [Validators.min(1)]],
    codigoEscrituracaoContabil: [null, [Validators.min(1)]],
    codigoVenda: [null, [Validators.min(1)]],
    codigoCompra: [null, [Validators.min(1)]],
    codigoCliente: [null, [Validators.min(1)]],
    codigoFornecedor: [null, [Validators.min(1)]],
    codigoDevolucaoVenda: [null, [Validators.min(1)]],
    codigoDevolucaoCompra: [null, [Validators.min(1)]],
    codigoProduto: [null, [Validators.min(1)]],
    data: [],
    empresaId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected serieDocumentoService: SerieDocumentoService,
    protected empresaService: EmpresaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ serieDocumento }) => {
      this.updateForm(serieDocumento);
    });
    this.empresaService.query({ filter: 'seriedocumento-is-null' }).subscribe(
      (res: HttpResponse<IEmpresa[]>) => {
        if (!this.editForm.get('empresaId').value) {
          this.empresas = res.body;
        } else {
          this.empresaService
            .find(this.editForm.get('empresaId').value)
            .subscribe(
              (subRes: HttpResponse<IEmpresa>) => (this.empresas = [subRes.body].concat(res.body)),
              (subRes: HttpErrorResponse) => this.onError(subRes.message)
            );
        }
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  updateForm(serieDocumento: ISerieDocumento) {
    this.editForm.patchValue({
      id: serieDocumento.id,
      serie: serieDocumento.serie,
      codigoLancamentoFinanceiro: serieDocumento.codigoLancamentoFinanceiro,
      codigoEscrituracaoContabil: serieDocumento.codigoEscrituracaoContabil,
      codigoVenda: serieDocumento.codigoVenda,
      codigoCompra: serieDocumento.codigoCompra,
      codigoCliente: serieDocumento.codigoCliente,
      codigoFornecedor: serieDocumento.codigoFornecedor,
      codigoDevolucaoVenda: serieDocumento.codigoDevolucaoVenda,
      codigoDevolucaoCompra: serieDocumento.codigoDevolucaoCompra,
      codigoProduto: serieDocumento.codigoProduto,
      data: serieDocumento.data,
      empresaId: serieDocumento.empresaId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const serieDocumento = this.createFromForm();
    if (serieDocumento.id !== undefined) {
      this.subscribeToSaveResponse(this.serieDocumentoService.update(serieDocumento));
    } else {
      this.subscribeToSaveResponse(this.serieDocumentoService.create(serieDocumento));
    }
  }

  private createFromForm(): ISerieDocumento {
    return {
      ...new SerieDocumento(),
      id: this.editForm.get(['id']).value,
      serie: this.editForm.get(['serie']).value,
      codigoLancamentoFinanceiro: this.editForm.get(['codigoLancamentoFinanceiro']).value,
      codigoEscrituracaoContabil: this.editForm.get(['codigoEscrituracaoContabil']).value,
      codigoVenda: this.editForm.get(['codigoVenda']).value,
      codigoCompra: this.editForm.get(['codigoCompra']).value,
      codigoCliente: this.editForm.get(['codigoCliente']).value,
      codigoFornecedor: this.editForm.get(['codigoFornecedor']).value,
      codigoDevolucaoVenda: this.editForm.get(['codigoDevolucaoVenda']).value,
      codigoDevolucaoCompra: this.editForm.get(['codigoDevolucaoCompra']).value,
      codigoProduto: this.editForm.get(['codigoProduto']).value,
      data: this.editForm.get(['data']).value,
      empresaId: this.editForm.get(['empresaId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISerieDocumento>>) {
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
}
