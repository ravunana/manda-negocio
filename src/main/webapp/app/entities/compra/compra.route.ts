import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Compra } from 'app/shared/model/compra.model';
import { CompraService } from './compra.service';
import { CompraComponent } from './compra.component';
import { CompraDetailComponent } from './compra-detail.component';
import { CompraUpdateComponent } from './compra-update.component';
import { ICompra } from 'app/shared/model/compra.model';

@Injectable({ providedIn: 'root' })
export class CompraResolve implements Resolve<ICompra> {
  constructor(private service: CompraService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICompra> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((compra: HttpResponse<Compra>) => compra.body));
    }
    return of(new Compra());
  }
}

export const compraRoute: Routes = [
  {
    path: '',
    component: CompraComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'mandaApp.compra.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CompraDetailComponent,
    resolve: {
      compra: CompraResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.compra.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CompraUpdateComponent,
    resolve: {
      compra: CompraResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.compra.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CompraUpdateComponent,
    resolve: {
      compra: CompraResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.compra.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
