import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IItemCompra } from 'app/shared/model/item-compra.model';
import { ItemCompraService } from './item-compra.service';

@Component({
  templateUrl: './item-compra-delete-dialog.component.html'
})
export class ItemCompraDeleteDialogComponent {
  itemCompra: IItemCompra;

  constructor(
    protected itemCompraService: ItemCompraService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.itemCompraService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'itemCompraListModification',
        content: 'Deleted an itemCompra'
      });
      this.activeModal.dismiss(true);
    });
  }
}
