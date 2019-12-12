import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MandaSharedModule } from 'app/shared/shared.module';
import { GrupoTributacaoImpostoComponent } from './grupo-tributacao-imposto.component';
import { GrupoTributacaoImpostoDetailComponent } from './grupo-tributacao-imposto-detail.component';
import { GrupoTributacaoImpostoUpdateComponent } from './grupo-tributacao-imposto-update.component';
import { GrupoTributacaoImpostoDeleteDialogComponent } from './grupo-tributacao-imposto-delete-dialog.component';
import { grupoTributacaoImpostoRoute } from './grupo-tributacao-imposto.route';

@NgModule({
  imports: [MandaSharedModule, RouterModule.forChild(grupoTributacaoImpostoRoute)],
  declarations: [
    GrupoTributacaoImpostoComponent,
    GrupoTributacaoImpostoDetailComponent,
    GrupoTributacaoImpostoUpdateComponent,
    GrupoTributacaoImpostoDeleteDialogComponent
  ],
  entryComponents: [GrupoTributacaoImpostoDeleteDialogComponent]
})
export class MandaGrupoTributacaoImpostoModule {}
