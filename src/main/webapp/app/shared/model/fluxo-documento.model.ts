import { IProduto } from 'app/shared/model/produto.model';
import { IItemCompra } from 'app/shared/model/item-compra.model';
import { IItemVenda } from 'app/shared/model/item-venda.model';
import { EntidadeSistema } from 'app/shared/model/enumerations/entidade-sistema.model';

export interface IFluxoDocumento {
  id?: number;
  nome?: string;
  aumentaEstoque?: boolean;
  aumentaValorCaixa?: boolean;
  cor?: string;
  padrao?: boolean;
  descricao?: any;
  entidade?: EntidadeSistema;
  produtos?: IProduto[];
  itemCompras?: IItemCompra[];
  itemVendas?: IItemVenda[];
}

export class FluxoDocumento implements IFluxoDocumento {
  constructor(
    public id?: number,
    public nome?: string,
    public aumentaEstoque?: boolean,
    public aumentaValorCaixa?: boolean,
    public cor?: string,
    public padrao?: boolean,
    public descricao?: any,
    public entidade?: EntidadeSistema,
    public produtos?: IProduto[],
    public itemCompras?: IItemCompra[],
    public itemVendas?: IItemVenda[]
  ) {
    this.aumentaEstoque = this.aumentaEstoque || false;
    this.aumentaValorCaixa = this.aumentaValorCaixa || false;
    this.padrao = this.padrao || false;
  }
}
