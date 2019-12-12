import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IContaDebito } from 'app/shared/model/conta-debito.model';

type EntityResponseType = HttpResponse<IContaDebito>;
type EntityArrayResponseType = HttpResponse<IContaDebito[]>;

@Injectable({ providedIn: 'root' })
export class ContaDebitoService {
  public resourceUrl = SERVER_API_URL + 'api/conta-debitos';

  constructor(protected http: HttpClient) {}

  create(contaDebito: IContaDebito): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(contaDebito);
    return this.http
      .post<IContaDebito>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(contaDebito: IContaDebito): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(contaDebito);
    return this.http
      .put<IContaDebito>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IContaDebito>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IContaDebito[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(contaDebito: IContaDebito): IContaDebito {
    const copy: IContaDebito = Object.assign({}, contaDebito, {
      data: contaDebito.data != null && contaDebito.data.isValid() ? contaDebito.data.toJSON() : null
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
      res.body.forEach((contaDebito: IContaDebito) => {
        contaDebito.data = contaDebito.data != null ? moment(contaDebito.data) : null;
      });
    }
    return res;
  }
}
