import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAuditoriaVenda } from 'app/shared/model/auditoria-venda.model';

type EntityResponseType = HttpResponse<IAuditoriaVenda>;
type EntityArrayResponseType = HttpResponse<IAuditoriaVenda[]>;

@Injectable({ providedIn: 'root' })
export class AuditoriaVendaService {
  public resourceUrl = SERVER_API_URL + 'api/auditoria-vendas';

  constructor(protected http: HttpClient) {}

  create(auditoriaVenda: IAuditoriaVenda): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(auditoriaVenda);
    return this.http
      .post<IAuditoriaVenda>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(auditoriaVenda: IAuditoriaVenda): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(auditoriaVenda);
    return this.http
      .put<IAuditoriaVenda>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAuditoriaVenda>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAuditoriaVenda[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(auditoriaVenda: IAuditoriaVenda): IAuditoriaVenda {
    const copy: IAuditoriaVenda = Object.assign({}, auditoriaVenda, {
      data: auditoriaVenda.data != null && auditoriaVenda.data.isValid() ? auditoriaVenda.data.toJSON() : null
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
      res.body.forEach((auditoriaVenda: IAuditoriaVenda) => {
        auditoriaVenda.data = auditoriaVenda.data != null ? moment(auditoriaVenda.data) : null;
      });
    }
    return res;
  }
}
