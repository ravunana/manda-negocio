import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { RetencaoFonte } from 'app/shared/model/retencao-fonte.model';
import { RetencaoFonteService } from './retencao-fonte.service';
import { RetencaoFonteComponent } from './retencao-fonte.component';
import { RetencaoFonteDetailComponent } from './retencao-fonte-detail.component';
import { RetencaoFonteUpdateComponent } from './retencao-fonte-update.component';
import { IRetencaoFonte } from 'app/shared/model/retencao-fonte.model';

@Injectable({ providedIn: 'root' })
export class RetencaoFonteResolve implements Resolve<IRetencaoFonte> {
  constructor(private service: RetencaoFonteService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IRetencaoFonte> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((retencaoFonte: HttpResponse<RetencaoFonte>) => retencaoFonte.body));
    }
    return of(new RetencaoFonte());
  }
}

export const retencaoFonteRoute: Routes = [
  {
    path: '',
    component: RetencaoFonteComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'mandaApp.retencaoFonte.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: RetencaoFonteDetailComponent,
    resolve: {
      retencaoFonte: RetencaoFonteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.retencaoFonte.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: RetencaoFonteUpdateComponent,
    resolve: {
      retencaoFonte: RetencaoFonteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.retencaoFonte.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: RetencaoFonteUpdateComponent,
    resolve: {
      retencaoFonte: RetencaoFonteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.retencaoFonte.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
