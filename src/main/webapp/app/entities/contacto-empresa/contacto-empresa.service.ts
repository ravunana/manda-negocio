import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IContactoEmpresa } from 'app/shared/model/contacto-empresa.model';

type EntityResponseType = HttpResponse<IContactoEmpresa>;
type EntityArrayResponseType = HttpResponse<IContactoEmpresa[]>;

@Injectable({ providedIn: 'root' })
export class ContactoEmpresaService {
  public resourceUrl = SERVER_API_URL + 'api/contacto-empresas';

  constructor(protected http: HttpClient) {}

  create(contactoEmpresa: IContactoEmpresa): Observable<EntityResponseType> {
    return this.http.post<IContactoEmpresa>(this.resourceUrl, contactoEmpresa, { observe: 'response' });
  }

  update(contactoEmpresa: IContactoEmpresa): Observable<EntityResponseType> {
    return this.http.put<IContactoEmpresa>(this.resourceUrl, contactoEmpresa, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IContactoEmpresa>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IContactoEmpresa[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
