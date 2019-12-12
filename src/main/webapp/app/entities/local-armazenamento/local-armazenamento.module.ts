import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MandaSharedModule } from 'app/shared/shared.module';
import { LocalArmazenamentoComponent } from './local-armazenamento.component';
import { LocalArmazenamentoDetailComponent } from './local-armazenamento-detail.component';
import { LocalArmazenamentoUpdateComponent } from './local-armazenamento-update.component';
import { LocalArmazenamentoDeleteDialogComponent } from './local-armazenamento-delete-dialog.component';
import { localArmazenamentoRoute } from './local-armazenamento.route';

@NgModule({
  imports: [MandaSharedModule, RouterModule.forChild(localArmazenamentoRoute)],
  declarations: [
    LocalArmazenamentoComponent,
    LocalArmazenamentoDetailComponent,
    LocalArmazenamentoUpdateComponent,
    LocalArmazenamentoDeleteDialogComponent
  ],
  entryComponents: [LocalArmazenamentoDeleteDialogComponent]
})
export class MandaLocalArmazenamentoModule {}
