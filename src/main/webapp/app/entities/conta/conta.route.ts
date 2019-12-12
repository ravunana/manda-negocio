import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Conta } from 'app/shared/model/conta.model';
import { ContaService } from './conta.service';
import { ContaComponent } from './conta.component';
import { ContaDetailComponent } from './conta-detail.component';
import { ContaUpdateComponent } from './conta-update.component';
import { IConta } from 'app/shared/model/conta.model';

@Injectable({ providedIn: 'root' })
export class ContaResolve implements Resolve<IConta> {
  constructor(private service: ContaService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IConta> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((conta: HttpResponse<Conta>) => conta.body));
    }
    return of(new Conta());
  }
}

export const contaRoute: Routes = [
  {
    path: '',
    component: ContaComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'mandaApp.conta.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ContaDetailComponent,
    resolve: {
      conta: ContaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.conta.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ContaUpdateComponent,
    resolve: {
      conta: ContaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.conta.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ContaUpdateComponent,
    resolve: {
      conta: ContaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.conta.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
