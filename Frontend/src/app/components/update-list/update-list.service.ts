import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Category } from 'src/app/shared/enum/category';
import { Transaktion } from 'src/app/shared/model/transaktion';

@Injectable({
  providedIn: 'root',
})
export class UpdateListService {
  url: string = 'http://localhost:8080/update-list';

  constructor(private http: HttpClient) {}

  getList(): Observable<any> {
    return this.http.get<Transaktion>(this.url);
  }

  getFake(): Transaktion[] {
    return [
      {
        id: 1,
        valutaDate: new Date(20101010),
        agent: 'agent1',
        purpose: 'purpose1',
        bookingText: 'bookin1',
        amount: 100,
        category: Category.GEZ,
      },
      {
        id: 2,
        valutaDate: new Date(20101010),
        agent: 'agent1',
        purpose: 'purpose1',
        bookingText: 'bookin1',
        amount: 100,
        category: Category.GEZ,
      },
      {
        id: 3,
        valutaDate: new Date(20101010),
        agent: 'agent1',
        purpose: 'purpose1',
        bookingText: 'bookin1',
        amount: 100,
        category: Category.GEZ,
      },
      {
        id: 4,
        valutaDate: new Date(20101010),
        agent: 'agent1',
        purpose: 'purpose1',
        bookingText: 'bookin1',
        amount: 100,
        category: Category.GEZ,
      },
      {
        id: 5,
        valutaDate: new Date(20101010),
        agent: 'agent1',
        purpose: 'purpose1',
        bookingText: 'bookin1',
        amount: 100,
        category: Category.GEZ,
      },
      {
        id: 6,
        valutaDate: new Date(20101010),
        agent: 'agent1',
        purpose: 'purpose1',
        bookingText: 'bookin1',
        amount: 100,
        category: Category.GEZ,
      },
      {
        id: 7,
        valutaDate: new Date(20101010),
        agent: 'agent1',
        purpose: 'purpose1',
        bookingText: 'bookin1',
        amount: 100,
        category: Category.GEZ,
      },
      {
        id: 8,
        valutaDate: new Date(20101010),
        agent: 'agent1',
        purpose: 'purpose1',
        bookingText: 'bookin1',
        amount: 100,
        category: Category.GEZ,
      },
      {
        id: 9,
        valutaDate: new Date(20101010),
        agent: 'agent1',
        purpose: 'purpose1',
        bookingText: 'bookin1',
        amount: 100,
        category: Category.GEZ,
      },
      {
        id: 10,
        valutaDate: new Date(20101010),
        agent: 'agent1',
        purpose: 'purpose1',
        bookingText: 'bookin1',
        amount: 100,
        category: Category.GEZ,
      },
      {
        id: 11,
        valutaDate: new Date(20101010),
        agent: 'agent1',
        purpose: 'purpose1',
        bookingText: 'bookin1',
        amount: 100,
        category: Category.GEZ,
      },
    ];
  }
}
