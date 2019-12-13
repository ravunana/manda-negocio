import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { ICoordenadaBancaria, CoordenadaBancaria } from 'app/shared/model/coordenada-bancaria.model';
import { CoordenadaBancariaService } from './coordenada-bancaria.service';
import { IConta } from 'app/shared/model/conta.model';
import { ContaService } from 'app/entities/conta/conta.service';
import { IEmpresa } from 'app/shared/model/empresa.model';
import { EmpresaService } from 'app/entities/empresa/empresa.service';

@Component({
  selector: 'rv-coordenada-bancaria-update',
  templateUrl: './coordenada-bancaria-update.component.html'
})
export class CoordenadaBancariaUpdateComponent implements OnInit {
  isSaving: boolean;

  contas: IConta[];

  empresas: IEmpresa[];

  editForm = this.fb.group({
    id: [],
    descricao: [null, [Validators.required]],
    proprietario: [null, [Validators.required]],
    numeroConta: [null, [Validators.required]],
    iban: [null, []],
    ativo: [null, [Validators.required]],
    mostrarDocumento: [],
    mostrarPontoVenda: [],
    padraoRecebimento: [],
    padraoPagamento: [],
    contaId: [],
    empresas: [null, Validators.required]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected coordenadaBancariaService: CoordenadaBancariaService,
    protected contaService: ContaService,
    protected empresaService: EmpresaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ coordenadaBancaria }) => {
      this.updateForm(coordenadaBancaria);
    });
    this.contaService
      .query()
      .subscribe((res: HttpResponse<IConta[]>) => (this.contas = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.empresaService
      .query()
      .subscribe((res: HttpResponse<IEmpresa[]>) => (this.empresas = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(coordenadaBancaria: ICoordenadaBancaria) {
    this.editForm.patchValue({
      id: coordenadaBancaria.id,
      descricao: coordenadaBancaria.descricao,
      proprietario: coordenadaBancaria.proprietario,
      numeroConta: coordenadaBancaria.numeroConta,
      iban: coordenadaBancaria.iban,
      ativo: coordenadaBancaria.ativo,
      mostrarDocumento: coordenadaBancaria.mostrarDocumento,
      mostrarPontoVenda: coordenadaBancaria.mostrarPontoVenda,
      padraoRecebimento: coordenadaBancaria.padraoRecebimento,
      padraoPagamento: coordenadaBancaria.padraoPagamento,
      contaId: coordenadaBancaria.contaId,
      empresas: coordenadaBancaria.empresas
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const coordenadaBancaria = this.createFromForm();
    if (coordenadaBancaria.id !== undefined) {
      this.subscribeToSaveResponse(this.coordenadaBancariaService.update(coordenadaBancaria));
    } else {
      this.subscribeToSaveResponse(this.coordenadaBancariaService.create(coordenadaBancaria));
    }
  }

  private createFromForm(): ICoordenadaBancaria {
    return {
      ...new CoordenadaBancaria(),
      id: this.editForm.get(['id']).value,
      descricao: this.editForm.get(['descricao']).value,
      proprietario: this.editForm.get(['proprietario']).value,
      numeroConta: this.editForm.get(['numeroConta']).value,
      iban: this.editForm.get(['iban']).value,
      ativo: this.editForm.get(['ativo']).value,
      mostrarDocumento: this.editForm.get(['mostrarDocumento']).value,
      mostrarPontoVenda: this.editForm.get(['mostrarPontoVenda']).value,
      padraoRecebimento: this.editForm.get(['padraoRecebimento']).value,
      padraoPagamento: this.editForm.get(['padraoPagamento']).value,
      contaId: this.editForm.get(['contaId']).value,
      empresas: this.editForm.get(['empresas']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICoordenadaBancaria>>) {
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

  trackEmpresaById(index: number, item: IEmpresa) {
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
    this.editForm.get('contaId').patchValue(conta.id, { emitEvent: false });
  }

  searchConta(conta) {
    this.contaService.query({ 'descricao.contains': conta.query }).subscribe(data => {
      this.contas = data.body;
    });
  }
}
