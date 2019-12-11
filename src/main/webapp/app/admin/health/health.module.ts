import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { MandaSharedModule } from 'app/shared/shared.module';

import { RvHealthCheckComponent } from './health.component';
import { RvHealthModalComponent } from './health-modal.component';

import { healthRoute } from './health.route';

@NgModule({
  imports: [MandaSharedModule, RouterModule.forChild([healthRoute])],
  declarations: [RvHealthCheckComponent, RvHealthModalComponent],
  entryComponents: [RvHealthModalComponent]
})
export class HealthModule {}
