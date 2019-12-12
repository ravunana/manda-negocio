import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDevolucaoVenda } from 'app/shared/model/devolucao-venda.model';

type EntityResponseType = HttpResponse<IDevolucaoVenda>;
type EntityArrayResponseType = HttpResponse<IDevolucaoVenda[]>;

@Injectable({ providedIn: 'root' })
export class DevolucaoVendaService {
  public resourceUrl = SERVER_API_URL + 'api/devolucao-vendas';

  constructor(protected http: HttpClient) {}

  create(devolucaoVenda: IDevolucaoVenda): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(devolucaoVenda);
    return this.http
      .post<IDevolucaoVenda>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(devolucaoVenda: IDevolucaoVenda): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(devolucaoVenda);
    return this.http
      .put<IDevolucaoVenda>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IDevolucaoVenda>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDevolucaoVenda[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(devolucaoVenda: IDevolucaoVenda): IDevolucaoVenda {
    const copy: IDevolucaoVenda = Object.assign({}, devolucaoVenda, {
      data: devolucaoVenda.data != null && devolucaoVenda.data.isValid() ? devolucaoVenda.data.toJSON() : null
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
      res.body.forEach((devolucaoVenda: IDevolucaoVenda) => {
        devolucaoVenda.data = devolucaoVenda.data != null ? moment(devolucaoVenda.data) : null;
      });
    }
    return res;
  }
}
