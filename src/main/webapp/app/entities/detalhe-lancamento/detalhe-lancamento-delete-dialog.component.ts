import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDetalheLancamento } from 'app/shared/model/detalhe-lancamento.model';
import { DetalheLancamentoService } from './detalhe-lancamento.service';

@Component({
  templateUrl: './detalhe-lancamento-delete-dialog.component.html'
})
export class DetalheLancamentoDeleteDialogComponent {
  detalheLancamento: IDetalheLancamento;

  constructor(
    protected detalheLancamentoService: DetalheLancamentoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.detalheLancamentoService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'detalheLancamentoListModification',
        content: 'Deleted an detalheLancamento'
      });
      this.activeModal.dismiss(true);
    });
  }
}
