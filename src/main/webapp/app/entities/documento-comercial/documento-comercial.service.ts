import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDocumentoComercial } from 'app/shared/model/documento-comercial.model';

type EntityResponseType = HttpResponse<IDocumentoComercial>;
type EntityArrayResponseType = HttpResponse<IDocumentoComercial[]>;

@Injectable({ providedIn: 'root' })
export class DocumentoComercialService {
  public resourceUrl = SERVER_API_URL + 'api/documento-comercials';

  constructor(protected http: HttpClient) {}

  create(documentoComercial: IDocumentoComercial): Observable<EntityResponseType> {
    return this.http.post<IDocumentoComercial>(this.resourceUrl, documentoComercial, { observe: 'response' });
  }

  update(documentoComercial: IDocumentoComercial): Observable<EntityResponseType> {
    return this.http.put<IDocumentoComercial>(this.resourceUrl, documentoComercial, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDocumentoComercial>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDocumentoComercial[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
