import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MandaSharedModule } from 'app/shared/shared.module';
import { UnidadeMedidaComponent } from './unidade-medida.component';
import { UnidadeMedidaDetailComponent } from './unidade-medida-detail.component';
import { UnidadeMedidaUpdateComponent } from './unidade-medida-update.component';
import { UnidadeMedidaDeleteDialogComponent } from './unidade-medida-delete-dialog.component';
import { unidadeMedidaRoute } from './unidade-medida.route';

@NgModule({
  imports: [MandaSharedModule, RouterModule.forChild(unidadeMedidaRoute)],
  declarations: [UnidadeMedidaComponent, UnidadeMedidaDetailComponent, UnidadeMedidaUpdateComponent, UnidadeMedidaDeleteDialogComponent],
  entryComponents: [UnidadeMedidaDeleteDialogComponent]
})
export class MandaUnidadeMedidaModule {}
