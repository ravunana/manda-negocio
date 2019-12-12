import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILicencaSoftware } from 'app/shared/model/licenca-software.model';
import { LicencaSoftwareService } from './licenca-software.service';

@Component({
  templateUrl: './licenca-software-delete-dialog.component.html'
})
export class LicencaSoftwareDeleteDialogComponent {
  licencaSoftware: ILicencaSoftware;

  constructor(
    protected licencaSoftwareService: LicencaSoftwareService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.licencaSoftwareService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'licencaSoftwareListModification',
        content: 'Deleted an licencaSoftware'
      });
      this.activeModal.dismiss(true);
    });
  }
}
