import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MandaSharedModule } from 'app/shared/shared.module';
import { RegraMovimentacaoDebitoComponent } from './regra-movimentacao-debito.component';
import { RegraMovimentacaoDebitoDetailComponent } from './regra-movimentacao-debito-detail.component';
import { RegraMovimentacaoDebitoUpdateComponent } from './regra-movimentacao-debito-update.component';
import { RegraMovimentacaoDebitoDeleteDialogComponent } from './regra-movimentacao-debito-delete-dialog.component';
import { regraMovimentacaoDebitoRoute } from './regra-movimentacao-debito.route';

@NgModule({
  imports: [MandaSharedModule, RouterModule.forChild(regraMovimentacaoDebitoRoute)],
  declarations: [
    RegraMovimentacaoDebitoComponent,
    RegraMovimentacaoDebitoDetailComponent,
    RegraMovimentacaoDebitoUpdateComponent,
    RegraMovimentacaoDebitoDeleteDialogComponent
  ],
  entryComponents: [RegraMovimentacaoDebitoDeleteDialogComponent]
})
export class MandaRegraMovimentacaoDebitoModule {}
