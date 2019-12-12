import { IFamiliaProduto } from 'app/shared/model/familia-produto.model';
import { IProduto } from 'app/shared/model/produto.model';

export interface IFamiliaProduto {
  id?: number;
  nome?: string;
  descricao?: any;
  familiaProdutos?: IFamiliaProduto[];
  produtos?: IProduto[];
  contaDescricao?: string;
  contaId?: number;
  hierarquiaNome?: string;
  hierarquiaId?: number;
}

export class FamiliaProduto implements IFamiliaProduto {
  constructor(
    public id?: number,
    public nome?: string,
    public descricao?: any,
    public familiaProdutos?: IFamiliaProduto[],
    public produtos?: IProduto[],
    public contaDescricao?: string,
    public contaId?: number,
    public hierarquiaNome?: string,
    public hierarquiaId?: number
  ) {}
}
