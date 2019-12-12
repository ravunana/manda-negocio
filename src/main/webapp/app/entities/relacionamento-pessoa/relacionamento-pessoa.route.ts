import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { RelacionamentoPessoa } from 'app/shared/model/relacionamento-pessoa.model';
import { RelacionamentoPessoaService } from './relacionamento-pessoa.service';
import { RelacionamentoPessoaComponent } from './relacionamento-pessoa.component';
import { RelacionamentoPessoaDetailComponent } from './relacionamento-pessoa-detail.component';
import { RelacionamentoPessoaUpdateComponent } from './relacionamento-pessoa-update.component';
import { IRelacionamentoPessoa } from 'app/shared/model/relacionamento-pessoa.model';

@Injectable({ providedIn: 'root' })
export class RelacionamentoPessoaResolve implements Resolve<IRelacionamentoPessoa> {
  constructor(private service: RelacionamentoPessoaService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IRelacionamentoPessoa> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((relacionamentoPessoa: HttpResponse<RelacionamentoPessoa>) => relacionamentoPessoa.body));
    }
    return of(new RelacionamentoPessoa());
  }
}

export const relacionamentoPessoaRoute: Routes = [
  {
    path: '',
    component: RelacionamentoPessoaComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'mandaApp.relacionamentoPessoa.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: RelacionamentoPessoaDetailComponent,
    resolve: {
      relacionamentoPessoa: RelacionamentoPessoaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.relacionamentoPessoa.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: RelacionamentoPessoaUpdateComponent,
    resolve: {
      relacionamentoPessoa: RelacionamentoPessoaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.relacionamentoPessoa.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: RelacionamentoPessoaUpdateComponent,
    resolve: {
      relacionamentoPessoa: RelacionamentoPessoaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.relacionamentoPessoa.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
