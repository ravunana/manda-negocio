import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { LocalArmazenamento } from 'app/shared/model/local-armazenamento.model';
import { LocalArmazenamentoService } from './local-armazenamento.service';
import { LocalArmazenamentoComponent } from './local-armazenamento.component';
import { LocalArmazenamentoDetailComponent } from './local-armazenamento-detail.component';
import { LocalArmazenamentoUpdateComponent } from './local-armazenamento-update.component';
import { ILocalArmazenamento } from 'app/shared/model/local-armazenamento.model';

@Injectable({ providedIn: 'root' })
export class LocalArmazenamentoResolve implements Resolve<ILocalArmazenamento> {
  constructor(private service: LocalArmazenamentoService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILocalArmazenamento> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((localArmazenamento: HttpResponse<LocalArmazenamento>) => localArmazenamento.body));
    }
    return of(new LocalArmazenamento());
  }
}

export const localArmazenamentoRoute: Routes = [
  {
    path: '',
    component: LocalArmazenamentoComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'mandaApp.localArmazenamento.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: LocalArmazenamentoDetailComponent,
    resolve: {
      localArmazenamento: LocalArmazenamentoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.localArmazenamento.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: LocalArmazenamentoUpdateComponent,
    resolve: {
      localArmazenamento: LocalArmazenamentoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.localArmazenamento.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: LocalArmazenamentoUpdateComponent,
    resolve: {
      localArmazenamento: LocalArmazenamentoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.localArmazenamento.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
