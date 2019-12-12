import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRegraMovimentacaoDebito } from 'app/shared/model/regra-movimentacao-debito.model';
import { RegraMovimentacaoDebitoService } from './regra-movimentacao-debito.service';

@Component({
  templateUrl: './regra-movimentacao-debito-delete-dialog.component.html'
})
export class RegraMovimentacaoDebitoDeleteDialogComponent {
  regraMovimentacaoDebito: IRegraMovimentacaoDebito;

  constructor(
    protected regraMovimentacaoDebitoService: RegraMovimentacaoDebitoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.regraMovimentacaoDebitoService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'regraMovimentacaoDebitoListModification',
        content: 'Deleted an regraMovimentacaoDebito'
      });
      this.activeModal.dismiss(true);
    });
  }
}
