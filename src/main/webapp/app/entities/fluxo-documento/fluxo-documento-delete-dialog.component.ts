import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFluxoDocumento } from 'app/shared/model/fluxo-documento.model';
import { FluxoDocumentoService } from './fluxo-documento.service';

@Component({
  templateUrl: './fluxo-documento-delete-dialog.component.html'
})
export class FluxoDocumentoDeleteDialogComponent {
  fluxoDocumento: IFluxoDocumento;

  constructor(
    protected fluxoDocumentoService: FluxoDocumentoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.fluxoDocumentoService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'fluxoDocumentoListModification',
        content: 'Deleted an fluxoDocumento'
      });
      this.activeModal.dismiss(true);
    });
  }
}
