import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MandaSharedModule } from 'app/shared/shared.module';
import { ContactoEmpresaComponent } from './contacto-empresa.component';
import { ContactoEmpresaDetailComponent } from './contacto-empresa-detail.component';
import { ContactoEmpresaUpdateComponent } from './contacto-empresa-update.component';
import { ContactoEmpresaDeleteDialogComponent } from './contacto-empresa-delete-dialog.component';
import { contactoEmpresaRoute } from './contacto-empresa.route';

@NgModule({
  imports: [MandaSharedModule, RouterModule.forChild(contactoEmpresaRoute)],
  declarations: [
    ContactoEmpresaComponent,
    ContactoEmpresaDetailComponent,
    ContactoEmpresaUpdateComponent,
    ContactoEmpresaDeleteDialogComponent
  ],
  entryComponents: [ContactoEmpresaDeleteDialogComponent]
})
export class MandaContactoEmpresaModule {}
