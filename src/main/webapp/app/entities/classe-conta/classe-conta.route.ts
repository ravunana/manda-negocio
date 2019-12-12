import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { ClasseConta } from 'app/shared/model/classe-conta.model';
import { ClasseContaService } from './classe-conta.service';
import { ClasseContaComponent } from './classe-conta.component';
import { ClasseContaDetailComponent } from './classe-conta-detail.component';
import { ClasseContaUpdateComponent } from './classe-conta-update.component';
import { IClasseConta } from 'app/shared/model/classe-conta.model';

@Injectable({ providedIn: 'root' })
export class ClasseContaResolve implements Resolve<IClasseConta> {
  constructor(private service: ClasseContaService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IClasseConta> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((classeConta: HttpResponse<ClasseConta>) => classeConta.body));
    }
    return of(new ClasseConta());
  }
}

export const classeContaRoute: Routes = [
  {
    path: '',
    component: ClasseContaComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'mandaApp.classeConta.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ClasseContaDetailComponent,
    resolve: {
      classeConta: ClasseContaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.classeConta.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ClasseContaUpdateComponent,
    resolve: {
      classeConta: ClasseContaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.classeConta.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ClasseContaUpdateComponent,
    resolve: {
      classeConta: ClasseContaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.classeConta.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
