import { Moment } from 'moment';

export interface IAuditoriaCompra {
  id?: number;
  estado?: string;
  data?: Moment;
  motivoAlteracaoDocumento?: string;
  origemDocumento?: string;
  hash?: string;
  hashControl?: string;
  compraNumero?: string;
  compraId?: number;
  utilizadorLogin?: string;
  utilizadorId?: number;
}

export class AuditoriaCompra implements IAuditoriaCompra {
  constructor(
    public id?: number,
    public estado?: string,
    public data?: Moment,
    public motivoAlteracaoDocumento?: string,
    public origemDocumento?: string,
    public hash?: string,
    public hashControl?: string,
    public compraNumero?: string,
    public compraId?: number,
    public utilizadorLogin?: string,
    public utilizadorId?: number
  ) {}
}
