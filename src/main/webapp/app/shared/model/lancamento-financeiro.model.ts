import { IDetalheLancamento } from 'app/shared/model/detalhe-lancamento.model';
import { IImposto } from 'app/shared/model/imposto.model';
import { EntidadeSistema } from 'app/shared/model/enumerations/entidade-sistema.model';

export interface ILancamentoFinanceiro {
  id?: number;
  tipoLancamento?: string;
  valor?: number;
  externo?: boolean;
  numero?: string;
  descricao?: any;
  entidadeDocumento?: EntidadeSistema;
  numeroDocumento?: string;
  detalheLancamentos?: IDetalheLancamento[];
  utilizadorLogin?: string;
  utilizadorId?: number;
  impostos?: IImposto[];
  formaLiquidacaoNome?: string;
  formaLiquidacaoId?: number;
  empresaNome?: string;
  empresaId?: number;
  tipoReciboNome?: string;
  tipoReciboId?: number;
  contaDescricao?: string;
  contaId?: number;
}

export class LancamentoFinanceiro implements ILancamentoFinanceiro {
  constructor(
    public id?: number,
    public tipoLancamento?: string,
    public valor?: number,
    public externo?: boolean,
    public numero?: string,
    public descricao?: any,
    public entidadeDocumento?: EntidadeSistema,
    public numeroDocumento?: string,
    public detalheLancamentos?: IDetalheLancamento[],
    public utilizadorLogin?: string,
    public utilizadorId?: number,
    public impostos?: IImposto[],
    public formaLiquidacaoNome?: string,
    public formaLiquidacaoId?: number,
    public empresaNome?: string,
    public empresaId?: number,
    public tipoReciboNome?: string,
    public tipoReciboId?: number,
    public contaDescricao?: string,
    public contaId?: number
  ) {
    this.externo = this.externo || false;
  }
}
