import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MandaSharedModule } from 'app/shared/shared.module';
import { VendaComponent } from './venda.component';
import { VendaDetailComponent } from './venda-detail.component';
import { VendaUpdateComponent } from './venda-update.component';
import { VendaDeleteDialogComponent } from './venda-delete-dialog.component';
import { vendaRoute } from './venda.route';

@NgModule({
  imports: [MandaSharedModule, RouterModule.forChild(vendaRoute)],
  declarations: [VendaComponent, VendaDetailComponent, VendaUpdateComponent, VendaDeleteDialogComponent],
  entryComponents: [VendaDeleteDialogComponent]
})
export class MandaVendaModule {}
