import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MandaSharedModule } from 'app/shared/shared.module';
import { ItemCompraComponent } from './item-compra.component';
import { ItemCompraDetailComponent } from './item-compra-detail.component';
import { ItemCompraUpdateComponent } from './item-compra-update.component';
import { ItemCompraDeleteDialogComponent } from './item-compra-delete-dialog.component';
import { itemCompraRoute } from './item-compra.route';

@NgModule({
  imports: [MandaSharedModule, RouterModule.forChild(itemCompraRoute)],
  declarations: [ItemCompraComponent, ItemCompraDetailComponent, ItemCompraUpdateComponent, ItemCompraDeleteDialogComponent],
  entryComponents: [ItemCompraDeleteDialogComponent]
})
export class MandaItemCompraModule {}
