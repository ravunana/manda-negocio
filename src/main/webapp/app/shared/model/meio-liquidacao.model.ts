import { IDetalheLancamento } from 'app/shared/model/detalhe-lancamento.model';

export interface IMeioLiquidacao {
  id?: number;
  nome?: string;
  sigla?: string;
  icon?: string;
  mostrarPontoVenda?: boolean;
  detalheLancamentos?: IDetalheLancamento[];
}

export class MeioLiquidacao implements IMeioLiquidacao {
  constructor(
    public id?: number,
    public nome?: string,
    public sigla?: string,
    public icon?: string,
    public mostrarPontoVenda?: boolean,
    public detalheLancamentos?: IDetalheLancamento[]
  ) {
    this.mostrarPontoVenda = this.mostrarPontoVenda || false;
  }
}
