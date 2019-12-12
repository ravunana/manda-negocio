import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IContactoEmpresa } from 'app/shared/model/contacto-empresa.model';
import { ContactoEmpresaService } from './contacto-empresa.service';

@Component({
  templateUrl: './contacto-empresa-delete-dialog.component.html'
})
export class ContactoEmpresaDeleteDialogComponent {
  contactoEmpresa: IContactoEmpresa;

  constructor(
    protected contactoEmpresaService: ContactoEmpresaService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.contactoEmpresaService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'contactoEmpresaListModification',
        content: 'Deleted an contactoEmpresa'
      });
      this.activeModal.dismiss(true);
    });
  }
}
