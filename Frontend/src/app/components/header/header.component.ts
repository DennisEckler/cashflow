import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { NgOptimizedImage } from '@angular/common';

@Component({
    selector: 'app-header',
    templateUrl: './header.component.html',
    styleUrls: ['./header.component.scss'],
    standalone: true,
    imports: [NgOptimizedImage, RouterLink],
})
export class HeaderComponent {
  logo = '../../../assets/Logo_Kostenbuch.png';
}
