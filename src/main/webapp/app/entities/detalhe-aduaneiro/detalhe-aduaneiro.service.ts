import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDetalheAduaneiro } from 'app/shared/model/detalhe-aduaneiro.model';

type EntityResponseType = HttpResponse<IDetalheAduaneiro>;
type EntityArrayResponseType = HttpResponse<IDetalheAduaneiro[]>;

@Injectable({ providedIn: 'root' })
export class DetalheAduaneiroService {
  public resourceUrl = SERVER_API_URL + 'api/detalhe-aduaneiros';

  constructor(protected http: HttpClient) {}

  create(detalheAduaneiro: IDetalheAduaneiro): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(detalheAduaneiro);
    return this.http
      .post<IDetalheAduaneiro>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(detalheAduaneiro: IDetalheAduaneiro): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(detalheAduaneiro);
    return this.http
      .put<IDetalheAduaneiro>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IDetalheAduaneiro>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDetalheAduaneiro[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(detalheAduaneiro: IDetalheAduaneiro): IDetalheAduaneiro {
    const copy: IDetalheAduaneiro = Object.assign({}, detalheAduaneiro, {
      dataFabrico:
        detalheAduaneiro.dataFabrico != null && detalheAduaneiro.dataFabrico.isValid()
          ? detalheAduaneiro.dataFabrico.format(DATE_FORMAT)
          : null,
      dataExpiracao:
        detalheAduaneiro.dataExpiracao != null && detalheAduaneiro.dataExpiracao.isValid()
          ? detalheAduaneiro.dataExpiracao.format(DATE_FORMAT)
          : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dataFabrico = res.body.dataFabrico != null ? moment(res.body.dataFabrico) : null;
      res.body.dataExpiracao = res.body.dataExpiracao != null ? moment(res.body.dataExpiracao) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((detalheAduaneiro: IDetalheAduaneiro) => {
        detalheAduaneiro.dataFabrico = detalheAduaneiro.dataFabrico != null ? moment(detalheAduaneiro.dataFabrico) : null;
        detalheAduaneiro.dataExpiracao = detalheAduaneiro.dataExpiracao != null ? moment(detalheAduaneiro.dataExpiracao) : null;
      });
    }
    return res;
  }
}
