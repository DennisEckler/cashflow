import { Component, OnInit } from '@angular/core';
import { Transaktion } from 'src/app/shared/model/transaktion';
import { ShowTransaktionsService } from './show-transaktions.service';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
    selector: 'app-show-transaktions',
    templateUrl: './show-transaktions.component.html',
    styleUrls: ['./show-transaktions.component.scss'],
    standalone: true,
})
export class ShowTransaktionsComponent implements OnInit {
  transaktions: Transaktion[] = new Array();
  showTransaktionsService: ShowTransaktionsService;

  constructor(showTransaktionsService: ShowTransaktionsService) {
    this.showTransaktionsService = showTransaktionsService;
  }

  ngOnInit() {
    this.showTransaktionsService.getTransaktions().subscribe({
      next: (v) => (this.transaktions = v),
      error: (error: HttpErrorResponse) => console.log(error.message),
      complete: () => console.log('load transaktions complete'),
    });
  }
}
