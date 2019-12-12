export interface IGrupoTributacaoImposto {
  id?: number;
  nome?: string;
  valor?: number;
  de?: number;
  ate?: number;
  limiteLiquidacao?: number;
  impostoCodigoImposto?: string;
  impostoId?: number;
  unidadeLimiteNome?: string;
  unidadeLimiteId?: number;
}

export class GrupoTributacaoImposto implements IGrupoTributacaoImposto {
  constructor(
    public id?: number,
    public nome?: string,
    public valor?: number,
    public de?: number,
    public ate?: number,
    public limiteLiquidacao?: number,
    public impostoCodigoImposto?: string,
    public impostoId?: number,
    public unidadeLimiteNome?: string,
    public unidadeLimiteId?: number
  ) {}
}
