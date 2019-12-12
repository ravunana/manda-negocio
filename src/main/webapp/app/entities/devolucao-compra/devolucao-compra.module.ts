import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MandaSharedModule } from 'app/shared/shared.module';
import { DevolucaoCompraComponent } from './devolucao-compra.component';
import { DevolucaoCompraDetailComponent } from './devolucao-compra-detail.component';
import { DevolucaoCompraUpdateComponent } from './devolucao-compra-update.component';
import { DevolucaoCompraDeleteDialogComponent } from './devolucao-compra-delete-dialog.component';
import { devolucaoCompraRoute } from './devolucao-compra.route';

@NgModule({
  imports: [MandaSharedModule, RouterModule.forChild(devolucaoCompraRoute)],
  declarations: [
    DevolucaoCompraComponent,
    DevolucaoCompraDetailComponent,
    DevolucaoCompraUpdateComponent,
    DevolucaoCompraDeleteDialogComponent
  ],
  entryComponents: [DevolucaoCompraDeleteDialogComponent]
})
export class MandaDevolucaoCompraModule {}
