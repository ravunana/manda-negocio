import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MandaSharedModule } from 'app/shared/shared.module';
import { ClasseContaComponent } from './classe-conta.component';
import { ClasseContaDetailComponent } from './classe-conta-detail.component';
import { ClasseContaUpdateComponent } from './classe-conta-update.component';
import { ClasseContaDeleteDialogComponent } from './classe-conta-delete-dialog.component';
import { classeContaRoute } from './classe-conta.route';

@NgModule({
  imports: [MandaSharedModule, RouterModule.forChild(classeContaRoute)],
  declarations: [ClasseContaComponent, ClasseContaDetailComponent, ClasseContaUpdateComponent, ClasseContaDeleteDialogComponent],
  entryComponents: [ClasseContaDeleteDialogComponent]
})
export class MandaClasseContaModule {}
