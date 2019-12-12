import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MandaSharedModule } from 'app/shared/shared.module';
import { DetalheAduaneiroComponent } from './detalhe-aduaneiro.component';
import { DetalheAduaneiroDetailComponent } from './detalhe-aduaneiro-detail.component';
import { DetalheAduaneiroUpdateComponent } from './detalhe-aduaneiro-update.component';
import { DetalheAduaneiroDeleteDialogComponent } from './detalhe-aduaneiro-delete-dialog.component';
import { detalheAduaneiroRoute } from './detalhe-aduaneiro.route';

@NgModule({
  imports: [MandaSharedModule, RouterModule.forChild(detalheAduaneiroRoute)],
  declarations: [
    DetalheAduaneiroComponent,
    DetalheAduaneiroDetailComponent,
    DetalheAduaneiroUpdateComponent,
    DetalheAduaneiroDeleteDialogComponent
  ],
  entryComponents: [DetalheAduaneiroDeleteDialogComponent]
})
export class MandaDetalheAduaneiroModule {}
