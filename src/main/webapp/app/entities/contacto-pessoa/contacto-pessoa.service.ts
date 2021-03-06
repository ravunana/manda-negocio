import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IContactoPessoa } from 'app/shared/model/contacto-pessoa.model';

type EntityResponseType = HttpResponse<IContactoPessoa>;
type EntityArrayResponseType = HttpResponse<IContactoPessoa[]>;

@Injectable({ providedIn: 'root' })
export class ContactoPessoaService {
  public resourceUrl = SERVER_API_URL + 'api/contacto-pessoas';

  constructor(protected http: HttpClient) {}

  create(contactoPessoa: IContactoPessoa): Observable<EntityResponseType> {
    return this.http.post<IContactoPessoa>(this.resourceUrl, contactoPessoa, { observe: 'response' });
  }

  update(contactoPessoa: IContactoPessoa): Observable<EntityResponseType> {
    return this.http.put<IContactoPessoa>(this.resourceUrl, contactoPessoa, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IContactoPessoa>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IContactoPessoa[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  deleteContacto(index: number): Observable<IContactoPessoa> {
    return this.http.delete<IContactoPessoa>(`${this.resourceUrl}/delete/${index}`);
  }

  addContacto(contacto: IContactoPessoa): Observable<IContactoPessoa> {
    return this.http.post<IContactoPessoa>(this.resourceUrl + '/add', contacto);
  }

  getContactos(): Observable<IContactoPessoa[]> {
    return this.http.get<IContactoPessoa[]>(this.resourceUrl + '/lista');
  }
}
