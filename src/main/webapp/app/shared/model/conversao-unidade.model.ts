export interface IConversaoUnidade {
  id?: number;
  valorEntrada?: number;
  valorSaida?: number;
  entradaNome?: string;
  entradaId?: number;
  saidaNome?: string;
  saidaId?: number;
  produtoNome?: string;
  produtoId?: number;
}

export class ConversaoUnidade implements IConversaoUnidade {
  constructor(
    public id?: number,
    public valorEntrada?: number,
    public valorSaida?: number,
    public entradaNome?: string,
    public entradaId?: number,
    public saidaNome?: string,
    public saidaId?: number,
    public produtoNome?: string,
    public produtoId?: number
  ) {}
}
