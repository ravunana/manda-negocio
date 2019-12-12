import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IRetencaoFonte } from 'app/shared/model/retencao-fonte.model';

type EntityResponseType = HttpResponse<IRetencaoFonte>;
type EntityArrayResponseType = HttpResponse<IRetencaoFonte[]>;

@Injectable({ providedIn: 'root' })
export class RetencaoFonteService {
  public resourceUrl = SERVER_API_URL + 'api/retencao-fontes';

  constructor(protected http: HttpClient) {}

  create(retencaoFonte: IRetencaoFonte): Observable<EntityResponseType> {
    return this.http.post<IRetencaoFonte>(this.resourceUrl, retencaoFonte, { observe: 'response' });
  }

  update(retencaoFonte: IRetencaoFonte): Observable<EntityResponseType> {
    return this.http.put<IRetencaoFonte>(this.resourceUrl, retencaoFonte, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IRetencaoFonte>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRetencaoFonte[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
