import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MandaSharedModule } from 'app/shared/shared.module';
import { LookupItemComponent } from './lookup-item.component';
import { LookupItemDetailComponent } from './lookup-item-detail.component';
import { LookupItemUpdateComponent } from './lookup-item-update.component';
import { LookupItemDeleteDialogComponent } from './lookup-item-delete-dialog.component';
import { lookupItemRoute } from './lookup-item.route';

@NgModule({
  imports: [MandaSharedModule, RouterModule.forChild(lookupItemRoute)],
  declarations: [LookupItemComponent, LookupItemDetailComponent, LookupItemUpdateComponent, LookupItemDeleteDialogComponent],
  entryComponents: [LookupItemDeleteDialogComponent]
})
export class MandaLookupItemModule {}
