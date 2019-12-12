import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICliente } from 'app/shared/model/cliente.model';
import { ClienteService } from './cliente.service';

@Component({
  templateUrl: './cliente-delete-dialog.component.html'
})
export class ClienteDeleteDialogComponent {
  cliente: ICliente;

  constructor(protected clienteService: ClienteService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.clienteService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'clienteListModification',
        content: 'Deleted an cliente'
      });
      this.activeModal.dismiss(true);
    });
  }
}
