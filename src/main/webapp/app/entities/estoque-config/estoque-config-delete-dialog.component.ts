import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEstoqueConfig } from 'app/shared/model/estoque-config.model';
import { EstoqueConfigService } from './estoque-config.service';

@Component({
  templateUrl: './estoque-config-delete-dialog.component.html'
})
export class EstoqueConfigDeleteDialogComponent {
  estoqueConfig: IEstoqueConfig;

  constructor(
    protected estoqueConfigService: EstoqueConfigService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.estoqueConfigService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'estoqueConfigListModification',
        content: 'Deleted an estoqueConfig'
      });
      this.activeModal.dismiss(true);
    });
  }
}
