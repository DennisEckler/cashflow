import { TestBed } from '@angular/core/testing';

import { ShowTransaktionsService } from './show-transaktions.service';

describe('ShowTransaktionsService', () => {
  let service: ShowTransaktionsService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ShowTransaktionsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
