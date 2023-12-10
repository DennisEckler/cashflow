import { TestBed } from '@angular/core/testing';

import { OverviewServiceService } from './overview-service.service';

describe('OverviewServiceService', () => {
  let service: OverviewServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(OverviewServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
