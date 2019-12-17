import { Moment } from 'moment';
import { IItemCompra } from 'app/shared/model/item-compra.model';
import { IImposto } from './imposto.model';

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
  impostos?: IImposto[];
  formaLiquidacaoId?: number;
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
    public empresaId?: number,
    public impostos?: IImposto[],
    public formaLiquidacaoId?: number
  ) {}
}
