import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILocalArmazenamento } from 'app/shared/model/local-armazenamento.model';
import { LocalArmazenamentoService } from './local-armazenamento.service';

@Component({
  templateUrl: './local-armazenamento-delete-dialog.component.html'
})
export class LocalArmazenamentoDeleteDialogComponent {
  localArmazenamento: ILocalArmazenamento;

  constructor(
    protected localArmazenamentoService: LocalArmazenamentoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.localArmazenamentoService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'localArmazenamentoListModification',
        content: 'Deleted an localArmazenamento'
      });
      this.activeModal.dismiss(true);
    });
  }
}
