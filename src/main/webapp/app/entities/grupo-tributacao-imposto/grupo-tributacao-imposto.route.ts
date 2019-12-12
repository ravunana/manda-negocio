import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { GrupoTributacaoImposto } from 'app/shared/model/grupo-tributacao-imposto.model';
import { GrupoTributacaoImpostoService } from './grupo-tributacao-imposto.service';
import { GrupoTributacaoImpostoComponent } from './grupo-tributacao-imposto.component';
import { GrupoTributacaoImpostoDetailComponent } from './grupo-tributacao-imposto-detail.component';
import { GrupoTributacaoImpostoUpdateComponent } from './grupo-tributacao-imposto-update.component';
import { IGrupoTributacaoImposto } from 'app/shared/model/grupo-tributacao-imposto.model';

@Injectable({ providedIn: 'root' })
export class GrupoTributacaoImpostoResolve implements Resolve<IGrupoTributacaoImposto> {
  constructor(private service: GrupoTributacaoImpostoService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IGrupoTributacaoImposto> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((grupoTributacaoImposto: HttpResponse<GrupoTributacaoImposto>) => grupoTributacaoImposto.body));
    }
    return of(new GrupoTributacaoImposto());
  }
}

export const grupoTributacaoImpostoRoute: Routes = [
  {
    path: '',
    component: GrupoTributacaoImpostoComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'mandaApp.grupoTributacaoImposto.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: GrupoTributacaoImpostoDetailComponent,
    resolve: {
      grupoTributacaoImposto: GrupoTributacaoImpostoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.grupoTributacaoImposto.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: GrupoTributacaoImpostoUpdateComponent,
    resolve: {
      grupoTributacaoImposto: GrupoTributacaoImpostoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.grupoTributacaoImposto.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: GrupoTributacaoImpostoUpdateComponent,
    resolve: {
      grupoTributacaoImposto: GrupoTributacaoImpostoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.grupoTributacaoImposto.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
