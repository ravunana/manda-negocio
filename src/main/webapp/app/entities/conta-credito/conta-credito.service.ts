import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IContaCredito } from 'app/shared/model/conta-credito.model';

type EntityResponseType = HttpResponse<IContaCredito>;
type EntityArrayResponseType = HttpResponse<IContaCredito[]>;

@Injectable({ providedIn: 'root' })
export class ContaCreditoService {
  public resourceUrl = SERVER_API_URL + 'api/conta-creditos';

  constructor(protected http: HttpClient) {}

  create(contaCredito: IContaCredito): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(contaCredito);
    return this.http
      .post<IContaCredito>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(contaCredito: IContaCredito): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(contaCredito);
    return this.http
      .put<IContaCredito>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IContaCredito>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IContaCredito[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(contaCredito: IContaCredito): IContaCredito {
    const copy: IContaCredito = Object.assign({}, contaCredito, {
      data: contaCredito.data != null && contaCredito.data.isValid() ? contaCredito.data.toJSON() : null
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
      res.body.forEach((contaCredito: IContaCredito) => {
        contaCredito.data = contaCredito.data != null ? moment(contaCredito.data) : null;
      });
    }
    return res;
  }
}
