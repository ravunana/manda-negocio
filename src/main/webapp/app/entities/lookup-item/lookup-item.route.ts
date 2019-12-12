import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { LookupItem } from 'app/shared/model/lookup-item.model';
import { LookupItemService } from './lookup-item.service';
import { LookupItemComponent } from './lookup-item.component';
import { LookupItemDetailComponent } from './lookup-item-detail.component';
import { LookupItemUpdateComponent } from './lookup-item-update.component';
import { ILookupItem } from 'app/shared/model/lookup-item.model';

@Injectable({ providedIn: 'root' })
export class LookupItemResolve implements Resolve<ILookupItem> {
  constructor(private service: LookupItemService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILookupItem> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((lookupItem: HttpResponse<LookupItem>) => lookupItem.body));
    }
    return of(new LookupItem());
  }
}

export const lookupItemRoute: Routes = [
  {
    path: '',
    component: LookupItemComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'mandaApp.lookupItem.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: LookupItemDetailComponent,
    resolve: {
      lookupItem: LookupItemResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.lookupItem.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: LookupItemUpdateComponent,
    resolve: {
      lookupItem: LookupItemResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.lookupItem.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: LookupItemUpdateComponent,
    resolve: {
      lookupItem: LookupItemResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.lookupItem.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
