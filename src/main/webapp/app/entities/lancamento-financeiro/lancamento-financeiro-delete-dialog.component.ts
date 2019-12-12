import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILancamentoFinanceiro } from 'app/shared/model/lancamento-financeiro.model';
import { LancamentoFinanceiroService } from './lancamento-financeiro.service';

@Component({
  templateUrl: './lancamento-financeiro-delete-dialog.component.html'
})
export class LancamentoFinanceiroDeleteDialogComponent {
  lancamentoFinanceiro: ILancamentoFinanceiro;

  constructor(
    protected lancamentoFinanceiroService: LancamentoFinanceiroService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.lancamentoFinanceiroService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'lancamentoFinanceiroListModification',
        content: 'Deleted an lancamentoFinanceiro'
      });
      this.activeModal.dismiss(true);
    });
  }
}
