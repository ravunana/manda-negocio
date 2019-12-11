import { Route } from '@angular/router';

import { RvMetricsMonitoringComponent } from './metrics.component';

export const metricsRoute: Route = {
  path: '',
  component: RvMetricsMonitoringComponent,
  data: {
    pageTitle: 'metrics.title'
  }
};
