import { Identifier } from './identifier';

export interface Category {
  categoryID: number | null;
  categoryLabel?: string;
  userID: string | null;
  identifier: Identifier[];
}
