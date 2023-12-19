import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TestComponent } from './test.component';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, TestComponent],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss',
})
export class LoginComponent {}
