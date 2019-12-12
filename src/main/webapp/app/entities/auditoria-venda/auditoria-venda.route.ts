import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { AuditoriaVenda } from 'app/shared/model/auditoria-venda.model';
import { AuditoriaVendaService } from './auditoria-venda.service';
import { AuditoriaVendaComponent } from './auditoria-venda.component';
import { AuditoriaVendaDetailComponent } from './auditoria-venda-detail.component';
import { AuditoriaVendaUpdateComponent } from './auditoria-venda-update.component';
import { IAuditoriaVenda } from 'app/shared/model/auditoria-venda.model';

@Injectable({ providedIn: 'root' })
export class AuditoriaVendaResolve implements Resolve<IAuditoriaVenda> {
  constructor(private service: AuditoriaVendaService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAuditoriaVenda> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((auditoriaVenda: HttpResponse<AuditoriaVenda>) => auditoriaVenda.body));
    }
    return of(new AuditoriaVenda());
  }
}

export const auditoriaVendaRoute: Routes = [
  {
    path: '',
    component: AuditoriaVendaComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'mandaApp.auditoriaVenda.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AuditoriaVendaDetailComponent,
    resolve: {
      auditoriaVenda: AuditoriaVendaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.auditoriaVenda.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AuditoriaVendaUpdateComponent,
    resolve: {
      auditoriaVenda: AuditoriaVendaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.auditoriaVenda.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AuditoriaVendaUpdateComponent,
    resolve: {
      auditoriaVenda: AuditoriaVendaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.auditoriaVenda.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
