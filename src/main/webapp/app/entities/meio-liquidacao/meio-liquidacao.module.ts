import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MandaSharedModule } from 'app/shared/shared.module';
import { MeioLiquidacaoComponent } from './meio-liquidacao.component';
import { MeioLiquidacaoDetailComponent } from './meio-liquidacao-detail.component';
import { MeioLiquidacaoUpdateComponent } from './meio-liquidacao-update.component';
import { MeioLiquidacaoDeleteDialogComponent } from './meio-liquidacao-delete-dialog.component';
import { meioLiquidacaoRoute } from './meio-liquidacao.route';

@NgModule({
  imports: [MandaSharedModule, RouterModule.forChild(meioLiquidacaoRoute)],
  declarations: [
    MeioLiquidacaoComponent,
    MeioLiquidacaoDetailComponent,
    MeioLiquidacaoUpdateComponent,
    MeioLiquidacaoDeleteDialogComponent
  ],
  entryComponents: [MeioLiquidacaoDeleteDialogComponent]
})
export class MandaMeioLiquidacaoModule {}
