import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { VariacaoProduto } from 'app/shared/model/variacao-produto.model';
import { VariacaoProdutoService } from './variacao-produto.service';
import { VariacaoProdutoComponent } from './variacao-produto.component';
import { VariacaoProdutoDetailComponent } from './variacao-produto-detail.component';
import { VariacaoProdutoUpdateComponent } from './variacao-produto-update.component';
import { IVariacaoProduto } from 'app/shared/model/variacao-produto.model';

@Injectable({ providedIn: 'root' })
export class VariacaoProdutoResolve implements Resolve<IVariacaoProduto> {
  constructor(private service: VariacaoProdutoService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IVariacaoProduto> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((variacaoProduto: HttpResponse<VariacaoProduto>) => variacaoProduto.body));
    }
    return of(new VariacaoProduto());
  }
}

export const variacaoProdutoRoute: Routes = [
  {
    path: '',
    component: VariacaoProdutoComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'mandaApp.variacaoProduto.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: VariacaoProdutoDetailComponent,
    resolve: {
      variacaoProduto: VariacaoProdutoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.variacaoProduto.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: VariacaoProdutoUpdateComponent,
    resolve: {
      variacaoProduto: VariacaoProdutoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.variacaoProduto.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: VariacaoProdutoUpdateComponent,
    resolve: {
      variacaoProduto: VariacaoProdutoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.variacaoProduto.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
