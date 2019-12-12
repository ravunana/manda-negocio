import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IGrupoTributacaoImposto } from 'app/shared/model/grupo-tributacao-imposto.model';
import { GrupoTributacaoImpostoService } from './grupo-tributacao-imposto.service';

@Component({
  templateUrl: './grupo-tributacao-imposto-delete-dialog.component.html'
})
export class GrupoTributacaoImpostoDeleteDialogComponent {
  grupoTributacaoImposto: IGrupoTributacaoImposto;

  constructor(
    protected grupoTributacaoImpostoService: GrupoTributacaoImpostoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.grupoTributacaoImpostoService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'grupoTributacaoImpostoListModification',
        content: 'Deleted an grupoTributacaoImposto'
      });
      this.activeModal.dismiss(true);
    });
  }
}
