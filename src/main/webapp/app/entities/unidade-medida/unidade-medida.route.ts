import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { UnidadeMedida } from 'app/shared/model/unidade-medida.model';
import { UnidadeMedidaService } from './unidade-medida.service';
import { UnidadeMedidaComponent } from './unidade-medida.component';
import { UnidadeMedidaDetailComponent } from './unidade-medida-detail.component';
import { UnidadeMedidaUpdateComponent } from './unidade-medida-update.component';
import { IUnidadeMedida } from 'app/shared/model/unidade-medida.model';

@Injectable({ providedIn: 'root' })
export class UnidadeMedidaResolve implements Resolve<IUnidadeMedida> {
  constructor(private service: UnidadeMedidaService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IUnidadeMedida> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((unidadeMedida: HttpResponse<UnidadeMedida>) => unidadeMedida.body));
    }
    return of(new UnidadeMedida());
  }
}

export const unidadeMedidaRoute: Routes = [
  {
    path: '',
    component: UnidadeMedidaComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'mandaApp.unidadeMedida.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: UnidadeMedidaDetailComponent,
    resolve: {
      unidadeMedida: UnidadeMedidaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.unidadeMedida.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: UnidadeMedidaUpdateComponent,
    resolve: {
      unidadeMedida: UnidadeMedidaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.unidadeMedida.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: UnidadeMedidaUpdateComponent,
    resolve: {
      unidadeMedida: UnidadeMedidaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.unidadeMedida.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
