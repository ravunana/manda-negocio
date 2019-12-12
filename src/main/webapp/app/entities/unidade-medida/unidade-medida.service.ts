import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IUnidadeMedida } from 'app/shared/model/unidade-medida.model';

type EntityResponseType = HttpResponse<IUnidadeMedida>;
type EntityArrayResponseType = HttpResponse<IUnidadeMedida[]>;

@Injectable({ providedIn: 'root' })
export class UnidadeMedidaService {
  public resourceUrl = SERVER_API_URL + 'api/unidade-medidas';

  constructor(protected http: HttpClient) {}

  create(unidadeMedida: IUnidadeMedida): Observable<EntityResponseType> {
    return this.http.post<IUnidadeMedida>(this.resourceUrl, unidadeMedida, { observe: 'response' });
  }

  update(unidadeMedida: IUnidadeMedida): Observable<EntityResponseType> {
    return this.http.put<IUnidadeMedida>(this.resourceUrl, unidadeMedida, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IUnidadeMedida>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IUnidadeMedida[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
