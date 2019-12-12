import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDocumentoComercial } from 'app/shared/model/documento-comercial.model';
import { DocumentoComercialService } from './documento-comercial.service';

@Component({
  templateUrl: './documento-comercial-delete-dialog.component.html'
})
export class DocumentoComercialDeleteDialogComponent {
  documentoComercial: IDocumentoComercial;

  constructor(
    protected documentoComercialService: DocumentoComercialService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.documentoComercialService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'documentoComercialListModification',
        content: 'Deleted an documentoComercial'
      });
      this.activeModal.dismiss(true);
    });
  }
}
