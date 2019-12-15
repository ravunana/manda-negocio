import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IProduto } from 'app/shared/model/produto.model';

type EntityResponseType = HttpResponse<IProduto>;
type EntityArrayResponseType = HttpResponse<IProduto[]>;

@Injectable({ providedIn: 'root' })
export class ProdutoService {
  public resourceUrl = SERVER_API_URL + 'api/produtos';

  constructor(protected http: HttpClient) {}

  create(produto: IProduto): Observable<EntityResponseType> {
    return this.http.post<IProduto>(this.resourceUrl, produto, { observe: 'response' });
  }

  update(produto: IProduto): Observable<EntityResponseType> {
    return this.http.put<IProduto>(this.resourceUrl, produto, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IProduto>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProduto[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getTotalUnitarioItem(quantidade: number, desconto: number, precoUnitario: number): Observable<number> {
    return this.http.get<number>(`${this.resourceUrl}/totalUnitario/${quantidade}/${desconto}/${precoUnitario}`);
  }

  calcularSubTotalItem(quantidade?: number, desconto?: number, precoUnitario?: number): number {
    const semDesconto = precoUnitario * quantidade;
    const valorDesconto = (desconto / 100) * precoUnitario;
    const totalUnitario = semDesconto - valorDesconto;
    return totalUnitario;
  }
}
