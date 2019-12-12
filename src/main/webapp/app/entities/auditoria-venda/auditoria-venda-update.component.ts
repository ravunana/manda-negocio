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
import { IAuditoriaVenda, AuditoriaVenda } from 'app/shared/model/auditoria-venda.model';
import { AuditoriaVendaService } from './auditoria-venda.service';
import { IVenda } from 'app/shared/model/venda.model';
import { VendaService } from 'app/entities/venda/venda.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

@Component({
  selector: 'rv-auditoria-venda-update',
  templateUrl: './auditoria-venda-update.component.html'
})
export class AuditoriaVendaUpdateComponent implements OnInit {
  isSaving: boolean;

  vendas: IVenda[];

  users: IUser[];

  editForm = this.fb.group({
    id: [],
    estado: [],
    data: [],
    motivoAlteracaoDocumento: [],
    origemDocumento: [],
    hash: [],
    hashControl: [],
    vendaId: [null, Validators.required],
    utilizadorId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected auditoriaVendaService: AuditoriaVendaService,
    protected vendaService: VendaService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ auditoriaVenda }) => {
      this.updateForm(auditoriaVenda);
    });
    this.vendaService.query({ filter: 'auditoriavenda-is-null' }).subscribe(
      (res: HttpResponse<IVenda[]>) => {
        if (!this.editForm.get('vendaId').value) {
          this.vendas = res.body;
        } else {
          this.vendaService
            .find(this.editForm.get('vendaId').value)
            .subscribe(
              (subRes: HttpResponse<IVenda>) => (this.vendas = [subRes.body].concat(res.body)),
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

  updateForm(auditoriaVenda: IAuditoriaVenda) {
    this.editForm.patchValue({
      id: auditoriaVenda.id,
      estado: auditoriaVenda.estado,
      data: auditoriaVenda.data != null ? auditoriaVenda.data.format(DATE_TIME_FORMAT) : null,
      motivoAlteracaoDocumento: auditoriaVenda.motivoAlteracaoDocumento,
      origemDocumento: auditoriaVenda.origemDocumento,
      hash: auditoriaVenda.hash,
      hashControl: auditoriaVenda.hashControl,
      vendaId: auditoriaVenda.vendaId,
      utilizadorId: auditoriaVenda.utilizadorId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const auditoriaVenda = this.createFromForm();
    if (auditoriaVenda.id !== undefined) {
      this.subscribeToSaveResponse(this.auditoriaVendaService.update(auditoriaVenda));
    } else {
      this.subscribeToSaveResponse(this.auditoriaVendaService.create(auditoriaVenda));
    }
  }

  private createFromForm(): IAuditoriaVenda {
    return {
      ...new AuditoriaVenda(),
      id: this.editForm.get(['id']).value,
      estado: this.editForm.get(['estado']).value,
      data: this.editForm.get(['data']).value != null ? moment(this.editForm.get(['data']).value, DATE_TIME_FORMAT) : undefined,
      motivoAlteracaoDocumento: this.editForm.get(['motivoAlteracaoDocumento']).value,
      origemDocumento: this.editForm.get(['origemDocumento']).value,
      hash: this.editForm.get(['hash']).value,
      hashControl: this.editForm.get(['hashControl']).value,
      vendaId: this.editForm.get(['vendaId']).value,
      utilizadorId: this.editForm.get(['utilizadorId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAuditoriaVenda>>) {
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

  trackVendaById(index: number, item: IVenda) {
    return item.id;
  }

  trackUserById(index: number, item: IUser) {
    return item.id;
  }
}
