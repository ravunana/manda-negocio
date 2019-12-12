import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MandaSharedModule } from 'app/shared/shared.module';
import { DetalheLancamentoComponent } from './detalhe-lancamento.component';
import { DetalheLancamentoDetailComponent } from './detalhe-lancamento-detail.component';
import { DetalheLancamentoUpdateComponent } from './detalhe-lancamento-update.component';
import { DetalheLancamentoDeleteDialogComponent } from './detalhe-lancamento-delete-dialog.component';
import { detalheLancamentoRoute } from './detalhe-lancamento.route';

@NgModule({
  imports: [MandaSharedModule, RouterModule.forChild(detalheLancamentoRoute)],
  declarations: [
    DetalheLancamentoComponent,
    DetalheLancamentoDetailComponent,
    DetalheLancamentoUpdateComponent,
    DetalheLancamentoDeleteDialogComponent
  ],
  entryComponents: [DetalheLancamentoDeleteDialogComponent]
})
export class MandaDetalheLancamentoModule {}
