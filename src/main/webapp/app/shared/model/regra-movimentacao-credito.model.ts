export interface IRegraMovimentacaoCredito {
  id?: number;
  descricao?: any;
  contaDescricao?: string;
  contaId?: number;
}

export class RegraMovimentacaoCredito implements IRegraMovimentacaoCredito {
  constructor(public id?: number, public descricao?: any, public contaDescricao?: string, public contaId?: number) {}
}
