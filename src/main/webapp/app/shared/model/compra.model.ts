import { Moment } from 'moment';
import { IItemCompra } from 'app/shared/model/item-compra.model';

export interface ICompra {
  id?: number;
  numero?: string;
  data?: Moment;
  observacaoGeral?: any;
  observacaoInterna?: any;
  itemCompras?: IItemCompra[];
  utilizadorLogin?: string;
  utilizadorId?: number;
  tipoDocumentoNome?: string;
  tipoDocumentoId?: number;
  empresaNome?: string;
  empresaId?: number;
}

export class Compra implements ICompra {
  constructor(
    public id?: number,
    public numero?: string,
    public data?: Moment,
    public observacaoGeral?: any,
    public observacaoInterna?: any,
    public itemCompras?: IItemCompra[],
    public utilizadorLogin?: string,
    public utilizadorId?: number,
    public tipoDocumentoNome?: string,
    public tipoDocumentoId?: number,
    public empresaNome?: string,
    public empresaId?: number
  ) {}
}
