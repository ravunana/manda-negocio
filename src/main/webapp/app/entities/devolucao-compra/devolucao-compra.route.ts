import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { DevolucaoCompra } from 'app/shared/model/devolucao-compra.model';
import { DevolucaoCompraService } from './devolucao-compra.service';
import { DevolucaoCompraComponent } from './devolucao-compra.component';
import { DevolucaoCompraDetailComponent } from './devolucao-compra-detail.component';
import { DevolucaoCompraUpdateComponent } from './devolucao-compra-update.component';
import { IDevolucaoCompra } from 'app/shared/model/devolucao-compra.model';

@Injectable({ providedIn: 'root' })
export class DevolucaoCompraResolve implements Resolve<IDevolucaoCompra> {
  constructor(private service: DevolucaoCompraService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDevolucaoCompra> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((devolucaoCompra: HttpResponse<DevolucaoCompra>) => devolucaoCompra.body));
    }
    return of(new DevolucaoCompra());
  }
}

export const devolucaoCompraRoute: Routes = [
  {
    path: '',
    component: DevolucaoCompraComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'mandaApp.devolucaoCompra.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: DevolucaoCompraDetailComponent,
    resolve: {
      devolucaoCompra: DevolucaoCompraResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.devolucaoCompra.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: DevolucaoCompraUpdateComponent,
    resolve: {
      devolucaoCompra: DevolucaoCompraResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.devolucaoCompra.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: DevolucaoCompraUpdateComponent,
    resolve: {
      devolucaoCompra: DevolucaoCompraResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.devolucaoCompra.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
