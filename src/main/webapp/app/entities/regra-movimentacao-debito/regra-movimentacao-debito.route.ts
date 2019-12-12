import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { RegraMovimentacaoDebito } from 'app/shared/model/regra-movimentacao-debito.model';
import { RegraMovimentacaoDebitoService } from './regra-movimentacao-debito.service';
import { RegraMovimentacaoDebitoComponent } from './regra-movimentacao-debito.component';
import { RegraMovimentacaoDebitoDetailComponent } from './regra-movimentacao-debito-detail.component';
import { RegraMovimentacaoDebitoUpdateComponent } from './regra-movimentacao-debito-update.component';
import { IRegraMovimentacaoDebito } from 'app/shared/model/regra-movimentacao-debito.model';

@Injectable({ providedIn: 'root' })
export class RegraMovimentacaoDebitoResolve implements Resolve<IRegraMovimentacaoDebito> {
  constructor(private service: RegraMovimentacaoDebitoService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IRegraMovimentacaoDebito> {
    const id = route.params['id'];
    if (id) {
      return this.service
        .find(id)
        .pipe(map((regraMovimentacaoDebito: HttpResponse<RegraMovimentacaoDebito>) => regraMovimentacaoDebito.body));
    }
    return of(new RegraMovimentacaoDebito());
  }
}

export const regraMovimentacaoDebitoRoute: Routes = [
  {
    path: '',
    component: RegraMovimentacaoDebitoComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'mandaApp.regraMovimentacaoDebito.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: RegraMovimentacaoDebitoDetailComponent,
    resolve: {
      regraMovimentacaoDebito: RegraMovimentacaoDebitoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.regraMovimentacaoDebito.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: RegraMovimentacaoDebitoUpdateComponent,
    resolve: {
      regraMovimentacaoDebito: RegraMovimentacaoDebitoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.regraMovimentacaoDebito.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: RegraMovimentacaoDebitoUpdateComponent,
    resolve: {
      regraMovimentacaoDebito: RegraMovimentacaoDebitoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.regraMovimentacaoDebito.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
