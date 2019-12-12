import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Venda } from 'app/shared/model/venda.model';
import { VendaService } from './venda.service';
import { VendaComponent } from './venda.component';
import { VendaDetailComponent } from './venda-detail.component';
import { VendaUpdateComponent } from './venda-update.component';
import { IVenda } from 'app/shared/model/venda.model';

@Injectable({ providedIn: 'root' })
export class VendaResolve implements Resolve<IVenda> {
  constructor(private service: VendaService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IVenda> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((venda: HttpResponse<Venda>) => venda.body));
    }
    return of(new Venda());
  }
}

export const vendaRoute: Routes = [
  {
    path: '',
    component: VendaComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'mandaApp.venda.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: VendaDetailComponent,
    resolve: {
      venda: VendaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.venda.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: VendaUpdateComponent,
    resolve: {
      venda: VendaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.venda.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: VendaUpdateComponent,
    resolve: {
      venda: VendaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.venda.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
