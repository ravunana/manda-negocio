import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAuditoriaVenda } from 'app/shared/model/auditoria-venda.model';
import { AuditoriaVendaService } from './auditoria-venda.service';

@Component({
  templateUrl: './auditoria-venda-delete-dialog.component.html'
})
export class AuditoriaVendaDeleteDialogComponent {
  auditoriaVenda: IAuditoriaVenda;

  constructor(
    protected auditoriaVendaService: AuditoriaVendaService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.auditoriaVendaService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'auditoriaVendaListModification',
        content: 'Deleted an auditoriaVenda'
      });
      this.activeModal.dismiss(true);
    });
  }
}
