import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICompostoProduto } from 'app/shared/model/composto-produto.model';

type EntityResponseType = HttpResponse<ICompostoProduto>;
type EntityArrayResponseType = HttpResponse<ICompostoProduto[]>;

@Injectable({ providedIn: 'root' })
export class CompostoProdutoService {
  public resourceUrl = SERVER_API_URL + 'api/composto-produtos';

  constructor(protected http: HttpClient) {}

  create(compostoProduto: ICompostoProduto): Observable<EntityResponseType> {
    return this.http.post<ICompostoProduto>(this.resourceUrl, compostoProduto, { observe: 'response' });
  }

  update(compostoProduto: ICompostoProduto): Observable<EntityResponseType> {
    return this.http.put<ICompostoProduto>(this.resourceUrl, compostoProduto, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICompostoProduto>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICompostoProduto[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
