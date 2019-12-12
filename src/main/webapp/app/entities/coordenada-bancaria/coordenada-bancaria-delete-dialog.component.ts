import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICoordenadaBancaria } from 'app/shared/model/coordenada-bancaria.model';
import { CoordenadaBancariaService } from './coordenada-bancaria.service';

@Component({
  templateUrl: './coordenada-bancaria-delete-dialog.component.html'
})
export class CoordenadaBancariaDeleteDialogComponent {
  coordenadaBancaria: ICoordenadaBancaria;

  constructor(
    protected coordenadaBancariaService: CoordenadaBancariaService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.coordenadaBancariaService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'coordenadaBancariaListModification',
        content: 'Deleted an coordenadaBancaria'
      });
      this.activeModal.dismiss(true);
    });
  }
}
