import { Identifier } from './identifier';

export interface Category {
  id: number;
  label: string;
  userID: string;
  identifier: Identifier[];
}
