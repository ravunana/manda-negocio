import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ILocalizacaoEmpresa } from 'app/shared/model/localizacao-empresa.model';

type EntityResponseType = HttpResponse<ILocalizacaoEmpresa>;
type EntityArrayResponseType = HttpResponse<ILocalizacaoEmpresa[]>;

@Injectable({ providedIn: 'root' })
export class LocalizacaoEmpresaService {
  public resourceUrl = SERVER_API_URL + 'api/localizacao-empresas';

  constructor(protected http: HttpClient) {}

  create(localizacaoEmpresa: ILocalizacaoEmpresa): Observable<EntityResponseType> {
    return this.http.post<ILocalizacaoEmpresa>(this.resourceUrl, localizacaoEmpresa, { observe: 'response' });
  }

  update(localizacaoEmpresa: ILocalizacaoEmpresa): Observable<EntityResponseType> {
    return this.http.put<ILocalizacaoEmpresa>(this.resourceUrl, localizacaoEmpresa, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ILocalizacaoEmpresa>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ILocalizacaoEmpresa[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
