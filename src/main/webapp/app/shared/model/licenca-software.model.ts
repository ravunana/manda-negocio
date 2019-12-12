import { Moment } from 'moment';

export interface ILicencaSoftware {
  id?: number;
  tipoSubscricao?: string;
  inicio?: Moment;
  fim?: Moment;
  data?: Moment;
  valor?: number;
  codigo?: string;
  numeroUsuario?: number;
  numeroEmpresa?: number;
  softwareNome?: string;
  softwareId?: number;
  empresaNome?: string;
  empresaId?: number;
}

export class LicencaSoftware implements ILicencaSoftware {
  constructor(
    public id?: number,
    public tipoSubscricao?: string,
    public inicio?: Moment,
    public fim?: Moment,
    public data?: Moment,
    public valor?: number,
    public codigo?: string,
    public numeroUsuario?: number,
    public numeroEmpresa?: number,
    public softwareNome?: string,
    public softwareId?: number,
    public empresaNome?: string,
    public empresaId?: number
  ) {}
}
