import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IMoeda } from 'app/shared/model/moeda.model';

type EntityResponseType = HttpResponse<IMoeda>;
type EntityArrayResponseType = HttpResponse<IMoeda[]>;

@Injectable({ providedIn: 'root' })
export class MoedaService {
  public resourceUrl = SERVER_API_URL + 'api/moedas';

  constructor(protected http: HttpClient) {}

  create(moeda: IMoeda): Observable<EntityResponseType> {
    return this.http.post<IMoeda>(this.resourceUrl, moeda, { observe: 'response' });
  }

  update(moeda: IMoeda): Observable<EntityResponseType> {
    return this.http.put<IMoeda>(this.resourceUrl, moeda, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IMoeda>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMoeda[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
