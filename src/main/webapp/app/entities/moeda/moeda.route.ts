import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Moeda } from 'app/shared/model/moeda.model';
import { MoedaService } from './moeda.service';
import { MoedaComponent } from './moeda.component';
import { MoedaDetailComponent } from './moeda-detail.component';
import { MoedaUpdateComponent } from './moeda-update.component';
import { IMoeda } from 'app/shared/model/moeda.model';

@Injectable({ providedIn: 'root' })
export class MoedaResolve implements Resolve<IMoeda> {
  constructor(private service: MoedaService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMoeda> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((moeda: HttpResponse<Moeda>) => moeda.body));
    }
    return of(new Moeda());
  }
}

export const moedaRoute: Routes = [
  {
    path: '',
    component: MoedaComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'mandaApp.moeda.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: MoedaDetailComponent,
    resolve: {
      moeda: MoedaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.moeda.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: MoedaUpdateComponent,
    resolve: {
      moeda: MoedaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.moeda.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: MoedaUpdateComponent,
    resolve: {
      moeda: MoedaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.moeda.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
