import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MandaSharedModule } from 'app/shared/shared.module';
import { DevolucaoVendaComponent } from './devolucao-venda.component';
import { DevolucaoVendaDetailComponent } from './devolucao-venda-detail.component';
import { DevolucaoVendaUpdateComponent } from './devolucao-venda-update.component';
import { DevolucaoVendaDeleteDialogComponent } from './devolucao-venda-delete-dialog.component';
import { devolucaoVendaRoute } from './devolucao-venda.route';

@NgModule({
  imports: [MandaSharedModule, RouterModule.forChild(devolucaoVendaRoute)],
  declarations: [
    DevolucaoVendaComponent,
    DevolucaoVendaDetailComponent,
    DevolucaoVendaUpdateComponent,
    DevolucaoVendaDeleteDialogComponent
  ],
  entryComponents: [DevolucaoVendaDeleteDialogComponent]
})
export class MandaDevolucaoVendaModule {}
