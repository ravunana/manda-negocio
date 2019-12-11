import { Route } from '@angular/router';

import { RvDocsComponent } from './docs.component';

export const docsRoute: Route = {
  path: '',
  component: RvDocsComponent,
  data: {
    pageTitle: 'global.menu.admin.apidocs'
  }
};
