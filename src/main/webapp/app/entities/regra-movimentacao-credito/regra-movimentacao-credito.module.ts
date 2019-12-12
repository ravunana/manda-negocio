import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MandaSharedModule } from 'app/shared/shared.module';
import { RegraMovimentacaoCreditoComponent } from './regra-movimentacao-credito.component';
import { RegraMovimentacaoCreditoDetailComponent } from './regra-movimentacao-credito-detail.component';
import { RegraMovimentacaoCreditoUpdateComponent } from './regra-movimentacao-credito-update.component';
import { RegraMovimentacaoCreditoDeleteDialogComponent } from './regra-movimentacao-credito-delete-dialog.component';
import { regraMovimentacaoCreditoRoute } from './regra-movimentacao-credito.route';

@NgModule({
  imports: [MandaSharedModule, RouterModule.forChild(regraMovimentacaoCreditoRoute)],
  declarations: [
    RegraMovimentacaoCreditoComponent,
    RegraMovimentacaoCreditoDetailComponent,
    RegraMovimentacaoCreditoUpdateComponent,
    RegraMovimentacaoCreditoDeleteDialogComponent
  ],
  entryComponents: [RegraMovimentacaoCreditoDeleteDialogComponent]
})
export class MandaRegraMovimentacaoCreditoModule {}
