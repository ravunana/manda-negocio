import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { ItemCompra } from 'app/shared/model/item-compra.model';
import { ItemCompraService } from './item-compra.service';
import { ItemCompraComponent } from './item-compra.component';
import { ItemCompraDetailComponent } from './item-compra-detail.component';
import { ItemCompraUpdateComponent } from './item-compra-update.component';
import { IItemCompra } from 'app/shared/model/item-compra.model';

@Injectable({ providedIn: 'root' })
export class ItemCompraResolve implements Resolve<IItemCompra> {
  constructor(private service: ItemCompraService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IItemCompra> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((itemCompra: HttpResponse<ItemCompra>) => itemCompra.body));
    }
    return of(new ItemCompra());
  }
}

export const itemCompraRoute: Routes = [
  {
    path: '',
    component: ItemCompraComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'mandaApp.itemCompra.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ItemCompraDetailComponent,
    resolve: {
      itemCompra: ItemCompraResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.itemCompra.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ItemCompraUpdateComponent,
    resolve: {
      itemCompra: ItemCompraResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.itemCompra.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ItemCompraUpdateComponent,
    resolve: {
      itemCompra: ItemCompraResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.itemCompra.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
