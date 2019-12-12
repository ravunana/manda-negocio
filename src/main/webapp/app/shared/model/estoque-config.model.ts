export interface IEstoqueConfig {
  id?: number;
  produtoNome?: string;
  produtoId?: number;
  contaVendaDescricao?: string;
  contaVendaId?: number;
  contaCompraDescricao?: string;
  contaCompraId?: number;
  contaImobilizadoDescricao?: string;
  contaImobilizadoId?: number;
  devolucaoCompraDescricao?: string;
  devolucaoCompraId?: number;
  devolucaoVendaDescricao?: string;
  devolucaoVendaId?: number;
}

export class EstoqueConfig implements IEstoqueConfig {
  constructor(
    public id?: number,
    public produtoNome?: string,
    public produtoId?: number,
    public contaVendaDescricao?: string,
    public contaVendaId?: number,
    public contaCompraDescricao?: string,
    public contaCompraId?: number,
    public contaImobilizadoDescricao?: string,
    public contaImobilizadoId?: number,
    public devolucaoCompraDescricao?: string,
    public devolucaoCompraId?: number,
    public devolucaoVendaDescricao?: string,
    public devolucaoVendaId?: number
  ) {}
}
