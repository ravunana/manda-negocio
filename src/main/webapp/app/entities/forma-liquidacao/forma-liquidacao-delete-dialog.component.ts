import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFormaLiquidacao } from 'app/shared/model/forma-liquidacao.model';
import { FormaLiquidacaoService } from './forma-liquidacao.service';

@Component({
  templateUrl: './forma-liquidacao-delete-dialog.component.html'
})
export class FormaLiquidacaoDeleteDialogComponent {
  formaLiquidacao: IFormaLiquidacao;

  constructor(
    protected formaLiquidacaoService: FormaLiquidacaoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.formaLiquidacaoService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'formaLiquidacaoListModification',
        content: 'Deleted an formaLiquidacao'
      });
      this.activeModal.dismiss(true);
    });
  }
}
