import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IMoeda, Moeda } from 'app/shared/model/moeda.model';
import { MoedaService } from './moeda.service';

@Component({
  selector: 'rv-moeda-update',
  templateUrl: './moeda-update.component.html'
})
export class MoedaUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required]],
    codigo: [null, [Validators.required, Validators.minLength(2), Validators.maxLength(10)]],
    pais: [],
    nacional: [],
    icon: []
  });

  constructor(protected moedaService: MoedaService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ moeda }) => {
      this.updateForm(moeda);
    });
  }

  updateForm(moeda: IMoeda) {
    this.editForm.patchValue({
      id: moeda.id,
      nome: moeda.nome,
      codigo: moeda.codigo,
      pais: moeda.pais,
      nacional: moeda.nacional,
      icon: moeda.icon
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const moeda = this.createFromForm();
    if (moeda.id !== undefined) {
      this.subscribeToSaveResponse(this.moedaService.update(moeda));
    } else {
      this.subscribeToSaveResponse(this.moedaService.create(moeda));
    }
  }

  private createFromForm(): IMoeda {
    return {
      ...new Moeda(),
      id: this.editForm.get(['id']).value,
      nome: this.editForm.get(['nome']).value,
      codigo: this.editForm.get(['codigo']).value,
      pais: this.editForm.get(['pais']).value,
      nacional: this.editForm.get(['nacional']).value,
      icon: this.editForm.get(['icon']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMoeda>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
