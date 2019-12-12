import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Software } from 'app/shared/model/software.model';
import { SoftwareService } from './software.service';
import { SoftwareComponent } from './software.component';
import { SoftwareDetailComponent } from './software-detail.component';
import { SoftwareUpdateComponent } from './software-update.component';
import { ISoftware } from 'app/shared/model/software.model';

@Injectable({ providedIn: 'root' })
export class SoftwareResolve implements Resolve<ISoftware> {
  constructor(private service: SoftwareService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISoftware> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((software: HttpResponse<Software>) => software.body));
    }
    return of(new Software());
  }
}

export const softwareRoute: Routes = [
  {
    path: '',
    component: SoftwareComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'mandaApp.software.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SoftwareDetailComponent,
    resolve: {
      software: SoftwareResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.software.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SoftwareUpdateComponent,
    resolve: {
      software: SoftwareResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.software.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SoftwareUpdateComponent,
    resolve: {
      software: SoftwareResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.software.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
