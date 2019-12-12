import { IMoradaPessoa } from 'app/shared/model/morada-pessoa.model';
import { IContactoPessoa } from 'app/shared/model/contacto-pessoa.model';

export interface IPessoa {
  id?: number;
  tipoPessoa?: string;
  nome?: string;
  nif?: string;
  imagemContentType?: string;
  imagem?: any;
  moradaPessoas?: IMoradaPessoa[];
  contactoPessoas?: IContactoPessoa[];
  utilizadorLogin?: string;
  utilizadorId?: number;
}

export class Pessoa implements IPessoa {
  constructor(
    public id?: number,
    public tipoPessoa?: string,
    public nome?: string,
    public nif?: string,
    public imagemContentType?: string,
    public imagem?: any,
    public moradaPessoas?: IMoradaPessoa[],
    public contactoPessoas?: IContactoPessoa[],
    public utilizadorLogin?: string,
    public utilizadorId?: number
  ) {}
}
