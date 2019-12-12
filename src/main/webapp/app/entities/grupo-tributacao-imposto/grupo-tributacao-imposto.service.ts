import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IGrupoTributacaoImposto } from 'app/shared/model/grupo-tributacao-imposto.model';

type EntityResponseType = HttpResponse<IGrupoTributacaoImposto>;
type EntityArrayResponseType = HttpResponse<IGrupoTributacaoImposto[]>;

@Injectable({ providedIn: 'root' })
export class GrupoTributacaoImpostoService {
  public resourceUrl = SERVER_API_URL + 'api/grupo-tributacao-impostos';

  constructor(protected http: HttpClient) {}

  create(grupoTributacaoImposto: IGrupoTributacaoImposto): Observable<EntityResponseType> {
    return this.http.post<IGrupoTributacaoImposto>(this.resourceUrl, grupoTributacaoImposto, { observe: 'response' });
  }

  update(grupoTributacaoImposto: IGrupoTributacaoImposto): Observable<EntityResponseType> {
    return this.http.put<IGrupoTributacaoImposto>(this.resourceUrl, grupoTributacaoImposto, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IGrupoTributacaoImposto>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IGrupoTributacaoImposto[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
