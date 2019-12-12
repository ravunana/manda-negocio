import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IImposto } from 'app/shared/model/imposto.model';

type EntityResponseType = HttpResponse<IImposto>;
type EntityArrayResponseType = HttpResponse<IImposto[]>;

@Injectable({ providedIn: 'root' })
export class ImpostoService {
  public resourceUrl = SERVER_API_URL + 'api/impostos';

  constructor(protected http: HttpClient) {}

  create(imposto: IImposto): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(imposto);
    return this.http
      .post<IImposto>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(imposto: IImposto): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(imposto);
    return this.http
      .put<IImposto>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IImposto>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IImposto[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(imposto: IImposto): IImposto {
    const copy: IImposto = Object.assign({}, imposto, {
      vigencia: imposto.vigencia != null && imposto.vigencia.isValid() ? imposto.vigencia.format(DATE_FORMAT) : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.vigencia = res.body.vigencia != null ? moment(res.body.vigencia) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((imposto: IImposto) => {
        imposto.vigencia = imposto.vigencia != null ? moment(imposto.vigencia) : null;
      });
    }
    return res;
  }
}
