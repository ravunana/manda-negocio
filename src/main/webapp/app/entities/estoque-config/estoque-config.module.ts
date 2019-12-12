import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MandaSharedModule } from 'app/shared/shared.module';
import { EstoqueConfigComponent } from './estoque-config.component';
import { EstoqueConfigDetailComponent } from './estoque-config-detail.component';
import { EstoqueConfigUpdateComponent } from './estoque-config-update.component';
import { EstoqueConfigDeleteDialogComponent } from './estoque-config-delete-dialog.component';
import { estoqueConfigRoute } from './estoque-config.route';

@NgModule({
  imports: [MandaSharedModule, RouterModule.forChild(estoqueConfigRoute)],
  declarations: [EstoqueConfigComponent, EstoqueConfigDetailComponent, EstoqueConfigUpdateComponent, EstoqueConfigDeleteDialogComponent],
  entryComponents: [EstoqueConfigDeleteDialogComponent]
})
export class MandaEstoqueConfigModule {}
