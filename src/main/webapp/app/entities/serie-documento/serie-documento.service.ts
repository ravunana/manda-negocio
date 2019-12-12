import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ISerieDocumento } from 'app/shared/model/serie-documento.model';

type EntityResponseType = HttpResponse<ISerieDocumento>;
type EntityArrayResponseType = HttpResponse<ISerieDocumento[]>;

@Injectable({ providedIn: 'root' })
export class SerieDocumentoService {
  public resourceUrl = SERVER_API_URL + 'api/serie-documentos';

  constructor(protected http: HttpClient) {}

  create(serieDocumento: ISerieDocumento): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(serieDocumento);
    return this.http
      .post<ISerieDocumento>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(serieDocumento: ISerieDocumento): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(serieDocumento);
    return this.http
      .put<ISerieDocumento>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ISerieDocumento>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISerieDocumento[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(serieDocumento: ISerieDocumento): ISerieDocumento {
    const copy: ISerieDocumento = Object.assign({}, serieDocumento, {
      data: serieDocumento.data != null && serieDocumento.data.isValid() ? serieDocumento.data.format(DATE_FORMAT) : null
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
      res.body.forEach((serieDocumento: ISerieDocumento) => {
        serieDocumento.data = serieDocumento.data != null ? moment(serieDocumento.data) : null;
      });
    }
    return res;
  }
}
