import { Moment } from 'moment';
import { EntidadeSistema } from 'app/shared/model/enumerations/entidade-sistema.model';

export interface IArquivo {
  id?: number;
  descricao?: string;
  entidade?: EntidadeSistema;
  anexoContentType?: string;
  anexo?: any;
  codigoEntidade?: string;
  data?: Moment;
  utilizadorLogin?: string;
  utilizadorId?: number;
}

export class Arquivo implements IArquivo {
  constructor(
    public id?: number,
    public descricao?: string,
    public entidade?: EntidadeSistema,
    public anexoContentType?: string,
    public anexo?: any,
    public codigoEntidade?: string,
    public data?: Moment,
    public utilizadorLogin?: string,
    public utilizadorId?: number
  ) {}
}
