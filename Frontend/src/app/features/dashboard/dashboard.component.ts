import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TestComponent } from './test.component';
import { FormsModule } from '@angular/forms';
import { SimpleChanges } from '@angular/core';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, TestComponent, FormsModule],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss',
})
export class DashboardComponent {
  message: string = '';
  changeValue() {
    this.message = this.message + 'valuechanged' + 1;
  }
}
