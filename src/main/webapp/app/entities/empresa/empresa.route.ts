import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Empresa } from 'app/shared/model/empresa.model';
import { EmpresaService } from './empresa.service';
import { EmpresaComponent } from './empresa.component';
import { EmpresaDetailComponent } from './empresa-detail.component';
import { EmpresaUpdateComponent } from './empresa-update.component';
import { IEmpresa } from 'app/shared/model/empresa.model';

@Injectable({ providedIn: 'root' })
export class EmpresaResolve implements Resolve<IEmpresa> {
  constructor(private service: EmpresaService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEmpresa> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((empresa: HttpResponse<Empresa>) => empresa.body));
    }
    return of(new Empresa());
  }
}

export const empresaRoute: Routes = [
  {
    path: '',
    component: EmpresaComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'mandaApp.empresa.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: EmpresaDetailComponent,
    resolve: {
      empresa: EmpresaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.empresa.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: EmpresaUpdateComponent,
    resolve: {
      empresa: EmpresaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.empresa.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: EmpresaUpdateComponent,
    resolve: {
      empresa: EmpresaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.empresa.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
