import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IFornecedor } from 'app/shared/model/fornecedor.model';

type EntityResponseType = HttpResponse<IFornecedor>;
type EntityArrayResponseType = HttpResponse<IFornecedor[]>;

@Injectable({ providedIn: 'root' })
export class FornecedorService {
  public resourceUrl = SERVER_API_URL + 'api/fornecedors';

  constructor(protected http: HttpClient) {}

  create(fornecedor: IFornecedor): Observable<EntityResponseType> {
    return this.http.post<IFornecedor>(this.resourceUrl, fornecedor, { observe: 'response' });
  }

  update(fornecedor: IFornecedor): Observable<EntityResponseType> {
    return this.http.put<IFornecedor>(this.resourceUrl, fornecedor, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFornecedor>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFornecedor[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
