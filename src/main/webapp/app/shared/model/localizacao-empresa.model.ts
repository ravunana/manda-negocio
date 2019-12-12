export interface ILocalizacaoEmpresa {
  id?: number;
  pais?: string;
  provincia?: string;
  municipio?: string;
  bairro?: string;
  rua?: string;
  quarteirao?: string;
  numeroPorta?: string;
  caixaPostal?: string;
  empresaNome?: string;
  empresaId?: number;
}

export class LocalizacaoEmpresa implements ILocalizacaoEmpresa {
  constructor(
    public id?: number,
    public pais?: string,
    public provincia?: string,
    public municipio?: string,
    public bairro?: string,
    public rua?: string,
    public quarteirao?: string,
    public numeroPorta?: string,
    public caixaPostal?: string,
    public empresaNome?: string,
    public empresaId?: number
  ) {}
}
