import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OverviewRowComponent } from './overview-row.component';

describe('OverviewRowComponent', () => {
  let component: OverviewRowComponent;
  let fixture: ComponentFixture<OverviewRowComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [OverviewRowComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(OverviewRowComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
