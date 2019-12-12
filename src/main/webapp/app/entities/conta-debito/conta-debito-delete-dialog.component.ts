import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IContaDebito } from 'app/shared/model/conta-debito.model';
import { ContaDebitoService } from './conta-debito.service';

@Component({
  templateUrl: './conta-debito-delete-dialog.component.html'
})
export class ContaDebitoDeleteDialogComponent {
  contaDebito: IContaDebito;

  constructor(
    protected contaDebitoService: ContaDebitoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.contaDebitoService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'contaDebitoListModification',
        content: 'Deleted an contaDebito'
      });
      this.activeModal.dismiss(true);
    });
  }
}
