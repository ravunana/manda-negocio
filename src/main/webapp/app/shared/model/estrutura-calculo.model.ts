import { Moment } from 'moment';

export interface IEstruturaCalculo {
  id?: number;
  custoMateriaPrima?: number;
  custoMaoObra?: number;
  custoEmbalagem?: number;
  custoAquisicao?: number;
  comissao?: number;
  margemLucro?: number;
  precoVenda?: number;
  data?: Moment;
  utilizadorLogin?: string;
  utilizadorId?: number;
  produtoNome?: string;
  produtoId?: number;
}

export class EstruturaCalculo implements IEstruturaCalculo {
  constructor(
    public id?: number,
    public custoMateriaPrima?: number,
    public custoMaoObra?: number,
    public custoEmbalagem?: number,
    public custoAquisicao?: number,
    public comissao?: number,
    public margemLucro?: number,
    public precoVenda?: number,
    public data?: Moment,
    public utilizadorLogin?: string,
    public utilizadorId?: number,
    public produtoNome?: string,
    public produtoId?: number
  ) {}
}
