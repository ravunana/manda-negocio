import { IConta } from 'app/shared/model/conta.model';
import { IContaDebito } from 'app/shared/model/conta-debito.model';
import { IContaCredito } from 'app/shared/model/conta-credito.model';
import { IEmpresa } from 'app/shared/model/empresa.model';

export interface IConta {
  id?: number;
  descricao?: string;
  codigo?: string;
  tipo?: string;
  grau?: number;
  natureza?: string;
  grupo?: string;
  conteudo?: any;
  contas?: IConta[];
  contaDebitos?: IContaDebito[];
  contaCreditos?: IContaCredito[];
  empresas?: IEmpresa[];
  contaAgregadoraDescricao?: string;
  contaAgregadoraId?: number;
  classeContaDescricao?: string;
  classeContaId?: number;
}

export class Conta implements IConta {
  constructor(
    public id?: number,
    public descricao?: string,
    public codigo?: string,
    public tipo?: string,
    public grau?: number,
    public natureza?: string,
    public grupo?: string,
    public conteudo?: any,
    public contas?: IConta[],
    public contaDebitos?: IContaDebito[],
    public contaCreditos?: IContaCredito[],
    public empresas?: IEmpresa[],
    public contaAgregadoraDescricao?: string,
    public contaAgregadoraId?: number,
    public classeContaDescricao?: string,
    public classeContaId?: number
  ) {}
}
