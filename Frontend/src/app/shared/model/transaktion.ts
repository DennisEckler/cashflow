import { Category } from '../enum/category';

export class Transaktion {
  id: number;
  valutaDate: Date;
  agent: string;
  bookingText: string;
  purpose: string;
  amount: number;
  category: Category;

  constructor(
    id: number,
    valutaDate: Date,
    agent: string,
    bookingText: string,
    purpose: string,
    amount: number,
    category: Category,
  ) {
    this.id = id;
    this.valutaDate = valutaDate;
    this.agent = agent;
    this.bookingText = bookingText;
    this.purpose = purpose;
    this.amount = amount;
    this.category = category;
  }
}
