import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ShowTransaktionsComponent } from './show-transaktions.component';

describe('ShowTransaktionsComponent', () => {
  let component: ShowTransaktionsComponent;
  let fixture: ComponentFixture<ShowTransaktionsComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ShowTransaktionsComponent]
    });
    fixture = TestBed.createComponent(ShowTransaktionsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
