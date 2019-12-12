import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IEmpresa } from 'app/shared/model/empresa.model';

type EntityResponseType = HttpResponse<IEmpresa>;
type EntityArrayResponseType = HttpResponse<IEmpresa[]>;

@Injectable({ providedIn: 'root' })
export class EmpresaService {
  public resourceUrl = SERVER_API_URL + 'api/empresas';

  constructor(protected http: HttpClient) {}

  create(empresa: IEmpresa): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(empresa);
    return this.http
      .post<IEmpresa>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(empresa: IEmpresa): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(empresa);
    return this.http
      .put<IEmpresa>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IEmpresa>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IEmpresa[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(empresa: IEmpresa): IEmpresa {
    const copy: IEmpresa = Object.assign({}, empresa, {
      fundacao: empresa.fundacao != null && empresa.fundacao.isValid() ? empresa.fundacao.format(DATE_FORMAT) : null,
      aberturaExercio:
        empresa.aberturaExercio != null && empresa.aberturaExercio.isValid() ? empresa.aberturaExercio.format(DATE_FORMAT) : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.fundacao = res.body.fundacao != null ? moment(res.body.fundacao) : null;
      res.body.aberturaExercio = res.body.aberturaExercio != null ? moment(res.body.aberturaExercio) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((empresa: IEmpresa) => {
        empresa.fundacao = empresa.fundacao != null ? moment(empresa.fundacao) : null;
        empresa.aberturaExercio = empresa.aberturaExercio != null ? moment(empresa.aberturaExercio) : null;
      });
    }
    return res;
  }
}
