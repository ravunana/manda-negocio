import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILookup } from 'app/shared/model/lookup.model';
import { LookupService } from './lookup.service';

@Component({
  templateUrl: './lookup-delete-dialog.component.html'
})
export class LookupDeleteDialogComponent {
  lookup: ILookup;

  constructor(protected lookupService: LookupService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.lookupService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'lookupListModification',
        content: 'Deleted an lookup'
      });
      this.activeModal.dismiss(true);
    });
  }
}
