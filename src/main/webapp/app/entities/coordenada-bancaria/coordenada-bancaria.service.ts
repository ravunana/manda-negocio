import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICoordenadaBancaria } from 'app/shared/model/coordenada-bancaria.model';

type EntityResponseType = HttpResponse<ICoordenadaBancaria>;
type EntityArrayResponseType = HttpResponse<ICoordenadaBancaria[]>;

@Injectable({ providedIn: 'root' })
export class CoordenadaBancariaService {
  public resourceUrl = SERVER_API_URL + 'api/coordenada-bancarias';

  constructor(protected http: HttpClient) {}

  create(coordenadaBancaria: ICoordenadaBancaria): Observable<EntityResponseType> {
    return this.http.post<ICoordenadaBancaria>(this.resourceUrl, coordenadaBancaria, { observe: 'response' });
  }

  update(coordenadaBancaria: ICoordenadaBancaria): Observable<EntityResponseType> {
    return this.http.put<ICoordenadaBancaria>(this.resourceUrl, coordenadaBancaria, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICoordenadaBancaria>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICoordenadaBancaria[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
