import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { RegraMovimentacaoCredito } from 'app/shared/model/regra-movimentacao-credito.model';
import { RegraMovimentacaoCreditoService } from './regra-movimentacao-credito.service';
import { RegraMovimentacaoCreditoComponent } from './regra-movimentacao-credito.component';
import { RegraMovimentacaoCreditoDetailComponent } from './regra-movimentacao-credito-detail.component';
import { RegraMovimentacaoCreditoUpdateComponent } from './regra-movimentacao-credito-update.component';
import { IRegraMovimentacaoCredito } from 'app/shared/model/regra-movimentacao-credito.model';

@Injectable({ providedIn: 'root' })
export class RegraMovimentacaoCreditoResolve implements Resolve<IRegraMovimentacaoCredito> {
  constructor(private service: RegraMovimentacaoCreditoService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IRegraMovimentacaoCredito> {
    const id = route.params['id'];
    if (id) {
      return this.service
        .find(id)
        .pipe(map((regraMovimentacaoCredito: HttpResponse<RegraMovimentacaoCredito>) => regraMovimentacaoCredito.body));
    }
    return of(new RegraMovimentacaoCredito());
  }
}

export const regraMovimentacaoCreditoRoute: Routes = [
  {
    path: '',
    component: RegraMovimentacaoCreditoComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'mandaApp.regraMovimentacaoCredito.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: RegraMovimentacaoCreditoDetailComponent,
    resolve: {
      regraMovimentacaoCredito: RegraMovimentacaoCreditoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.regraMovimentacaoCredito.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: RegraMovimentacaoCreditoUpdateComponent,
    resolve: {
      regraMovimentacaoCredito: RegraMovimentacaoCreditoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.regraMovimentacaoCredito.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: RegraMovimentacaoCreditoUpdateComponent,
    resolve: {
      regraMovimentacaoCredito: RegraMovimentacaoCreditoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.regraMovimentacaoCredito.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
