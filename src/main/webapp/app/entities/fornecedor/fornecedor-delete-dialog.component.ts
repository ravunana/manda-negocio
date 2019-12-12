import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFornecedor } from 'app/shared/model/fornecedor.model';
import { FornecedorService } from './fornecedor.service';

@Component({
  templateUrl: './fornecedor-delete-dialog.component.html'
})
export class FornecedorDeleteDialogComponent {
  fornecedor: IFornecedor;

  constructor(
    protected fornecedorService: FornecedorService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.fornecedorService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'fornecedorListModification',
        content: 'Deleted an fornecedor'
      });
      this.activeModal.dismiss(true);
    });
  }
}
