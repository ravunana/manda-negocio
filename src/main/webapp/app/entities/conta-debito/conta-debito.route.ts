import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { ContaDebito } from 'app/shared/model/conta-debito.model';
import { ContaDebitoService } from './conta-debito.service';
import { ContaDebitoComponent } from './conta-debito.component';
import { ContaDebitoDetailComponent } from './conta-debito-detail.component';
import { ContaDebitoUpdateComponent } from './conta-debito-update.component';
import { IContaDebito } from 'app/shared/model/conta-debito.model';

@Injectable({ providedIn: 'root' })
export class ContaDebitoResolve implements Resolve<IContaDebito> {
  constructor(private service: ContaDebitoService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IContaDebito> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((contaDebito: HttpResponse<ContaDebito>) => contaDebito.body));
    }
    return of(new ContaDebito());
  }
}

export const contaDebitoRoute: Routes = [
  {
    path: '',
    component: ContaDebitoComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'mandaApp.contaDebito.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ContaDebitoDetailComponent,
    resolve: {
      contaDebito: ContaDebitoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.contaDebito.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ContaDebitoUpdateComponent,
    resolve: {
      contaDebito: ContaDebitoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.contaDebito.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ContaDebitoUpdateComponent,
    resolve: {
      contaDebito: ContaDebitoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.contaDebito.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
