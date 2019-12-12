import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MandaSharedModule } from 'app/shared/shared.module';
import { AuditoriaVendaComponent } from './auditoria-venda.component';
import { AuditoriaVendaDetailComponent } from './auditoria-venda-detail.component';
import { AuditoriaVendaUpdateComponent } from './auditoria-venda-update.component';
import { AuditoriaVendaDeleteDialogComponent } from './auditoria-venda-delete-dialog.component';
import { auditoriaVendaRoute } from './auditoria-venda.route';

@NgModule({
  imports: [MandaSharedModule, RouterModule.forChild(auditoriaVendaRoute)],
  declarations: [
    AuditoriaVendaComponent,
    AuditoriaVendaDetailComponent,
    AuditoriaVendaUpdateComponent,
    AuditoriaVendaDeleteDialogComponent
  ],
  entryComponents: [AuditoriaVendaDeleteDialogComponent]
})
export class MandaAuditoriaVendaModule {}
