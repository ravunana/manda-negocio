import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMoradaPessoa } from 'app/shared/model/morada-pessoa.model';
import { MoradaPessoaService } from './morada-pessoa.service';

@Component({
  templateUrl: './morada-pessoa-delete-dialog.component.html'
})
export class MoradaPessoaDeleteDialogComponent {
  moradaPessoa: IMoradaPessoa;

  constructor(
    protected moradaPessoaService: MoradaPessoaService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.moradaPessoaService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'moradaPessoaListModification',
        content: 'Deleted an moradaPessoa'
      });
      this.activeModal.dismiss(true);
    });
  }
}
