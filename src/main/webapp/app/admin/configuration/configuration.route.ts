import { Route } from '@angular/router';

import { RvConfigurationComponent } from './configuration.component';

export const configurationRoute: Route = {
  path: '',
  component: RvConfigurationComponent,
  data: {
    pageTitle: 'configuration.title'
  }
};
