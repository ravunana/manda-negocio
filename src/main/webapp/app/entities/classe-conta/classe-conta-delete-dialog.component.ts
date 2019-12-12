import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IClasseConta } from 'app/shared/model/classe-conta.model';
import { ClasseContaService } from './classe-conta.service';

@Component({
  templateUrl: './classe-conta-delete-dialog.component.html'
})
export class ClasseContaDeleteDialogComponent {
  classeConta: IClasseConta;

  constructor(
    protected classeContaService: ClasseContaService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.classeContaService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'classeContaListModification',
        content: 'Deleted an classeConta'
      });
      this.activeModal.dismiss(true);
    });
  }
}
