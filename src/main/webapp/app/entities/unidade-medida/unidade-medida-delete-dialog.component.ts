import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IUnidadeMedida } from 'app/shared/model/unidade-medida.model';
import { UnidadeMedidaService } from './unidade-medida.service';

@Component({
  templateUrl: './unidade-medida-delete-dialog.component.html'
})
export class UnidadeMedidaDeleteDialogComponent {
  unidadeMedida: IUnidadeMedida;

  constructor(
    protected unidadeMedidaService: UnidadeMedidaService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.unidadeMedidaService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'unidadeMedidaListModification',
        content: 'Deleted an unidadeMedida'
      });
      this.activeModal.dismiss(true);
    });
  }
}
