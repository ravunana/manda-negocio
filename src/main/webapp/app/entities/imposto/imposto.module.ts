import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MandaSharedModule } from 'app/shared/shared.module';
import { ImpostoComponent } from './imposto.component';
import { ImpostoDetailComponent } from './imposto-detail.component';
import { ImpostoUpdateComponent } from './imposto-update.component';
import { ImpostoDeleteDialogComponent } from './imposto-delete-dialog.component';
import { impostoRoute } from './imposto.route';

@NgModule({
  imports: [MandaSharedModule, RouterModule.forChild(impostoRoute)],
  declarations: [ImpostoComponent, ImpostoDetailComponent, ImpostoUpdateComponent, ImpostoDeleteDialogComponent],
  entryComponents: [ImpostoDeleteDialogComponent]
})
export class MandaImpostoModule {}
