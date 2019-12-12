import { Moment } from 'moment';

export interface IDevolucaoCompra {
  id?: number;
  quantidade?: number;
  valor?: number;
  desconto?: number;
  data?: Moment;
  descricao?: any;
  itemId?: number;
}

export class DevolucaoCompra implements IDevolucaoCompra {
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
