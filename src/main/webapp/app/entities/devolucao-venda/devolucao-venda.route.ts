import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { DevolucaoVenda } from 'app/shared/model/devolucao-venda.model';
import { DevolucaoVendaService } from './devolucao-venda.service';
import { DevolucaoVendaComponent } from './devolucao-venda.component';
import { DevolucaoVendaDetailComponent } from './devolucao-venda-detail.component';
import { DevolucaoVendaUpdateComponent } from './devolucao-venda-update.component';
import { IDevolucaoVenda } from 'app/shared/model/devolucao-venda.model';

@Injectable({ providedIn: 'root' })
export class DevolucaoVendaResolve implements Resolve<IDevolucaoVenda> {
  constructor(private service: DevolucaoVendaService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDevolucaoVenda> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((devolucaoVenda: HttpResponse<DevolucaoVenda>) => devolucaoVenda.body));
    }
    return of(new DevolucaoVenda());
  }
}

export const devolucaoVendaRoute: Routes = [
  {
    path: '',
    component: DevolucaoVendaComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'mandaApp.devolucaoVenda.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: DevolucaoVendaDetailComponent,
    resolve: {
      devolucaoVenda: DevolucaoVendaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.devolucaoVenda.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: DevolucaoVendaUpdateComponent,
    resolve: {
      devolucaoVenda: DevolucaoVendaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.devolucaoVenda.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: DevolucaoVendaUpdateComponent,
    resolve: {
      devolucaoVenda: DevolucaoVendaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.devolucaoVenda.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
