import { IItemCompra } from 'app/shared/model/item-compra.model';
import { IProduto } from 'app/shared/model/produto.model';

export interface IFornecedor {
  id?: number;
  certificadoISO9001?: boolean;
  garantiaDefeitoFabrica?: boolean;
  transporte?: boolean;
  qualidade?: number;
  pagamentoPrazo?: boolean;
  metodosPagamento?: any;
  classificacao?: number;
  descricao?: any;
  ativo?: boolean;
  numero?: string;
  pessoaNome?: string;
  pessoaId?: number;
  itemCompras?: IItemCompra[];
  contaDescricao?: string;
  contaId?: number;
  produtos?: IProduto[];
}

export class Fornecedor implements IFornecedor {
  constructor(
    public id?: number,
    public certificadoISO9001?: boolean,
    public garantiaDefeitoFabrica?: boolean,
    public transporte?: boolean,
    public qualidade?: number,
    public pagamentoPrazo?: boolean,
    public metodosPagamento?: any,
    public classificacao?: number,
    public descricao?: any,
    public ativo?: boolean,
    public numero?: string,
    public pessoaNome?: string,
    public pessoaId?: number,
    public itemCompras?: IItemCompra[],
    public contaDescricao?: string,
    public contaId?: number,
    public produtos?: IProduto[]
  ) {
    this.certificadoISO9001 = this.certificadoISO9001 || false;
    this.garantiaDefeitoFabrica = this.garantiaDefeitoFabrica || false;
    this.transporte = this.transporte || false;
    this.pagamentoPrazo = this.pagamentoPrazo || false;
    this.ativo = this.ativo || false;
  }
}
