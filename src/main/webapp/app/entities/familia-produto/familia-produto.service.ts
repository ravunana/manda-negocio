import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IFamiliaProduto } from 'app/shared/model/familia-produto.model';

type EntityResponseType = HttpResponse<IFamiliaProduto>;
type EntityArrayResponseType = HttpResponse<IFamiliaProduto[]>;

@Injectable({ providedIn: 'root' })
export class FamiliaProdutoService {
  public resourceUrl = SERVER_API_URL + 'api/familia-produtos';

  constructor(protected http: HttpClient) {}

  create(familiaProduto: IFamiliaProduto): Observable<EntityResponseType> {
    return this.http.post<IFamiliaProduto>(this.resourceUrl, familiaProduto, { observe: 'response' });
  }

  update(familiaProduto: IFamiliaProduto): Observable<EntityResponseType> {
    return this.http.put<IFamiliaProduto>(this.resourceUrl, familiaProduto, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFamiliaProduto>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFamiliaProduto[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
