export interface IContactoPessoa {
  id?: number;
  tipoContacto?: string;
  descricao?: string;
  contacto?: string;
  pessoaNome?: string;
  pessoaId?: number;
}

export class ContactoPessoa implements IContactoPessoa {
  constructor(
    public id?: number,
    public tipoContacto?: string,
    public descricao?: string,
    public contacto?: string,
    public pessoaNome?: string,
    public pessoaId?: number
  ) {}
}
