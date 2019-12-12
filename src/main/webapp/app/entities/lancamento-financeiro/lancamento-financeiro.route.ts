import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { LancamentoFinanceiro } from 'app/shared/model/lancamento-financeiro.model';
import { LancamentoFinanceiroService } from './lancamento-financeiro.service';
import { LancamentoFinanceiroComponent } from './lancamento-financeiro.component';
import { LancamentoFinanceiroDetailComponent } from './lancamento-financeiro-detail.component';
import { LancamentoFinanceiroUpdateComponent } from './lancamento-financeiro-update.component';
import { ILancamentoFinanceiro } from 'app/shared/model/lancamento-financeiro.model';

@Injectable({ providedIn: 'root' })
export class LancamentoFinanceiroResolve implements Resolve<ILancamentoFinanceiro> {
  constructor(private service: LancamentoFinanceiroService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILancamentoFinanceiro> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((lancamentoFinanceiro: HttpResponse<LancamentoFinanceiro>) => lancamentoFinanceiro.body));
    }
    return of(new LancamentoFinanceiro());
  }
}

export const lancamentoFinanceiroRoute: Routes = [
  {
    path: '',
    component: LancamentoFinanceiroComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'mandaApp.lancamentoFinanceiro.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: LancamentoFinanceiroDetailComponent,
    resolve: {
      lancamentoFinanceiro: LancamentoFinanceiroResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.lancamentoFinanceiro.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: LancamentoFinanceiroUpdateComponent,
    resolve: {
      lancamentoFinanceiro: LancamentoFinanceiroResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.lancamentoFinanceiro.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: LancamentoFinanceiroUpdateComponent,
    resolve: {
      lancamentoFinanceiro: LancamentoFinanceiroResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.lancamentoFinanceiro.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
