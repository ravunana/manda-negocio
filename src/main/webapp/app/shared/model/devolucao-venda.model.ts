import { Moment } from 'moment';

export interface IDevolucaoVenda {
  id?: number;
  quantidade?: number;
  valor?: number;
  desconto?: number;
  data?: Moment;
  descricao?: any;
  itemId?: number;
}

export class DevolucaoVenda implements IDevolucaoVenda {
  constructor(
    public id?: number,
    public quantidade?: number,
    public valor?: number,
    public desconto?: number,
    public data?: Moment,
    public descricao?: any,
    public itemId?: number
  ) {}
}
