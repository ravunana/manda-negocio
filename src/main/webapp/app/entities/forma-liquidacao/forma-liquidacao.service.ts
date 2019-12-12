import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IFormaLiquidacao } from 'app/shared/model/forma-liquidacao.model';

type EntityResponseType = HttpResponse<IFormaLiquidacao>;
type EntityArrayResponseType = HttpResponse<IFormaLiquidacao[]>;

@Injectable({ providedIn: 'root' })
export class FormaLiquidacaoService {
  public resourceUrl = SERVER_API_URL + 'api/forma-liquidacaos';

  constructor(protected http: HttpClient) {}

  create(formaLiquidacao: IFormaLiquidacao): Observable<EntityResponseType> {
    return this.http.post<IFormaLiquidacao>(this.resourceUrl, formaLiquidacao, { observe: 'response' });
  }

  update(formaLiquidacao: IFormaLiquidacao): Observable<EntityResponseType> {
    return this.http.put<IFormaLiquidacao>(this.resourceUrl, formaLiquidacao, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFormaLiquidacao>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFormaLiquidacao[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
