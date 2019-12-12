import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { ContactoPessoa } from 'app/shared/model/contacto-pessoa.model';
import { ContactoPessoaService } from './contacto-pessoa.service';
import { ContactoPessoaComponent } from './contacto-pessoa.component';
import { ContactoPessoaDetailComponent } from './contacto-pessoa-detail.component';
import { ContactoPessoaUpdateComponent } from './contacto-pessoa-update.component';
import { IContactoPessoa } from 'app/shared/model/contacto-pessoa.model';

@Injectable({ providedIn: 'root' })
export class ContactoPessoaResolve implements Resolve<IContactoPessoa> {
  constructor(private service: ContactoPessoaService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IContactoPessoa> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((contactoPessoa: HttpResponse<ContactoPessoa>) => contactoPessoa.body));
    }
    return of(new ContactoPessoa());
  }
}

export const contactoPessoaRoute: Routes = [
  {
    path: '',
    component: ContactoPessoaComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'mandaApp.contactoPessoa.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ContactoPessoaDetailComponent,
    resolve: {
      contactoPessoa: ContactoPessoaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.contactoPessoa.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ContactoPessoaUpdateComponent,
    resolve: {
      contactoPessoa: ContactoPessoaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.contactoPessoa.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ContactoPessoaUpdateComponent,
    resolve: {
      contactoPessoa: ContactoPessoaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.contactoPessoa.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
