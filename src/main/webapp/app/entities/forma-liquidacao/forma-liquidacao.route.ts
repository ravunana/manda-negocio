import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { FormaLiquidacao } from 'app/shared/model/forma-liquidacao.model';
import { FormaLiquidacaoService } from './forma-liquidacao.service';
import { FormaLiquidacaoComponent } from './forma-liquidacao.component';
import { FormaLiquidacaoDetailComponent } from './forma-liquidacao-detail.component';
import { FormaLiquidacaoUpdateComponent } from './forma-liquidacao-update.component';
import { IFormaLiquidacao } from 'app/shared/model/forma-liquidacao.model';

@Injectable({ providedIn: 'root' })
export class FormaLiquidacaoResolve implements Resolve<IFormaLiquidacao> {
  constructor(private service: FormaLiquidacaoService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFormaLiquidacao> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((formaLiquidacao: HttpResponse<FormaLiquidacao>) => formaLiquidacao.body));
    }
    return of(new FormaLiquidacao());
  }
}

export const formaLiquidacaoRoute: Routes = [
  {
    path: '',
    component: FormaLiquidacaoComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'mandaApp.formaLiquidacao.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: FormaLiquidacaoDetailComponent,
    resolve: {
      formaLiquidacao: FormaLiquidacaoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.formaLiquidacao.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: FormaLiquidacaoUpdateComponent,
    resolve: {
      formaLiquidacao: FormaLiquidacaoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.formaLiquidacao.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: FormaLiquidacaoUpdateComponent,
    resolve: {
      formaLiquidacao: FormaLiquidacaoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.formaLiquidacao.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
