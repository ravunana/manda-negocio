import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MandaSharedModule } from 'app/shared/shared.module';
import { LookupComponent } from './lookup.component';
import { LookupDetailComponent } from './lookup-detail.component';
import { LookupUpdateComponent } from './lookup-update.component';
import { LookupDeleteDialogComponent } from './lookup-delete-dialog.component';
import { lookupRoute } from './lookup.route';

@NgModule({
  imports: [MandaSharedModule, RouterModule.forChild(lookupRoute)],
  declarations: [LookupComponent, LookupDetailComponent, LookupUpdateComponent, LookupDeleteDialogComponent],
  entryComponents: [LookupDeleteDialogComponent]
})
export class MandaLookupModule {}
