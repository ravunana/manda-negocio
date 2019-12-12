import { ICompostoProduto } from 'app/shared/model/composto-produto.model';
import { IConversaoUnidade } from 'app/shared/model/conversao-unidade.model';
import { IEstruturaCalculo } from 'app/shared/model/estrutura-calculo.model';
import { IItemCompra } from 'app/shared/model/item-compra.model';
import { IItemVenda } from 'app/shared/model/item-venda.model';
import { IImposto } from 'app/shared/model/imposto.model';
import { IFornecedor } from 'app/shared/model/fornecedor.model';

export interface IProduto {
  id?: number;
  tipo?: string;
  ean?: string;
  numero?: string;
  nome?: string;
  imagemContentType?: string;
  imagem?: any;
  composto?: boolean;
  estoqueMinimo?: number;
  estoqueMaximo?: number;
  estoqueAtual?: number;
  descricao?: any;
  mostrarPDV?: boolean;
  prazoMedioEntrega?: string;
  compostoProdutos?: ICompostoProduto[];
  conversaoUnidades?: IConversaoUnidade[];
  estruturaCalculos?: IEstruturaCalculo[];
  itemCompras?: IItemCompra[];
  itemVendas?: IItemVenda[];
  utilizadorLogin?: string;
  utilizadorId?: number;
  impostos?: IImposto[];
  fornecedors?: IFornecedor[];
  localArmazenamentoNome?: string;
  localArmazenamentoId?: number;
  familiaNome?: string;
  familiaId?: number;
  empresaNome?: string;
  empresaId?: number;
  estadoNome?: string;
  estadoId?: number;
}

export class Produto implements IProduto {
  constructor(
    public id?: number,
    public tipo?: string,
    public ean?: string,
    public numero?: string,
    public nome?: string,
    public imagemContentType?: string,
    public imagem?: any,
    public composto?: boolean,
    public estoqueMinimo?: number,
    public estoqueMaximo?: number,
    public estoqueAtual?: number,
    public descricao?: any,
    public mostrarPDV?: boolean,
    public prazoMedioEntrega?: string,
    public compostoProdutos?: ICompostoProduto[],
    public conversaoUnidades?: IConversaoUnidade[],
    public estruturaCalculos?: IEstruturaCalculo[],
    public itemCompras?: IItemCompra[],
    public itemVendas?: IItemVenda[],
    public utilizadorLogin?: string,
    public utilizadorId?: number,
    public impostos?: IImposto[],
    public fornecedors?: IFornecedor[],
    public localArmazenamentoNome?: string,
    public localArmazenamentoId?: number,
    public familiaNome?: string,
    public familiaId?: number,
    public empresaNome?: string,
    public empresaId?: number,
    public estadoNome?: string,
    public estadoId?: number
  ) {
    this.composto = this.composto || false;
    this.mostrarPDV = this.mostrarPDV || false;
  }
}
