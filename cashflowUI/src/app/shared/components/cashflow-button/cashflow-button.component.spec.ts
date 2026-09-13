import { ComponentFixture, TestBed } from '@angular/core/testing';
import { beforeEach, describe, expect, it } from 'vitest';
import { CashflowButtonComponent } from './cashflow-button.component';

describe('CashflowButtonComponent', () => {
  let component: CashflowButtonComponent;
  let fixture: ComponentFixture<CashflowButtonComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CashflowButtonComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(CashflowButtonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
