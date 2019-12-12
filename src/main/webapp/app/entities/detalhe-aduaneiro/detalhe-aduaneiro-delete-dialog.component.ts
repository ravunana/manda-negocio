import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDetalheAduaneiro } from 'app/shared/model/detalhe-aduaneiro.model';
import { DetalheAduaneiroService } from './detalhe-aduaneiro.service';

@Component({
  templateUrl: './detalhe-aduaneiro-delete-dialog.component.html'
})
export class DetalheAduaneiroDeleteDialogComponent {
  detalheAduaneiro: IDetalheAduaneiro;

  constructor(
    protected detalheAduaneiroService: DetalheAduaneiroService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.detalheAduaneiroService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'detalheAduaneiroListModification',
        content: 'Deleted an detalheAduaneiro'
      });
      this.activeModal.dismiss(true);
    });
  }
}
