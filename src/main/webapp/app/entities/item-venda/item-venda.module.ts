import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MandaSharedModule } from 'app/shared/shared.module';
import { ItemVendaComponent } from './item-venda.component';
import { ItemVendaDetailComponent } from './item-venda-detail.component';
import { ItemVendaUpdateComponent } from './item-venda-update.component';
import { ItemVendaDeleteDialogComponent } from './item-venda-delete-dialog.component';
import { itemVendaRoute } from './item-venda.route';

@NgModule({
  imports: [MandaSharedModule, RouterModule.forChild(itemVendaRoute)],
  declarations: [ItemVendaComponent, ItemVendaDetailComponent, ItemVendaUpdateComponent, ItemVendaDeleteDialogComponent],
  entryComponents: [ItemVendaDeleteDialogComponent]
})
export class MandaItemVendaModule {}
