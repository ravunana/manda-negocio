import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { FluxoDocumento } from 'app/shared/model/fluxo-documento.model';
import { FluxoDocumentoService } from './fluxo-documento.service';
import { FluxoDocumentoComponent } from './fluxo-documento.component';
import { FluxoDocumentoDetailComponent } from './fluxo-documento-detail.component';
import { FluxoDocumentoUpdateComponent } from './fluxo-documento-update.component';
import { IFluxoDocumento } from 'app/shared/model/fluxo-documento.model';

@Injectable({ providedIn: 'root' })
export class FluxoDocumentoResolve implements Resolve<IFluxoDocumento> {
  constructor(private service: FluxoDocumentoService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFluxoDocumento> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((fluxoDocumento: HttpResponse<FluxoDocumento>) => fluxoDocumento.body));
    }
    return of(new FluxoDocumento());
  }
}

export const fluxoDocumentoRoute: Routes = [
  {
    path: '',
    component: FluxoDocumentoComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'mandaApp.fluxoDocumento.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: FluxoDocumentoDetailComponent,
    resolve: {
      fluxoDocumento: FluxoDocumentoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.fluxoDocumento.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: FluxoDocumentoUpdateComponent,
    resolve: {
      fluxoDocumento: FluxoDocumentoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.fluxoDocumento.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: FluxoDocumentoUpdateComponent,
    resolve: {
      fluxoDocumento: FluxoDocumentoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.fluxoDocumento.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
