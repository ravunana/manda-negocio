import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MandaSharedModule } from 'app/shared/shared.module';
import { CoordenadaBancariaComponent } from './coordenada-bancaria.component';
import { CoordenadaBancariaDetailComponent } from './coordenada-bancaria-detail.component';
import { CoordenadaBancariaUpdateComponent } from './coordenada-bancaria-update.component';
import { CoordenadaBancariaDeleteDialogComponent } from './coordenada-bancaria-delete-dialog.component';
import { coordenadaBancariaRoute } from './coordenada-bancaria.route';

@NgModule({
  imports: [MandaSharedModule, RouterModule.forChild(coordenadaBancariaRoute)],
  declarations: [
    CoordenadaBancariaComponent,
    CoordenadaBancariaDetailComponent,
    CoordenadaBancariaUpdateComponent,
    CoordenadaBancariaDeleteDialogComponent
  ],
  entryComponents: [CoordenadaBancariaDeleteDialogComponent]
})
export class MandaCoordenadaBancariaModule {}
