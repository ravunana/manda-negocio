import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IAuditoriaCompra, AuditoriaCompra } from 'app/shared/model/auditoria-compra.model';
import { AuditoriaCompraService } from './auditoria-compra.service';
import { ICompra } from 'app/shared/model/compra.model';
import { CompraService } from 'app/entities/compra/compra.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

@Component({
  selector: 'rv-auditoria-compra-update',
  templateUrl: './auditoria-compra-update.component.html'
})
export class AuditoriaCompraUpdateComponent implements OnInit {
  isSaving: boolean;

  compras: ICompra[];

  users: IUser[];

  editForm = this.fb.group({
    id: [],
    estado: [],
    data: [],
    motivoAlteracaoDocumento: [],
    origemDocumento: [],
    hash: [],
    hashControl: [],
    compraId: [],
    utilizadorId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected auditoriaCompraService: AuditoriaCompraService,
    protected compraService: CompraService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ auditoriaCompra }) => {
      this.updateForm(auditoriaCompra);
    });
    this.compraService.query({ filter: 'auditoriacompra-is-null' }).subscribe(
      (res: HttpResponse<ICompra[]>) => {
        if (!this.editForm.get('compraId').value) {
          this.compras = res.body;
        } else {
          this.compraService
            .find(this.editForm.get('compraId').value)
            .subscribe(
              (subRes: HttpResponse<ICompra>) => (this.compras = [subRes.body].concat(res.body)),
              (subRes: HttpErrorResponse) => this.onError(subRes.message)
            );
        }
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
    this.userService
      .query()
      .subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(auditoriaCompra: IAuditoriaCompra) {
    this.editForm.patchValue({
      id: auditoriaCompra.id,
      estado: auditoriaCompra.estado,
      data: auditoriaCompra.data != null ? auditoriaCompra.data.format(DATE_TIME_FORMAT) : null,
      motivoAlteracaoDocumento: auditoriaCompra.motivoAlteracaoDocumento,
      origemDocumento: auditoriaCompra.origemDocumento,
      hash: auditoriaCompra.hash,
      hashControl: auditoriaCompra.hashControl,
      compraId: auditoriaCompra.compraId,
      utilizadorId: auditoriaCompra.utilizadorId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const auditoriaCompra = this.createFromForm();
    if (auditoriaCompra.id !== undefined) {
      this.subscribeToSaveResponse(this.auditoriaCompraService.update(auditoriaCompra));
    } else {
      this.subscribeToSaveResponse(this.auditoriaCompraService.create(auditoriaCompra));
    }
  }

  private createFromForm(): IAuditoriaCompra {
    return {
      ...new AuditoriaCompra(),
      id: this.editForm.get(['id']).value,
      estado: this.editForm.get(['estado']).value,
      data: this.editForm.get(['data']).value != null ? moment(this.editForm.get(['data']).value, DATE_TIME_FORMAT) : undefined,
      motivoAlteracaoDocumento: this.editForm.get(['motivoAlteracaoDocumento']).value,
      origemDocumento: this.editForm.get(['origemDocumento']).value,
      hash: this.editForm.get(['hash']).value,
      hashControl: this.editForm.get(['hashControl']).value,
      compraId: this.editForm.get(['compraId']).value,
      utilizadorId: this.editForm.get(['utilizadorId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAuditoriaCompra>>) {
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

  trackCompraById(index: number, item: ICompra) {
    return item.id;
  }

  trackUserById(index: number, item: IUser) {
    return item.id;
  }
}
