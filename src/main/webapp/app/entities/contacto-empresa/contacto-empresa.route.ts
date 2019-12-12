import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { ContactoEmpresa } from 'app/shared/model/contacto-empresa.model';
import { ContactoEmpresaService } from './contacto-empresa.service';
import { ContactoEmpresaComponent } from './contacto-empresa.component';
import { ContactoEmpresaDetailComponent } from './contacto-empresa-detail.component';
import { ContactoEmpresaUpdateComponent } from './contacto-empresa-update.component';
import { IContactoEmpresa } from 'app/shared/model/contacto-empresa.model';

@Injectable({ providedIn: 'root' })
export class ContactoEmpresaResolve implements Resolve<IContactoEmpresa> {
  constructor(private service: ContactoEmpresaService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IContactoEmpresa> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((contactoEmpresa: HttpResponse<ContactoEmpresa>) => contactoEmpresa.body));
    }
    return of(new ContactoEmpresa());
  }
}

export const contactoEmpresaRoute: Routes = [
  {
    path: '',
    component: ContactoEmpresaComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'mandaApp.contactoEmpresa.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ContactoEmpresaDetailComponent,
    resolve: {
      contactoEmpresa: ContactoEmpresaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.contactoEmpresa.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ContactoEmpresaUpdateComponent,
    resolve: {
      contactoEmpresa: ContactoEmpresaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.contactoEmpresa.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ContactoEmpresaUpdateComponent,
    resolve: {
      contactoEmpresa: ContactoEmpresaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mandaApp.contactoEmpresa.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
