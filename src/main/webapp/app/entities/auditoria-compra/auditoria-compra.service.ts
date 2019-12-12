import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAuditoriaCompra } from 'app/shared/model/auditoria-compra.model';

type EntityResponseType = HttpResponse<IAuditoriaCompra>;
type EntityArrayResponseType = HttpResponse<IAuditoriaCompra[]>;

@Injectable({ providedIn: 'root' })
export class AuditoriaCompraService {
  public resourceUrl = SERVER_API_URL + 'api/auditoria-compras';

  constructor(protected http: HttpClient) {}

  create(auditoriaCompra: IAuditoriaCompra): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(auditoriaCompra);
    return this.http
      .post<IAuditoriaCompra>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(auditoriaCompra: IAuditoriaCompra): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(auditoriaCompra);
    return this.http
      .put<IAuditoriaCompra>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAuditoriaCompra>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAuditoriaCompra[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(auditoriaCompra: IAuditoriaCompra): IAuditoriaCompra {
    const copy: IAuditoriaCompra = Object.assign({}, auditoriaCompra, {
      data: auditoriaCompra.data != null && auditoriaCompra.data.isValid() ? auditoriaCompra.data.toJSON() : null
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
      res.body.forEach((auditoriaCompra: IAuditoriaCompra) => {
        auditoriaCompra.data = auditoriaCompra.data != null ? moment(auditoriaCompra.data) : null;
      });
    }
    return res;
  }
}
