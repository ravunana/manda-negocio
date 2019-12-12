import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDetalheLancamento } from 'app/shared/model/detalhe-lancamento.model';

type EntityResponseType = HttpResponse<IDetalheLancamento>;
type EntityArrayResponseType = HttpResponse<IDetalheLancamento[]>;

@Injectable({ providedIn: 'root' })
export class DetalheLancamentoService {
  public resourceUrl = SERVER_API_URL + 'api/detalhe-lancamentos';

  constructor(protected http: HttpClient) {}

  create(detalheLancamento: IDetalheLancamento): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(detalheLancamento);
    return this.http
      .post<IDetalheLancamento>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(detalheLancamento: IDetalheLancamento): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(detalheLancamento);
    return this.http
      .put<IDetalheLancamento>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IDetalheLancamento>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDetalheLancamento[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(detalheLancamento: IDetalheLancamento): IDetalheLancamento {
    const copy: IDetalheLancamento = Object.assign({}, detalheLancamento, {
      data: detalheLancamento.data != null && detalheLancamento.data.isValid() ? detalheLancamento.data.toJSON() : null,
      dataVencimento:
        detalheLancamento.dataVencimento != null && detalheLancamento.dataVencimento.isValid()
          ? detalheLancamento.dataVencimento.format(DATE_FORMAT)
          : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.data = res.body.data != null ? moment(res.body.data) : null;
      res.body.dataVencimento = res.body.dataVencimento != null ? moment(res.body.dataVencimento) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((detalheLancamento: IDetalheLancamento) => {
        detalheLancamento.data = detalheLancamento.data != null ? moment(detalheLancamento.data) : null;
        detalheLancamento.dataVencimento = detalheLancamento.dataVencimento != null ? moment(detalheLancamento.dataVencimento) : null;
      });
    }
    return res;
  }
}
