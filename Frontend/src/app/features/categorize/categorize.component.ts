import { Component, OnInit, inject } from '@angular/core';
import { CategorizeService } from '../../core/services/categorize.service';
import { Transaktion } from 'src/app/core/model/transaktion';
import { HttpErrorResponse } from '@angular/common/http';
import { Category } from '../../core/enum/category';
import { FormsModule } from '@angular/forms';
import { TransaktionDTO } from 'src/app/core/model/transaktion-dto';

@Component({
  selector: 'app-categorize',
  templateUrl: './categorize.component.html',
  styleUrls: ['./categorize.component.scss'],
  standalone: true,
  imports: [FormsModule],
})
export class CategorizeComponent implements OnInit {
  private categorizeService = inject(CategorizeService);
  transaktions?: Transaktion[] = undefined;
  categories: Category[] = Object.values(Category);
  transaktionDTO: TransaktionDTO[] = [];

  constructor() {}

  ngOnInit(): void {
    this.categorizeService.getList().subscribe({
      next: (v) => {
        this.transaktions = v;
        console.log(
          'get request was succesfull and transaktions are filled with length of: ' +
            this.transaktions?.length,
        );
        console.log(this.transaktions);
      },
      error: (error: HttpErrorResponse) => {
        console.log(`error: ${error.message}`);
      },
      complete: () => console.log('complete'),
    });
  }

  onClick() {
    if (this.transaktions) {
      this.transaktions.forEach((transaktion) => {
        if (transaktion.category.toString() !== 'LEER') {
          this.transaktionDTO.push({
            id: transaktion.id,
            category: transaktion.category.toUpperCase(),
          });
        }
      });

      if (this.transaktionDTO.length > 0) {
        console.log('sending');
        this.categorizeService.saveList(this.transaktionDTO).subscribe({
          next: (v) => console.log(v),
          error: (error: HttpErrorResponse) =>
            console.log(`Error message: ${error.message}`),
        });
        this.transaktionDTO.length = 0;
      }
    }
    this.ngOnInit();
  }
}
