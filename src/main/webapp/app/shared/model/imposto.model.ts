import { Moment } from 'moment';
import { IGrupoTributacaoImposto } from 'app/shared/model/grupo-tributacao-imposto.model';
import { IRetencaoFonte } from 'app/shared/model/retencao-fonte.model';
import { ILancamentoFinanceiro } from 'app/shared/model/lancamento-financeiro.model';
import { IProduto } from 'app/shared/model/produto.model';

export interface IImposto {
  id?: number;
  tipoImposto?: string;
  codigoImposto?: string;
  porcentagem?: boolean;
  valor?: number;
  descricao?: any;
  pais?: string;
  vigencia?: Moment;
  grupoTributacaoImpostos?: IGrupoTributacaoImposto[];
  retencaoFontes?: IRetencaoFonte[];
  contaDescricao?: string;
  contaId?: number;
  lancamentos?: ILancamentoFinanceiro[];
  produtos?: IProduto[];
}

export class Imposto implements IImposto {
  constructor(
    public id?: number,
    public tipoImposto?: string,
    public codigoImposto?: string,
    public porcentagem?: boolean,
    public valor?: number,
    public descricao?: any,
    public pais?: string,
    public vigencia?: Moment,
    public grupoTributacaoImpostos?: IGrupoTributacaoImposto[],
    public retencaoFontes?: IRetencaoFonte[],
    public contaDescricao?: string,
    public contaId?: number,
    public lancamentos?: ILancamentoFinanceiro[],
    public produtos?: IProduto[]
  ) {
    this.porcentagem = this.porcentagem || false;
  }
}
