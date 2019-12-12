import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRetencaoFonte } from 'app/shared/model/retencao-fonte.model';
import { RetencaoFonteService } from './retencao-fonte.service';

@Component({
  templateUrl: './retencao-fonte-delete-dialog.component.html'
})
export class RetencaoFonteDeleteDialogComponent {
  retencaoFonte: IRetencaoFonte;

  constructor(
    protected retencaoFonteService: RetencaoFonteService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.retencaoFonteService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'retencaoFonteListModification',
        content: 'Deleted an retencaoFonte'
      });
      this.activeModal.dismiss(true);
    });
  }
}
