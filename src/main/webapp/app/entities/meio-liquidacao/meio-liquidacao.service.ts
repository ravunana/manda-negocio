import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IMeioLiquidacao } from 'app/shared/model/meio-liquidacao.model';

type EntityResponseType = HttpResponse<IMeioLiquidacao>;
type EntityArrayResponseType = HttpResponse<IMeioLiquidacao[]>;

@Injectable({ providedIn: 'root' })
export class MeioLiquidacaoService {
  public resourceUrl = SERVER_API_URL + 'api/meio-liquidacaos';

  constructor(protected http: HttpClient) {}

  create(meioLiquidacao: IMeioLiquidacao): Observable<EntityResponseType> {
    return this.http.post<IMeioLiquidacao>(this.resourceUrl, meioLiquidacao, { observe: 'response' });
  }

  update(meioLiquidacao: IMeioLiquidacao): Observable<EntityResponseType> {
    return this.http.put<IMeioLiquidacao>(this.resourceUrl, meioLiquidacao, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IMeioLiquidacao>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMeioLiquidacao[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
