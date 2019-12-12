export interface IRetencaoFonte {
  id?: number;
  motivoRetencao?: string;
  valor?: string;
  porcentagem?: boolean;
  detalheId?: number;
  impostoCodigoImposto?: string;
  impostoId?: number;
}

export class RetencaoFonte implements IRetencaoFonte {
  constructor(
    public id?: number,
    public motivoRetencao?: string,
    public valor?: string,
    public porcentagem?: boolean,
    public detalheId?: number,
    public impostoCodigoImposto?: string,
    public impostoId?: number
  ) {
    this.porcentagem = this.porcentagem || false;
  }
}
