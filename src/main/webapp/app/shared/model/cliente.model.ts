import { IVenda } from 'app/shared/model/venda.model';

export interface ICliente {
  id?: number;
  ativo?: boolean;
  perfilProfissional?: any;
  satisfacao?: number;
  frequencia?: number;
  canalUsado?: string;
  numero?: string;
  autofacturacao?: boolean;
  pessoaNome?: string;
  pessoaId?: number;
  vendas?: IVenda[];
  contaDescricao?: string;
  contaId?: number;
}

export class Cliente implements ICliente {
  constructor(
    public id?: number,
    public ativo?: boolean,
    public perfilProfissional?: any,
    public satisfacao?: number,
    public frequencia?: number,
    public canalUsado?: string,
    public numero?: string,
    public autofacturacao?: boolean,
    public pessoaNome?: string,
    public pessoaId?: number,
    public vendas?: IVenda[],
    public contaDescricao?: string,
    public contaId?: number
  ) {
    this.ativo = this.ativo || false;
    this.autofacturacao = this.autofacturacao || false;
  }
}
