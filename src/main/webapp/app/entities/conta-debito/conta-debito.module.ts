import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MandaSharedModule } from 'app/shared/shared.module';
import { ContaDebitoComponent } from './conta-debito.component';
import { ContaDebitoDetailComponent } from './conta-debito-detail.component';
import { ContaDebitoUpdateComponent } from './conta-debito-update.component';
import { ContaDebitoDeleteDialogComponent } from './conta-debito-delete-dialog.component';
import { contaDebitoRoute } from './conta-debito.route';

@NgModule({
  imports: [MandaSharedModule, RouterModule.forChild(contaDebitoRoute)],
  declarations: [ContaDebitoComponent, ContaDebitoDetailComponent, ContaDebitoUpdateComponent, ContaDebitoDeleteDialogComponent],
  entryComponents: [ContaDebitoDeleteDialogComponent]
})
export class MandaContaDebitoModule {}
