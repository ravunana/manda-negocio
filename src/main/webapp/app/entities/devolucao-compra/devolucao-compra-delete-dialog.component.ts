import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDevolucaoCompra } from 'app/shared/model/devolucao-compra.model';
import { DevolucaoCompraService } from './devolucao-compra.service';

@Component({
  templateUrl: './devolucao-compra-delete-dialog.component.html'
})
export class DevolucaoCompraDeleteDialogComponent {
  devolucaoCompra: IDevolucaoCompra;

  constructor(
    protected devolucaoCompraService: DevolucaoCompraService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.devolucaoCompraService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'devolucaoCompraListModification',
        content: 'Deleted an devolucaoCompra'
      });
      this.activeModal.dismiss(true);
    });
  }
}
