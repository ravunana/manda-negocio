import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MandaSharedModule } from 'app/shared/shared.module';
import { AuditoriaCompraComponent } from './auditoria-compra.component';
import { AuditoriaCompraDetailComponent } from './auditoria-compra-detail.component';
import { AuditoriaCompraUpdateComponent } from './auditoria-compra-update.component';
import { AuditoriaCompraDeleteDialogComponent } from './auditoria-compra-delete-dialog.component';
import { auditoriaCompraRoute } from './auditoria-compra.route';

@NgModule({
  imports: [MandaSharedModule, RouterModule.forChild(auditoriaCompraRoute)],
  declarations: [
    AuditoriaCompraComponent,
    AuditoriaCompraDetailComponent,
    AuditoriaCompraUpdateComponent,
    AuditoriaCompraDeleteDialogComponent
  ],
  entryComponents: [AuditoriaCompraDeleteDialogComponent]
})
export class MandaAuditoriaCompraModule {}
