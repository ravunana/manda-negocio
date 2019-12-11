import { Route } from '@angular/router';

import { RvHealthCheckComponent } from './health.component';

export const healthRoute: Route = {
  path: '',
  component: RvHealthCheckComponent,
  data: {
    pageTitle: 'health.title'
  }
};
