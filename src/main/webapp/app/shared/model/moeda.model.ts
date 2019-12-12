import { IDetalheLancamento } from 'app/shared/model/detalhe-lancamento.model';

export interface IMoeda {
  id?: number;
  nome?: string;
  codigo?: string;
  pais?: string;
  nacional?: boolean;
  icon?: string;
  detalheLancamentos?: IDetalheLancamento[];
}

export class Moeda implements IMoeda {
  constructor(
    public id?: number,
    public nome?: string,
    public codigo?: string,
    public pais?: string,
    public nacional?: boolean,
    public icon?: string,
    public detalheLancamentos?: IDetalheLancamento[]
  ) {
    this.nacional = this.nacional || false;
  }
}
