import { IDetalheLancamento } from 'app/shared/model/detalhe-lancamento.model';
import { IEmpresa } from 'app/shared/model/empresa.model';

export interface ICoordenadaBancaria {
  id?: number;
  descricao?: string;
  proprietario?: string;
  numeroConta?: string;
  iban?: string;
  ativo?: boolean;
  mostrarDocumento?: boolean;
  mostrarPontoVenda?: boolean;
  padraoRecebimento?: boolean;
  padraoPagamento?: boolean;
  detalheLancamentos?: IDetalheLancamento[];
  contaDescricao?: string;
  contaId?: number;
  empresas?: IEmpresa[];
}

export class CoordenadaBancaria implements ICoordenadaBancaria {
  constructor(
    public id?: number,
    public descricao?: string,
    public proprietario?: string,
    public numeroConta?: string,
    public iban?: string,
    public ativo?: boolean,
    public mostrarDocumento?: boolean,
    public mostrarPontoVenda?: boolean,
    public padraoRecebimento?: boolean,
    public padraoPagamento?: boolean,
    public detalheLancamentos?: IDetalheLancamento[],
    public contaDescricao?: string,
    public contaId?: number,
    public empresas?: IEmpresa[]
  ) {
    this.ativo = this.ativo || false;
    this.mostrarDocumento = this.mostrarDocumento || false;
    this.mostrarPontoVenda = this.mostrarPontoVenda || false;
    this.padraoRecebimento = this.padraoRecebimento || false;
    this.padraoPagamento = this.padraoPagamento || false;
  }
}
