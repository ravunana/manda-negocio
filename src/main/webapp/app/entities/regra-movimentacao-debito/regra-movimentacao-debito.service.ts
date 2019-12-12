import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IRegraMovimentacaoDebito } from 'app/shared/model/regra-movimentacao-debito.model';

type EntityResponseType = HttpResponse<IRegraMovimentacaoDebito>;
type EntityArrayResponseType = HttpResponse<IRegraMovimentacaoDebito[]>;

@Injectable({ providedIn: 'root' })
export class RegraMovimentacaoDebitoService {
  public resourceUrl = SERVER_API_URL + 'api/regra-movimentacao-debitos';

  constructor(protected http: HttpClient) {}

  create(regraMovimentacaoDebito: IRegraMovimentacaoDebito): Observable<EntityResponseType> {
    return this.http.post<IRegraMovimentacaoDebito>(this.resourceUrl, regraMovimentacaoDebito, { observe: 'response' });
  }

  update(regraMovimentacaoDebito: IRegraMovimentacaoDebito): Observable<EntityResponseType> {
    return this.http.put<IRegraMovimentacaoDebito>(this.resourceUrl, regraMovimentacaoDebito, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IRegraMovimentacaoDebito>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRegraMovimentacaoDebito[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
