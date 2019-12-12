import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Arquivo } from 'app/shared/model/arquivo.model';
import { ArquivoService } from './arquivo.service';
import { ArquivoComponent } from './arquivo.component';
import { ArquivoDetailComponent } from './arquivo-detail.component';
import { ArquivoUpdateComponent } from './arquivo-update.component';
import { IArquivo } from 'app/shared/model/arquivo.model';

@Injectable({ providedIn: 'root' })
export class ArquivoResolve implements Resolve<IArquivo> {
  constructor(private service: ArquivoService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IArquivo> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((arquivo: HttpResponse<Arquivo>) => arquivo.body));
    }
    return of(new Arquivo());
  }
}

export const arquivoRoute: Routes = [
  {
    path: '',
    component: ArquivoComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'mandaApp.arquivo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ArquivoDetailComponent,
    resolve: {
      arquivo: ArquivoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.arquivo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ArquivoUpdateComponent,
    resolve: {
      arquivo: ArquivoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.arquivo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ArquivoUpdateComponent,
    resolve: {
      arquivo: ArquivoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.arquivo.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
