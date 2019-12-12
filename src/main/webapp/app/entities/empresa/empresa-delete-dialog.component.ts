import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEmpresa } from 'app/shared/model/empresa.model';
import { EmpresaService } from './empresa.service';

@Component({
  templateUrl: './empresa-delete-dialog.component.html'
})
export class EmpresaDeleteDialogComponent {
  empresa: IEmpresa;

  constructor(protected empresaService: EmpresaService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.empresaService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'empresaListModification',
        content: 'Deleted an empresa'
      });
      this.activeModal.dismiss(true);
    });
  }
}
