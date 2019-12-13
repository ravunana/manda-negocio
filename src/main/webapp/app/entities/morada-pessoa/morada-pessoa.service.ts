import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IMoradaPessoa } from 'app/shared/model/morada-pessoa.model';

type EntityResponseType = HttpResponse<IMoradaPessoa>;
type EntityArrayResponseType = HttpResponse<IMoradaPessoa[]>;

@Injectable({ providedIn: 'root' })
export class MoradaPessoaService {
  public resourceUrl = SERVER_API_URL + 'api/morada-pessoas';

  constructor(protected http: HttpClient) {}

  create(moradaPessoa: IMoradaPessoa): Observable<EntityResponseType> {
    return this.http.post<IMoradaPessoa>(this.resourceUrl, moradaPessoa, { observe: 'response' });
  }

  update(moradaPessoa: IMoradaPessoa): Observable<EntityResponseType> {
    return this.http.put<IMoradaPessoa>(this.resourceUrl, moradaPessoa, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IMoradaPessoa>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMoradaPessoa[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  deleteMorada(index: number): Observable<IMoradaPessoa> {
    return this.http.delete<IMoradaPessoa>(`${this.resourceUrl}/delete/${index}`);
  }

  addMorada(morada: IMoradaPessoa): Observable<IMoradaPessoa> {
    return this.http.post<IMoradaPessoa>(this.resourceUrl + '/add', morada);
  }

  getMoradas(): Observable<IMoradaPessoa[]> {
    return this.http.get<IMoradaPessoa[]>(this.resourceUrl + '/lista');
  }
}
