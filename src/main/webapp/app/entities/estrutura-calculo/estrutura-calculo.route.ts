import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { EstruturaCalculo } from 'app/shared/model/estrutura-calculo.model';
import { EstruturaCalculoService } from './estrutura-calculo.service';
import { EstruturaCalculoComponent } from './estrutura-calculo.component';
import { EstruturaCalculoDetailComponent } from './estrutura-calculo-detail.component';
import { EstruturaCalculoUpdateComponent } from './estrutura-calculo-update.component';
import { IEstruturaCalculo } from 'app/shared/model/estrutura-calculo.model';

@Injectable({ providedIn: 'root' })
export class EstruturaCalculoResolve implements Resolve<IEstruturaCalculo> {
  constructor(private service: EstruturaCalculoService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEstruturaCalculo> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((estruturaCalculo: HttpResponse<EstruturaCalculo>) => estruturaCalculo.body));
    }
    return of(new EstruturaCalculo());
  }
}

export const estruturaCalculoRoute: Routes = [
  {
    path: '',
    component: EstruturaCalculoComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'mandaApp.estruturaCalculo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: EstruturaCalculoDetailComponent,
    resolve: {
      estruturaCalculo: EstruturaCalculoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.estruturaCalculo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: EstruturaCalculoUpdateComponent,
    resolve: {
      estruturaCalculo: EstruturaCalculoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.estruturaCalculo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: EstruturaCalculoUpdateComponent,
    resolve: {
      estruturaCalculo: EstruturaCalculoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.estruturaCalculo.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
