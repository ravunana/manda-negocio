import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { LicencaSoftware } from 'app/shared/model/licenca-software.model';
import { LicencaSoftwareService } from './licenca-software.service';
import { LicencaSoftwareComponent } from './licenca-software.component';
import { LicencaSoftwareDetailComponent } from './licenca-software-detail.component';
import { LicencaSoftwareUpdateComponent } from './licenca-software-update.component';
import { ILicencaSoftware } from 'app/shared/model/licenca-software.model';

@Injectable({ providedIn: 'root' })
export class LicencaSoftwareResolve implements Resolve<ILicencaSoftware> {
  constructor(private service: LicencaSoftwareService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILicencaSoftware> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((licencaSoftware: HttpResponse<LicencaSoftware>) => licencaSoftware.body));
    }
    return of(new LicencaSoftware());
  }
}

export const licencaSoftwareRoute: Routes = [
  {
    path: '',
    component: LicencaSoftwareComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'mandaApp.licencaSoftware.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: LicencaSoftwareDetailComponent,
    resolve: {
      licencaSoftware: LicencaSoftwareResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.licencaSoftware.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: LicencaSoftwareUpdateComponent,
    resolve: {
      licencaSoftware: LicencaSoftwareResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.licencaSoftware.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: LicencaSoftwareUpdateComponent,
    resolve: {
      licencaSoftware: LicencaSoftwareResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.licencaSoftware.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
