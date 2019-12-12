import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILocalizacaoEmpresa } from 'app/shared/model/localizacao-empresa.model';
import { LocalizacaoEmpresaService } from './localizacao-empresa.service';

@Component({
  templateUrl: './localizacao-empresa-delete-dialog.component.html'
})
export class LocalizacaoEmpresaDeleteDialogComponent {
  localizacaoEmpresa: ILocalizacaoEmpresa;

  constructor(
    protected localizacaoEmpresaService: LocalizacaoEmpresaService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.localizacaoEmpresaService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'localizacaoEmpresaListModification',
        content: 'Deleted an localizacaoEmpresa'
      });
      this.activeModal.dismiss(true);
    });
  }
}
