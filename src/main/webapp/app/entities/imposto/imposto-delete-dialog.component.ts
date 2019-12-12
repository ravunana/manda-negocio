import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IImposto } from 'app/shared/model/imposto.model';
import { ImpostoService } from './imposto.service';

@Component({
  templateUrl: './imposto-delete-dialog.component.html'
})
export class ImpostoDeleteDialogComponent {
  imposto: IImposto;

  constructor(protected impostoService: ImpostoService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.impostoService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'impostoListModification',
        content: 'Deleted an imposto'
      });
      this.activeModal.dismiss(true);
    });
  }
}
