import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IVariacaoProduto } from 'app/shared/model/variacao-produto.model';
import { VariacaoProdutoService } from './variacao-produto.service';

@Component({
  templateUrl: './variacao-produto-delete-dialog.component.html'
})
export class VariacaoProdutoDeleteDialogComponent {
  variacaoProduto: IVariacaoProduto;

  constructor(
    protected variacaoProdutoService: VariacaoProdutoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.variacaoProdutoService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'variacaoProdutoListModification',
        content: 'Deleted an variacaoProduto'
      });
      this.activeModal.dismiss(true);
    });
  }
}
