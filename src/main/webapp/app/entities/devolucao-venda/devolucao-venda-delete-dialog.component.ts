import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDevolucaoVenda } from 'app/shared/model/devolucao-venda.model';
import { DevolucaoVendaService } from './devolucao-venda.service';

@Component({
  templateUrl: './devolucao-venda-delete-dialog.component.html'
})
export class DevolucaoVendaDeleteDialogComponent {
  devolucaoVenda: IDevolucaoVenda;

  constructor(
    protected devolucaoVendaService: DevolucaoVendaService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.devolucaoVendaService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'devolucaoVendaListModification',
        content: 'Deleted an devolucaoVenda'
      });
      this.activeModal.dismiss(true);
    });
  }
}
