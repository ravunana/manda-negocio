import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MandaSharedModule } from 'app/shared/shared.module';
import { FluxoDocumentoComponent } from './fluxo-documento.component';
import { FluxoDocumentoDetailComponent } from './fluxo-documento-detail.component';
import { FluxoDocumentoUpdateComponent } from './fluxo-documento-update.component';
import { FluxoDocumentoDeleteDialogComponent } from './fluxo-documento-delete-dialog.component';
import { fluxoDocumentoRoute } from './fluxo-documento.route';

@NgModule({
  imports: [MandaSharedModule, RouterModule.forChild(fluxoDocumentoRoute)],
  declarations: [
    FluxoDocumentoComponent,
    FluxoDocumentoDetailComponent,
    FluxoDocumentoUpdateComponent,
    FluxoDocumentoDeleteDialogComponent
  ],
  entryComponents: [FluxoDocumentoDeleteDialogComponent]
})
export class MandaFluxoDocumentoModule {}
