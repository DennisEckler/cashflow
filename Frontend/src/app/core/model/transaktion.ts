import { Category } from '../enum/category';

export class Transaktion {
  id: number;
  date: Date;
  agent: string;
  bookingText: string;
  purpose: string;
  amount: number;
  category: Category;

  constructor(
    id: number,
    date: Date,
    agent: string,
    bookingText: string,
    purpose: string,
    amount: number,
    category: Category
  ) {
    this.id = id;
    this.date = date;
    this.agent = agent;
    this.bookingText = bookingText;
    this.purpose = purpose;
    this.amount = amount;
    this.category = category;
  }
}
