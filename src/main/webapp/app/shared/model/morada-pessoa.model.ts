export interface IMoradaPessoa {
  id?: number;
  pais?: string;
  provincia?: string;
  municipio?: string;
  bairro?: string;
  rua?: string;
  quarteirao?: string;
  numeroPorta?: string;
  pessoaNome?: string;
  pessoaId?: number;
}

export class MoradaPessoa implements IMoradaPessoa {
  constructor(
    public id?: number,
    public pais?: string,
    public provincia?: string,
    public municipio?: string,
    public bairro?: string,
    public rua?: string,
    public quarteirao?: string,
    public numeroPorta?: string,
    public pessoaNome?: string,
    public pessoaId?: number
  ) {}
}
