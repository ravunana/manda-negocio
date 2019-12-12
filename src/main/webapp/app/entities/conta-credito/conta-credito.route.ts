import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { ContaCredito } from 'app/shared/model/conta-credito.model';
import { ContaCreditoService } from './conta-credito.service';
import { ContaCreditoComponent } from './conta-credito.component';
import { ContaCreditoDetailComponent } from './conta-credito-detail.component';
import { ContaCreditoUpdateComponent } from './conta-credito-update.component';
import { IContaCredito } from 'app/shared/model/conta-credito.model';

@Injectable({ providedIn: 'root' })
export class ContaCreditoResolve implements Resolve<IContaCredito> {
  constructor(private service: ContaCreditoService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IContaCredito> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((contaCredito: HttpResponse<ContaCredito>) => contaCredito.body));
    }
    return of(new ContaCredito());
  }
}

export const contaCreditoRoute: Routes = [
  {
    path: '',
    component: ContaCreditoComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'mandaApp.contaCredito.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ContaCreditoDetailComponent,
    resolve: {
      contaCredito: ContaCreditoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.contaCredito.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ContaCreditoUpdateComponent,
    resolve: {
      contaCredito: ContaCreditoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.contaCredito.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ContaCreditoUpdateComponent,
    resolve: {
      contaCredito: ContaCreditoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.contaCredito.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
