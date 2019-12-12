import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IRegraMovimentacaoCredito } from 'app/shared/model/regra-movimentacao-credito.model';

type EntityResponseType = HttpResponse<IRegraMovimentacaoCredito>;
type EntityArrayResponseType = HttpResponse<IRegraMovimentacaoCredito[]>;

@Injectable({ providedIn: 'root' })
export class RegraMovimentacaoCreditoService {
  public resourceUrl = SERVER_API_URL + 'api/regra-movimentacao-creditos';

  constructor(protected http: HttpClient) {}

  create(regraMovimentacaoCredito: IRegraMovimentacaoCredito): Observable<EntityResponseType> {
    return this.http.post<IRegraMovimentacaoCredito>(this.resourceUrl, regraMovimentacaoCredito, { observe: 'response' });
  }

  update(regraMovimentacaoCredito: IRegraMovimentacaoCredito): Observable<EntityResponseType> {
    return this.http.put<IRegraMovimentacaoCredito>(this.resourceUrl, regraMovimentacaoCredito, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IRegraMovimentacaoCredito>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRegraMovimentacaoCredito[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
