import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRegraMovimentacaoCredito } from 'app/shared/model/regra-movimentacao-credito.model';
import { RegraMovimentacaoCreditoService } from './regra-movimentacao-credito.service';

@Component({
  templateUrl: './regra-movimentacao-credito-delete-dialog.component.html'
})
export class RegraMovimentacaoCreditoDeleteDialogComponent {
  regraMovimentacaoCredito: IRegraMovimentacaoCredito;

  constructor(
    protected regraMovimentacaoCreditoService: RegraMovimentacaoCreditoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.regraMovimentacaoCreditoService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'regraMovimentacaoCreditoListModification',
        content: 'Deleted an regraMovimentacaoCredito'
      });
      this.activeModal.dismiss(true);
    });
  }
}
