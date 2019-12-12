import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { ILookupItem, LookupItem } from 'app/shared/model/lookup-item.model';
import { LookupItemService } from './lookup-item.service';
import { ILookup } from 'app/shared/model/lookup.model';
import { LookupService } from 'app/entities/lookup/lookup.service';

@Component({
  selector: 'rv-lookup-item-update',
  templateUrl: './lookup-item-update.component.html'
})
export class LookupItemUpdateComponent implements OnInit {
  isSaving: boolean;

  lookups: ILookup[];

  editForm = this.fb.group({
    id: [],
    valor: [],
    nome: [],
    type: [],
    ordem: [],
    lookupId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected lookupItemService: LookupItemService,
    protected lookupService: LookupService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ lookupItem }) => {
      this.updateForm(lookupItem);
    });
    this.lookupService
      .query()
      .subscribe((res: HttpResponse<ILookup[]>) => (this.lookups = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(lookupItem: ILookupItem) {
    this.editForm.patchValue({
      id: lookupItem.id,
      valor: lookupItem.valor,
      nome: lookupItem.nome,
      type: lookupItem.type,
      ordem: lookupItem.ordem,
      lookupId: lookupItem.lookupId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const lookupItem = this.createFromForm();
    if (lookupItem.id !== undefined) {
      this.subscribeToSaveResponse(this.lookupItemService.update(lookupItem));
    } else {
      this.subscribeToSaveResponse(this.lookupItemService.create(lookupItem));
    }
  }

  private createFromForm(): ILookupItem {
    return {
      ...new LookupItem(),
      id: this.editForm.get(['id']).value,
      valor: this.editForm.get(['valor']).value,
      nome: this.editForm.get(['nome']).value,
      type: this.editForm.get(['type']).value,
      ordem: this.editForm.get(['ordem']).value,
      lookupId: this.editForm.get(['lookupId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILookupItem>>) {
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

  trackLookupById(index: number, item: ILookup) {
    return item.id;
  }
}
