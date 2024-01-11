import { ComponentFixture, TestBed } from '@angular/core/testing';

import { IdentifierChipComponent } from './identifier-chip.component';

describe('IdentifierChipComponent', () => {
  let component: IdentifierChipComponent;
  let fixture: ComponentFixture<IdentifierChipComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [IdentifierChipComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(IdentifierChipComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
