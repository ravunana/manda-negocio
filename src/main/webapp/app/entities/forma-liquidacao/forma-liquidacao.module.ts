import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MandaSharedModule } from 'app/shared/shared.module';
import { FormaLiquidacaoComponent } from './forma-liquidacao.component';
import { FormaLiquidacaoDetailComponent } from './forma-liquidacao-detail.component';
import { FormaLiquidacaoUpdateComponent } from './forma-liquidacao-update.component';
import { FormaLiquidacaoDeleteDialogComponent } from './forma-liquidacao-delete-dialog.component';
import { formaLiquidacaoRoute } from './forma-liquidacao.route';

@NgModule({
  imports: [MandaSharedModule, RouterModule.forChild(formaLiquidacaoRoute)],
  declarations: [
    FormaLiquidacaoComponent,
    FormaLiquidacaoDetailComponent,
    FormaLiquidacaoUpdateComponent,
    FormaLiquidacaoDeleteDialogComponent
  ],
  entryComponents: [FormaLiquidacaoDeleteDialogComponent]
})
export class MandaFormaLiquidacaoModule {}
