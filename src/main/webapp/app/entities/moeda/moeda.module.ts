import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MandaSharedModule } from 'app/shared/shared.module';
import { MoedaComponent } from './moeda.component';
import { MoedaDetailComponent } from './moeda-detail.component';
import { MoedaUpdateComponent } from './moeda-update.component';
import { MoedaDeleteDialogComponent } from './moeda-delete-dialog.component';
import { moedaRoute } from './moeda.route';

@NgModule({
  imports: [MandaSharedModule, RouterModule.forChild(moedaRoute)],
  declarations: [MoedaComponent, MoedaDetailComponent, MoedaUpdateComponent, MoedaDeleteDialogComponent],
  entryComponents: [MoedaDeleteDialogComponent]
})
export class MandaMoedaModule {}
