import { Moment } from 'moment';
import { IContaDebito } from 'app/shared/model/conta-debito.model';
import { IContaCredito } from 'app/shared/model/conta-credito.model';
import { EntidadeSistema } from 'app/shared/model/enumerations/entidade-sistema.model';

export interface IEscrituracaoContabil {
  id?: number;
  numero?: string;
  historico?: any;
  valor?: number;
  referencia?: string;
  entidadeReferencia?: EntidadeSistema;
  tipo?: string;
  dataDocumento?: Moment;
  data?: Moment;
  contaDebitos?: IContaDebito[];
  contaCreditos?: IContaCredito[];
  utilizadorLogin?: string;
  utilizadorId?: number;
  empresaNome?: string;
  empresaId?: number;
}

export class EscrituracaoContabil implements IEscrituracaoContabil {
  constructor(
    public id?: number,
    public numero?: string,
    public historico?: any,
    public valor?: number,
    public referencia?: string,
    public entidadeReferencia?: EntidadeSistema,
    public tipo?: string,
    public dataDocumento?: Moment,
    public data?: Moment,
    public contaDebitos?: IContaDebito[],
    public contaCreditos?: IContaCredito[],
    public utilizadorLogin?: string,
    public utilizadorId?: number,
    public empresaNome?: string,
    public empresaId?: number
  ) {}
}
