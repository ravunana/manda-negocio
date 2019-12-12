import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IFluxoDocumento } from 'app/shared/model/fluxo-documento.model';

type EntityResponseType = HttpResponse<IFluxoDocumento>;
type EntityArrayResponseType = HttpResponse<IFluxoDocumento[]>;

@Injectable({ providedIn: 'root' })
export class FluxoDocumentoService {
  public resourceUrl = SERVER_API_URL + 'api/fluxo-documentos';

  constructor(protected http: HttpClient) {}

  create(fluxoDocumento: IFluxoDocumento): Observable<EntityResponseType> {
    return this.http.post<IFluxoDocumento>(this.resourceUrl, fluxoDocumento, { observe: 'response' });
  }

  update(fluxoDocumento: IFluxoDocumento): Observable<EntityResponseType> {
    return this.http.put<IFluxoDocumento>(this.resourceUrl, fluxoDocumento, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFluxoDocumento>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFluxoDocumento[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
