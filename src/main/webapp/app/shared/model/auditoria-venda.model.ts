import { Moment } from 'moment';

export interface IAuditoriaVenda {
  id?: number;
  estado?: string;
  data?: Moment;
  motivoAlteracaoDocumento?: string;
  origemDocumento?: string;
  hash?: string;
  hashControl?: string;
  vendaNumero?: string;
  vendaId?: number;
  utilizadorLogin?: string;
  utilizadorId?: number;
}

export class AuditoriaVenda implements IAuditoriaVenda {
  constructor(
    public id?: number,
    public estado?: string,
    public data?: Moment,
    public motivoAlteracaoDocumento?: string,
    public origemDocumento?: string,
    public hash?: string,
    public hashControl?: string,
    public vendaNumero?: string,
    public vendaId?: number,
    public utilizadorLogin?: string,
    public utilizadorId?: number
  ) {}
}
