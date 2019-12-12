import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IConta } from 'app/shared/model/conta.model';

type EntityResponseType = HttpResponse<IConta>;
type EntityArrayResponseType = HttpResponse<IConta[]>;

@Injectable({ providedIn: 'root' })
export class ContaService {
  public resourceUrl = SERVER_API_URL + 'api/contas';

  constructor(protected http: HttpClient) {}

  create(conta: IConta): Observable<EntityResponseType> {
    return this.http.post<IConta>(this.resourceUrl, conta, { observe: 'response' });
  }

  update(conta: IConta): Observable<EntityResponseType> {
    return this.http.put<IConta>(this.resourceUrl, conta, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IConta>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IConta[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
