import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IEstruturaCalculo } from 'app/shared/model/estrutura-calculo.model';

type EntityResponseType = HttpResponse<IEstruturaCalculo>;
type EntityArrayResponseType = HttpResponse<IEstruturaCalculo[]>;

@Injectable({ providedIn: 'root' })
export class EstruturaCalculoService {
  public resourceUrl = SERVER_API_URL + 'api/estrutura-calculos';

  constructor(protected http: HttpClient) {}

  create(estruturaCalculo: IEstruturaCalculo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(estruturaCalculo);
    return this.http
      .post<IEstruturaCalculo>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(estruturaCalculo: IEstruturaCalculo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(estruturaCalculo);
    return this.http
      .put<IEstruturaCalculo>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IEstruturaCalculo>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IEstruturaCalculo[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(estruturaCalculo: IEstruturaCalculo): IEstruturaCalculo {
    const copy: IEstruturaCalculo = Object.assign({}, estruturaCalculo, {
      data: estruturaCalculo.data != null && estruturaCalculo.data.isValid() ? estruturaCalculo.data.toJSON() : null
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
      res.body.forEach((estruturaCalculo: IEstruturaCalculo) => {
        estruturaCalculo.data = estruturaCalculo.data != null ? moment(estruturaCalculo.data) : null;
      });
    }
    return res;
  }
}
