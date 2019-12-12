export interface IVariacaoProduto {
  id?: number;
  genero?: string;
  cor?: string;
  modelo?: string;
  marca?: string;
  tamanho?: string;
  produtoNome?: string;
  produtoId?: number;
}

export class VariacaoProduto implements IVariacaoProduto {
  constructor(
    public id?: number,
    public genero?: string,
    public cor?: string,
    public modelo?: string,
    public marca?: string,
    public tamanho?: string,
    public produtoNome?: string,
    public produtoId?: number
  ) {}
}
