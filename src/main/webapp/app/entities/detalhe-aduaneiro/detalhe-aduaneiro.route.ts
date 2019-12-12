import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { DetalheAduaneiro } from 'app/shared/model/detalhe-aduaneiro.model';
import { DetalheAduaneiroService } from './detalhe-aduaneiro.service';
import { DetalheAduaneiroComponent } from './detalhe-aduaneiro.component';
import { DetalheAduaneiroDetailComponent } from './detalhe-aduaneiro-detail.component';
import { DetalheAduaneiroUpdateComponent } from './detalhe-aduaneiro-update.component';
import { IDetalheAduaneiro } from 'app/shared/model/detalhe-aduaneiro.model';

@Injectable({ providedIn: 'root' })
export class DetalheAduaneiroResolve implements Resolve<IDetalheAduaneiro> {
  constructor(private service: DetalheAduaneiroService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDetalheAduaneiro> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((detalheAduaneiro: HttpResponse<DetalheAduaneiro>) => detalheAduaneiro.body));
    }
    return of(new DetalheAduaneiro());
  }
}

export const detalheAduaneiroRoute: Routes = [
  {
    path: '',
    component: DetalheAduaneiroComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'mandaApp.detalheAduaneiro.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: DetalheAduaneiroDetailComponent,
    resolve: {
      detalheAduaneiro: DetalheAduaneiroResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.detalheAduaneiro.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: DetalheAduaneiroUpdateComponent,
    resolve: {
      detalheAduaneiro: DetalheAduaneiroResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.detalheAduaneiro.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: DetalheAduaneiroUpdateComponent,
    resolve: {
      detalheAduaneiro: DetalheAduaneiroResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.detalheAduaneiro.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
