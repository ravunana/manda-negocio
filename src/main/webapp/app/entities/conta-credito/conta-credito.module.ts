import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MandaSharedModule } from 'app/shared/shared.module';
import { ContaCreditoComponent } from './conta-credito.component';
import { ContaCreditoDetailComponent } from './conta-credito-detail.component';
import { ContaCreditoUpdateComponent } from './conta-credito-update.component';
import { ContaCreditoDeleteDialogComponent } from './conta-credito-delete-dialog.component';
import { contaCreditoRoute } from './conta-credito.route';

@NgModule({
  imports: [MandaSharedModule, RouterModule.forChild(contaCreditoRoute)],
  declarations: [ContaCreditoComponent, ContaCreditoDetailComponent, ContaCreditoUpdateComponent, ContaCreditoDeleteDialogComponent],
  entryComponents: [ContaCreditoDeleteDialogComponent]
})
export class MandaContaCreditoModule {}
