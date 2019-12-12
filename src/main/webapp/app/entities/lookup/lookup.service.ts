import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ILookup } from 'app/shared/model/lookup.model';

type EntityResponseType = HttpResponse<ILookup>;
type EntityArrayResponseType = HttpResponse<ILookup[]>;

@Injectable({ providedIn: 'root' })
export class LookupService {
  public resourceUrl = SERVER_API_URL + 'api/lookups';

  constructor(protected http: HttpClient) {}

  create(lookup: ILookup): Observable<EntityResponseType> {
    return this.http.post<ILookup>(this.resourceUrl, lookup, { observe: 'response' });
  }

  update(lookup: ILookup): Observable<EntityResponseType> {
    return this.http.put<ILookup>(this.resourceUrl, lookup, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ILookup>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ILookup[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
