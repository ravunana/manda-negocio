import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Lookup } from 'app/shared/model/lookup.model';
import { LookupService } from './lookup.service';
import { LookupComponent } from './lookup.component';
import { LookupDetailComponent } from './lookup-detail.component';
import { LookupUpdateComponent } from './lookup-update.component';
import { ILookup } from 'app/shared/model/lookup.model';

@Injectable({ providedIn: 'root' })
export class LookupResolve implements Resolve<ILookup> {
  constructor(private service: LookupService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILookup> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((lookup: HttpResponse<Lookup>) => lookup.body));
    }
    return of(new Lookup());
  }
}

export const lookupRoute: Routes = [
  {
    path: '',
    component: LookupComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'mandaApp.lookup.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: LookupDetailComponent,
    resolve: {
      lookup: LookupResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.lookup.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: LookupUpdateComponent,
    resolve: {
      lookup: LookupResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.lookup.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: LookupUpdateComponent,
    resolve: {
      lookup: LookupResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.lookup.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
