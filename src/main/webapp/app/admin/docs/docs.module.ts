import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { MandaSharedModule } from 'app/shared/shared.module';

import { RvDocsComponent } from './docs.component';

import { docsRoute } from './docs.route';

@NgModule({
  imports: [MandaSharedModule, RouterModule.forChild([docsRoute])],
  declarations: [RvDocsComponent]
})
export class DocsModule {}
