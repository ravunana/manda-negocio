import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IClasseConta } from 'app/shared/model/classe-conta.model';

type EntityResponseType = HttpResponse<IClasseConta>;
type EntityArrayResponseType = HttpResponse<IClasseConta[]>;

@Injectable({ providedIn: 'root' })
export class ClasseContaService {
  public resourceUrl = SERVER_API_URL + 'api/classe-contas';

  constructor(protected http: HttpClient) {}

  create(classeConta: IClasseConta): Observable<EntityResponseType> {
    return this.http.post<IClasseConta>(this.resourceUrl, classeConta, { observe: 'response' });
  }

  update(classeConta: IClasseConta): Observable<EntityResponseType> {
    return this.http.put<IClasseConta>(this.resourceUrl, classeConta, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IClasseConta>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IClasseConta[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
