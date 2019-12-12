import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MandaSharedModule } from 'app/shared/shared.module';
import { LocalizacaoEmpresaComponent } from './localizacao-empresa.component';
import { LocalizacaoEmpresaDetailComponent } from './localizacao-empresa-detail.component';
import { LocalizacaoEmpresaUpdateComponent } from './localizacao-empresa-update.component';
import { LocalizacaoEmpresaDeleteDialogComponent } from './localizacao-empresa-delete-dialog.component';
import { localizacaoEmpresaRoute } from './localizacao-empresa.route';

@NgModule({
  imports: [MandaSharedModule, RouterModule.forChild(localizacaoEmpresaRoute)],
  declarations: [
    LocalizacaoEmpresaComponent,
    LocalizacaoEmpresaDetailComponent,
    LocalizacaoEmpresaUpdateComponent,
    LocalizacaoEmpresaDeleteDialogComponent
  ],
  entryComponents: [LocalizacaoEmpresaDeleteDialogComponent]
})
export class MandaLocalizacaoEmpresaModule {}
