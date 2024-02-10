import { Identifier } from './identifier';

export interface Category {
  categoryID: number;
  categoryLabel: string;
  userID: string;
  identifier: Identifier[];
}
