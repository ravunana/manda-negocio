import { Moment } from 'moment';

export interface IContaCredito {
  id?: number;
  valor?: number;
  data?: Moment;
  contaCreditarDescricao?: string;
  contaCreditarId?: number;
  lancamentoCreditoHistorico?: string;
  lancamentoCreditoId?: number;
}

export class ContaCredito implements IContaCredito {
  constructor(
    public id?: number,
    public valor?: number,
    public data?: Moment,
    public contaCreditarDescricao?: string,
    public contaCreditarId?: number,
    public lancamentoCreditoHistorico?: string,
    public lancamentoCreditoId?: number
  ) {}
}
