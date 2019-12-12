import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MandaSharedModule } from 'app/shared/shared.module';
import { LicencaSoftwareComponent } from './licenca-software.component';
import { LicencaSoftwareDetailComponent } from './licenca-software-detail.component';
import { LicencaSoftwareUpdateComponent } from './licenca-software-update.component';
import { LicencaSoftwareDeleteDialogComponent } from './licenca-software-delete-dialog.component';
import { licencaSoftwareRoute } from './licenca-software.route';

@NgModule({
  imports: [MandaSharedModule, RouterModule.forChild(licencaSoftwareRoute)],
  declarations: [
    LicencaSoftwareComponent,
    LicencaSoftwareDetailComponent,
    LicencaSoftwareUpdateComponent,
    LicencaSoftwareDeleteDialogComponent
  ],
  entryComponents: [LicencaSoftwareDeleteDialogComponent]
})
export class MandaLicencaSoftwareModule {}
