import { Moment } from 'moment';

export interface IContaDebito {
  id?: number;
  valor?: number;
  data?: Moment;
  contaDebitarDescricao?: string;
  contaDebitarId?: number;
  lancamentoDebitoHistorico?: string;
  lancamentoDebitoId?: number;
}

export class ContaDebito implements IContaDebito {
  constructor(
    public id?: number,
    public valor?: number,
    public data?: Moment,
    public contaDebitarDescricao?: string,
    public contaDebitarId?: number,
    public lancamentoDebitoHistorico?: string,
    public lancamentoDebitoId?: number
  ) {}
}
