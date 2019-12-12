import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICompra } from 'app/shared/model/compra.model';
import { CompraService } from './compra.service';

@Component({
  templateUrl: './compra-delete-dialog.component.html'
})
export class CompraDeleteDialogComponent {
  compra: ICompra;

  constructor(protected compraService: CompraService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.compraService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'compraListModification',
        content: 'Deleted an compra'
      });
      this.activeModal.dismiss(true);
    });
  }
}
