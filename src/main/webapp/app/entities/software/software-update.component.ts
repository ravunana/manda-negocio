import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { ISoftware, Software } from 'app/shared/model/software.model';
import { SoftwareService } from './software.service';

@Component({
  selector: 'rv-software-update',
  templateUrl: './software-update.component.html'
})
export class SoftwareUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    empresa: [null, [Validators.required]],
    tipoSistema: [null, [Validators.required]],
    nif: [null, [Validators.required]],
    numeroValidacaoAGT: [null, [Validators.required]],
    nome: [null, [Validators.required, Validators.maxLength(255)]],
    versao: [null, [Validators.required, Validators.maxLength(30)]],
    hashCode: [null, [Validators.required]],
    hashControl: [null, [Validators.required]]
  });

  constructor(protected softwareService: SoftwareService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ software }) => {
      this.updateForm(software);
    });
  }

  updateForm(software: ISoftware) {
    this.editForm.patchValue({
      id: software.id,
      empresa: software.empresa,
      tipoSistema: software.tipoSistema,
      nif: software.nif,
      numeroValidacaoAGT: software.numeroValidacaoAGT,
      nome: software.nome,
      versao: software.versao,
      hashCode: software.hashCode,
      hashControl: software.hashControl
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const software = this.createFromForm();
    if (software.id !== undefined) {
      this.subscribeToSaveResponse(this.softwareService.update(software));
    } else {
      this.subscribeToSaveResponse(this.softwareService.create(software));
    }
  }

  private createFromForm(): ISoftware {
    return {
      ...new Software(),
      id: this.editForm.get(['id']).value,
      empresa: this.editForm.get(['empresa']).value,
      tipoSistema: this.editForm.get(['tipoSistema']).value,
      nif: this.editForm.get(['nif']).value,
      numeroValidacaoAGT: this.editForm.get(['numeroValidacaoAGT']).value,
      nome: this.editForm.get(['nome']).value,
      versao: this.editForm.get(['versao']).value,
      hashCode: this.editForm.get(['hashCode']).value,
      hashControl: this.editForm.get(['hashControl']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISoftware>>) {
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
