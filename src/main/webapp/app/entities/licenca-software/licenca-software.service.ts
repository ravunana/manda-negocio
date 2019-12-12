import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ILicencaSoftware } from 'app/shared/model/licenca-software.model';

type EntityResponseType = HttpResponse<ILicencaSoftware>;
type EntityArrayResponseType = HttpResponse<ILicencaSoftware[]>;

@Injectable({ providedIn: 'root' })
export class LicencaSoftwareService {
  public resourceUrl = SERVER_API_URL + 'api/licenca-softwares';

  constructor(protected http: HttpClient) {}

  create(licencaSoftware: ILicencaSoftware): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(licencaSoftware);
    return this.http
      .post<ILicencaSoftware>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(licencaSoftware: ILicencaSoftware): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(licencaSoftware);
    return this.http
      .put<ILicencaSoftware>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ILicencaSoftware>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ILicencaSoftware[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(licencaSoftware: ILicencaSoftware): ILicencaSoftware {
    const copy: ILicencaSoftware = Object.assign({}, licencaSoftware, {
      inicio: licencaSoftware.inicio != null && licencaSoftware.inicio.isValid() ? licencaSoftware.inicio.toJSON() : null,
      fim: licencaSoftware.fim != null && licencaSoftware.fim.isValid() ? licencaSoftware.fim.toJSON() : null,
      data: licencaSoftware.data != null && licencaSoftware.data.isValid() ? licencaSoftware.data.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.inicio = res.body.inicio != null ? moment(res.body.inicio) : null;
      res.body.fim = res.body.fim != null ? moment(res.body.fim) : null;
      res.body.data = res.body.data != null ? moment(res.body.data) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((licencaSoftware: ILicencaSoftware) => {
        licencaSoftware.inicio = licencaSoftware.inicio != null ? moment(licencaSoftware.inicio) : null;
        licencaSoftware.fim = licencaSoftware.fim != null ? moment(licencaSoftware.fim) : null;
        licencaSoftware.data = licencaSoftware.data != null ? moment(licencaSoftware.data) : null;
      });
    }
    return res;
  }
}
