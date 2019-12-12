import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { AuditoriaCompra } from 'app/shared/model/auditoria-compra.model';
import { AuditoriaCompraService } from './auditoria-compra.service';
import { AuditoriaCompraComponent } from './auditoria-compra.component';
import { AuditoriaCompraDetailComponent } from './auditoria-compra-detail.component';
import { AuditoriaCompraUpdateComponent } from './auditoria-compra-update.component';
import { IAuditoriaCompra } from 'app/shared/model/auditoria-compra.model';

@Injectable({ providedIn: 'root' })
export class AuditoriaCompraResolve implements Resolve<IAuditoriaCompra> {
  constructor(private service: AuditoriaCompraService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAuditoriaCompra> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((auditoriaCompra: HttpResponse<AuditoriaCompra>) => auditoriaCompra.body));
    }
    return of(new AuditoriaCompra());
  }
}

export const auditoriaCompraRoute: Routes = [
  {
    path: '',
    component: AuditoriaCompraComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'mandaApp.auditoriaCompra.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AuditoriaCompraDetailComponent,
    resolve: {
      auditoriaCompra: AuditoriaCompraResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.auditoriaCompra.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AuditoriaCompraUpdateComponent,
    resolve: {
      auditoriaCompra: AuditoriaCompraResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.auditoriaCompra.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AuditoriaCompraUpdateComponent,
    resolve: {
      auditoriaCompra: AuditoriaCompraResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.auditoriaCompra.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
