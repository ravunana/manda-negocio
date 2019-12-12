import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { FamiliaProduto } from 'app/shared/model/familia-produto.model';
import { FamiliaProdutoService } from './familia-produto.service';
import { FamiliaProdutoComponent } from './familia-produto.component';
import { FamiliaProdutoDetailComponent } from './familia-produto-detail.component';
import { FamiliaProdutoUpdateComponent } from './familia-produto-update.component';
import { IFamiliaProduto } from 'app/shared/model/familia-produto.model';

@Injectable({ providedIn: 'root' })
export class FamiliaProdutoResolve implements Resolve<IFamiliaProduto> {
  constructor(private service: FamiliaProdutoService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFamiliaProduto> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((familiaProduto: HttpResponse<FamiliaProduto>) => familiaProduto.body));
    }
    return of(new FamiliaProduto());
  }
}

export const familiaProdutoRoute: Routes = [
  {
    path: '',
    component: FamiliaProdutoComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'mandaApp.familiaProduto.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: FamiliaProdutoDetailComponent,
    resolve: {
      familiaProduto: FamiliaProdutoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.familiaProduto.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: FamiliaProdutoUpdateComponent,
    resolve: {
      familiaProduto: FamiliaProdutoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.familiaProduto.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: FamiliaProdutoUpdateComponent,
    resolve: {
      familiaProduto: FamiliaProdutoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.familiaProduto.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
