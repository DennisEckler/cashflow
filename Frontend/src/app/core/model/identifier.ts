import { Category } from './category';

export interface Identifier {
  identifierID: number;
  identifierLabel: string;
  category: Category;
}
