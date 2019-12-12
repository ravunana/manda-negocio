import { Moment } from 'moment';

export interface IItemVenda {
  id?: number;
  quantidade?: number;
  valor?: number;
  desconto?: number;
  data?: Moment;
  vendaNumero?: string;
  vendaId?: number;
  produtoNome?: string;
  produtoId?: number;
  statusNome?: string;
  statusId?: number;
}

export class ItemVenda implements IItemVenda {
  constructor(
    public id?: number,
    public quantidade?: number,
    public valor?: number,
    public desconto?: number,
    public data?: Moment,
    public vendaNumero?: string,
    public vendaId?: number,
    public produtoNome?: string,
    public produtoId?: number,
    public statusNome?: string,
    public statusId?: number
  ) {}
}
