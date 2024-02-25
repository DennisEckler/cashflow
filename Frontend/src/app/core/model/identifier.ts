import { Transaction } from './transaction';
import { Category } from './category';
export interface Identifier {
  identifierID: number | null;
  identifierLabel: string;
  transactions: Transaction[];
  category: Category;
}
