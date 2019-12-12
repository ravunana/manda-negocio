import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Imposto } from 'app/shared/model/imposto.model';
import { ImpostoService } from './imposto.service';
import { ImpostoComponent } from './imposto.component';
import { ImpostoDetailComponent } from './imposto-detail.component';
import { ImpostoUpdateComponent } from './imposto-update.component';
import { IImposto } from 'app/shared/model/imposto.model';

@Injectable({ providedIn: 'root' })
export class ImpostoResolve implements Resolve<IImposto> {
  constructor(private service: ImpostoService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IImposto> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((imposto: HttpResponse<Imposto>) => imposto.body));
    }
    return of(new Imposto());
  }
}

export const impostoRoute: Routes = [
  {
    path: '',
    component: ImpostoComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'mandaApp.imposto.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ImpostoDetailComponent,
    resolve: {
      imposto: ImpostoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.imposto.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ImpostoUpdateComponent,
    resolve: {
      imposto: ImpostoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.imposto.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ImpostoUpdateComponent,
    resolve: {
      imposto: ImpostoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.imposto.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
