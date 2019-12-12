import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { CoordenadaBancaria } from 'app/shared/model/coordenada-bancaria.model';
import { CoordenadaBancariaService } from './coordenada-bancaria.service';
import { CoordenadaBancariaComponent } from './coordenada-bancaria.component';
import { CoordenadaBancariaDetailComponent } from './coordenada-bancaria-detail.component';
import { CoordenadaBancariaUpdateComponent } from './coordenada-bancaria-update.component';
import { ICoordenadaBancaria } from 'app/shared/model/coordenada-bancaria.model';

@Injectable({ providedIn: 'root' })
export class CoordenadaBancariaResolve implements Resolve<ICoordenadaBancaria> {
  constructor(private service: CoordenadaBancariaService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICoordenadaBancaria> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((coordenadaBancaria: HttpResponse<CoordenadaBancaria>) => coordenadaBancaria.body));
    }
    return of(new CoordenadaBancaria());
  }
}

export const coordenadaBancariaRoute: Routes = [
  {
    path: '',
    component: CoordenadaBancariaComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'mandaApp.coordenadaBancaria.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CoordenadaBancariaDetailComponent,
    resolve: {
      coordenadaBancaria: CoordenadaBancariaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.coordenadaBancaria.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CoordenadaBancariaUpdateComponent,
    resolve: {
      coordenadaBancaria: CoordenadaBancariaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.coordenadaBancaria.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CoordenadaBancariaUpdateComponent,
    resolve: {
      coordenadaBancaria: CoordenadaBancariaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.coordenadaBancaria.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
