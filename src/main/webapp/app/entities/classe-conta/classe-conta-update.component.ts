import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IClasseConta, ClasseConta } from 'app/shared/model/classe-conta.model';
import { ClasseContaService } from './classe-conta.service';

@Component({
  selector: 'rv-classe-conta-update',
  templateUrl: './classe-conta-update.component.html'
})
export class ClasseContaUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    descricao: [null, [Validators.required]],
    codigo: [null, [Validators.required]]
  });

  constructor(protected classeContaService: ClasseContaService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ classeConta }) => {
      this.updateForm(classeConta);
    });
  }

  updateForm(classeConta: IClasseConta) {
    this.editForm.patchValue({
      id: classeConta.id,
      descricao: classeConta.descricao,
      codigo: classeConta.codigo
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const classeConta = this.createFromForm();
    if (classeConta.id !== undefined) {
      this.subscribeToSaveResponse(this.classeContaService.update(classeConta));
    } else {
      this.subscribeToSaveResponse(this.classeContaService.create(classeConta));
    }
  }

  private createFromForm(): IClasseConta {
    return {
      ...new ClasseConta(),
      id: this.editForm.get(['id']).value,
      descricao: this.editForm.get(['descricao']).value,
      codigo: this.editForm.get(['codigo']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IClasseConta>>) {
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
