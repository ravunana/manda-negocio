import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IConversaoUnidade } from 'app/shared/model/conversao-unidade.model';
import { ConversaoUnidadeService } from './conversao-unidade.service';

@Component({
  templateUrl: './conversao-unidade-delete-dialog.component.html'
})
export class ConversaoUnidadeDeleteDialogComponent {
  conversaoUnidade: IConversaoUnidade;

  constructor(
    protected conversaoUnidadeService: ConversaoUnidadeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.conversaoUnidadeService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'conversaoUnidadeListModification',
        content: 'Deleted an conversaoUnidade'
      });
      this.activeModal.dismiss(true);
    });
  }
}
