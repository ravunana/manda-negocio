import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { CompostoProduto } from 'app/shared/model/composto-produto.model';
import { CompostoProdutoService } from './composto-produto.service';
import { CompostoProdutoComponent } from './composto-produto.component';
import { CompostoProdutoDetailComponent } from './composto-produto-detail.component';
import { CompostoProdutoUpdateComponent } from './composto-produto-update.component';
import { ICompostoProduto } from 'app/shared/model/composto-produto.model';

@Injectable({ providedIn: 'root' })
export class CompostoProdutoResolve implements Resolve<ICompostoProduto> {
  constructor(private service: CompostoProdutoService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICompostoProduto> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((compostoProduto: HttpResponse<CompostoProduto>) => compostoProduto.body));
    }
    return of(new CompostoProduto());
  }
}

export const compostoProdutoRoute: Routes = [
  {
    path: '',
    component: CompostoProdutoComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'mandaApp.compostoProduto.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CompostoProdutoDetailComponent,
    resolve: {
      compostoProduto: CompostoProdutoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.compostoProduto.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CompostoProdutoUpdateComponent,
    resolve: {
      compostoProduto: CompostoProdutoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.compostoProduto.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CompostoProdutoUpdateComponent,
    resolve: {
      compostoProduto: CompostoProdutoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.compostoProduto.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
