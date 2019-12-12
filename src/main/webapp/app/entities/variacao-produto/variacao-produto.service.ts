import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IVariacaoProduto } from 'app/shared/model/variacao-produto.model';

type EntityResponseType = HttpResponse<IVariacaoProduto>;
type EntityArrayResponseType = HttpResponse<IVariacaoProduto[]>;

@Injectable({ providedIn: 'root' })
export class VariacaoProdutoService {
  public resourceUrl = SERVER_API_URL + 'api/variacao-produtos';

  constructor(protected http: HttpClient) {}

  create(variacaoProduto: IVariacaoProduto): Observable<EntityResponseType> {
    return this.http.post<IVariacaoProduto>(this.resourceUrl, variacaoProduto, { observe: 'response' });
  }

  update(variacaoProduto: IVariacaoProduto): Observable<EntityResponseType> {
    return this.http.put<IVariacaoProduto>(this.resourceUrl, variacaoProduto, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IVariacaoProduto>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IVariacaoProduto[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
