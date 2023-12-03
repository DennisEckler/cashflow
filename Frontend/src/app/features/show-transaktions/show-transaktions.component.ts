import { Component, OnInit, inject } from '@angular/core';
import { Transaktion } from 'src/app/core/model/transaktion';
import { ShowTransaktionsService } from '../../core/services/show-transaktions.service';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-show-transaktions',
  templateUrl: './show-transaktions.component.html',
  styleUrls: ['./show-transaktions.component.scss'],
  standalone: true,
})
export class ShowTransaktionsComponent implements OnInit {
  transaktions: Transaktion[] = new Array();
  private showTransaktionsService = inject(ShowTransaktionsService);

  constructor() {}

  ngOnInit() {
    this.showTransaktionsService.getTransaktions().subscribe({
      next: (v) => (this.transaktions = v),
      error: (error: HttpErrorResponse) => console.log(error.message),
      complete: () => console.log('load transaktions complete'),
    });
  }
}
