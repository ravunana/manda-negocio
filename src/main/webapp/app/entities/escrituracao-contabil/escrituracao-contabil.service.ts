import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IEscrituracaoContabil } from 'app/shared/model/escrituracao-contabil.model';

type EntityResponseType = HttpResponse<IEscrituracaoContabil>;
type EntityArrayResponseType = HttpResponse<IEscrituracaoContabil[]>;

@Injectable({ providedIn: 'root' })
export class EscrituracaoContabilService {
  public resourceUrl = SERVER_API_URL + 'api/escrituracao-contabils';

  constructor(protected http: HttpClient) {}

  create(escrituracaoContabil: IEscrituracaoContabil): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(escrituracaoContabil);
    return this.http
      .post<IEscrituracaoContabil>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(escrituracaoContabil: IEscrituracaoContabil): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(escrituracaoContabil);
    return this.http
      .put<IEscrituracaoContabil>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IEscrituracaoContabil>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IEscrituracaoContabil[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(escrituracaoContabil: IEscrituracaoContabil): IEscrituracaoContabil {
    const copy: IEscrituracaoContabil = Object.assign({}, escrituracaoContabil, {
      dataDocumento:
        escrituracaoContabil.dataDocumento != null && escrituracaoContabil.dataDocumento.isValid()
          ? escrituracaoContabil.dataDocumento.format(DATE_FORMAT)
          : null,
      data: escrituracaoContabil.data != null && escrituracaoContabil.data.isValid() ? escrituracaoContabil.data.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dataDocumento = res.body.dataDocumento != null ? moment(res.body.dataDocumento) : null;
      res.body.data = res.body.data != null ? moment(res.body.data) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((escrituracaoContabil: IEscrituracaoContabil) => {
        escrituracaoContabil.dataDocumento = escrituracaoContabil.dataDocumento != null ? moment(escrituracaoContabil.dataDocumento) : null;
        escrituracaoContabil.data = escrituracaoContabil.data != null ? moment(escrituracaoContabil.data) : null;
      });
    }
    return res;
  }
}
