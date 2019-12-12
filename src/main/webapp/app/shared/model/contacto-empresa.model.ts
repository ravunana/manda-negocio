export interface IContactoEmpresa {
  id?: number;
  tipoContacto?: string;
  descricao?: string;
  contacto?: string;
  padrao?: boolean;
  empresaNome?: string;
  empresaId?: number;
}

export class ContactoEmpresa implements IContactoEmpresa {
  constructor(
    public id?: number,
    public tipoContacto?: string,
    public descricao?: string,
    public contacto?: string,
    public padrao?: boolean,
    public empresaNome?: string,
    public empresaId?: number
  ) {
    this.padrao = this.padrao || false;
  }
}
