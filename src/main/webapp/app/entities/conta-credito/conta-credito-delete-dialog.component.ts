import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IContaCredito } from 'app/shared/model/conta-credito.model';
import { ContaCreditoService } from './conta-credito.service';

@Component({
  templateUrl: './conta-credito-delete-dialog.component.html'
})
export class ContaCreditoDeleteDialogComponent {
  contaCredito: IContaCredito;

  constructor(
    protected contaCreditoService: ContaCreditoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.contaCreditoService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'contaCreditoListModification',
        content: 'Deleted an contaCredito'
      });
      this.activeModal.dismiss(true);
    });
  }
}
