import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IConversaoUnidade } from 'app/shared/model/conversao-unidade.model';

type EntityResponseType = HttpResponse<IConversaoUnidade>;
type EntityArrayResponseType = HttpResponse<IConversaoUnidade[]>;

@Injectable({ providedIn: 'root' })
export class ConversaoUnidadeService {
  public resourceUrl = SERVER_API_URL + 'api/conversao-unidades';

  constructor(protected http: HttpClient) {}

  create(conversaoUnidade: IConversaoUnidade): Observable<EntityResponseType> {
    return this.http.post<IConversaoUnidade>(this.resourceUrl, conversaoUnidade, { observe: 'response' });
  }

  update(conversaoUnidade: IConversaoUnidade): Observable<EntityResponseType> {
    return this.http.put<IConversaoUnidade>(this.resourceUrl, conversaoUnidade, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IConversaoUnidade>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IConversaoUnidade[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
