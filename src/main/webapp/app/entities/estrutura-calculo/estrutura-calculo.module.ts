import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MandaSharedModule } from 'app/shared/shared.module';
import { EstruturaCalculoComponent } from './estrutura-calculo.component';
import { EstruturaCalculoDetailComponent } from './estrutura-calculo-detail.component';
import { EstruturaCalculoUpdateComponent } from './estrutura-calculo-update.component';
import { EstruturaCalculoDeleteDialogComponent } from './estrutura-calculo-delete-dialog.component';
import { estruturaCalculoRoute } from './estrutura-calculo.route';

@NgModule({
  imports: [MandaSharedModule, RouterModule.forChild(estruturaCalculoRoute)],
  declarations: [
    EstruturaCalculoComponent,
    EstruturaCalculoDetailComponent,
    EstruturaCalculoUpdateComponent,
    EstruturaCalculoDeleteDialogComponent
  ],
  entryComponents: [EstruturaCalculoDeleteDialogComponent]
})
export class MandaEstruturaCalculoModule {}
