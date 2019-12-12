import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { SerieDocumento } from 'app/shared/model/serie-documento.model';
import { SerieDocumentoService } from './serie-documento.service';
import { SerieDocumentoComponent } from './serie-documento.component';
import { SerieDocumentoDetailComponent } from './serie-documento-detail.component';
import { SerieDocumentoUpdateComponent } from './serie-documento-update.component';
import { ISerieDocumento } from 'app/shared/model/serie-documento.model';

@Injectable({ providedIn: 'root' })
export class SerieDocumentoResolve implements Resolve<ISerieDocumento> {
  constructor(private service: SerieDocumentoService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISerieDocumento> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((serieDocumento: HttpResponse<SerieDocumento>) => serieDocumento.body));
    }
    return of(new SerieDocumento());
  }
}

export const serieDocumentoRoute: Routes = [
  {
    path: '',
    component: SerieDocumentoComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'mandaApp.serieDocumento.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SerieDocumentoDetailComponent,
    resolve: {
      serieDocumento: SerieDocumentoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.serieDocumento.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SerieDocumentoUpdateComponent,
    resolve: {
      serieDocumento: SerieDocumentoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.serieDocumento.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SerieDocumentoUpdateComponent,
    resolve: {
      serieDocumento: SerieDocumentoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.serieDocumento.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
