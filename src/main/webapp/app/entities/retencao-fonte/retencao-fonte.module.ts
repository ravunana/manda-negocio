import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MandaSharedModule } from 'app/shared/shared.module';
import { RetencaoFonteComponent } from './retencao-fonte.component';
import { RetencaoFonteDetailComponent } from './retencao-fonte-detail.component';
import { RetencaoFonteUpdateComponent } from './retencao-fonte-update.component';
import { RetencaoFonteDeleteDialogComponent } from './retencao-fonte-delete-dialog.component';
import { retencaoFonteRoute } from './retencao-fonte.route';

@NgModule({
  imports: [MandaSharedModule, RouterModule.forChild(retencaoFonteRoute)],
  declarations: [RetencaoFonteComponent, RetencaoFonteDetailComponent, RetencaoFonteUpdateComponent, RetencaoFonteDeleteDialogComponent],
  entryComponents: [RetencaoFonteDeleteDialogComponent]
})
export class MandaRetencaoFonteModule {}
