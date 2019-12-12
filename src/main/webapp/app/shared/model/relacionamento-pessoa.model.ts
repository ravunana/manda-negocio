export interface IRelacionamentoPessoa {
  id?: number;
  grauParentesco?: string;
  deNome?: string;
  deId?: number;
  paraNome?: string;
  paraId?: number;
}

export class RelacionamentoPessoa implements IRelacionamentoPessoa {
  constructor(
    public id?: number,
    public grauParentesco?: string,
    public deNome?: string,
    public deId?: number,
    public paraNome?: string,
    public paraId?: number
  ) {}
}
