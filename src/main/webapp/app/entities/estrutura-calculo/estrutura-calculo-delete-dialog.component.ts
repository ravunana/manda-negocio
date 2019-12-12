import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEstruturaCalculo } from 'app/shared/model/estrutura-calculo.model';
import { EstruturaCalculoService } from './estrutura-calculo.service';

@Component({
  templateUrl: './estrutura-calculo-delete-dialog.component.html'
})
export class EstruturaCalculoDeleteDialogComponent {
  estruturaCalculo: IEstruturaCalculo;

  constructor(
    protected estruturaCalculoService: EstruturaCalculoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.estruturaCalculoService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'estruturaCalculoListModification',
        content: 'Deleted an estruturaCalculo'
      });
      this.activeModal.dismiss(true);
    });
  }
}
