import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ILancamentoFinanceiro } from 'app/shared/model/lancamento-financeiro.model';
import { IDetalheLancamento } from 'app/shared/model/detalhe-lancamento.model';
import { EntidadeSistema } from 'app/shared/model/enumerations/entidade-sistema.model';

type EntityResponseType = HttpResponse<ILancamentoFinanceiro>;
type EntityArrayResponseType = HttpResponse<ILancamentoFinanceiro[]>;

@Injectable({ providedIn: 'root' })
export class LancamentoFinanceiroService {
  public resourceUrl = SERVER_API_URL + 'api/lancamento-financeiros';

  constructor(protected http: HttpClient) {}

  create(lancamentoFinanceiro: ILancamentoFinanceiro): Observable<EntityResponseType> {
    return this.http.post<ILancamentoFinanceiro>(this.resourceUrl, lancamentoFinanceiro, { observe: 'response' });
  }

  update(lancamentoFinanceiro: ILancamentoFinanceiro): Observable<EntityResponseType> {
    return this.http.put<ILancamentoFinanceiro>(this.resourceUrl, lancamentoFinanceiro, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ILancamentoFinanceiro>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ILancamentoFinanceiro[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addDetalhe(detalhe: IDetalheLancamento): Observable<IDetalheLancamento> {
    return this.http.post<IDetalheLancamento>(`${this.resourceUrl}/add-valor`, detalhe);
  }

  deleteDetalhe(index: number): Observable<IDetalheLancamento> {
    return this.http.delete<IDetalheLancamento>(`${this.resourceUrl}/delete-detalhe/${index}`);
  }

  getDetalhes(): Observable<IDetalheLancamento[]> {
    return this.http.get<IDetalheLancamento[]>(this.resourceUrl + '/listar-detalhes');
  }

  // getLancamentoByEntidadeAndNumero( entidade: EntidadeSistema, numero: string): Observable<ILancamentoFinanceiro> {
  //   return this.http.get<ILancamentoFinanceiro>(this.resourceUrl + '/' + entidade + '/' + numero);
  // }

  getLancamentoByEntidadeAndNumero(entidade: EntidadeSistema, numero: string): Observable<ILancamentoFinanceiro> {
    return this.http.get<ILancamentoFinanceiro>(`${this.resourceUrl}/${entidade}/${numero}`);
  }
}
