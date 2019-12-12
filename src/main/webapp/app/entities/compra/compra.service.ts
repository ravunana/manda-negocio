import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICompra } from 'app/shared/model/compra.model';

type EntityResponseType = HttpResponse<ICompra>;
type EntityArrayResponseType = HttpResponse<ICompra[]>;

@Injectable({ providedIn: 'root' })
export class CompraService {
  public resourceUrl = SERVER_API_URL + 'api/compras';

  constructor(protected http: HttpClient) {}

  create(compra: ICompra): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(compra);
    return this.http
      .post<ICompra>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(compra: ICompra): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(compra);
    return this.http
      .put<ICompra>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICompra>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICompra[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(compra: ICompra): ICompra {
    const copy: ICompra = Object.assign({}, compra, {
      data: compra.data != null && compra.data.isValid() ? compra.data.toJSON() : null
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
      res.body.forEach((compra: ICompra) => {
        compra.data = compra.data != null ? moment(compra.data) : null;
      });
    }
    return res;
  }
}
