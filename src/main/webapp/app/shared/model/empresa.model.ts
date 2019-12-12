import { Moment } from 'moment';
import { IEscrituracaoContabil } from 'app/shared/model/escrituracao-contabil.model';
import { IEmpresa } from 'app/shared/model/empresa.model';
import { ILocalizacaoEmpresa } from 'app/shared/model/localizacao-empresa.model';
import { IContactoEmpresa } from 'app/shared/model/contacto-empresa.model';
import { ILicencaSoftware } from 'app/shared/model/licenca-software.model';
import { ILancamentoFinanceiro } from 'app/shared/model/lancamento-financeiro.model';
import { IProduto } from 'app/shared/model/produto.model';
import { ILocalArmazenamento } from 'app/shared/model/local-armazenamento.model';
import { ICompra } from 'app/shared/model/compra.model';
import { IVenda } from 'app/shared/model/venda.model';
import { IConta } from 'app/shared/model/conta.model';
import { ICoordenadaBancaria } from 'app/shared/model/coordenada-bancaria.model';

export interface IEmpresa {
  id?: number;
  tipoSociedade?: string;
  nome?: string;
  logotipoContentType?: string;
  logotipo?: any;
  capitalSocial?: number;
  fundacao?: Moment;
  nif?: string;
  numeroRegistroComercial?: string;
  despesaFixa?: number;
  despesaVariavel?: number;
  aberturaExercio?: Moment;
  designacaoComercial?: any;
  sede?: boolean;
  escrituracaoContabils?: IEscrituracaoContabil[];
  empresas?: IEmpresa[];
  localizacaoEmpresas?: ILocalizacaoEmpresa[];
  contactoEmpresas?: IContactoEmpresa[];
  licencaSoftwares?: ILicencaSoftware[];
  lancamentoFinanceiros?: ILancamentoFinanceiro[];
  produtos?: IProduto[];
  localArmazenamentos?: ILocalArmazenamento[];
  compras?: ICompra[];
  vendas?: IVenda[];
  utilizadorLogin?: string;
  utilizadorId?: number;
  contaDescricao?: string;
  contaId?: number;
  hierarquiaNome?: string;
  hierarquiaId?: number;
  contas?: IConta[];
  coordenadaBancarias?: ICoordenadaBancaria[];
}

export class Empresa implements IEmpresa {
  constructor(
    public id?: number,
    public tipoSociedade?: string,
    public nome?: string,
    public logotipoContentType?: string,
    public logotipo?: any,
    public capitalSocial?: number,
    public fundacao?: Moment,
    public nif?: string,
    public numeroRegistroComercial?: string,
    public despesaFixa?: number,
    public despesaVariavel?: number,
    public aberturaExercio?: Moment,
    public designacaoComercial?: any,
    public sede?: boolean,
    public escrituracaoContabils?: IEscrituracaoContabil[],
    public empresas?: IEmpresa[],
    public localizacaoEmpresas?: ILocalizacaoEmpresa[],
    public contactoEmpresas?: IContactoEmpresa[],
    public licencaSoftwares?: ILicencaSoftware[],
    public lancamentoFinanceiros?: ILancamentoFinanceiro[],
    public produtos?: IProduto[],
    public localArmazenamentos?: ILocalArmazenamento[],
    public compras?: ICompra[],
    public vendas?: IVenda[],
    public utilizadorLogin?: string,
    public utilizadorId?: number,
    public contaDescricao?: string,
    public contaId?: number,
    public hierarquiaNome?: string,
    public hierarquiaId?: number,
    public contas?: IConta[],
    public coordenadaBancarias?: ICoordenadaBancaria[]
  ) {
    this.sede = this.sede || false;
  }
}
