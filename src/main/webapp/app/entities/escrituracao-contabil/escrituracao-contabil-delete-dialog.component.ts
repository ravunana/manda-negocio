import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEscrituracaoContabil } from 'app/shared/model/escrituracao-contabil.model';
import { EscrituracaoContabilService } from './escrituracao-contabil.service';

@Component({
  templateUrl: './escrituracao-contabil-delete-dialog.component.html'
})
export class EscrituracaoContabilDeleteDialogComponent {
  escrituracaoContabil: IEscrituracaoContabil;

  constructor(
    protected escrituracaoContabilService: EscrituracaoContabilService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.escrituracaoContabilService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'escrituracaoContabilListModification',
        content: 'Deleted an escrituracaoContabil'
      });
      this.activeModal.dismiss(true);
    });
  }
}
