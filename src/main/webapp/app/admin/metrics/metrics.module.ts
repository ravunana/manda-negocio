import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { MandaSharedModule } from 'app/shared/shared.module';

import { RvMetricsMonitoringComponent } from './metrics.component';

import { metricsRoute } from './metrics.route';

@NgModule({
  imports: [MandaSharedModule, RouterModule.forChild([metricsRoute])],
  declarations: [RvMetricsMonitoringComponent]
})
export class MetricsModule {}
