import { ILancamentoFinanceiro } from 'app/shared/model/lancamento-financeiro.model';
import { ICompra } from 'app/shared/model/compra.model';
import { IVenda } from 'app/shared/model/venda.model';
import { EntidadeSistema } from 'app/shared/model/enumerations/entidade-sistema.model';

export interface IDocumentoComercial {
  id?: number;
  nome?: string;
  serie?: string;
  padrao?: boolean;
  movimentaEstoque?: boolean;
  movimentaCaixa?: boolean;
  movimentaContabilidade?: boolean;
  cor?: string;
  descricao?: any;
  mostraPontoVenda?: boolean;
  entidade?: EntidadeSistema;
  lancamentoFinanceiros?: ILancamentoFinanceiro[];
  compras?: ICompra[];
  vendas?: IVenda[];
}

export class DocumentoComercial implements IDocumentoComercial {
  constructor(
    public id?: number,
    public nome?: string,
    public serie?: string,
    public padrao?: boolean,
    public movimentaEstoque?: boolean,
    public movimentaCaixa?: boolean,
    public movimentaContabilidade?: boolean,
    public cor?: string,
    public descricao?: any,
    public mostraPontoVenda?: boolean,
    public entidade?: EntidadeSistema,
    public lancamentoFinanceiros?: ILancamentoFinanceiro[],
    public compras?: ICompra[],
    public vendas?: IVenda[]
  ) {
    this.padrao = this.padrao || false;
    this.movimentaEstoque = this.movimentaEstoque || false;
    this.movimentaCaixa = this.movimentaCaixa || false;
    this.movimentaContabilidade = this.movimentaContabilidade || false;
    this.mostraPontoVenda = this.mostraPontoVenda || false;
  }
}
