import { Moment } from 'moment';

export interface IItemCompra {
  id?: number;
  quantidade?: number;
  desconto?: number;
  dataSolicitacao?: Moment;
  dataEntrega?: Moment;
  descricao?: any;
  valor?: number;
  solicitante?: string;
  compraNumero?: string;
  compraId?: number;
  produtoNome?: string;
  produtoId?: number;
  fornecedorNumero?: string;
  fornecedorId?: number;
  statusNome?: string;
  statusId?: number;
}

export class ItemCompra implements IItemCompra {
  constructor(
    public id?: number,
    public quantidade?: number,
    public desconto?: number,
    public dataSolicitacao?: Moment,
    public dataEntrega?: Moment,
    public descricao?: any,
    public valor?: number,
    public solicitante?: string,
    public compraNumero?: string,
    public compraId?: number,
    public produtoNome?: string,
    public produtoId?: number,
    public fornecedorNumero?: string,
    public fornecedorId?: number,
    public statusNome?: string,
    public statusId?: number
  ) {}
}
