import { Component, OnInit } from '@angular/core';
import { UpdateListService } from './update-list.service';
import { Transaktion } from 'src/app/shared/model/transaktion';

@Component({
  selector: 'app-update-list',
  templateUrl: './update-list.component.html',
  styleUrls: ['./update-list.component.scss'],
})
export class UpdateListComponent implements OnInit {
  transaktion?: Transaktion = undefined;

  ngOnInit(): void {}
  constructor(private updateListService: UpdateListService) {}

  onClick() {
    this.updateListService.getList().subscribe({
      next: (v) => {
        this.transaktion = v as Transaktion;
        console.log(`next: ${v}`);
      },
      error: (e) => console.log(`error: ${e}`),
      complete: () => console.log('complete'),
    });
  }
}
