import { IUnidadeMedida } from 'app/shared/model/unidade-medida.model';
import { IGrupoTributacaoImposto } from 'app/shared/model/grupo-tributacao-imposto.model';
import { ICompostoProduto } from 'app/shared/model/composto-produto.model';

export interface IUnidadeMedida {
  id?: number;
  nome?: string;
  sigla?: string;
  valor?: number;
  unidadeMedidas?: IUnidadeMedida[];
  grupoTributacaoImpostos?: IGrupoTributacaoImposto[];
  compostoProdutos?: ICompostoProduto[];
  unidadeConversaoNome?: string;
  unidadeConversaoId?: number;
}

export class UnidadeMedida implements IUnidadeMedida {
  constructor(
    public id?: number,
    public nome?: string,
    public sigla?: string,
    public valor?: number,
    public unidadeMedidas?: IUnidadeMedida[],
    public grupoTributacaoImpostos?: IGrupoTributacaoImposto[],
    public compostoProdutos?: ICompostoProduto[],
    public unidadeConversaoNome?: string,
    public unidadeConversaoId?: number
  ) {}
}
