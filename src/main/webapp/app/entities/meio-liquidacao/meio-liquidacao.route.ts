import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { MeioLiquidacao } from 'app/shared/model/meio-liquidacao.model';
import { MeioLiquidacaoService } from './meio-liquidacao.service';
import { MeioLiquidacaoComponent } from './meio-liquidacao.component';
import { MeioLiquidacaoDetailComponent } from './meio-liquidacao-detail.component';
import { MeioLiquidacaoUpdateComponent } from './meio-liquidacao-update.component';
import { IMeioLiquidacao } from 'app/shared/model/meio-liquidacao.model';

@Injectable({ providedIn: 'root' })
export class MeioLiquidacaoResolve implements Resolve<IMeioLiquidacao> {
  constructor(private service: MeioLiquidacaoService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMeioLiquidacao> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((meioLiquidacao: HttpResponse<MeioLiquidacao>) => meioLiquidacao.body));
    }
    return of(new MeioLiquidacao());
  }
}

export const meioLiquidacaoRoute: Routes = [
  {
    path: '',
    component: MeioLiquidacaoComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'mandaApp.meioLiquidacao.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: MeioLiquidacaoDetailComponent,
    resolve: {
      meioLiquidacao: MeioLiquidacaoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.meioLiquidacao.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: MeioLiquidacaoUpdateComponent,
    resolve: {
      meioLiquidacao: MeioLiquidacaoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.meioLiquidacao.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: MeioLiquidacaoUpdateComponent,
    resolve: {
      meioLiquidacao: MeioLiquidacaoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.meioLiquidacao.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
