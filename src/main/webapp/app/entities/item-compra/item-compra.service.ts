import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IItemCompra } from 'app/shared/model/item-compra.model';

type EntityResponseType = HttpResponse<IItemCompra>;
type EntityArrayResponseType = HttpResponse<IItemCompra[]>;

@Injectable({ providedIn: 'root' })
export class ItemCompraService {
  public resourceUrl = SERVER_API_URL + 'api/item-compras';

  constructor(protected http: HttpClient) {}

  create(itemCompra: IItemCompra): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(itemCompra);
    return this.http
      .post<IItemCompra>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(itemCompra: IItemCompra): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(itemCompra);
    return this.http
      .put<IItemCompra>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IItemCompra>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IItemCompra[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(itemCompra: IItemCompra): IItemCompra {
    const copy: IItemCompra = Object.assign({}, itemCompra, {
      dataSolicitacao:
        itemCompra.dataSolicitacao != null && itemCompra.dataSolicitacao.isValid() ? itemCompra.dataSolicitacao.toJSON() : null,
      dataEntrega: itemCompra.dataEntrega != null && itemCompra.dataEntrega.isValid() ? itemCompra.dataEntrega.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dataSolicitacao = res.body.dataSolicitacao != null ? moment(res.body.dataSolicitacao) : null;
      res.body.dataEntrega = res.body.dataEntrega != null ? moment(res.body.dataEntrega) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((itemCompra: IItemCompra) => {
        itemCompra.dataSolicitacao = itemCompra.dataSolicitacao != null ? moment(itemCompra.dataSolicitacao) : null;
        itemCompra.dataEntrega = itemCompra.dataEntrega != null ? moment(itemCompra.dataEntrega) : null;
      });
    }
    return res;
  }
}
