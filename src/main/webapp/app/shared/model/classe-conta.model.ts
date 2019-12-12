import { IConta } from 'app/shared/model/conta.model';

export interface IClasseConta {
  id?: number;
  descricao?: string;
  codigo?: string;
  contas?: IConta[];
}

export class ClasseConta implements IClasseConta {
  constructor(public id?: number, public descricao?: string, public codigo?: string, public contas?: IConta[]) {}
}
