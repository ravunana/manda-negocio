import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MandaSharedModule } from 'app/shared/shared.module';
import { ContaComponent } from './conta.component';
import { ContaDetailComponent } from './conta-detail.component';
import { ContaUpdateComponent } from './conta-update.component';
import { ContaDeleteDialogComponent } from './conta-delete-dialog.component';
import { contaRoute } from './conta.route';

@NgModule({
  imports: [MandaSharedModule, RouterModule.forChild(contaRoute)],
  declarations: [ContaComponent, ContaDetailComponent, ContaUpdateComponent, ContaDeleteDialogComponent],
  entryComponents: [ContaDeleteDialogComponent]
})
export class MandaContaModule {}
