import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMeioLiquidacao } from 'app/shared/model/meio-liquidacao.model';
import { MeioLiquidacaoService } from './meio-liquidacao.service';

@Component({
  templateUrl: './meio-liquidacao-delete-dialog.component.html'
})
export class MeioLiquidacaoDeleteDialogComponent {
  meioLiquidacao: IMeioLiquidacao;

  constructor(
    protected meioLiquidacaoService: MeioLiquidacaoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.meioLiquidacaoService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'meioLiquidacaoListModification',
        content: 'Deleted an meioLiquidacao'
      });
      this.activeModal.dismiss(true);
    });
  }
}
