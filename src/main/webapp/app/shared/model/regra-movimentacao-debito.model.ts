export interface IRegraMovimentacaoDebito {
  id?: number;
  descricao?: any;
  contaDescricao?: string;
  contaId?: number;
}

export class RegraMovimentacaoDebito implements IRegraMovimentacaoDebito {
  constructor(public id?: number, public descricao?: any, public contaDescricao?: string, public contaId?: number) {}
}
