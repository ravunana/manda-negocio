import { Moment } from 'moment';

export interface IDetalheAduaneiro {
  id?: number;
  numeroONU?: string;
  largura?: number;
  altura?: number;
  pesoLiquido?: number;
  pesoBruto?: number;
  dataFabrico?: Moment;
  dataExpiracao?: Moment;
  produtoNome?: string;
  produtoId?: number;
}

export class DetalheAduaneiro implements IDetalheAduaneiro {
  constructor(
    public id?: number,
    public numeroONU?: string,
    public largura?: number,
    public altura?: number,
    public pesoLiquido?: number,
    public pesoBruto?: number,
    public dataFabrico?: Moment,
    public dataExpiracao?: Moment,
    public produtoNome?: string,
    public produtoId?: number
  ) {}
}
