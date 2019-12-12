import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MandaSharedModule } from 'app/shared/shared.module';
import { LancamentoFinanceiroComponent } from './lancamento-financeiro.component';
import { LancamentoFinanceiroDetailComponent } from './lancamento-financeiro-detail.component';
import { LancamentoFinanceiroUpdateComponent } from './lancamento-financeiro-update.component';
import { LancamentoFinanceiroDeleteDialogComponent } from './lancamento-financeiro-delete-dialog.component';
import { lancamentoFinanceiroRoute } from './lancamento-financeiro.route';

@NgModule({
  imports: [MandaSharedModule, RouterModule.forChild(lancamentoFinanceiroRoute)],
  declarations: [
    LancamentoFinanceiroComponent,
    LancamentoFinanceiroDetailComponent,
    LancamentoFinanceiroUpdateComponent,
    LancamentoFinanceiroDeleteDialogComponent
  ],
  entryComponents: [LancamentoFinanceiroDeleteDialogComponent]
})
export class MandaLancamentoFinanceiroModule {}
