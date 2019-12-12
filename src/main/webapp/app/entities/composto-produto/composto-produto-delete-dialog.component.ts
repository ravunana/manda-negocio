import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICompostoProduto } from 'app/shared/model/composto-produto.model';
import { CompostoProdutoService } from './composto-produto.service';

@Component({
  templateUrl: './composto-produto-delete-dialog.component.html'
})
export class CompostoProdutoDeleteDialogComponent {
  compostoProduto: ICompostoProduto;

  constructor(
    protected compostoProdutoService: CompostoProdutoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.compostoProdutoService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'compostoProdutoListModification',
        content: 'Deleted an compostoProduto'
      });
      this.activeModal.dismiss(true);
    });
  }
}
