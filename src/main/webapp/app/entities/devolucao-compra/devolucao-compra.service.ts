import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDevolucaoCompra } from 'app/shared/model/devolucao-compra.model';

type EntityResponseType = HttpResponse<IDevolucaoCompra>;
type EntityArrayResponseType = HttpResponse<IDevolucaoCompra[]>;

@Injectable({ providedIn: 'root' })
export class DevolucaoCompraService {
  public resourceUrl = SERVER_API_URL + 'api/devolucao-compras';

  constructor(protected http: HttpClient) {}

  create(devolucaoCompra: IDevolucaoCompra): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(devolucaoCompra);
    return this.http
      .post<IDevolucaoCompra>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(devolucaoCompra: IDevolucaoCompra): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(devolucaoCompra);
    return this.http
      .put<IDevolucaoCompra>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IDevolucaoCompra>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDevolucaoCompra[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(devolucaoCompra: IDevolucaoCompra): IDevolucaoCompra {
    const copy: IDevolucaoCompra = Object.assign({}, devolucaoCompra, {
      data: devolucaoCompra.data != null && devolucaoCompra.data.isValid() ? devolucaoCompra.data.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.data = res.body.data != null ? moment(res.body.data) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((devolucaoCompra: IDevolucaoCompra) => {
        devolucaoCompra.data = devolucaoCompra.data != null ? moment(devolucaoCompra.data) : null;
      });
    }
    return res;
  }
}
