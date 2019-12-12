import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFamiliaProduto } from 'app/shared/model/familia-produto.model';
import { FamiliaProdutoService } from './familia-produto.service';

@Component({
  templateUrl: './familia-produto-delete-dialog.component.html'
})
export class FamiliaProdutoDeleteDialogComponent {
  familiaProduto: IFamiliaProduto;

  constructor(
    protected familiaProdutoService: FamiliaProdutoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.familiaProdutoService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'familiaProdutoListModification',
        content: 'Deleted an familiaProduto'
      });
      this.activeModal.dismiss(true);
    });
  }
}
