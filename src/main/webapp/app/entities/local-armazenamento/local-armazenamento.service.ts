import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ILocalArmazenamento } from 'app/shared/model/local-armazenamento.model';

type EntityResponseType = HttpResponse<ILocalArmazenamento>;
type EntityArrayResponseType = HttpResponse<ILocalArmazenamento[]>;

@Injectable({ providedIn: 'root' })
export class LocalArmazenamentoService {
  public resourceUrl = SERVER_API_URL + 'api/local-armazenamentos';

  constructor(protected http: HttpClient) {}

  create(localArmazenamento: ILocalArmazenamento): Observable<EntityResponseType> {
    return this.http.post<ILocalArmazenamento>(this.resourceUrl, localArmazenamento, { observe: 'response' });
  }

  update(localArmazenamento: ILocalArmazenamento): Observable<EntityResponseType> {
    return this.http.put<ILocalArmazenamento>(this.resourceUrl, localArmazenamento, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ILocalArmazenamento>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ILocalArmazenamento[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
