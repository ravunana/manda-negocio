import { Moment } from 'moment';
import { IRetencaoFonte } from 'app/shared/model/retencao-fonte.model';

export interface IDetalheLancamento {
  id?: number;
  valor?: number;
  desconto?: number;
  juro?: number;
  descricao?: any;
  data?: Moment;
  retencaoFonte?: boolean;
  dataVencimento?: Moment;
  liquidado?: boolean;
  retencaoFontes?: IRetencaoFonte[];
  utilizadorLogin?: string;
  utilizadorId?: number;
  lancamentoFinanceiroNumero?: string;
  lancamentoFinanceiroId?: number;
  metodoLiquidacaoNome?: string;
  metodoLiquidacaoId?: number;
  moedaCodigo?: string;
  moedaId?: number;
  coordenadaDescricao?: string;
  coordenadaId?: number;
}

export class DetalheLancamento implements IDetalheLancamento {
  constructor(
    public id?: number,
    public valor?: number,
    public desconto?: number,
    public juro?: number,
    public descricao?: any,
    public data?: Moment,
    public retencaoFonte?: boolean,
    public dataVencimento?: Moment,
    public liquidado?: boolean,
    public retencaoFontes?: IRetencaoFonte[],
    public utilizadorLogin?: string,
    public utilizadorId?: number,
    public lancamentoFinanceiroNumero?: string,
    public lancamentoFinanceiroId?: number,
    public metodoLiquidacaoNome?: string,
    public metodoLiquidacaoId?: number,
    public moedaCodigo?: string,
    public moedaId?: number,
    public coordenadaDescricao?: string,
    public coordenadaId?: number
  ) {
    this.retencaoFonte = this.retencaoFonte || false;
    this.liquidado = this.liquidado || false;
  }
}
