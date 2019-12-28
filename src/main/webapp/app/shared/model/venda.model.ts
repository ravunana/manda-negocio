import { Moment } from 'moment';
import { IItemVenda } from 'app/shared/model/item-venda.model';
import { IImposto } from './imposto.model';

export interface IVenda {
  id?: number;
  numero?: string;
  data?: Moment;
  observacaoGeral?: any;
  observacaoInterna?: any;
  itemVendas?: IItemVenda[];
  vendedorLogin?: string;
  vendedorId?: number;
  clienteNumero?: string;
  clienteId?: number;
  tipoDocumentoNome?: string;
  tipoDocumentoId?: number;
  empresaNome?: string;
  empresaId?: number;
  impostos?: IImposto[];
  formaLiquidacaoId?: number;
}

export class Venda implements IVenda {
  constructor(
    public id?: number,
    public numero?: string,
    public data?: Moment,
    public observacaoGeral?: any,
    public observacaoInterna?: any,
    public itemVendas?: IItemVenda[],
    public vendedorLogin?: string,
    public vendedorId?: number,
    public clienteNumero?: string,
    public clienteId?: number,
    public tipoDocumentoNome?: string,
    public tipoDocumentoId?: number,
    public empresaNome?: string,
    public empresaId?: number,
    public impostos?: IImposto[],
    public formaLiquidacaoId?: number
  ) {}
}
