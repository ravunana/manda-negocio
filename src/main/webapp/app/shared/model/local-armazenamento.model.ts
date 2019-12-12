import { IProduto } from 'app/shared/model/produto.model';
import { ILocalArmazenamento } from 'app/shared/model/local-armazenamento.model';

export interface ILocalArmazenamento {
  id?: number;
  nome?: string;
  descricao?: any;
  produtos?: IProduto[];
  localArmazenamentos?: ILocalArmazenamento[];
  hierarquiaNome?: string;
  hierarquiaId?: number;
  empresaNome?: string;
  empresaId?: number;
}

export class LocalArmazenamento implements ILocalArmazenamento {
  constructor(
    public id?: number,
    public nome?: string,
    public descricao?: any,
    public produtos?: IProduto[],
    public localArmazenamentos?: ILocalArmazenamento[],
    public hierarquiaNome?: string,
    public hierarquiaId?: number,
    public empresaNome?: string,
    public empresaId?: number
  ) {}
}
