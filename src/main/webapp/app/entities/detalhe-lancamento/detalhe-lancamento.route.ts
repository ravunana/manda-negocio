import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { DetalheLancamento } from 'app/shared/model/detalhe-lancamento.model';
import { DetalheLancamentoService } from './detalhe-lancamento.service';
import { DetalheLancamentoComponent } from './detalhe-lancamento.component';
import { DetalheLancamentoDetailComponent } from './detalhe-lancamento-detail.component';
import { DetalheLancamentoUpdateComponent } from './detalhe-lancamento-update.component';
import { IDetalheLancamento } from 'app/shared/model/detalhe-lancamento.model';

@Injectable({ providedIn: 'root' })
export class DetalheLancamentoResolve implements Resolve<IDetalheLancamento> {
  constructor(private service: DetalheLancamentoService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDetalheLancamento> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((detalheLancamento: HttpResponse<DetalheLancamento>) => detalheLancamento.body));
    }
    return of(new DetalheLancamento());
  }
}

export const detalheLancamentoRoute: Routes = [
  {
    path: '',
    component: DetalheLancamentoComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'mandaApp.detalheLancamento.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: DetalheLancamentoDetailComponent,
    resolve: {
      detalheLancamento: DetalheLancamentoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.detalheLancamento.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: DetalheLancamentoUpdateComponent,
    resolve: {
      detalheLancamento: DetalheLancamentoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.detalheLancamento.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: DetalheLancamentoUpdateComponent,
    resolve: {
      detalheLancamento: DetalheLancamentoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.detalheLancamento.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
