import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { EstoqueConfig } from 'app/shared/model/estoque-config.model';
import { EstoqueConfigService } from './estoque-config.service';
import { EstoqueConfigComponent } from './estoque-config.component';
import { EstoqueConfigDetailComponent } from './estoque-config-detail.component';
import { EstoqueConfigUpdateComponent } from './estoque-config-update.component';
import { IEstoqueConfig } from 'app/shared/model/estoque-config.model';

@Injectable({ providedIn: 'root' })
export class EstoqueConfigResolve implements Resolve<IEstoqueConfig> {
  constructor(private service: EstoqueConfigService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEstoqueConfig> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((estoqueConfig: HttpResponse<EstoqueConfig>) => estoqueConfig.body));
    }
    return of(new EstoqueConfig());
  }
}

export const estoqueConfigRoute: Routes = [
  {
    path: '',
    component: EstoqueConfigComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'mandaApp.estoqueConfig.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: EstoqueConfigDetailComponent,
    resolve: {
      estoqueConfig: EstoqueConfigResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.estoqueConfig.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: EstoqueConfigUpdateComponent,
    resolve: {
      estoqueConfig: EstoqueConfigResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.estoqueConfig.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: EstoqueConfigUpdateComponent,
    resolve: {
      estoqueConfig: EstoqueConfigResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.estoqueConfig.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
