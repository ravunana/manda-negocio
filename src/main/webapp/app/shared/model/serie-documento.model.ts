import { Moment } from 'moment';

export interface ISerieDocumento {
  id?: number;
  serie?: string;
  codigoLancamentoFinanceiro?: number;
  codigoEscrituracaoContabil?: number;
  codigoVenda?: number;
  codigoCompra?: number;
  codigoCliente?: number;
  codigoFornecedor?: number;
  codigoDevolucaoVenda?: number;
  codigoDevolucaoCompra?: number;
  codigoProduto?: number;
  data?: Moment;
  empresaNome?: string;
  empresaId?: number;
}

export class SerieDocumento implements ISerieDocumento {
  constructor(
    public id?: number,
    public serie?: string,
    public codigoLancamentoFinanceiro?: number,
    public codigoEscrituracaoContabil?: number,
    public codigoVenda?: number,
    public codigoCompra?: number,
    public codigoCliente?: number,
    public codigoFornecedor?: number,
    public codigoDevolucaoVenda?: number,
    public codigoDevolucaoCompra?: number,
    public codigoProduto?: number,
    public data?: Moment,
    public empresaNome?: string,
    public empresaId?: number
  ) {}
}
