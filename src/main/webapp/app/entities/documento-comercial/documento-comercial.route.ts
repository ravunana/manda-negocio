import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { DocumentoComercial } from 'app/shared/model/documento-comercial.model';
import { DocumentoComercialService } from './documento-comercial.service';
import { DocumentoComercialComponent } from './documento-comercial.component';
import { DocumentoComercialDetailComponent } from './documento-comercial-detail.component';
import { DocumentoComercialUpdateComponent } from './documento-comercial-update.component';
import { IDocumentoComercial } from 'app/shared/model/documento-comercial.model';

@Injectable({ providedIn: 'root' })
export class DocumentoComercialResolve implements Resolve<IDocumentoComercial> {
  constructor(private service: DocumentoComercialService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDocumentoComercial> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((documentoComercial: HttpResponse<DocumentoComercial>) => documentoComercial.body));
    }
    return of(new DocumentoComercial());
  }
}

export const documentoComercialRoute: Routes = [
  {
    path: '',
    component: DocumentoComercialComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'mandaApp.documentoComercial.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: DocumentoComercialDetailComponent,
    resolve: {
      documentoComercial: DocumentoComercialResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.documentoComercial.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: DocumentoComercialUpdateComponent,
    resolve: {
      documentoComercial: DocumentoComercialResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.documentoComercial.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: DocumentoComercialUpdateComponent,
    resolve: {
      documentoComercial: DocumentoComercialResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.documentoComercial.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
