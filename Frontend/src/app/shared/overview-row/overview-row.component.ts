import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Input } from '@angular/core';
import { OverviewRow } from 'src/app/core/model/overviewRow';

@Component({
  selector: 'app-overview-row',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './overview-row.component.html',
  styleUrl: './overview-row.component.scss',
})
export class OverviewRowComponent {
  @Input() overviewRows!: OverviewRow[];
}
