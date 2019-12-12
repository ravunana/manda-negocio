import { ILookupItem } from 'app/shared/model/lookup-item.model';
import { EntidadeSistema } from 'app/shared/model/enumerations/entidade-sistema.model';

export interface ILookup {
  id?: number;
  nome?: string;
  entidade?: EntidadeSistema;
  modificavel?: boolean;
  lookupItems?: ILookupItem[];
}

export class Lookup implements ILookup {
  constructor(
    public id?: number,
    public nome?: string,
    public entidade?: EntidadeSistema,
    public modificavel?: boolean,
    public lookupItems?: ILookupItem[]
  ) {
    this.modificavel = this.modificavel || false;
  }
}
