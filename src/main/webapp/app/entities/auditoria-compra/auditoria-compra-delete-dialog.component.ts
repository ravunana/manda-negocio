import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAuditoriaCompra } from 'app/shared/model/auditoria-compra.model';
import { AuditoriaCompraService } from './auditoria-compra.service';

@Component({
  templateUrl: './auditoria-compra-delete-dialog.component.html'
})
export class AuditoriaCompraDeleteDialogComponent {
  auditoriaCompra: IAuditoriaCompra;

  constructor(
    protected auditoriaCompraService: AuditoriaCompraService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.auditoriaCompraService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'auditoriaCompraListModification',
        content: 'Deleted an auditoriaCompra'
      });
      this.activeModal.dismiss(true);
    });
  }
}
