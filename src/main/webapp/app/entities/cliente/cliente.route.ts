import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Cliente } from 'app/shared/model/cliente.model';
import { ClienteService } from './cliente.service';
import { ClienteComponent } from './cliente.component';
import { ClienteDetailComponent } from './cliente-detail.component';
import { ClienteUpdateComponent } from './cliente-update.component';
import { ICliente } from 'app/shared/model/cliente.model';

@Injectable({ providedIn: 'root' })
export class ClienteResolve implements Resolve<ICliente> {
  constructor(private service: ClienteService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICliente> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((cliente: HttpResponse<Cliente>) => cliente.body));
    }
    return of(new Cliente());
  }
}

export const clienteRoute: Routes = [
  {
    path: '',
    component: ClienteComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'mandaApp.cliente.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ClienteDetailComponent,
    resolve: {
      cliente: ClienteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.cliente.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ClienteUpdateComponent,
    resolve: {
      cliente: ClienteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.cliente.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ClienteUpdateComponent,
    resolve: {
      cliente: ClienteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.cliente.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
