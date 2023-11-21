import { Component, OnInit } from '@angular/core';
import { Transaktion } from 'src/app/shared/model/transaktion';
import { ShowTransaktionsService } from './show-transaktions.service';
import { Category } from 'src/app/shared/enum/category';

@Component({
  selector: 'app-show-transaktions',
  templateUrl: './show-transaktions.component.html',
  styleUrls: ['./show-transaktions.component.scss'],
})
export class ShowTransaktionsComponent implements OnInit {
  transaktions: Transaktion[] = new Array();
  showTransaktionsService: ShowTransaktionsService;

  constructor(showTransaktionsService: ShowTransaktionsService) {
    this.showTransaktionsService = showTransaktionsService;
  }

  ngOnInit() {
    this.transaktions.push({
      id: 1,
      valutaDate: new Date(20101010),
      agent: 'agent1',
      purpose: 'purpose1',
      bookingText: 'bookin1',
      amount: 100,
      category: Category.GEZ,
    });
  }
}
