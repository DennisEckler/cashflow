import { Component, OnInit } from '@angular/core';
import { UpdateListService } from './update-list.service';
import { Transaktion } from 'src/app/shared/model/transaktion';

@Component({
  selector: 'app-update-list',
  templateUrl: './update-list.component.html',
  styleUrls: ['./update-list.component.scss'],
})
export class UpdateListComponent implements OnInit {
  transaktions?: Transaktion[] = undefined;

  ngOnInit(): void {}
  constructor(private updateListService: UpdateListService) {}

  onClick() {
    this.updateListService.getList().subscribe({
      next: (v) => {
        this.transaktions = v;
      },
      error: (e) => {
        this.transaktions = this.updateListService.getFake();
        console.log(`error: ${e}`);
        console.log(this.transaktions);
      },
      complete: () => console.log('complete'),
    });
  }
}
