import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  trigger,
  style,
  state,
  animate,
  transition,
} from '@angular/animations';

@Component({
  selector: 'app-home',
  standalone: true,
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
  imports: [CommonModule],
  animations: [
    trigger('showFirst', [
      transition(':enter', [
        style({ opacity: 0 }),
        animate('1s', style({ opacity: 1 })),
      ]),
    ]),
    trigger('showSecond', [
      transition(':enter', [
        style({ opacity: 0 }),
        animate('4s', style({ opacity: 1 })),
      ]),
    ]),
    trigger('showThird', [
      transition(':enter', [
        style({ opacity: 0 }),
        animate('7s', style({ opacity: 1 })),
      ]),
    ]),
  ],
})
export class HomeComponent {}
