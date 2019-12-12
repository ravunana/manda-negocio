import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { ConversaoUnidade } from 'app/shared/model/conversao-unidade.model';
import { ConversaoUnidadeService } from './conversao-unidade.service';
import { ConversaoUnidadeComponent } from './conversao-unidade.component';
import { ConversaoUnidadeDetailComponent } from './conversao-unidade-detail.component';
import { ConversaoUnidadeUpdateComponent } from './conversao-unidade-update.component';
import { IConversaoUnidade } from 'app/shared/model/conversao-unidade.model';

@Injectable({ providedIn: 'root' })
export class ConversaoUnidadeResolve implements Resolve<IConversaoUnidade> {
  constructor(private service: ConversaoUnidadeService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IConversaoUnidade> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((conversaoUnidade: HttpResponse<ConversaoUnidade>) => conversaoUnidade.body));
    }
    return of(new ConversaoUnidade());
  }
}

export const conversaoUnidadeRoute: Routes = [
  {
    path: '',
    component: ConversaoUnidadeComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'mandaApp.conversaoUnidade.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ConversaoUnidadeDetailComponent,
    resolve: {
      conversaoUnidade: ConversaoUnidadeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.conversaoUnidade.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ConversaoUnidadeUpdateComponent,
    resolve: {
      conversaoUnidade: ConversaoUnidadeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.conversaoUnidade.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ConversaoUnidadeUpdateComponent,
    resolve: {
      conversaoUnidade: ConversaoUnidadeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.conversaoUnidade.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
