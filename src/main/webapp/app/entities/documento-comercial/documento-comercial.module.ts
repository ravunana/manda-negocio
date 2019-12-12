import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MandaSharedModule } from 'app/shared/shared.module';
import { DocumentoComercialComponent } from './documento-comercial.component';
import { DocumentoComercialDetailComponent } from './documento-comercial-detail.component';
import { DocumentoComercialUpdateComponent } from './documento-comercial-update.component';
import { DocumentoComercialDeleteDialogComponent } from './documento-comercial-delete-dialog.component';
import { documentoComercialRoute } from './documento-comercial.route';

@NgModule({
  imports: [MandaSharedModule, RouterModule.forChild(documentoComercialRoute)],
  declarations: [
    DocumentoComercialComponent,
    DocumentoComercialDetailComponent,
    DocumentoComercialUpdateComponent,
    DocumentoComercialDeleteDialogComponent
  ],
  entryComponents: [DocumentoComercialDeleteDialogComponent]
})
export class MandaDocumentoComercialModule {}
