import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IItemVenda } from 'app/shared/model/item-venda.model';

type EntityResponseType = HttpResponse<IItemVenda>;
type EntityArrayResponseType = HttpResponse<IItemVenda[]>;

@Injectable({ providedIn: 'root' })
export class ItemVendaService {
  public resourceUrl = SERVER_API_URL + 'api/item-vendas';

  constructor(protected http: HttpClient) {}

  create(itemVenda: IItemVenda): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(itemVenda);
    return this.http
      .post<IItemVenda>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(itemVenda: IItemVenda): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(itemVenda);
    return this.http
      .put<IItemVenda>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IItemVenda>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IItemVenda[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(itemVenda: IItemVenda): IItemVenda {
    const copy: IItemVenda = Object.assign({}, itemVenda, {
      data: itemVenda.data != null && itemVenda.data.isValid() ? itemVenda.data.toJSON() : null
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
      res.body.forEach((itemVenda: IItemVenda) => {
        itemVenda.data = itemVenda.data != null ? moment(itemVenda.data) : null;
      });
    }
    return res;
  }
}
