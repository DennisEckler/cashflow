import { Category } from './category';

export interface Identifier {
  id: number;
  label: string;
  category: Category;
}
