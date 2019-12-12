export interface ICompostoProduto {
  id?: number;
  quantidade?: number;
  valor?: number;
  permanente?: boolean;
  produtoNome?: string;
  produtoId?: number;
  unidadeNome?: string;
  unidadeId?: number;
  compostoNome?: string;
  compostoId?: number;
}

export class CompostoProduto implements ICompostoProduto {
  constructor(
    public id?: number,
    public quantidade?: number,
    public valor?: number,
    public permanente?: boolean,
    public produtoNome?: string,
    public produtoId?: number,
    public unidadeNome?: string,
    public unidadeId?: number,
    public compostoNome?: string,
    public compostoId?: number
  ) {
    this.permanente = this.permanente || false;
  }
}
