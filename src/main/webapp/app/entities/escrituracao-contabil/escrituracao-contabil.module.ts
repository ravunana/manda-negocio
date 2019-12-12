import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MandaSharedModule } from 'app/shared/shared.module';
import { EscrituracaoContabilComponent } from './escrituracao-contabil.component';
import { EscrituracaoContabilDetailComponent } from './escrituracao-contabil-detail.component';
import { EscrituracaoContabilUpdateComponent } from './escrituracao-contabil-update.component';
import { EscrituracaoContabilDeleteDialogComponent } from './escrituracao-contabil-delete-dialog.component';
import { escrituracaoContabilRoute } from './escrituracao-contabil.route';

@NgModule({
  imports: [MandaSharedModule, RouterModule.forChild(escrituracaoContabilRoute)],
  declarations: [
    EscrituracaoContabilComponent,
    EscrituracaoContabilDetailComponent,
    EscrituracaoContabilUpdateComponent,
    EscrituracaoContabilDeleteDialogComponent
  ],
  entryComponents: [EscrituracaoContabilDeleteDialogComponent]
})
export class MandaEscrituracaoContabilModule {}
