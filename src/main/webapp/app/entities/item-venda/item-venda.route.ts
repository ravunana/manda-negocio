import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { ItemVenda } from 'app/shared/model/item-venda.model';
import { ItemVendaService } from './item-venda.service';
import { ItemVendaComponent } from './item-venda.component';
import { ItemVendaDetailComponent } from './item-venda-detail.component';
import { ItemVendaUpdateComponent } from './item-venda-update.component';
import { IItemVenda } from 'app/shared/model/item-venda.model';

@Injectable({ providedIn: 'root' })
export class ItemVendaResolve implements Resolve<IItemVenda> {
  constructor(private service: ItemVendaService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IItemVenda> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((itemVenda: HttpResponse<ItemVenda>) => itemVenda.body));
    }
    return of(new ItemVenda());
  }
}

export const itemVendaRoute: Routes = [
  {
    path: '',
    component: ItemVendaComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'mandaApp.itemVenda.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ItemVendaDetailComponent,
    resolve: {
      itemVenda: ItemVendaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.itemVenda.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ItemVendaUpdateComponent,
    resolve: {
      itemVenda: ItemVendaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.itemVenda.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ItemVendaUpdateComponent,
    resolve: {
      itemVenda: ItemVendaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.itemVenda.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
