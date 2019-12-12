import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IItemVenda } from 'app/shared/model/item-venda.model';
import { ItemVendaService } from './item-venda.service';

@Component({
  templateUrl: './item-venda-delete-dialog.component.html'
})
export class ItemVendaDeleteDialogComponent {
  itemVenda: IItemVenda;

  constructor(protected itemVendaService: ItemVendaService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.itemVendaService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'itemVendaListModification',
        content: 'Deleted an itemVenda'
      });
      this.activeModal.dismiss(true);
    });
  }
}
