import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { IMoradaPessoa, MoradaPessoa } from 'app/shared/model/morada-pessoa.model';
import { MoradaPessoaService } from './morada-pessoa.service';
import { IPessoa } from 'app/shared/model/pessoa.model';
import { PessoaService } from 'app/entities/pessoa/pessoa.service';

@Component({
  selector: 'rv-morada-pessoa-update',
  templateUrl: './morada-pessoa-update.component.html'
})
export class MoradaPessoaUpdateComponent implements OnInit {
  isSaving: boolean;

  pessoas: IPessoa[];

  editForm = this.fb.group({
    id: [],
    pais: [],
    provincia: [],
    municipio: [],
    bairro: [null, [Validators.required]],
    rua: [null, [Validators.required, Validators.maxLength(200)]],
    quarteirao: [null, [Validators.maxLength(10)]],
    numeroPorta: [null, [Validators.maxLength(10)]],
    pessoaId: [null, Validators.required]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected moradaPessoaService: MoradaPessoaService,
    protected pessoaService: PessoaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ moradaPessoa }) => {
      this.updateForm(moradaPessoa);
    });
    this.pessoaService
      .query()
      .subscribe((res: HttpResponse<IPessoa[]>) => (this.pessoas = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(moradaPessoa: IMoradaPessoa) {
    this.editForm.patchValue({
      id: moradaPessoa.id,
      pais: moradaPessoa.pais,
      provincia: moradaPessoa.provincia,
      municipio: moradaPessoa.municipio,
      bairro: moradaPessoa.bairro,
      rua: moradaPessoa.rua,
      quarteirao: moradaPessoa.quarteirao,
      numeroPorta: moradaPessoa.numeroPorta,
      pessoaId: moradaPessoa.pessoaId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const moradaPessoa = this.createFromForm();
    if (moradaPessoa.id !== undefined) {
      this.subscribeToSaveResponse(this.moradaPessoaService.update(moradaPessoa));
    } else {
      this.subscribeToSaveResponse(this.moradaPessoaService.create(moradaPessoa));
    }
  }

  private createFromForm(): IMoradaPessoa {
    return {
      ...new MoradaPessoa(),
      id: this.editForm.get(['id']).value,
      pais: this.editForm.get(['pais']).value,
      provincia: this.editForm.get(['provincia']).value,
      municipio: this.editForm.get(['municipio']).value,
      bairro: this.editForm.get(['bairro']).value,
      rua: this.editForm.get(['rua']).value,
      quarteirao: this.editForm.get(['quarteirao']).value,
      numeroPorta: this.editForm.get(['numeroPorta']).value,
      pessoaId: this.editForm.get(['pessoaId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMoradaPessoa>>) {
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

  trackPessoaById(index: number, item: IPessoa) {
    return item.id;
  }
}
