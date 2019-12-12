import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISerieDocumento } from 'app/shared/model/serie-documento.model';
import { SerieDocumentoService } from './serie-documento.service';

@Component({
  templateUrl: './serie-documento-delete-dialog.component.html'
})
export class SerieDocumentoDeleteDialogComponent {
  serieDocumento: ISerieDocumento;

  constructor(
    protected serieDocumentoService: SerieDocumentoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.serieDocumentoService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'serieDocumentoListModification',
        content: 'Deleted an serieDocumento'
      });
      this.activeModal.dismiss(true);
    });
  }
}
