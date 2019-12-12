import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { EscrituracaoContabil } from 'app/shared/model/escrituracao-contabil.model';
import { EscrituracaoContabilService } from './escrituracao-contabil.service';
import { EscrituracaoContabilComponent } from './escrituracao-contabil.component';
import { EscrituracaoContabilDetailComponent } from './escrituracao-contabil-detail.component';
import { EscrituracaoContabilUpdateComponent } from './escrituracao-contabil-update.component';
import { IEscrituracaoContabil } from 'app/shared/model/escrituracao-contabil.model';

@Injectable({ providedIn: 'root' })
export class EscrituracaoContabilResolve implements Resolve<IEscrituracaoContabil> {
  constructor(private service: EscrituracaoContabilService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEscrituracaoContabil> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((escrituracaoContabil: HttpResponse<EscrituracaoContabil>) => escrituracaoContabil.body));
    }
    return of(new EscrituracaoContabil());
  }
}

export const escrituracaoContabilRoute: Routes = [
  {
    path: '',
    component: EscrituracaoContabilComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'mandaApp.escrituracaoContabil.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: EscrituracaoContabilDetailComponent,
    resolve: {
      escrituracaoContabil: EscrituracaoContabilResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.escrituracaoContabil.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: EscrituracaoContabilUpdateComponent,
    resolve: {
      escrituracaoContabil: EscrituracaoContabilResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.escrituracaoContabil.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: EscrituracaoContabilUpdateComponent,
    resolve: {
      escrituracaoContabil: EscrituracaoContabilResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.escrituracaoContabil.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
