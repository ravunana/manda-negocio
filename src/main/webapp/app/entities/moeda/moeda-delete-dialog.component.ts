import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMoeda } from 'app/shared/model/moeda.model';
import { MoedaService } from './moeda.service';

@Component({
  templateUrl: './moeda-delete-dialog.component.html'
})
export class MoedaDeleteDialogComponent {
  moeda: IMoeda;

  constructor(protected moedaService: MoedaService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.moedaService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'moedaListModification',
        content: 'Deleted an moeda'
      });
      this.activeModal.dismiss(true);
    });
  }
}
