import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { LocalizacaoEmpresa } from 'app/shared/model/localizacao-empresa.model';
import { LocalizacaoEmpresaService } from './localizacao-empresa.service';
import { LocalizacaoEmpresaComponent } from './localizacao-empresa.component';
import { LocalizacaoEmpresaDetailComponent } from './localizacao-empresa-detail.component';
import { LocalizacaoEmpresaUpdateComponent } from './localizacao-empresa-update.component';
import { ILocalizacaoEmpresa } from 'app/shared/model/localizacao-empresa.model';

@Injectable({ providedIn: 'root' })
export class LocalizacaoEmpresaResolve implements Resolve<ILocalizacaoEmpresa> {
  constructor(private service: LocalizacaoEmpresaService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILocalizacaoEmpresa> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((localizacaoEmpresa: HttpResponse<LocalizacaoEmpresa>) => localizacaoEmpresa.body));
    }
    return of(new LocalizacaoEmpresa());
  }
}

export const localizacaoEmpresaRoute: Routes = [
  {
    path: '',
    component: LocalizacaoEmpresaComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'mandaApp.localizacaoEmpresa.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: LocalizacaoEmpresaDetailComponent,
    resolve: {
      localizacaoEmpresa: LocalizacaoEmpresaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.localizacaoEmpresa.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: LocalizacaoEmpresaUpdateComponent,
    resolve: {
      localizacaoEmpresa: LocalizacaoEmpresaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.localizacaoEmpresa.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: LocalizacaoEmpresaUpdateComponent,
    resolve: {
      localizacaoEmpresa: LocalizacaoEmpresaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.localizacaoEmpresa.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
