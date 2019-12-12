import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { MoradaPessoa } from 'app/shared/model/morada-pessoa.model';
import { MoradaPessoaService } from './morada-pessoa.service';
import { MoradaPessoaComponent } from './morada-pessoa.component';
import { MoradaPessoaDetailComponent } from './morada-pessoa-detail.component';
import { MoradaPessoaUpdateComponent } from './morada-pessoa-update.component';
import { IMoradaPessoa } from 'app/shared/model/morada-pessoa.model';

@Injectable({ providedIn: 'root' })
export class MoradaPessoaResolve implements Resolve<IMoradaPessoa> {
  constructor(private service: MoradaPessoaService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMoradaPessoa> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((moradaPessoa: HttpResponse<MoradaPessoa>) => moradaPessoa.body));
    }
    return of(new MoradaPessoa());
  }
}

export const moradaPessoaRoute: Routes = [
  {
    path: '',
    component: MoradaPessoaComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'mandaApp.moradaPessoa.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: MoradaPessoaDetailComponent,
    resolve: {
      moradaPessoa: MoradaPessoaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.moradaPessoa.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: MoradaPessoaUpdateComponent,
    resolve: {
      moradaPessoa: MoradaPessoaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.moradaPessoa.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: MoradaPessoaUpdateComponent,
    resolve: {
      moradaPessoa: MoradaPessoaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.moradaPessoa.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
