import { ILancamentoFinanceiro } from 'app/shared/model/lancamento-financeiro.model';

export interface IFormaLiquidacao {
  id?: number;
  nome?: string;
  juro?: number;
  prazoLiquidacao?: number;
  quantidade?: number;
  icon?: string;
  lancamentoFinanceiros?: ILancamentoFinanceiro[];
}

export class FormaLiquidacao implements IFormaLiquidacao {
  constructor(
    public id?: number,
    public nome?: string,
    public juro?: number,
    public prazoLiquidacao?: number,
    public quantidade?: number,
    public icon?: string,
    public lancamentoFinanceiros?: ILancamentoFinanceiro[]
  ) {}
}
